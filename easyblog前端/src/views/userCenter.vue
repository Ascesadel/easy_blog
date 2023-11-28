<template>
  <div class="scene">
    <canvas class="canvasStyle" id="canvas"></canvas>
    <div class="sceneLeft">
      <div class="sceneLeftMain">
        <el-slider v-model="size" />
        <el-space :size="size" style="margin-top: 20px; align-items: normal;">
          <!-- 个人信息卡片 -->
          <el-card class="box-card" style="width: 250px">
            <template #header>
              <div class="card-header sceneLeftMainHeader">
                <div class="sceneLeftMainHeaderImg">
                  <el-avatar shape="square" :size="60" :src="squareUrl" />
                </div>
                <div class="sceneLeftMainHeaderName">
                  <span v-for="(char, index) in charArray" :key="index" :style="`--i: ${index}`">{{char}}</span>
                </div>
              </div>
            </template>
            <div class="text item">最近登录:</div>
            <div class="text item">{{ currentUser.lastTime }}</div>
            <div class="text item">注册时间:</div>
            <div class="text item">{{ currentUser.createTime }}</div>
          </el-card>
          <!-- 修改信息卡片 -->
          <el-card class="box-card" style="width: 250px">
            <template #header>
              <div class="card-header sceneLeftMainHeader">
                <span>修改信息</span>
              </div>
            </template>
            <div class="text item sceneLeftMainItem">
              <el-form ref="avatarUpload" class="el-form elAvatarForm" :model="avatarForm" label-width="100px">
                <div class="elAvatarFormTop">点击更换头像</div>
                <div class="elAvatarFormMain">
                  <el-upload class="avatar-uploader elAvatarFormMainImg" :action="getServerUrl() + 'user/uploadAvatar'" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="avatarForm.avatar" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                      <Icon :iconHref="'#icon-tianjia2'" />
                    </el-icon>
                  </el-upload>
                </div>
                <el-button class="elAvatarFormFooter" @click="handleConfirm">确认更换</el-button>
              </el-form>
              <el-input v-model="avatarForm.avatar" type="hidden"></el-input>
            </div>
          </el-card>
          <!-- 修改昵称卡片 -->
          <el-card class="box-card" style="width: 250px;background: none;">
            <template #header>
              <div class="card-header sceneLeftMainHeader">
                <span>修改昵称</span>
              </div>
            </template>
            <div v-if="changeNameFlag == 0" class="text item sceneLeftMainItem">
              <el-button class="sceneLeftMainItemRightBtn" @click="showChangeUserTrueNameInput">点击修改昵称</el-button>
            </div>
            <div v-else class="text item sceneLeftMainItem">
              <el-input v-model="inputtruename" type="text" size="large" auto-complete="off" placeholder="新的昵称">
                <template #prefix>
                  <el-icon class="iconfont icon-denglu" />
                </template>
              </el-input>
              <el-button class="sceneLeftMainItemRightBtn" @click="confirmChangeUserTrueName">保存</el-button>
            </div>
          </el-card>
          <!-- 修改密码卡片 -->
          <el-card class="box-card" style="width: 250px;background: none;">
            <template #header>
              <div class="card-header sceneLeftMainHeader">
                <span>修改密码</span>
              </div>
            </template>
            <div v-if="changePasswordFlag == 0" class="text item sceneLeftMainItem">
              <el-button class="sceneLeftMainItemRightBtn" @click="showChangeUserPasswordInput">点击修改密码</el-button>
            </div>
            <div v-else class="text item sceneLeftMainItem" style="flex-direction: column;">
              <el-input v-model="inputOldPassword" type="text" size="large" auto-complete="off" placeholder="旧密码">
                <template #prefix>
                  <el-icon class="iconfont icon-denglu" />
                </template>
              </el-input>
              <el-input v-model="inputNewPassword" type="password" size="large" auto-complete="off" placeholder="新密码">
                <template #prefix>
                  <el-icon class="iconfont icon-denglu" />
                </template>
              </el-input>
              <el-input v-model="inputConfirmPassword" type="password" size="large" auto-complete="off" placeholder="再次输入新密码">
                <template #prefix>
                  <el-icon class="iconfont icon-denglu" />
                </template>
              </el-input>
              <el-button class="sceneLeftMainItemRightBtn" @click="confirmChangeUserPassword">保存</el-button>
            </div>
          </el-card>
        </el-space>
      </div>
    </div>
    <div class="sceneRight" v-if="pyFlag">
      <div class="sceneRightMain">
        <div class="sceneRightMainHeader">
          <el-button class="catBtn3" @click="turnToCity()">其他城市</el-button>
          <div class="currentWeatherBox">
            <div class="currentWeatherBoxMain">当前温度:{{ currentTem.temperature }}度 </div>
            <div class="currentWeatherBoxFooter">
              <div class="currentWeatherBoxFooterText">当前风向:{{ currentTem.windDirection }} </div>
              <div class="currentWeatherBoxFooterText">当前风速:{{ currentTem.windSpeed }} m/s</div>
            </div>
            <div style="position: absolute;right: 10%;">最后更新时间:{{ currentTem.lastUpdate }} </div>
          </div>
        </div>
        <div class="sceneRightMainBody">
          <div v-for="(item,index) in weatherLisets" :key="index" class="sceneRightMainItem">
            <div :class="index%2==0 ? 'watherBox':'watherBox2'">
              <div>时间:{{ item.time }} </div>
              <div>温度:{{ item.temperature }}度 </div>
              <div>风向:{{ item.wind }} </div>
              <div>风速:{{ item.velocity }} m/s </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="sceneRight2" v-else @click="fault()">
      <p class="faulttext">WEATHER FORECST</p>
      <p class="faulttext">WEATHER FORECST</p>
      <p class="faulttext">WEATHER FORECST</p>
      <p class="faulttext">WEATHER FORECST</p>
      <p class="faulttext">WEATHER FORECST</p>
    </div>

  </div>
