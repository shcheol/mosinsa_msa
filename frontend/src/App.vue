<template>
  <Header/>

  <router-view/>
  <Footer/>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

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
  components: {Header, Footer}
}

</script>
<style>
html, body, #app {
  height: 100vh;
}
#app {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100vh;
}

</style>
