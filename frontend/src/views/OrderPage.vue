<template>
  <div class="container">
    <div class="card mb-3" style="max-width: 540px;" v-for="(op) in myOrderProducts" :key="op">
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
      <button class="btn btn-primary" @click="orders(myOrderProducts)">주문하기</button>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data(){
    return{
      productId: null,
      price:null,
      quantity: null,
      myOrderProducts: []
    }
  },
  beforeCreate() {
    if (localStorage.getItem("customerId")==null){
      alert('login이 필요합니다.')
      this.$router.push({name:"login"})
    }
  },
  mounted() {
    this.myOrderProducts = history.state.orderProduct;
  },
  methods : {
    orders(orderProducts) {
      this.modalState = false;
      apiBoard.postOrders(localStorage.getItem("customerId"), orderProducts).then((response) => {
        console.log(response);
        this.$router.push({
          name: 'orderDetails',
          params: {id: response.data.response.orderId}
        })
      })
          .catch(function (e) {
            console.log(e);
          });
    },
  }
}
</script>

<style scoped>

</style>