<template>
  <div class="register-container">
    <el-form ref="registerRef" :model="registerForm" :rules="registerRules" label-width="20px" class="register-page">
      <div class="title" id="Entry">
        <h1>注册账号</h1>
      </div>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" size="large" auto-complete="off" placeholder="账号">
          <template #prefix>
            <Icon :iconHref="'#icon-zhuce'" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="registerForm.password" type="password" autocomplete="off" placeholder="请输入密码">
          <template #prefix>
            <Icon :iconHref="'#icon-tianchongxing-'" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="ConPassword">
        <el-input v-model="registerForm.ConPassword" type="password" autocomplete="off" placeholder="请再次输入密码">
          <template #prefix>
            <Icon :iconHref="'#icon-tianchongxing-'" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="code">
        <el-input v-model="registerForm.code" auto-complete="off" placeholder="请输入验证码" class="codeInput"></el-input>
        <Identify class="codePic" @update:identifyCode="updateIdentifyCode" @click="clickRefreshCode()"></Identify> <!-- :identifyCode="identifyCode" -->
        <el-button type="primary" @click="clickRefreshCode()">刷新</el-button>
      </el-form-item>

      <el-form-item class="registerBtn">
        <el-button type="primary" @click="handleRegister">Register</el-button>
        <el-button @click="resetForm" style="margin-left: 60px;">Reset</el-button>
        <el-button type="info" @click="BackToLogin" style="margin-left: 60px;">Back</el-button>
      </el-form-item>
    </el-form>
    <div class="el-register-footer">
      <span>Copyright © 2022-2023 <a href="https://www.bilibili.com/bangumi/play/ss44776?spm_id_from=333.337.0.0" target="_blank" style="color:white">Desire Grand Prix</a> 版权所有.</span>
    </div>
  </div>
</template>

<script setup>
import requestUtil from '@/util/request'
import store from '@/store'

import { onMounted, ref, provide } from 'vue'
import { ElMessage } from 'element-plus'
import qs from "qs"
import { useRouter } from 'vue-router'
import Identify from '../components/canvas7.vue'
import Icon from '@/components/Icon.vue'

const registerRef = ref(null)
const router = useRouter()
// count传递变量(provide组件跨级传值)
let count = ref(0)
provide('countNum', count)
let currentCode = ref('')

const registerForm = ref({
  username: "",
  password: "",
  ConPassword: "",
  code: '',
})

const checkUsername = async (rule, value, callback) => {
  const res = await requestUtil.post("user/checkUserName", { username: registerForm.value.username });
  if (res.data.code == 500) {
    callback(new Error("用户名已存在！"));
  } else {
    callback();
  }
}

const checkConfirmPwd = () => {
  if (registerForm.value.ConPassword !== registerForm.value.password) {
    return Error("两次输入的密码不一致!");
  } else {
    return true;
  }
}

const registerRules = ref({
  username: [{ required: true, message: "请输入您的账号" },
  { required: true, trigger: "blur", validator: checkUsername }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  ConPassword: [{ required: true, trigger: "blur", message: "请再次输入您的密码" },
  { required: true, trigger: "blur", validator: checkConfirmPwd }],
  code: [{ required: true, trigger: "blur", message: "请输入验证码" }]
});

const handleRegister = () => {
  registerRef.value.validate(async (vaild) => {
    if (vaild) {
      // if (registerForm.value.code !== currentCode.value) {
      //   ElMessage.error('请填写正确验证码')
      //   clickRefreshCode()
      // } else if (registerForm.value.code == currentCode.value) {
      //   ElMessage.success('你填写了正确的验证码，夸夸你哦')
      // }
      if (registerForm.value.code !== currentCode.value) {
        ElMessage.error('请填写正确验证码')
        refreshCode()
      } else {
        let result = await requestUtil.post("user/register", registerForm.value);
        let data = result.data;
        if (data.code == 200) {
          ElMessage.success("注册成功");
          router.push('/login')
        } else {
          ElMessage.error(data.msg);
        }
        console.log("表单中数据为" + qs.stringify({ username: registerForm.value.username, password: registerForm.value.password }))
      }
    } else {
      console.log("注册数据不正确")
    }
  })
}

onMounted(() => {
  count.value++;
  console.log("验证码是:" + currentCode.value)
})

const resetForm = () => {
  ElMessage.success("表单重置成功!");
  registerRef.value.resetFields();
}

function clickRefreshCode() {
  count.value++;
}

function updateIdentifyCode(newIdentifyCode) {
  currentCode.value = newIdentifyCode;
  console.log("验证码是:" + currentCode.value)
}

const BackToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
  background-image: url("../assets/mouBg.jpg");
}

.title {
  text-align: center;
  margin-top: -10px;
}

#Entry h1 {
  color: transparent;
  background: url("../assets/title_bg.png") repeat-x;
  background-clip: text;
  -webkit-background-clip: text;
  animation: animate 10s linear infinite;
}

@keyframes animate {
  0% {
    background-position: left 0px top -50px;
  }

  30% {
    background-position: left 100px top -100px;
  }

  60% {
    background-position: left 200px top -200px;
  }

  80% {
    background-position: left 300px top -100px;
  }

  100% {
    background-position: left 400px top -50px;
  }
}

.codeInput {
  width: 180px;
}

.codePic {
  margin: 10px 15px 0 15px;
}

.register-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  width: 400px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.el-register-footer {
  background-color: black;
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
