<template>
  <div class="container">
    <div id="addedproductlist">
      <div>
        <div>
          <div v-if="$store.getters.getCartProducts.length === 0" class="shopped-item">
            장바구니에 담은 상품이 없습니다.
          </div>
          <div v-else class="shopped-item" v-for="cp in $store.getters.getCartProducts" :key="cp">
            <div>
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
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  methods: {
    addCart(product) {
      this.$store.dispatch("addCart", product);
    },
    removeCart(product) {
      this.$store.dispatch("removeCart", product);
    }
  },

}
</script>
