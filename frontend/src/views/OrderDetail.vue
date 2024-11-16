<template>
  <div class="container">
    <h4>주문완료</h4>

    <table class="table">
      <tbody>
      <tr>
        <td>주문번호</td>
        <td v-if="order!=null">{{ order.id }}</td>
      </tr>
      <tr>
        <td>총 금액</td>
        <td v-if="order!=null">{{ order.totalPrice }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="order!=null">{{ order.status }}</td>
      </tr>
      </tbody>
    </table>
    <h5>주문상품</h5>
    <div v-if="order!=null">
      <div v-for="(op) in order.orderProducts" :key="op">
        <div>
          <div>{{ op.name }}</div>
          <div v-for="option in op.productOptions" :key="option"
               style="display: inline;padding-right: 10px; color: #888888; font-size: small">
            {{ option.name }}
          </div>
          <div style="display: inline;padding-right: 10px; color: #888888; font-size: small">/{{ op.quantity }}개</div>
          <div>
            <div v-if="op.orderCoupon.length===0">
              <p>{{ op.amounts }} 원</p>
            </div>
            <div v-else>
              <p style="font-size: small;color: #888888; text-decoration: line-through; padding-bottom: 1px">
                {{ op.price }}원</p>
              <p style="display: inline; color: red">{{ op.amounts }}원</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-light btn-outline-dark btn-sm" @click="cancelOrder(order.id)">주문취소</button>
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
          this.order = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    cancelOrder() {
      apiBoard.cancelOrders(this.order.id).then((response) => {
        console.log(response);
        if (response.status === 200){
          alert("주문이 취소되었습니다.")
        } else{
          alert("주문 취소 실패1")
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