</template>
<script setup>
import { init } from "@/util/rain.js";
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted, watch } from "vue";
import requestUtil, { getServerUrl } from '@/util/request'
import requestUtil2 from '@/util/requestPython'
import store from "@/store";
import router from "@/router";
import { ElMessage, ElLoading, ElMessageBox } from "element-plus";

let currentUser = ref(store.getters.GET_USERINFO);

let truename = ref(currentUser.value.tname);

let userid = ref(currentUser.value.userId);

let inputtruename = ref("");

const size = ref(20);
// // 将 ref 值转换为字符数组
let charArray = [];

// // 遍历 ref 值中的每个字符，将其 Unicode 码点存储在数组中
for (let i = 0; i < truename.value.length; i++) {
  charArray.push(truename.value.charAt(i));
}

let squareUrl = ref(getServerUrl() + 'images/avatar/' + currentUser.value.avatar)
/**上传封面类 */
const imageUrl = ref("");
const avatarUpload = ref(null);
const avatarForm = ref({
  userId: -1,
  avatar: "",
});

const handleAvatarSuccess = (res) => {
  imageUrl.value = getServerUrl() + res.data.src;
  avatarForm.value.avatar = res.data.title;
};

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isJPG) {
    ElMessage.error("图片必须是jpg格式");
  }
  if (!isLt2M) {
    ElMessage.error("图片大小不能超过2M!");
  }
  return isJPG && isLt2M;
};

const handleConfirm = async () => {
  avatarForm.value.userId = userid;
  let result = await requestUtil.post("user/updateAvatar", avatarForm.value);
  let data = result.data;
  if (data.code == 200) {
    const currentUser = data.currentUser;
    ElMessage.success("执行成功！")
    store.commit("SET_USERINFO", currentUser);
  } else {
    ElMessage.error(data.msg);
  }
};
/**修改昵称 */
let changeNameFlag = ref(0);

const showChangeUserTrueNameInput = () => {
  changeNameFlag.value++;
}

const confirmChangeUserTrueName = async () => {
  let result = await requestUtil.post("user/checkTrueName?tname=" + inputtruename.value);
  let data = result.data;
  if (data.code == 200) {
    let res = await requestUtil.post("user/changeTrueName?id=" + userid.value + "&tname=" + inputtruename.value);
    let data = res.data;
    if (data.code == 200) {
      const currentUser = data.currentUser;
      ElMessage.success("修改昵称成功")
      inputtruename.value = "";
      store.commit("SET_USERINFO", currentUser);
      changeNameFlag.value--;
    } else {
      inputtruename.value = "";
      ElMessage.error("修改昵称存在异常，请联系工作人员");
      changeNameFlag.value--;
    }
  } else {
    ElMessage.error("该昵称已存在");
  }
}

