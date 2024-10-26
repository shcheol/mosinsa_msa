<template>
  <div class="container">
    <Categories :category="category" :selectId="selectId" @update="selectedCategory" />
    <Products :category="category" :selectId="selectId"/>
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
      selectId: this.$route.params.selectId,
    }
  },
  watch: {
    $route() {
      this.category = this.$route.params.id
      this.selectId = this.$route.params.selectId
    },
  },
  methods: {
    selectedCategory(id, selectId) {
      this.category = id;
      this.selectId = selectId;
      console.log('11')
      console.log(this.category)
      console.log(this.selectId)
    },
  }
}
</script>
