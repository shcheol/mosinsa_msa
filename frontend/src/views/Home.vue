<template>
  <div class="home"></div>
  <div class="container">
    <h2>카테고리</h2>
    <div>
      <ul>
        <li v-for="category in categories" :key="category"
            @click="setCategoryId(category.categoryId)" style="display: inline-block">
          <div v-if="category.categoryId===-1">{{ category.name }}</div>
          <div v-else> | {{ category.name }}</div>
        </li>
      </ul>

    </div>
    <div class="main__container">
      <div class="item" v-for="(product, i) in products" :key="product">
          <p @click="productDetails(i)">{{ product.name }}</p>
          <p>가격 : {{ product.price }} 원</p>
      </div>
    </div>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      categories: [{
        categoryId: -1,
        name: '전체'
      }],
      temp: [],
      categoryId: null,
      products: null,
      detail: null
    }
  },
  mounted() {
    apiBoard.getCategories()
        .then((response) => {
          this.temp = response.data;
          Array.prototype.push.apply(this.categories, this.temp)
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
      if (id === -1) {
        apiBoard.getProducts()
            .then((response) => {
              console.log(response.data);
              this.products = response.data.response.content;
            })
            .catch(function (e) {
              console.log(e);
            });
      } else {

        this.categoryId = id;
        apiBoard.getProductsInCategory(id)
            .then((response) => {
              console.log(response.data);
              this.products = response.data.response.content;
            })
            .catch(function (e) {
              console.log(e);
            });
      }
    }
  }
}
</script>

<style scoped>

</style>