<template>
  <div class="container">
      <div class="mb-3">
        <p>logout: {{userInfo}}</p>
      </div>
  </div>
</template>

<script>

export default {
  data(){
    return{
      userInfo: null
    }
  },
  mounted(){
    this.userInfo = localStorage.getItem("customerId");
    this.$store.getters.getLoginState;
    localStorage.clear();
  }
}
</script>

<style scoped>

</style>