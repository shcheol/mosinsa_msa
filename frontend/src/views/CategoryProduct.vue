<template>
  <div class="container">
    <Categories :category="category" @update="selectedCategory" />
    <Products :category="category"/>
  </div>
</template>

<script>
import Categories from "@/views/Categories";
import Products from "@/views/Products";

export default {
  components: {Products, Categories},
  data() {
    return {
      category: this.$route.params.id,
      // selectedId: null,
    }
  },
  watch: {
    $route(id) {
      console.log(id)
      this.category = this.$route.params.id
    },
  },
  mounted() {
    console.log('daf')
  }  ,
  methods: {
    selectedCategory(id) {
      this.category = id;
      console.log('11')
    },
  }
}
</script>
