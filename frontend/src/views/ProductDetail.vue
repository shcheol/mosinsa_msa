<template>
  <div class="container">
    <h2>상품 상세</h2>

    <table class="table">
      <tbody>
      <tr>
        <td>상품번호</td>
        <td v-if="product!=null">{{ product.productId }}</td>
      </tr>
      <tr>
        <td>이름</td>
        <td v-if="product!=null">{{ product.name }}</td>
      </tr>
      <tr>
        <td>수량</td>
        <td v-if="product!=null">{{ product.stock }}</td>
      </tr>
      <tr>
        <td>가격</td>
        <td v-if="product!=null">{{ product.price }}</td>
      </tr>
      <tr>
        <td>좋아요</td>
        <td v-if="product!=null">{{ product.likes }}</td>
        <button @click="likes">좋아요</button>
      </tr>
      </tbody>
    </table>

    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      product: null,
    }
  },
  mounted() {
    apiBoard.getProductDetails(this.$route.params.id)
        .then((response) => {
          console.log('response' + response);
          this.product = response.data.response;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    likes() {
    }
  }

}
</script>
