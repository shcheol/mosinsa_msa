<template>
  <div class="container">
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
            <p class='page-link' @click="getProducts(currentPage -1)">Prev</p>
          </li>
          <li v-for="(cur, idx) in pageArr" :key="cur" class='page-item'>
            <p :class="currentPage === idx? 'page-link active':'page-link'" @click="showProjects(idx)">{{ idx + 1 }}</p>
          </li>
          <li :class="last? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getProducts(currentPage + 1)">Next</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import apiBoard from "@/api/board";

export default {
  data() {
    return {
      products: null,
      detail: null,
      currentPage: 0,
      pageArr: null,
      first: null,
      last: null,
      categoryId: this.category,
    }
  },
  props: ['category'],
  watch: {
    category() {
      this.categoryId = this.category;
      this.getProducts(this.categoryId);
    }
  },
  mounted() {
    this.getProducts(this.categoryId);
  },
  methods: {
    productDetails(i) {
      this.$router.push({
        name: 'productDetails',
        params: {id: this.products[i].productId}
      })
    },
    getProducts(categoryId) {
      apiBoard.getProducts(categoryId)
          .then((response) => {
            this.products = response.data.content;
            this.pageArr = new Array(response.data.totalPages);
            this.first = response.data.first;
            this.last = response.data.last;
          });
    }
  }
}
</script>
