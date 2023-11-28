<template>
  <div class="s-canvas">
    <canvas id="s-canvas" :width="contentWidth" :height="contentHeight"></canvas>
  </div>
</template>
<script setup>
import { ref, watch, onMounted, defineProps, inject, defineEmits } from 'vue';

const identifyCodes = '1234567890abcdefjhijklinopqrsduvwxyz'
let identifyCode = ref('1234')
// flagNum接收变量(inject组件跨级传值)
let flagNum = inject('countNum')
const emits = defineEmits(['update:identifyCode'])

const props = defineProps({
  fontSizeMin: {
    type: Number,
    default: 25
  },
  fontSizeMax: {
    type: Number,
    default: 35
  },
  backgroundColorMin: {
    type: Number,
    default: 200
  },
  backgroundColorMax: {
    type: Number,
    default: 220
  },
  dotColorMin: {
    type: Number,
    default: 60
  },
  dotColorMax: {
    type: Number,
    default: 120
  },
  contentWidth: {
    type: Number,
    default: 90
  },
  contentHeight: {
    type: Number,
    default: 38
  }
})

const randomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min)
}

// 生成一个随机的颜色
const randomColor = (min, max) => {
  let r = randomNum(min, max)
  let g = randomNum(min, max)
  let b = randomNum(min, max)
  return 'rgb(' + r + ',' + g + ',' + b + ')'
}

const drawPic = (identifyCode) => {
  let canvas = document.getElementById('s-canvas')
  let ctx = canvas.getContext('2d')
  ctx.textBaseline = 'bottom'
  // 绘制背景
  ctx.fillStyle = '#e6ecfd'
  ctx.fillRect(0, 0, props.contentWidth, props.contentHeight)
  // 绘制文字
  for (let i = 0; i < identifyCode.length; i++) {
    drawText(ctx, identifyCode[i], i)
  }
  drawLine(ctx)
  drawDot(ctx)
}

const drawText = (ctx, txt, i) => {
  ctx.fillStyle = randomColor(50, 160) // 随机生成字体颜色
  ctx.font = randomNum(props.fontSizeMin, props.fontSizeMax) + 'px SimHei' // 随机生成字体大小
  let x = (i + 1) * (props.contentWidth / (identifyCode.length + 1))
  let y = randomNum(props.fontSizeMax, props.contentHeight - 5)
  var deg = randomNum(-30, 30)
  // 修改坐标原点和旋转角度
  ctx.translate(x, y)
  ctx.rotate(deg * Math.PI / 180)
  ctx.fillText(txt, 0, 0)
  // 恢复坐标原点和旋转角度
  ctx.rotate(-deg * Math.PI / 180)
  ctx.translate(-x, -y)
}

const drawLine = (ctx) => {
  // 绘制干扰线
  for (let i = 0; i < 4; i++) {
    ctx.strokeStyle = randomColor(100, 200)
    ctx.beginPath()
    ctx.moveTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.lineTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.stroke()
  }
}

const drawDot = (ctx) => {
  // 绘制干扰点
  for (let i = 0; i < 30; i++) {
    ctx.fillStyle = randomColor(0, 255)
    ctx.beginPath()
    ctx.arc(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight), 1, 0, 2 * Math.PI)
    ctx.fill()
  }
}

// 重置验证码
const refreshCode = () => {
  identifyCode = '';
  makeCode(identifyCodes, 4);
  drawPic(identifyCode);
}
const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    identifyCode += identifyCodes[randomNumCode(0, identifyCodes.length)]
  }
}
const randomNumCode = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min)
}

// 替换`watch`部分
watch(flagNum, (newValue, oldValue) => {
  if (newValue > 0) {
    refreshCode();
    emits('update:identifyCode', identifyCode);
    flagNum.value--;
  }
})

// 替换`mounted`生命周期钩子
onMounted(() => {
  refreshCode();
})
</script>