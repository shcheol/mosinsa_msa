<template>
  <div class="aboutView"></div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  mounted() {
    apiBoard.getProducts()
        .then(function (response){
          console.log(response);
        })
        .catch(function (e){
      console.log(e);
    });
  }
}
</script>

<style scoped>

</style>