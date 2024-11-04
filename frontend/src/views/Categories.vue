<template>
  <div class="container">
    <h5>{{ categoryMenu.name }}</h5>
    <div style="background: #eeeeee">
      <div v-for="category in categoryMenu.childCategories" :key="category"
           @click="selectCategory(categoryId, category.categoryId)"
           style="display: inline-block; background: #eeeeee">
        <button type="button" class="btn btn-outline- text-small "
                :class="[selectedId === category.categoryId? ' text-dark ' : ' text-black-50 ' ]">{{ category.name }}
        </button>
      </div>
      <div v-if="hasSubChildCategories" style="background: #ffffff">
        <div v-for="category in selectedSubChildCategories" :key="category"
             @click="selectCategory(categoryId, category.categoryId)"
             style="display: inline-block; background: lightgray">
          <div>
            <button type="button" class="btn btn-outline- text-small"
                    :class="[selectedId === category.categoryId?'text-dark' : 'text-black-50' ]">{{ category.name }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiBoard from "@/api/board";

export default {
  data() {
    return {
      categoryId: this.category,
      selectedId: this.selectId,
      categoryMenu: {
        "categoryId": null,
        "name": null,
        "childCategories": [],
      },
      selectedSubChildCategories: null,
      hasSubChildCategories: null,
    }
  },
  props: ['category', 'selectId'],
  watch: {
    category() {
      this.categoryId = this.category;
      this.selectedId = this.selectId;
      this.selectedSubChildCategories = null;
      this.getCategories(this.categoryId);
    },
    selectId() {
      this.categoryId = this.category;
      this.selectedId = this.selectId;
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
            this.categoryMenu = response.data;
            this.hasSubChildCategories = this.categoryMenu.childCategories.at(0).childCategories.length > 0;
            if (this.hasSubChildCategories) {
              let find = this.categoryMenu.childCategories.find(cc => cc.categoryId === this.selectId);
              if (find) {
                this.selectedSubChildCategories = find.childCategories;
              }
            }
          });
    },
    selectCategory(id, selectId) {
      this.$emit('update', id, selectId);
    }
  }
}
</script>
