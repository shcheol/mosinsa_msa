<template>
  <div class="home">

    <Promotions />
    <SimpleCategories/>
    <TimeSaleProducts/>
  </div>
</template>

<script>
import Promotions from "@/views/Promotions";
import SimpleCategories from "@/views/SimpleCategories";
import TimeSaleProducts from "@/views/TimeSaleProducts";
import Products from "@/views/Products";

export default {
  components: {TimeSaleProducts, Products, SimpleCategories, Promotions},
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