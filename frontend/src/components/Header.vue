<template>
  <header>
    <div class="px-3 py-2 bg-dark text-white">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <a href="/" class="d-flex align-items-center my-2 my-lg-0 me-lg-auto text-white text-decoration-none">
            <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
              <use xlink:href="#bootstrap"/>
            </svg>
          </a>

          <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
            <li>
              <router-link to="/" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#home"/>
                </svg>
                MOSINSA
              </router-link>
            </li>
            <li>
              <router-link to="/myPage" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                My
              </router-link>
            </li>
            <li v-if="!$store.state.isLogin">
              <router-link to="/login" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                Login
              </router-link>
            </li>
            <li v-else>
              <router-link to="/logout" class="nav-link text-white">
                <svg class="bi d-block mx-auto mb-1" width="24" height="24">
                  <use xlink:href="#speedometer2"/>
                </svg>
                Logout
              </router-link>
            </li>

          </ul>
        </div>
      </div>
    </div>

    <br/>
  </header>
</template>

<script>

export default {
  name: "Header",
  data(){
    return{
      userInfo: null,
    }
  },
  mounted(){
    this.userInfo = JSON.parse(localStorage.getItem("customer-info"));
  }
}
</script>

<style scoped>

</style>