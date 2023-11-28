<template>
  <div class="rain">
    <div v-for="(item,index) in rainNumber" :key="index" class="rain-item" ref="rain-item" :style="`transform:rotate(${rotateDeg}deg);width:${w}px;height:${h}px;`">
      <div class="line" :style="`animationDelay:${index*100}ms`"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  rainNumber: {
    type: Number,
    default: 20,
  },
  rotateDeg: {
    type: Number,
    default: 40,
  },
  w: {
    type: Number,
    default: 1,
  },
  h: {
    type: Number,
    default: 120,
  },
})

const rainArr = ref(null)

onMounted(() => {
  randomRain()
})

const randomRain = () => {
  const rainItems = Array.from(document.querySelectorAll('.rain-item'));
  rainArr.value = rainItems;
  rainArr.value.forEach((item) => {
    item.style.top = `${Math.floor(Math.random() * 0 + 1)}px`
    item.style.left = `${Math.floor(Math.random() * 2000 + 1)}px`
  })
}
</script>

<style>
.rain {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  position: relative;
  background-color: black;
}

.rain-item {
  position: absolute;
  width: 2px;
  height: 30px;
  display: inline-block;
}

.line {
  animation: raining 2s infinite linear;
  position: absolute;
  content: "";
  top: -30px;
  left: 0;
  width: 100%;
  height: 100%;
  box-shadow: 0px 5px 20px 0px #fcfcfc;
  background: linear-gradient(
    to top,
    rgb(249, 249, 249),
    rgba(11, 36, 66, 0.1)
  );
}
@keyframes raining {
  0% {
    top: -0;
    opacity: 0;
  }
  10% {
    top: 10;
    opacity: 0.5;
  }
  25% {
    top: 200;
    opacity: 0.5;
  }
  50% {
    top: 400px;
    opacity: 1;
  }
  75% {
    top: 600px;
    opacity: 0.5;
  }
  100% {
    top: 800px;
    opacity: 0;
  }
}
</style>

