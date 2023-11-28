<template>
  <div class="login-container">
    <el-form ref="loginRef" :model="loginForm" status-icon :rules="loginRules" label-width="20px" class="login-page">
      <div class="title" id="Geats">
        <h1>easyBlog登录</h1>
      </div>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" size="large" auto-complete="off" placeholder="账号">
          <template #prefix>
            <el-icon class="iconfont icon-denglu" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" autocomplete="off" placeholder="密码">
          <template #prefix>
            <el-icon class="iconfont icon-mima" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.prevent="handleLogin">Login</el-button>
        <el-button @click="resetForm" style="margin-left: 60px">Reset</el-button>
        <el-button type="primary" @click="turnToRegister" style="margin-left: 60px">Register</el-button>
      </el-form-item>
    </el-form>
    <div class="el-login-footer">
      <span>Copyright © 2022-2024
        <a href="https://www.bilibili.com/bangumi/play/ss44776?spm_id_from=333.337.0.0" target="_blank" style="color: white">Desire Grand Prix</a>
        版权所有.</span>
    </div>
  </div>
</template>

<script setup>
import requestUtil from "@/util/request";
import store from "@/store";
import { ref } from "vue";
import { ElMessage, ElLoading } from "element-plus";
import qs from "qs";
import router from "@/router";

const loading = ref(false);

const loginRef = ref(null);

const loginForm = ref({
  username: "admin",
  password: "123456",
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
};

const handleLogin = () => {
  let loginLoading = ElLoading.service({
    lock: true,
    text: "登录中...",
    background: "rgba(0, 0, 0, 0.7)",
    timer: 3000, // 动画时长为3s
  });

  loginRef.value.validate(async (valid) => {
    if (valid) {
      let result = await requestUtil.post("user/login?" + qs.stringify(loginForm.value));
      let data = result.data;
      if (data.code == 200) {
        const currentUser = data.currentUser;
        store.commit("SET_USERINFO", currentUser);
        store.commit("SET_MANAGERTYPE", 0);
        ElMessage.success(data.msg);
        loginLoading.close();
        // router.replace("/manager");
        router.replace("/home");
      } else {
        ElMessage.error(data.msg);
        loginLoading.close();
      }
    } else {
      ElMessage.error("认证失败!");
      loginLoading.close();
    }
  });
};

const resetForm = () => {
  ElMessage.success("表单重置成功!");
  loginRef.value.resetFields();
};

const turnToRegister = () => {
  router.replace("/register")
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  background-image: url("../assets/oceanBg.jpg");
}

.title {
  text-align: center;
}

#Geats h1 {
  color: transparent;
  background: url("../assets/title_b.png") repeat-x;
  background-clip: text;
  -webkit-background-clip: text;
  animation: animate 10s linear infinite;
}

@keyframes animate {
  0% {
    background-position: left 0px top -300px;
  }

  30% {
    background-position: left 200px top -350px;
  }

  60% {
    background-position: left 400px top -400px;
  }

  80% {
    background-position: left 600px top -350px;
  }

  100% {
    background-position: left 800px top -300px;
  }
}

.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  width: 400px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 10px #cac6c6;
}

.el-login-footer {
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
