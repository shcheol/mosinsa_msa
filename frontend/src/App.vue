<template>
  <div class="menu">
    <router-link to="/">Home</router-link>
    <router-link to="/promotions">Promotions</router-link>
    <router-link to="/myPage">MyPage</router-link>
    <router-link v-if="!isLogin" to="/login">Login</router-link>
    <router-link v-if="isLogin" to="/logout">Logout</router-link>
  </div>
  <router-view/>

  <div class="black-bg" v-if="modalState">
    <div class="white-bg">
      <h4>상세페이지</h4>
      <h4>상세페이지내용</h4>
      <button @click="modalState=!modalState">닫기</button>
    </div>
  </div>

</template>

<script>


export default {
  name: 'App',
  data() {
    return {
      isLogin : false
    }
  },
  mounted() {
    this.isLogin = localStorage.getItem("customerId")!=null
  },
  components: {}
}

</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.menu{
  background: darkslateblue;
  padding: 15px;
  border-radius: 5px;
}

.menu a {
  color: white;
  padding: 10px;
}

body{
  margin: 0
}
div{
  box-sizing: border-box;
}
.black-bg{
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.5);
  position: fixed; padding: 20px;
}
.white-bg{
  width: 100%;
  background: white;
  border-radius: 8px; padding: 20px;
}

table{
  border:1px;
  margin-left:auto;
  margin-right:auto;
}

 tbody {
   /* 테투리 css */
   border: 4px dotted #009688;
   border-radius: 20px;

   /* 기본 css */
   display: inline-block;
   text-align: center;
   padding: 20px 100px;
   /*color: white;*/
   background-color: white;
 }

</style>
