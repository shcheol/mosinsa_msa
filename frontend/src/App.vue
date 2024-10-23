<template>
  <Header/>
  <div id="wrapper">
    <router-view/>
  </div>
  <Sidebar />
  <Footer/>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import Sidebar from '@/components/Sidebar.vue'

export default {
  name: 'App',
  components: {Header, Footer, Sidebar}
}

</script>
<style>
html, body, #app {
  height: 100vh;
  padding-left: 22px;
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
