<template>
  <div class="container">
    <Categories :selected="category" @update="selectedCategory" />
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
      category: null,
      selectedId: null,
    }
  },
  beforeMount() {
    this.category = this.$route.params.id;
  },
  methods: {
    selectedCategory(id) {
      this.category = id;
    },
  }
}
</script>
