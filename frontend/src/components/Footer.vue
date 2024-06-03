<template>
  <footer>
    <a
        href="https://github.com/shcheol"
        target="_blank">
      (c) {{ new Date().getFullYear() }} shcheol </a>
  </footer>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
footer {
  text-align: center;
  background: black;
  width: 100%;
  height: 60px;
  position: relative;

}
</style>