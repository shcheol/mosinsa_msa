<template>
  <div class="menu">
    <router-link to="/">Home</router-link>
    <router-link to="/myPage">MyPage</router-link>
    <router-link to="/about">About</router-link>
    <router-link to="/login">Login</router-link>
  </div>
  <router-view/>

<!--
  <img alt="Vue logo" src="./assets/logo.png">-->

  <div class="black-bg" v-if="modalState">
    <div class="white-bg">
      <h4>상세페이지</h4>
      <h4>상세페이지내용</h4>
      <button @click="modalState=!modalState">닫기</button>
    </div>
  </div>


<!--  <div v-for="(product,i) in products" :key="product">-->
<!--    <h4 :style="스타일" @click="modalState = !modalState">{{ product }}</h4>-->
<!--    <p>{{ products[i] }} 원</p>-->
<!--    <button v-on:click="increase(i)">좋아요</button> <span>좋아요수 : {{ likes[i] }}</span>-->
<!--  </div>-->

</template>

<script>


export default {
  name: 'App',
  data() {
    return {
      스타일: 'color: blue',
      products: ['역삼동운룸', '천호동원룸', '어디 원룸'],
      menus: ['Home', 'Shop', 'About'],
      likes : ['0','0','0'],
      modalState : false
    }
  },
  methods : {
    increase(i){
      this.likes[i]++;
    }
  },
  components: {}
}

</script>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<title></title>
</head>
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
</style>
