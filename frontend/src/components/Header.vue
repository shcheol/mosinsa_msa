<template>
  <header>
    <div class="px-3 py-2 bg-dark text-white">
      <div class="container">

        <div class="d-flex align-items-center justify-content-between">
          <!-- 좌측: Navigation 메뉴 -->
          <div class="d-flex align-items-center">
            <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
              <li>
                <router-link to="/" class="nav-link text-white">
                  <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                    <use xlink:href="#home"/>
                  </svg>
                  MOSINSA
                </router-link>
              </li>
            </ul>

          </div>
          <div class="d-flex align-items-center">
            <div>
              <router-link to="/myPage" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                My
              </router-link>
            </div>
            <div v-if="!$store.state.isLogin">
              <router-link to="/login" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                Login
              </router-link>
            </div>
            <div v-else>
              <router-link to="/logout" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                Logout
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>

    <br/>
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

</style>