<template>
  <div class="container">
    <div id="addedproductlist">
      <div>
        <div>
          <div v-if="$store.getters.getCartProducts.length === 0">
            장바구니에 담은 상품이 없습니다.
          </div>
          <div v-else v-for="(cp, idx) in $store.getters.getCartProducts" :key="cp">

            <div>
              <label><input type="checkbox" :id="idx" v-model="checked[idx]"/></label>
<!--              <img src="../assets/logo.png"/>-->
              <div>상품명: {{ cp.name }}</div>
            </div>
            <div>
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
      let orderProduct = this.$store.getters.getCartProducts.filter((cp,idx) => this.checked[idx]).map(cp => {
        return {
          productId: cp.productId, price: cp.price, quantity: cp.quantity
        }
      });

      this.$router.push({
        name: 'orderPage',
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
