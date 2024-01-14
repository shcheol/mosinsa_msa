<template>
  <div class="home"></div>

  <div v-for="product in products" :key="product">
    <h4>{{product.name}}</h4>
    <p>가격 : {{product.price}} 원</p>
    <p>잔여 수량 : {{product.stock}}</p>
    <p>좋아요 : {{product.likes}}</p>
  </div>

</template>

<script>
import apiBoard from '@/api/board'

export default {
  data(){
    return {
      products: null
    }
  },
  mounted() {
    apiBoard.getProducts()
        .then((response) => {
          console.log(response.data);
          this.products = response.data.response.content;
        })
        .catch(function (e){
      console.log(e);
    });
  }
}
</script>

<style scoped>

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