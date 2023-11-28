<template>
  <div class="app-wrapper">
    <el-container>
      <el-header class="homeHeader">
        <Header></Header>
      </el-header>

      <el-main style="height: 100%; width: 100%; --el-main-padding: 0px">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Header from "../components/home/homeHeader.vue";
</script>
<style>
.app-wrapper {
  position: relative;
  width: 100%;
  height: 100vh;
}

.el-container {
  height: 100%;
  width: 100%;
}

.homeHeader {
  width: 100%;
  z-index: 1;
  --el-header-padding: 0px;
}
</style>