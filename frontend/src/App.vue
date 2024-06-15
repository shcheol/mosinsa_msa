<template>
  <Header/>
  <div id="wrapper">
    <router-view/>
  </div>
  <Footer/>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: 'App',
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
#wrapper{
  flex: 1;
}
</style>
