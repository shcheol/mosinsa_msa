<template>
  <div>
    <ul>
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
  }
}
</script>

<style scoped>

body {
  margin: 0
}

div {
  box-sizing: border-box;
}
</style>