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

export default {
  components: {TimeSaleProducts, SimpleCategories, Promotions},
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