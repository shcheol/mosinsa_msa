<template>
  <div class="container">

    <div class="black-bg" v-if="modalState" @click="closeModal()">
      <div class="white-bg" @click="(event) => {event.stopPropagation()}">
        <div v-for="(productOption, idx) in product.productOptions" :key="productOption" style="padding-bottom: 3px;">
          <select class="select-box" v-model="result[idx]" style="text-align:center;" @change="onChange()">
            <option disabled value="">{{ productOption.optionName }}</option>
            <option v-for="(optionValue) in productOption.productOptionsValues" :key="optionValue"
                    :value="optionValue" style="  border: 1px solid #888;border-radius: 8px;">
              <div style="display: inline; padding-right: 10px">
                {{ optionValue.optionValue }}
                <div v-if="optionValue.changeType==='PLUS'"> + {{optionValue.changePrice}}</div>
                <div v-else-if="optionValue.changeType==='MINUS'"> - {{optionValue.changePrice}}</div>
              </div>

            </option>
          </select>
          <br/>

        </div>
        <div v-for="(selected, idx) in selectedProducts" :key="selected">
          <div>
            <p style="display: inline; padding-right: 3px" v-for="op in selected.options" :key="op">{{ op.name }}</p>
            <button class="btn btn-light text-danger text-small btn-sm" style="padding-left: 10px" @click="cancel(idx)">X</button>

            <input type="number" class="form-control" id="quantity" name="quantity" v-model="selected.stock" @change="stockChange(selected)"/>
            <p>{{ selected.totalPrice }}</p>
          </div>
        </div>

        <div>
          <p>총 결제 금액: {{orderPrice}}</p>
        </div>
        <button class="btn btn-dark" @click="orders(product.productId, product.price, quantity)">주문하기</button>
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
        <td>가격</td>
        <td v-if="product!=null"><div>
          <p style="font-size: small;color: #888888; text-decoration: line-through; padding-bottom: 1px">{{ product.price }}원</p>
          <p style="display: inline; color: red; padding-right: 3px">{{product.sales.discountRate}}%</p>
          <p style="display: inline; color: black">{{product.sales.discountedPrice}}원</p>
        </div>
        </td>
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
    <button class="btn btn-dark" @click="addCart(product)">담기</button>
    <button class="btn btn-dark" @click="modalState=!modalState">주문하기</button>
    <br/>
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
      result: [],
      optionLen: 0,
      selectedProducts: [],
      orderPrice: 0,
    }
  },
  mounted() {
    this.productId = this.$route.params.id;
    this.getProductDetails(this.productId);
    this.totalReaction(this.productId);
  },
  methods: {
    closeModal() {
      this.modalState = false;
    },
    getProductDetails(productId) {
      apiBoard.getProductDetails(productId)
          .then((response) => {
            console.log(response);
            this.product = response.data;
            this.optionLen = this.product.productOptions.length;
            this.initResult();
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
    },
    stockChange(selected){
      selected.totalPrice = selected.perPrice * selected.stock;
      this.calculateOrderPrice();
    },
    onChange() {
      for (let i = 0; i < this.optionLen; i++) {
        if (this.result[i] === '') {
          return;
        }
      }

      let price = this.product.price;
      const temp = [];
      for (let i = 0; i < this.optionLen; i++) {
        temp.push({
          id: this.result[i].id,
          name: this.result[i].optionValue,
        });
        if (this.result[i].changeType === 'MINUS') {
          price -= this.result[i].changePrice;
        } else if (this.result[i].changeType === 'PLUS') {
          price += this.result[i].changePrice;
        }
      }

      this.selectedProducts.push({
        name: this.product.name,
        options: temp,
        stock: 1,
        perPrice: price,
        totalPrice: price*1
      })

      this.calculateOrderPrice();
      this.initResult();
    },
    cancel(idx){
      this.selectedProducts.splice(idx,1);
      this.calculateOrderPrice();
    },
    initResult() {
      this.result = [];
      for (let i = 0; i < this.optionLen; i++) {
        this.result.push('');
      }
    },
    calculateOrderPrice(){
      this.orderPrice =0;
      this.selectedProducts.forEach(sp => this.orderPrice+=sp.totalPrice);
    },
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

.select-box {
  padding: 3px 3px 3px;
  border: 1px solid #888;
  border-radius: 8px;
  width: 100%
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
}

</style>