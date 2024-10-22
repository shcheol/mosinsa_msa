<template>
  <div class="home">

    <Promotions />
    <SimpleCategories/>
    <h5>타임세일</h5>
    <h5>인기순</h5>
    <Products :category="category"/>

  </div>
</template>

<script>
import Promotions from "@/views/Promotions";
import SimpleCategories from "@/views/SimpleCategories";
import Products from "@/views/Products";

export default {
  components: {Products, SimpleCategories, Promotions},
  data() {
    return {
      temp: [],
      category: null,
    }
  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped>

</style>