<template>
  <div class="container">

    <div class="black-bg" v-if="modalState">
      <div class="white-bg">
        <h4>수량</h4>
        <input type="number" class="form-control" id="quantity" name="quantity" v-model="quantity"/>
        <button @click="orders(product.productId, product.price, quantity)">주문하기</button>
      </div>
    </div>

    <h2>상품 상세</h2>
    <table class="table">
      <tbody>
      <tr>
        <td>상품번호</td>
        <td v-if="product!=null">{{ product.productId }}</td>
      </tr>
      <tr>
        <td>이름</td>
        <td v-if="product!=null">{{ product.name }}</td>
      </tr>
      <tr>
        <td>수량</td>
        <td v-if="product!=null">{{ product.stock }}</td>
      </tr>
      <tr>
        <td>가격</td>
        <td v-if="product!=null">{{ product.price }}</td>
      </tr>
      <tr>
        <td>좋아요</td>
        <td v-if="product!=null">{{ product.likes }}</td>
        <button @click="likes(product.productId)">좋아요</button>
      </tr>
      </tbody>
    </table>
    <button @click="addCart(product)">담기</button>
    <button @click="modalState=!modalState">주문하기</button>
    <div>
      <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
    </div>

    <br/>
    <Reviews :props-value="productId" />


  </div>
</template>

<script>
import apiBoard from '@/api/board'
import Reviews from "./Reviews.vue"


export default {
  components: {Reviews}
  ,
  data() {
    return {
      productId: null,
      product: null,
      modalState: false,
      quantity: null,
      customerInfo: JSON.parse(localStorage.getItem("customer-info"))
    }
  },
  mounted() {
    this.productId = this.$route.params.id;
    apiBoard.getProductDetails(this.productId)
        .then((response) => {
          console.log(response);
          this.product = response.data.response;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {

    orders(productId, price, quantity) {
      this.modalState = false;

      this.$router.push({
        name: 'orderConfirm',
        state: {orderProduct: [{productId: productId, price: price, quantity: quantity}]}
      })
    },
    addCart(product) {
      this.$store.dispatch("addCart", product);
    },
    likes(productId) {
      apiBoard.postLikesProduct(productId)
          .then((response) => {
            console.log(response);
            apiBoard.getProductDetails(productId)
                .then((response) => {
                  console.log(response);
                  this.product = response.data.response;
                })
                .catch(function (e) {
                  console.log(e);
                });
          })
          .catch(function (e) {
            console.log(e);
          });
    },

  }

}
</script>

<style>
.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: relative;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
}

</style>