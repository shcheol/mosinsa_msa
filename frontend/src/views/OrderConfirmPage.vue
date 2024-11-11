<template>
  <div class="container">
    <h4>주문정보</h4>
    <br/>
    <h5>주문 상품 {{ myOrderProducts.length }}개</h5>
    <div v-for="(op) in myOrderProducts" :key="op">

      <div>
        <div>{{ op.name }}</div>
        <div v-for="option in op.options" :key="option"
             style="display: inline;padding-right: 10px; color: #888888; font-size: small">
          {{ option.name }}
        </div>
        <div style="display: inline;padding-right: 10px; color: #888888; font-size: small">/{{ op.stock }}개</div>
        <div>
          <div v-if="!op.coupon">
            <p>{{ op.totalPrice }} 원</p>
          </div>
          <div v-else>
            <p style="font-size: small;color: #888888; text-decoration: line-through; padding-bottom: 1px">
              {{ op.perPrice }}원</p>
            <p style="display: inline; color: red">{{ op.totalPrice }}원</p>
          </div>
        </div>
        <button class="btn btn-light btn-outline-dark btn-sm" @click="myCoupons(op)">쿠폰사용</button>
      </div>
    </div>

    <br/>
    <div>
      <p>총 결제 금액: {{ totalPrice }}</p>
    </div>
    <br/>


    <h5>배송지</h5>
    <div>
      <ul>
        <li>
          <div>이름</div>
          <input type="text" placeholder="받는분의 이름을 작성하세요." v-model="shippingInfo.receiver.name">
        </li>
        <li>
          <div>휴대폰번호</div>
          <input type="text" placeholder="010-xxxx-xxxx" v-model="shippingInfo.receiver.phoneNumber">
        </li>
        <li>
          <div>주소</div>
          <input placeholder="시/도" v-model="shippingInfo.address.address1">
          <input placeholder="구" v-model="shippingInfo.address.address2">
          <input placeholder="상세주소" v-model="shippingInfo.address.zipCode">
        </li>
        <li>
          <div>요청사항</div>
          <input type="text" placeholder="메시지를 작성하세요." v-model="shippingInfo.message"/>
        </li>
      </ul>
    </div>

    <br/>
    <div>
      <button style="float: right" class="btn btn-dark" @click="orderConfirm(myOrderProducts)">주문하기</button>
    </div>
  </div>
  <div class="black-bg" v-if="modalState" @click="closeModal()">
    <div class="white-bg" @click="(event) => {event.stopPropagation()}">
      <h5>쿠폰목록</h5>
      <div v-if="coupons.length > 0">
        <div v-for="(coupon) in coupons" :key="coupon">
          <p style="display: inline;padding-right: 10px">{{ coupon.details.discountPolicy }}</p>
          <button class="btn btn-light btn-outline-dark btn-sm"
                  @click="useCoupon(coupon.couponId, coupon.details.discountPolicy)">적용
          </button>
        </div>
      </div>
      <div v-else>사용가능한 쿠폰이 없습니다</div>
    </div>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      modalState: false,
      couponId: null,
      shippingInfo: {
        "message": "",
        "address": {
          "zipCode": "zipcode",
          "address1": "address1",
          "address2": "address2"
        },
        "receiver": {
          "name": "test",
          "phoneNumber": "010-1111-1111"
        }
      },
      myOrderProducts: JSON.parse(history.state.orderProduct),
      coupons: [],
      totalPrice: 0,
      temp: null,
    }
  },
  beforeCreate() {
    if (localStorage.getItem("customer-info") == null) {
      alert('login이 필요합니다.')
      this.$router.push({name: "login"})
    }
  },
  mounted() {
    console.log(this.myOrderProducts)
    this.calculateTotalPrice();
  },
  methods: {
    closeModal() {
      this.modalState = false;
    },
    calculateTotalPrice() {
      this.totalPrice = 0;
      this.myOrderProducts.forEach(op => this.totalPrice += op.totalPrice);
    },
    orderConfirm(myOrderProducts) {
      this.modalState = false;
      apiBoard.postOrders(myOrderProducts, this.shippingInfo)
          .then((response) => {
            console.log(response);
            this.$router.push({
              name: 'orderDetails',
              params: {id: response.data.orderId}
            })
          }).catch(function (e) {
        console.log(e);
        alert(e.response.data.message);
      });
    },
    myCoupons(op) {
      this.modalState = true;
      this.temp = op;
      console.log(this.temp);
      apiBoard.getCoupons()
          .then((response) => {
            this.coupons = response.data.filter(c => c.state === "ISSUED")
                .filter(c => {
                  let current = new Date();
                  let duringDate = new Date(c.details.duringDate);
                  return current <= duringDate;
                }).map(c => c);
          });
    },
    useCoupon(id, discount) {
      this.modalState = false;
      let price = 0;
      if (discount === 'WON_10000') {
        price = 10000;
      }
      if (discount === 'WON_3000') {
        price = 3000;
      }
      if (discount === 'WON_1000') {
        price = 1000;
      }
      if (discount === 'PERCENT_10') {
        price = this.temp.perPrice * 0.1;
      }
      if (discount === 'PERCENT_20') {
        price = this.temp.perPrice * 0.2;
      }
      this.temp.coupon = {
        id: id,
      }
      this.temp.totalPrice -= price;
      this.calculateTotalPrice();
    }
  }
}
</script>

<style scoped>
div {
  box-sizing: border-box;
}

.black-bg {
  /*display: none; !* Hidden by default *!*/
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0, 0, 0); /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
  max-width: 100%;
  max-height: 100%;
}

.white-bg {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  border-radius: 8px;
  width: 80%; /* Could be more or less, depending on screen size */
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
  padding: 3px;
}
</style>