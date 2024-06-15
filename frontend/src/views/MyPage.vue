<template>
  <div>
    <ul>
      <li>
        <a>
          <router-link to="/myPage/cart">장바구니</router-link>
        </a>
      </li>
      <li>
        <a>
          <router-link to="/myPage/coupons">쿠폰함</router-link>
        </a>
      </li>
      <li>
        <a>
          <router-link to="/myPage/likes">좋아요</router-link>
        </a>
      </li>
      <li>
        <a>
          <router-link to="/myPage/orders">주문목록</router-link>
        </a>
      </li>
    </ul>

  </div>

</template>

<script>

export default {
  data() {
    return {}
  },
  beforeCreate() {
    if (localStorage.getItem("customerId")===null){
      alert('login이 필요합니다.')
      this.$router.push({name:"login"})
    }
  },
}
</script>

<style scoped>


</style>