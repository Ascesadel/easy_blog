<template>
  <div class="rainy">
    <canvas class="canvasStyle" id="canvas"></canvas>
  </div>
</template>
  <script setup>
import { init } from "@/util/rain.js";
import { ref, onMounted } from "vue";

/*下雨特效 */
const ctx = ref(null)

function initCanvas() {
  const canvas = document.querySelector('#canvas')
  ctx.value = canvas.getContext('2d')
  canvas.width = window.innerWidth
  canvas.height = window.innerHeight
  init(ctx.value)
}

onMounted(() => {
  initCanvas()
})
  </script>
  <style>
.rainy {
  height: 100%;
  width: 100%;
}
.canvasStyle {
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: -1;
  left: 0;
  bottom: 0;
}
</style>