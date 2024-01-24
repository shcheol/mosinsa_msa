<template>
  <div class="home"></div>
  <div class="container">
    <h2>카테고리</h2>
    <div>
      <ul>
        <li class="category" v-for="category in categories" :key="category"
            @click="setCategoryId(category.categoryId)">
          <div v-if="category.categoryId===-1">{{ category.name }}</div>
          <div v-else>|{{ category.name }}</div>
        </li>
      </ul>

    </div>
    <ul v-for="(product, i) in products" :key="product">
          <li class="item">
            <p @click="productDetails(i)">{{ product.name }}</p>
            <p>가격 : {{ product.price }} 원</p>
          </li>
    </ul>
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


div {
  box-sizing: border-box;
}

.item {
  /* 테투리 css */
  border: 4px dotted #009688;
  border-radius: 20px;

  /* 기본 css */
  display: inline-block;
  text-align: center;
  padding: 20px 80px;
  /*color: white;*/
  background-color: white;
}

.category {
  display: inline-block;
}

</style>