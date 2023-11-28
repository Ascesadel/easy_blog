<template>
  <div class="meteor-background">
    <star-background />
  </div>
</template>

<script>
import StarBackground from './meteor-compent.vue'
// 组件作者 https://blog.csdn.net/weixin_43484014/article/details/122553975
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

.meteor-background {
  height: 100%;
  width: 100%;
  overflow: hidden;
  background-image: url("../assets/nightBg.jpg");
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
}
</style>