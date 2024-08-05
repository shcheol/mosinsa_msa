<template>
  <div class="container">
      <div class="mb-3">
        <p v-if="userInfo!=null" >logout: {{userInfo.name}}</p>
        <p v-else >login한 상태가 아닙니다.</p>
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
    this.userInfo = JSON.parse(localStorage.getItem("customer-info"));
    this.$store.commit('setLogoutState');
    localStorage.clear();
  }
}
</script>

<style scoped>

</style>