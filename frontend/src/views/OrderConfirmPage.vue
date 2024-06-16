<template>
  <div class="container">
    <h2>주문정보</h2>
    <br/>
    <h3>주문 상품</h3>
    <div v-for="(op) in myOrderProducts" :key="op">
      <table class="table">
        <tbody>
        <tr>
          <td>상품번호</td>
          <td v-if="op!=null">{{ op.productId }}</td>
        </tr>
        <tr>
          <td>가격</td>
          <td v-if="op!=null">{{ op.price }} 원</td>
        </tr>
        <tr>
          <td>수량</td>
          <td v-if="op!=null">{{ op.quantity }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <br/>
    <h3>쿠폰</h3>
    <div>
      <p v-if="couponId!=null"> {{ couponId }}</p>
      <button style="float: right" class="btn btn-dark" @click="myCoupons">쿠폰</button>
    </div>
    <br/>

    <h3>배송정보</h3>
    <div>
      <ul>
        <li>
          <label>이름: </label>
          <input type="text" v-model="shippingInfo.receiver.name">
        </li>
        <li>
          <label>연락처: </label>
          <input type="text" v-model="shippingInfo.receiver.phoneNumber">
        </li>

        <li>
          <label>주소: </label>
          <input v-model="shippingInfo.address.address1">
          <input v-model="shippingInfo.address.address2">
          <input v-model="shippingInfo.address.zipCode">
        </li>
        <li>
          <label>비고: </label>
          <input type="text" placeholder="메시지를 작성하세요." v-model="shippingInfo.message"/>
        </li>
      </ul>
    </div>

    <br/>
    <div>
      <button style="float: right" class="btn btn-dark" @click="orderConfirm(myOrderProducts)">주문하기</button>
    </div>
  </div>
  <div class="black-bg" v-if="modalState">
    <div class="white-bg">
      <h4>쿠폰목록</h4>
      <div v-if="coupons.length > 0">
        <div v-for="(coupon) in coupons" :key="coupon">
          <p>{{ coupon.details.discountPolicy }}</p>
          <button @click="useCoupon(coupon.couponId, coupon.details.discountPolicy, coupon.state)">사용하기</button>
        </div>
      </div>
      <div v-else>사용가능한 쿠폰이 없습니다</div>

      <button @click="modalState=!modalState">닫기</button>
    </div>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      modalState: false,
      productId: null,
      price: null,
      quantity: null,
      couponId: null,
      shippingInfo: {
        "message": "",
        "address": {
          "zipCode": "zipcode",
          "address1": "address1",
          "address2": "address2"
        },
        "receiver": {
          "name": "이름",
          "phoneNumber": "010-1111-1111"
        }
      },
      myOrderProducts: [],
      coupons: []
    }
  },
  beforeCreate() {
    if (localStorage.getItem("customer-info") == null) {
      alert('login이 필요합니다.')
      this.$router.push({name: "login"})
    }
  },
  mounted() {
    this.myOrderProducts = history.state.orderProduct;
    console.log(this.myOrderProducts);
  },
  methods: {
    orderConfirm(myOrderProducts) {
      this.modalState = false;
      apiBoard.postOrderConfirm(myOrderProducts, this.couponId, this.shippingInfo).then((response) => {
        console.log(response);
        this.$router.push({
          name: 'orderPage',
          state: {orderConfirmed: response.data.response}
        })
      }).catch(function (e) {
        console.log(e);
      });
    },
    myCoupons() {
      this.modalState = true;
      apiBoard.getCoupons()
          .then((response) => {
            console.log(response);
            this.coupons = response.data.filter(c => c.state === "ISSUED")
                .filter(c => {
                  let current = new Date();
                  let duringDate = new Date(c.details.duringDate);
                  console.log(duringDate);
                  return current <= duringDate;
                }).map(c => c);
            console.log(this.coupons);
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    useCoupon(id) {
      this.modalState = false;
      this.couponId = id;
    }
  }
}
</script>

<style scoped>
div {
  box-sizing: border-box;
}

.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
  padding: 3px;
}
</style>