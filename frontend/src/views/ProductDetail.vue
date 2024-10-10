<template>
  <div class="container">

    <div class="black-bg" v-if="modalState" @click="closeModal()">
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
        <td v-if="product!=null">{{ product.currentStock }}/{{ product.totalStock }}</td>
      </tr>
      <tr>
        <td>가격</td>
        <td v-if="product!=null">{{ product.price }}</td>
      </tr>
      <div v-if="reactionCntInfo!=null" class="likes" style="display: inline;" @click="likes(product.productId)">
        <img v-if="!reactionCntInfo.hasReacted" src="../assets/likes.png" width="50" height="50"
             style="display: inline; position: relative; left: 4px;" alt="likes"/>
        <img v-else src="../assets/mylikes.png" width="50" height="50"
             style="display: inline; position: relative; left: 4px;" alt="likes"/>
        <span style="display: inline; position: relative;left: 10px">{{ reactionCntInfo.reactionCnt }}</span>
      </div>
      </tbody>
    </table>
    <button @click="addCart(product)">담기</button>
    <button @click="modalState=!modalState">주문하기</button>
    <div>
      <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
    </div>

    <br/>
    <div>
      <Reviews :props-value="productId"/>
    </div>

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
      customerInfo: JSON.parse(localStorage.getItem("customer-info")),
      reactionCntInfo: null,
    }
  },
  mounted() {
    this.productId = this.$route.params.id;
    this.getProductDetails(this.productId);
    this.totalReaction(this.productId);
  },
  methods: {
    closeModal(){
      this.modalState = false;
    }
    ,
    getProductDetails(productId) {
      apiBoard.getProductDetails(productId)
          .then((response) => {
            console.log(response);
            this.product = response.data;
          });
    },
    orders(productId, price, quantity) {
      this.closeModal();

      this.$router.push({
        name: 'orderConfirm',
        state: {orderProduct: [{productId: productId, price: price, quantity: quantity}]}
      })
    },
    addCart(product) {
      this.$store.dispatch("addCart", product);
    },
    likes(productId) {
      if (this.reactionCntInfo != null) {
        if (!this.reactionCntInfo.hasReacted) {
          apiBoard.postReaction('PRODUCT', productId, 'LIKES')
              .then((response) => {
                console.log(response);
                this.totalReaction(productId)
              });
        } else {
          apiBoard.postReactionCancel('PRODUCT', productId, 'LIKES')
              .then((response) => {
                console.log(response);
                this.totalReaction(productId)
              });
        }
      }
    },
    totalReaction(productId) {
      apiBoard.getReactionCount('PRODUCT', productId, 'LIKES')
          .then((response) => {
            console.log(response);
            this.reactionCntInfo = response.data;
          });
    }
  }

}
</script>

<style>
.black-bg {
  /*display: none; !* Hidden by default *!*/
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0, 0, 0); /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
  max-width: 100%;
  max-height: 100%;
}

.white-bg {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  border-radius: 8px;
  width: 80%; /* Could be more or less, depending on screen size */
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
}

</style>