<template>
  <div class="container">
    <h2>좋아요 목록</h2>
    <div v-if="products === null || products.length <= 0">
      찜한 상품이 없습니다.
    </div>
    <div v-else v-for="product in products" :key="product">
      <table class="table">
        <tbody>
        <tr>
          <td>상품명</td>
          <td v-if="product!=null">{{ product.name }}</td>
          <button @click="likes(product.productId)">좋아요</button>
        </tr>
        <tr>
          <td>가격</td>
          <td v-if="product!=null">{{ product.price }}</td>
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
      products: null
    }
  },
  mounted() {
    this.showMyProducts();
  },
  methods: {
    showMyProducts() {
      apiBoard.getLikesProducts()
          .then((response) => {
            console.log(response);
            this.products = response.data.content;
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    likes(productId) {
      apiBoard.postReaction(productId)
          .then((response) => {
            console.log(response);
            this.showMyProducts();
          })
          .catch(function (e) {
            console.log(e);
          });
    }
  }

}
</script>
