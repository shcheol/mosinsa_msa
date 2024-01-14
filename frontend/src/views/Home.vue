<template>
  <div class="home"></div>
  <div class="container">
    <h2>카테고리</h2>

    <div class="card mb-3" style="max-width: 540px;" v-for="product in products" :key="product">
      <div class="row g-0">
        <div class="col-md-4">
          <a @click="productDetails(product.productId)">{{ product.name }}
            <img class="img-fluid rounded-start" src="../assets/logo.png" alt="../assets/logo.png" >
          </a>
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p>가격 : {{ product.price }} 원</p>
          </div>
        </div>
      </div>
    </div>

    <div class="card mb-3" style="max-width: 540px;" v-for="product in products" :key="product">
      <h4 @click="productDetails(product.productId)">{{ product.name }}</h4>
      <p>가격 : {{ product.price }} 원</p>
      <p>잔여 수량 : {{ product.stock }}</p>
      <p>좋아요 : {{ product.likes }}</p>
    </div>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      products: null,
      detail: null
    }
  },
  mounted() {
    apiBoard.getProducts()
        .then((response) => {
          console.log(response.data);
          this.products = response.data.response.content;
        })
        .catch(function (e) {
          console.log(e);
        });


  },
  methods: {
    productDetails(id) {
      apiBoard.getProductDetails(id)
          .then((response) => {
            console.log(response.data);
            this.detail = response.data.response;
          })
          .catch(function (e) {
            console.log(e);
          });
    }
  }
}
</script>

<style scoped>

body {
  margin: 0
}

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