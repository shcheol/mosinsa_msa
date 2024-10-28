<template>
  <sidebar-menu class="sidebar"
      :collapsed="collapsed"
      :menu="menu"
      :show-one-child="true"
      :relative="false"
      :hide-toggle="false"
      :theme="'white-theme'"
      @collapse="onCollapse"
      @itemClick="onItemClick"
  ></sidebar-menu>
</template>

<script>
import apiBoard from "@/api/board";

export default {
  data() {
    return {
      menu: [
        {
          header: false,
          hiddenOnCollapse: false,
          title: "CATEGORY"
        },

      ],
      collapsed: true,
      temp: [],
      categories: [],
    };
  },
  mounted() {
    this.getCategories();
  },
  methods: {
    onItemClick(e, i) {
      console.log(i);
      this.$router.push({
        name: 'categoryProducts',
        params: {id: i.cId, selectId: i.selectId}
      })
    },
    onCollapse(c) {
      this.collapsed = c;
    },
    getCategories() {
      apiBoard.getCategories()
          .then((response) => {
            this.temp = response.data;
            Array.prototype.push.apply(this.categories, this.temp)
            this.categories.forEach(c => {
              const data = {
                title: c.name,
                icon: "fa fa-envelope",
                cId: c.categoryId,
                selectId: c.categoryId,
                child: []
              };
              c.childCategories.forEach(cc => {
                const childData = {
                  title: cc.name,
                  cId: c.categoryId,
                  selectId: cc.categoryId,
                  child: []
                };
                cc.childCategories.forEach(ccc => {
                  childData.child.push({
                    title: ccc.name,
                    cId: c.categoryId,
                    selectId: ccc.categoryId,
                  })
                })
                data.child.push(childData)
              })
              this.menu.push(data)
            });

          });
    },
  }
};
</script>

<style>
.sidebar.v-sidebar-menu .vsm-arrow:after {
  content: "\f105";
  font-family: "FontAwesome",sans-serif;
}
.sidebar.v-sidebar-menu .collapse-btn:after {
  content: "\f07e";
  font-family: "FontAwesome",sans-serif;
}
</style>
