<template>
  <div class="container">
    <h5>{{ categoryMenu.name }}</h5>
    <div>
      <ul>
        <li v-for="category in categoryMenu.childCategories" :key="category"
            @click="selectCategory(category.categoryId)" style="display: inline-block">
          <div style="padding-right: 10px; padding-left: 10px;"> {{ category.name }}</div>
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
      categoryId: this.category,
      categoryMenu: {
        "categoryId": null,
        "name": null,
        "childCategories": [],
      },
    }
  },
  props: ['category'],
  watch: {
    category() {
      this.categoryId = this.category;
      this.getCategories(this.categoryId);
    }
  },
  mounted() {
    this.getCategories(this.categoryId);
  },
  methods: {
    getCategories(id) {
      apiBoard.getCategories(id)
          .then((response) => {
            console.log(response);
            this.categoryMenu = response.data;
          });
    },
    selectCategory(id) {
      this.$emit('update', id);
    }
  }
}
</script>
