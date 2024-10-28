<template>
  <header>
    <div class="px-3 py-2 text-white ">
      <div class="container">
        <div class="d-flex align-items-center justify-content-between">
          <!-- 좌측: Navigation 메뉴 -->
          <div class="d-flex align-items-center">
            <router-link to="/" class="nav-link text-black">
              <p class="text-black text-xl-center" style="font-weight: bold; font-size: x-large ">MOSINSA</p>
            </router-link>
          </div>
          <div class="d-flex align-items-center">
            <div>
              <router-link to="/myPage" class="nav-link text-white">
                <img src="../assets/mypage.png" width="32" height="32" alt="My">
              </router-link>
            </div>
            <div style="padding-left: 10px">
              <img src="../assets/category.png" width="32" height="32" alt="category">
            </div>
          </div>
        </div>
      </div>
    </div>

  </header>
</template>

<script>

export default {
  name: "Header",
  data() {
    return {
      userInfo: null,
    }
  },
  mounted() {
    this.userInfo = JSON.parse(localStorage.getItem("customer-info"));
  }
}
</script>

<style scoped>
header {
  background: gray;
}
</style>