<template>
  <div class="login-container">
    <star-background />
  </div>
</template>

<script>
import StarBackground from './Testb.vue'

export default {
  beforeCreate: function () {
    document.getElementsByTagName('body')[0].className = 'body-bg'
  },
  components: { StarBackground }
}
</script>

<style>
.body-bg {
  background-attachment: fixed;
  overflow: hidden;
}

.login-container {
  height: 100%;
  width: 100%;
  overflow: hidden;
  background-image: url("../assets/nightBg.jpg");
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
}
</style>

