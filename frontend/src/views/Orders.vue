<template>
  <div class="container">
    <h2>주문목록</h2>

    <div v-for="(order) in orders" :key="order">
      <table class="table">
        <tbody>
        <tr>
          <td>주문번호</td>
          <td v-if="order!=null">{{ order.id }}</td>
          <button v-if="order!=null" @click="orderDetails(order.id)">주문상세</button>
        </tr>
        <tr>
          <td>결제금액</td>
          <td v-if="order!=null">{{ order.totalPrice }}</td>
        </tr>
        <tr>
          <td>주문상태</td>
          <td v-if="order!=null">{{ order.status }}</td>
        </tr>
        </tbody>
      </table>

    </div>


    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      orders: null
    }
  },
  mounted() {
    apiBoard.getOrders()
        .then((response) => {
          console.log(response);
          this.orders = response.data.content;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    orderDetails(id) {
      this.$router.push({
        name: 'orderDetails',
        params: {id: id}
      })
    }
  }

}
</script>
