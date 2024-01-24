<template>
  <div class="home"></div>
  <div class="container">
    <h2>카테고리</h2>
    <div>
      <a>전체</a>
      <div v-for="category in categories" :key="category">
        <a @click="setCategoryId(category.categoryId)">{{ category.name }}</a>
      </div>
    </div>
    <div class="card mb-3" style="max-width: 540px;" v-for="(product, i) in products" :key="product">
      <div class="row g-0">
        <div class="col-md-4">
          <a @click="productDetails(i)">{{ product.name }}
            <!--            <img class="img-fluid rounded-start" src="../assets/logo.png" alt="../assets/logo.png" >-->
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
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      categories: [],
      categoryId: null,
      products: null,
      detail: null
    }
  },
  mounted() {
    apiBoard.getCategories()
        .then((response) => {
          console.log(response.data);
          this.categories = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
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
    productDetails(i) {
      this.$router.push({
        name: 'productDetails',
        params: {id: this.products[i].productId}
      })
    },
    setCategoryId(id) {
      this.categoryId = id;
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