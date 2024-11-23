<template>
  <div class="container">
    <div class="main__container">
      <h5>타임세일</h5>

      <div style="display: grid;  grid-template-columns: 1fr 1fr 1fr;">
      <div @click="productDetails(i)" class="item" v-for="(product, i) in products" :key="product" >
        <span>{{ product.name }}</span>
        <p style="color: #888888; font-size: small">{{ product.brand.name }}</p>
          <div v-if="product.sales.discountRate!==0">
            <p style="display: inline; color: red; padding-right: 3px">{{product.sales.discountRate}}%</p>
            <p style="display: inline; color: black">{{product.sales.discountedPrice}}원</p>
          </div>
          <div v-else>
            <p style="color: black">{{ product.price }}원</p>
          </div>
      </div>
    </div>
    </div>

    <div class="page-info">
      <div class="d-flex justify-content-center">
        <ul class="pagination">
          <li :class="first? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getProducts(currentPage -1)">Prev</p>
          </li>
<!--          <li v-for="(cur, idx) in pageArr" :key="cur" class='page-item'>-->
<!--            <p :class="currentPage === idx? 'page-link active':'page-link'" @click="getProducts(idx,this.selectedId)">{{ idx + 1 }}</p>-->
<!--          </li>-->
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
    }
  },
  mounted() {
    this.getProducts(0);
  },
  methods: {
    productDetails(i) {
      this.$router.push({
        name: 'productDetails',
        params: {id: this.products[i].productId}
      })
    },
    getProducts(page) {
      apiBoard.getSaleProducts(page, 'TIME_SALE')
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
