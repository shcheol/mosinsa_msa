<template>
  <div class="container">
<!--    <h2>상품 상세</h2>-->

<!--    <table class="table">-->
<!--      <tbody>-->
<!--      <tr>-->
<!--        <td>쿠폰번호</td>-->
<!--        <td v-if="product!=null">{{product.productId}}</td>-->
<!--      </tr>-->
<!--      <tr>-->
<!--        <td>이름</td>-->
<!--        <td>{{product.name}}</td>-->
<!--      </tr>-->
<!--      <tr>-->
<!--        <td>수량</td>-->
<!--        <td>{{product.stock}}</td>-->
<!--      </tr>-->
<!--      <tr>-->
<!--        <td>가격</td>-->
<!--        <td>{{product.price}}</td>-->
<!--      </tr>-->
<!--      <tr>-->
<!--        <td>좋아요</td>-->
<!--        <td>{{product.likes}}</td>-->
<!--      </tr>-->
<!--      </tbody>-->
<!--    </table>-->

<!--    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>-->
  </div>
</template>

<script>
import apiBoard from '@/api/board'
/*import router from '@/router/index.js'*/

export default {
  data(){
    return {
      product :null,
      productId: null
    }
  },
  mounted() {
     this.init();
  },
  methods: {
    async init() {
/*      const router = useRouter()
      await router.isReady();
      console.log(router)*/

      apiBoard.getProductDetails(this.$route.params.id)
          .then((response) => {
            console.log(response);
            this.product = response.data.response;
          })
          .catch(function (e) {
            console.log(e);
          });
    }
}

}
</script>