/**修改密码 */
let inputOldPassword = ref("");
let inputNewPassword = ref("");
let inputConfirmPassword = ref("");
let changePasswordFlag = ref(0);

const showChangeUserPasswordInput = () => {
  changePasswordFlag.value++;
}

const confirmChangeUserPassword = async () => {
  if (inputOldPassword.value == "") {
    ElMessage.error("请输入原密码！");
    return false;
  }
  if (inputNewPassword.value == "") {
    ElMessage.error("请输入新密码！");
    return false;
  }
  if (inputNewPassword.value != inputConfirmPassword.value) {
    ElMessage.error("两次新密码不一致！！！");
    return false;
  }
  let result = await requestUtil.post("user/changePassword?id=" + userid.value + "&password=" + inputOldPassword.value + "&newPassword=" + inputNewPassword.value);
  let data = result.data;
  if (data.code == 200) {
    const currentUser = data.currentUser;
    ElMessage.success("修改密码成功")
    inputOldPassword.value = "";
    inputNewPassword.value = "";
    inputConfirmPassword.value = "";
    store.commit("SET_USERINFO", currentUser);
    changePasswordFlag.value--;
  } else {
    ElMessage.error(data.msg);
  }
}

/**监听session */
watch(
  () => store.getters.GET_USERINFO,
  (newUserInfo, oldUserInfo) => {
    currentUser.value = newUserInfo;
    while (charArray.length) {
      charArray.pop();
    }
    let truename = ref(currentUser.value.tname);
    for (let i = 0; i < truename.value.length; i++) {
      charArray.push(truename.value.charAt(i));
    }
    squareUrl.value = getServerUrl() + 'images/avatar/' + currentUser.value.avatar;
  }
);
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
  handleWeather()
  texts.value = [...document.getElementsByClassName('faulttext')];
})
/**跳转天气 */
function turnToCity() {
  router.push('/city')
}
/**测试python */
let pyFlag = ref(false)
let weatherLisets = ref([])
let currentTem = ref()
const handleWeather = async () => {
  try {
    let result = await requestUtil2.get2("");
    let data = result.data;
    if (data.code == 200) {
      console.log("天气接口已开启");
      getWeather();
    } else {
      console.log("连接天气接口失败")
    }
  } catch (error) {
    console.log("天气接口未开启");
  }
};

const getWeather = async () => {
  try {
    let result = await requestUtil2.get2("/getweather");
    let data = result.data;
    if (data.code == 200) {
      pyFlag.value = true;
      weatherLisets.value = data.data.lists;
      currentTem.value = data.data.dict;
    } else {
      console.log("连接天气接口失败")
    }
  } catch (error) {
    console.log("网络连接错误")
  }

};

/**故障文字 */
const texts = ref([]);
let player = ref(null);

const fault = () => {
  clearInterval(player);

  setTimeout(() => {
    clearInterval(player);
    texts.value.forEach((text) => {
      text.classList.remove("faulttext_fault");
      text.style.transform = '';
      text.style.clipPath = '';
    })
  }, 3000);

  player = setInterval(() => {
    texts.value.forEach((text) => {
      // 图像混合
      text.classList.add("faulttext_fault");
      // 图像抖动
      text.style.transform = `translate(${Math.random() * 60 - 30}%,${Math.random() * 60 - 30}%)`;
      // 图像错位
      let x = Math.random() * 100;
      let y = Math.random() * 100;
      let h = Math.random() * 50 + 50;
      let w = Math.random() * 40 + 10;
      text.style.clipPath = `polygon(${x}% ${y}%,${x + w}% ${y}%,${x + w}% ${y + h}%,${x}% ${y + h}%)`;
    })
  }, 30);
}
</script>
<style>
.scene {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-rows: 5fr 3fr;
  grid-template-columns: 1fr;
}

