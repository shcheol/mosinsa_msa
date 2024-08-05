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

    <div class="page-info">
      <div class="d-flex justify-content-center">
        <ul class="pagination">
          <li :class="first? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getPromotions(currentPage -1)">Prev</p>
          </li>
          <li v-for="(cur, idx) in pageArr" :key="cur" class='page-item'>
            <p :class="currentPage === idx? 'page-link active':'page-link'" @click="showProjects(idx)">{{ idx + 1 }}</p>
          </li>
          <li :class="last? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getPromotions(currentPage + 1)">Next</p>
          </li>
        </ul>
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
      detail: null,
      currentPage: 0,
      pageArr: null,
      first: null,
      last: null,
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
          this.products = response.data.content;
          this.pageArr = new Array(response.data.totalPages);
          this.first = response.data.first;
          this.last = response.data.last;
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
              this.products = response.data.content;
            })
            .catch(function (e) {
              console.log(e);
            });
      } else {

        this.categoryId = id;
        apiBoard.getProductsInCategory(id)
            .then((response) => {
              console.log(response.data);
              this.products = response.data.content;
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