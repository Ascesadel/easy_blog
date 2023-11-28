<template>
  <div class="headerPage">
    <el-container>
      <div class="headerView">
        <div class="userView">
          <div class="userInfo">
            <div class="userInfoImg">
              <el-avatar shape="square" :size="40" :src="squareUrl" />
            </div>
            <div class="userInfoName">{{ currentUser.tname }}</div>
          </div>
        </div>
        <div class="nullView"></div>
        <div class="actionView">
          <el-button style="transition: transform 0.5s;" class="menuLi catBtn3" :class="flag ? 'menuLi':'menuLi2'" @click="turnToUserHome()">个人中心</el-button>
          <el-button v-if=" flag " class="catBtn2" @click="menuChange()">
            菜单
          </el-button>
          <el-button v-else class="catBtn2" @click="menuChange()">
            关闭
          </el-button>
          <el-button style="transition: transform 0.5s;" class="menuLi catBtn3" :class="flag ? 'menuLi3':'menuLi2'" @click="logout()">退出登录</el-button>
          <el-button style="transition: transform 0.5s;" class="menuLi catBtn3" :class="flag ? 'menuLi3':'menuLi2'" @click="backToManager()">返回博客页面</el-button>
        </div>
      </div>
    </el-container>
  </div>
</template>

<script setup>
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted, watch } from "vue";
import requestUtil, { getServerUrl } from '@/util/request'
import store from "@/store";
import router from "@/router";
import { ElMessage } from "element-plus";

let currentUser = ref(store.getters.GET_USERINFO);

let squareUrl = ref(getServerUrl() + 'images/avatar/' + currentUser.value.avatar)

let flag = ref(true);

function menuChange() {
  flag.value = !flag.value;
}

function turnToUserHome() {
  router.replace("/userCenter")
}

function backToManager() {
  router.replace("/manager")
}

const logout = () => {
  store.dispatch('logout')
  ElMessage.success("退出成功!");
}
watch(
  () => store.getters.GET_USERINFO,
  (newUserInfo, oldUserInfo) => {
    currentUser.value = newUserInfo;
    squareUrl.value = getServerUrl() + 'images/avatar/' + currentUser.value.avatar;
  }
);
</script>
<style>
.headerPage {
  height: 100%;
  width: 100%;
  background-color: black;
  background-image: radial-gradient(
      white,
      rgba(255, 255, 255, 0.2) 2px,
      transparent 40px
    ),
    radial-gradient(white, rgba(255, 255, 255, 0.15) 1px, transparent 30px),
    radial-gradient(white, rgba(255, 255, 255, 0.1) 2px, transparent 40px),
    radial-gradient(
      rgba(255, 255, 255, 0.4),
      rgba(255, 255, 255, 0.1) 2px,
      transparent 30px
    );
  background-size: 550px 550px, 350px 350px, 250px 250px, 150px 150px;
  background-position: 0 0, 40px 60px, 130px 270px, 70px 100px;
  animation: gradientBG 8s ease infinite;
}

@keyframes gradientBG {
  0% {
    background-position: 0% 50%;
  }

  25% {
    background-position: 50% 25%;
  }

  50% {
    background-position: 100% 50%;
  }

  75% {
    background-position: 25% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}

.el-container {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.headerView {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-rows: 1fr;
  grid-template-columns: 1fr 1fr 1fr;
}

.userView,
.nullView,
.actionView {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.userInfo {
  width: 80%;
  height: 100%;
  display: flex;
  color: white;
  align-items: center;
  justify-content: center;
}

.userInfoImg {
  width: 25%;
  height: 100%;
  margin-right: 10%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.userInfoName {
  font-size: 20px;
  font-family: 楷体;
  letter-spacing: 3px;
}

/**尖耳朵样式 */
.catBtn2 {
  position: relative;
  border: none;
  background-color: #e8e46d;
  color: white;
  cursor: pointer;
  transition: all 0.3s;
}

.catBtn2::before,
.catBtn2::after {
  content: "";
  position: absolute;
  background-color: inherit;
  background-color: #ecbf8fc2;
  transition: all 0.3s;
}

.catBtn2::before {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  left: -17px;
  top: -15px;
  rotate: -50deg;
}

.catBtn2::after {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  right: -17px;
  top: -15px;
  rotate: 50deg;
}

.catBtn2:hover {
  background-color: #79ded2;
}

.catBtn2:hover::before,
.catBtn2:hover::after {
  background-color: #fff;
}

/**尖耳朵样式3 */
.catBtn3 {
  position: relative;
  border: none;
  background-color: #e8e46d;
  color: black;
  cursor: pointer;
  transition: all 0.3s;
}

.catBtn3::before,
.catBtn3::after {
  content: "";
  position: absolute;
  background-color: inherit;
  background-color: #ecbf8fc2;
  transition: all 0.3s;
}

.catBtn3::before {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  left: -2px;
  top: -15px;
  rotate: -50deg;
}

.catBtn3::after {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  right: -2px;
  top: -15px;
  rotate: 50deg;
}

.catBtn3:hover {
  background-color: #2352ec;
  color: snow;
}

.catBtn3:hover::before,
.catBtn3:hover::after {
  clip-path: ellipse(15% 50% at 50% 50%);
  background-color: #fff;
}

/* 菜单设计 */
.menuLi {
  background-color: #e8e46d;
  visibility: hidden;
  opacity: 0;
  transform: translateX(100px);
}

.menuLi3 {
  background-color: #e8e46d;
  visibility: hidden;
  opacity: 0;
  transform: translateX(-100px);
}

.menuLi2 {
  background-color: #efd9bc;
  visibility: visible;
  opacity: 1;
  transform: translateX(0);
}
</style>