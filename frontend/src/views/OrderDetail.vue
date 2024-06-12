<template>
  <div class="container">
    <h2>주문 상세</h2>

    <table class="table">
      <tbody>
      <tr>
        <td>주문번호</td>
        <td v-if="order!=null">{{ order.orderId }}</td>
      </tr>
      <tr>
        <td>쿠폰</td>
        <td v-if="order!=null && order.couponId!=null">{{ order.couponId }}</td>
      </tr>
      <tr>
        <td>결제 금액</td>
        <td v-if="order!=null">{{ order.totalPrice }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="order!=null">{{ order.status }}</td>
      </tr>
      </tbody>
    </table>
    <h3>상품 목록</h3>
    <div v-if="order!=null">
      <table class="table">
          <tbody v-for="op in order.orderProducts" :key="op">
          <tr>
            <td>상품아이디</td>
            <td>{{ op.productId }}</td>
          </tr>
          <tr>
            <td>가격</td>
            <td>{{ op.price }}</td>
          </tr>
          <tr>
            <td>수량</td>
            <td>{{ op.quantity }}</td>
          </tr>
          <tr>
            <td>합계</td>
            <td>{{ op.amounts }}</td>
          </tr>
          </tbody>
      </table>
    </div>
    <button class="btn btn-dark" @click="cancelOrder(order.orderId)">주문취소</button>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      order: null,
    }
  },
  mounted() {
    apiBoard.getOrderDetails(this.$route.params.id)
        .then((response) => {
          console.log(response);
          this.order = response.data.response;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    cancelOrder() {
      apiBoard.cancelOrders(localStorage.getItem("customerId"), this.order.orderId).then((response) => {
        console.log(response);
        if (response.status === 200){
          alert("주문이 취소되었습니다.")
        } else{
          alert("주문 취소 실패")
        }
      })
          .catch(function (e) {
            alert("주문 취소 실패")
            console.log(e);
          });
    }
  }

}
</script>
