<template>
  <div class="container">
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
    <div>
      <button class="btn btn-primary" @click="myCoupons">쿠폰</button>
      <p v-if="orderCouponInfo!=null"> {{ orderCouponInfo.couponId }}</p>
    </div>
    <div>
      <button class="btn btn-primary" @click="orders(myOrderProducts)">주문하기</button>
    </div>
  </div>
  <div class="black-bg" v-if="modalState">
    <div class="white-bg">
      <h4>쿠폰목록</h4>
      <div v-if="coupons.length > 0">
        <div style="max-width: 540px;" v-for="(coupon) in coupons" :key="coupon">
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
      orderCouponInfo: null,
      myOrderProducts: [],
      coupons: []
    }
  },
  beforeCreate() {
    if (localStorage.getItem("customerId") == null) {
      alert('login이 필요합니다.')
      this.$router.push({name: "login"})
    }
  },
  mounted() {
    this.myOrderProducts = history.state.orderProduct;
  },
  methods: {
    orders(orderProducts) {
      this.modalState = false;
      apiBoard.postOrders(localStorage.getItem("customerId"), orderProducts, this.orderCouponInfo.couponId).then((response) => {
        console.log(response);
        this.$router.push({
          name: 'orderDetails',
          params: {id: response.data.response.orderId}
        })
        this.$state.clear();
      })
          .catch(function (e) {
            console.log(e);
          });
    },
    myCoupons() {
      this.modalState = true;
      apiBoard.getCoupons(localStorage.getItem("customerId"))
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
    useCoupon(id, discountPolicy, state) {
      this.modalState = false;
      this.orderCouponInfo = { couponId: id, discountPolicy: discountPolicy, state: state};
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
</style>