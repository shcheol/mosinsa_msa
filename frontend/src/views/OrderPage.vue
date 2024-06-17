<template>
  <div class="container">
    <h2>주문서 확인</h2>
    <br/>
    <div v-if="orderConfirm!=null">
      <h3>주문 상품</h3>
      <div v-for="(op) in orderConfirm.orderProducts" :key="op">
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
        <p v-if="orderConfirm.couponId!=null"> {{ orderConfirm.couponId }}</p>
      </div>
      <br/>

      <h3>배송정보</h3>
      <div>
        <ul>
          <li>
            <p>이름: {{ orderConfirm.shippingInfo.receiver.name }}</p>
          </li>
          <li>
            <p>연락처: {{ orderConfirm.shippingInfo.receiver.phoneNumber }}</p>
          </li>
          <li>
            <p>주소: {{ orderConfirm.shippingInfo.address.address1 }} {{ orderConfirm.shippingInfo.address.address2 }}
              {{ orderConfirm.shippingInfo.address.zipCode }}</p>
          </li>
          <li>
            <p>비고: {{ orderConfirm.shippingInfo.message }}</p>
          </li>
        </ul>
      </div>
    </div>
    <br/>
    <div>
      <button style="float: right" class="btn btn-dark" @click="orders()">주문하기</button>
      <button style="float: right" class="btn btn-dark" @click="orderFix()">주문서수정</button>
    </div>
  </div>

</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      orderConfirm: null
    }
  },
  beforeCreate() {
    if (localStorage.getItem("customer-info") == null) {
      alert('login이 필요합니다.')
      this.$router.push({name: "login"})
    }
  },
  mounted() {
    this.orderConfirm = history.state.orderConfirmed;
  },
  methods: {
    orders() {
      apiBoard.postOrders(this.orderConfirm)
          .then((response) => {
            console.log(response);
            this.$router.push({
              name: 'orderDetails',
              params: {id: response.data.response.orderId}
            })
          }).catch(function (e) {
        console.log(e);
      });
    },
    orderFix() {
      this.$router.go(-1);
    }

  }
}
</script>

<style scoped>
div {
  box-sizing: border-box;
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
}
</style>