.sceneLeft,
.sceneRight {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/**下半样式 */
.sceneRight {
  border-top: 3px solid black;
  box-sizing: border-box;
}

.sceneRight2 {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(153, 210, 233, 0.838);
}

.sceneRightMain {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 3fr 4fr;
}

.sceneRightMainHeader {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.currentWeatherBox {
  height: 100%;
  width: 80%;
  text-align: center;
  display: flex;
  flex-direction: column;
  background-image: url("../assets/weatherBg.jpg");
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
  color: snow;
}

.currentWeatherBoxMain {
  height: 60%;
  width: 100%;
  font-size: 35px;
  font-weight: bold;
}

.currentWeatherBoxFooter {
  height: 40%;
  width: 100%;
  display: flex;
}

.currentWeatherBoxFooterText {
  height: 100%;
  width: 100%;
  text-align: center;
}

.sceneRightMainBody {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr;
}

.sceneRightMainItem {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.watherBox {
  height: 80%;
  width: 80%;
  text-align: center;
  border: 2px solid gray;
  box-sizing: border-box;
  box-shadow: 2px 2px 0px 2px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.watherBox2 {
  height: 80%;
  width: 80%;
  text-align: center;
  border: 2px solid gray;
  box-sizing: border-box;
  box-shadow: 2px 2px 0px 2px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background-color: rgba(189, 186, 186, 0.459);
}

/**上半样式设计 */
.sceneLeftMain {
  height: 80%;
  width: 90%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  border: 3px solid gray;
}

.sceneLeftMainHeader {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.sceneLeftMainHeaderImg {
  height: 70%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.sceneLeftMainHeaderName {
  height: 30%;
  width: 100%;
  text-align: center;
}

.sceneLeftMainItem {
  height: 150px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.elAvatarForm {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.elAvatarFormTop {
  height: 20%;
  width: 100%;
  text-align: center;
}

.elAvatarFormMain {
  height: 70%;
  width: 100%;
}

.elAvatarFormMainImg {
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.el-icon.avatar-uploader-icon {
  color: #8c939d;
  width: 100%;
  height: 100%;
  font-size: 100px;
  text-align: center;
}

.avatar {
  width: 100%;
  height: 100%;
  display: block;
}

.elAvatarFormFooter {
  height: 10%;
  width: 50%;
  display: flex;
  align-items: center;
  text-align: center;
  margin-top: 5px;
}

.sceneLeftMainItemRightBtn {
  height: 20%;
  width: 60%;
  margin-left: 5px;
  color: snow;
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
  background-size: 200px 200px, 50px 50px, 100px 100px, 150px 150px;
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

.sceneLeftMainItemRightBtn:hover {
  color: red;
  background-color: black;
}

/* text-shadow */
.sceneLeft {
  color: rgb(255, 255, 255 /25%);
}

.sceneLeft span {
  animation: ani 4s linear calc(var(--i) * 300ms) infinite alternate;
}

@keyframes ani {
  to {
    color: #0f0;
    text-shadow: 0 0 12px #000, 0 0 16px #0f0, 0 0 40px #0f0, 0 0 160vh #0f0,
      0 20px 0 rgb(255, 12, 170 /17%), 0 -20px 0 rgb(145, 246, 255 /19%);
    filter: hue-rotate(360deg);
  }
}
/**下雨 */
.canvasStyle {
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: -1;
  left: 0;
  bottom: 0;
}

/**故障文字 */
.faulttext {
  position: absolute;
  font-size: 5rem;
  letter-spacing: 0.5rem;
  color: snow;
  user-select: none;
}

.faulttext_fault::after,
.faulttext_fault::before {
  content: "WEATHER FORECST";
  position: absolute;
  left: 0;
  top: 0;
  mix-blend-mode: screen;
}

.faulttext_fault::after {
  color: #ff0000;
  transform: translateX(2%);
}

.faulttext_fault::before {
  color: #0000ff;
  transform: translateX(-2%);
}

/**尖耳朵样式3 */
.catBtn3 {
  right: 10px;
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
  color: rgb(255, 252, 250);
}

.catBtn3:hover::before,
.catBtn3:hover::after {
  clip-path: ellipse(15% 50% at 50% 50%);
  background-color: #f48646;
}
</style>