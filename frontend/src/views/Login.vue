<template>
  <div class="container">
      <div class="mb-3">
        <label for="username" class="form-label">아이디</label>
        <input type="text" class="form-control" id="username" name="username" v-model="username">
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">암호</label>
        <input type="password" class="form-control" id="password" name="password" v-model="password" @keyup.enter="login(username, password)">
      </div>
      <button class="btn btn-dark" @click="login(username, password)">로그인</button>
  </div>
</template>

<script>
import apiBoard from '@/api/board'
import router from "@/router/index.js";

export default {
  data(){
    return{
      username:"",
      password:""
    }
  },
  methods : {
    login(username, password) {
      apiBoard.postLogin(username, password)
          .then((response) => {
            localStorage.setItem('access-token', response.headers.get("access-token"));
            localStorage.setItem('refresh-token', response.headers.get("refresh-token"));
            localStorage.setItem('customer-info', response.headers.get("customer-info"));

            this.$store.commit('setLoginState');
            router.push("/");
          })
          .catch(function (e) {
            console.log(e);
          });
    }
  }
}
</script>

<style scoped>

</style>