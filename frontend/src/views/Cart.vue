<template>
  <div class="container">
    <div id="addedproductlist">
      <div>
        <div>
          <div v-if="$store.getters.getCartProducts.length === 0">
            장바구니에 담은 상품이 없습니다.
          </div>
          <div v-else v-for="(cp, idx) in $store.getters.getCartProducts" :key="cp">

            <div class="cart-item">
              <input type="checkbox" :id="idx" v-model="checked[idx]"/>
              <div>상품명: {{ cp.name }}</div>
              <div> 수량: {{ cp.quantity }}</div>
              <button @click="removeCart(cp)">-</button>
              <button @click="addCart(cp)">+</button>
            </div>
            <div>
              {{ cp.price }}원
            </div>
          </div>
          <div>
            총 가격 : {{ $store.getters.cartTotal }}원
          </div>
          <div>
            <button @click="orders">주문하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data(){
    return{
      checked:[]
    }
  },
  methods: {
    orders() {
      if (this.checked.length < 1){
        alert("담은 상품이 없습니다.");
        return;
      }
      let orderProduct = this.$store.getters.getCartProducts.filter((cp,idx) => this.checked[idx]).map(cp => {
        return {
          productId: cp.productId, price: cp.price, quantity: cp.quantity
        }
      });
      console.log(orderProduct);
      if (orderProduct.length < 1){
        alert("선택된 상품이 없습니다.");
        return;
      }
      this.$router.push({
        name: 'orderConfirm',
        state: {orderProduct: orderProduct}
      })
    },
    addCart(product) {
      this.$store.dispatch("addCart", product);
    },
    removeCart(product) {
      this.$store.dispatch("removeCart", product);
    }
  },

}
</script>

<style>
.cart-item{
  border: 4px dotted #009688;
  border-radius: 20px;

  /* 기본 css */
  display: inline-block;
  text-align: center;
  padding: 20px 100px;
  /*color: white;*/
  background-color: white;
}
</style>
