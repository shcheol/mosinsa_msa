<template>
  <div class="container">
    <div class="main__container">
      <div class="item" v-for="(product, i) in products" :key="product">
        <p @click="productDetails(i)">{{ product.name }}</p>
        <p>{{ product.brand.name }}</p>
        <p>{{ product.price }} 원</p>
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
      selectedId: this.selectId,
    }
  },
  props: ['category', 'selectId'],
  watch: {
    category() {
      this.categoryId = this.category;
      this.selectedId = this.selectId;
      console.log(this.selectedId);
      this.getProducts(this.category);
    },
    selectId() {
      this.categoryId = this.category;
      this.selectedId = this.selectId;
      this.getProducts(this.selectedId);
    }
  },
  mounted() {
    this.getProducts(this.selectedId);
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
            console.log(this.products)
            this.pageArr = new Array(response.data.totalPages);
            this.first = response.data.first;
            this.last = response.data.last;
          });
    }
  }
}
</script>
