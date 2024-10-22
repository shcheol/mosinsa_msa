<template>
  <div class="home">

    <Promotions />
    <Categories @update="selectedCategory"/>
    <Items :category="category"/>

  </div>
</template>

<script>
import Promotions from "@/views/Promotions";
import Categories from "@/views/Categories";
import Products from "@/views/Products";

export default {
  components: {Items: Products, Categories, Promotions},
  data() {
    return {
      temp: [],
      category: null,
    }
  },
  mounted() {

  },
  methods: {
    selectedCategory(id) {
      console.log('수신한 메시지:', id);
      this.category = id;
    },
  }
}
</script>

<style scoped>

</style>