<template>
  <div class="container">
      <div class="mb-3">
        <label for="username" class="form-label">아이디</label>
        <input type="text" class="form-control" id="username" name="username" v-model="username">
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">암호</label>
        <input type="password" class="form-control" id="password" name="password" v-model="password">
      </div>
      <button class="btn btn-primary" @click="login(username, password)">로그인</button>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

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
          .then(function (response) {
            console.log(response);
            this.$router.push("/");
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