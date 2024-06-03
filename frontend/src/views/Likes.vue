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
        <td>상품번호</td>
        <td v-if="product!=null">{{product.productId}}</td>
      </tr>
      <tr>
        <td>상품명</td>
        <td v-if="product!=null">{{product.name}}</td>
      </tr>
      <tr>
        <td>수량</td>
        <td v-if="product!=null">{{product.stock}}</td>
      </tr>
      <tr>
        <td>가격</td>
        <td v-if="product!=null">{{product.price}}</td>
      </tr>
      <tr>
        <td>좋아요</td>
        <td v-if="product!=null">{{product.likes}}</td>
        <button @click="likes(product.productId)">좋아요</button>
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
  data(){
    return {
      products: null
    }
  },
  mounted() {
    apiBoard.getLikesProducts(localStorage.getItem("customerId"))
        .then((response) => {
          console.log(response);
          this.products = response.data.response;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    likes(productId){
      apiBoard.postLikesProduct(localStorage.getItem("customerId"), productId)
          .then((response) => {
            console.log(response);
            apiBoard.getLikesProducts(localStorage.getItem("customerId"))
                .then((response) => {
                  console.log(response);
                  this.products = response.data.response;
                })
                .catch(function (e) {
                  console.log(e);
                });
          })
          .catch(function (e) {
            console.log(e);
          });
    }
}

}
</script>
