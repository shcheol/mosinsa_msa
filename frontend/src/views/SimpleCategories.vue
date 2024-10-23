<template>
  <div class="container">
    <h5>카테고리</h5>
    <div>
      <ul>
        <li v-for="category in categories" :key="category"
            @click="selectCategory(category.categoryId)" style="display: inline-block">
          <div style="padding-right: 10px"> {{ category.name }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import apiBoard from "@/api/board";

export default {
  data() {
    return {
      temp: [],
      categories: [],
    }
  },
  mounted() {
    this.getCategories();
  },
  methods: {
    getCategories() {
      apiBoard.getCategories()
          .then((response) => {
            console.log(response.data);
            this.temp = response.data;
            Array.prototype.push.apply(this.categories, this.temp)
          });
    },
    selectCategory(id) {
      this.$router.push({
        name: 'categoryProducts',
        params: {id: id}
      })
    }
  }
}
</script>
