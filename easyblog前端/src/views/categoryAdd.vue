<template>
  <!--用于封装博客的部分数据，其他数据在下一个页面进行补全-->
  <div class="catagory-container">
    <el-form ref="catagoryRef" :model="catagoryForm" status-icon :rules="catagoryRules" label-width="20px" class="catagory-form">
      <div class="title" id="Geats">
        <h1>创建新的博客</h1>
      </div>

      <el-form-item prop="categoryName">
        <el-input v-model="catagoryForm.categoryName" type="text" size="large" auto-complete="off" placeholder="博客名称">
          <template #prefix>
            <el-icon class="iconfont icon-denglu" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="categoryBrief">
        <el-input v-model="catagoryForm.categoryBrief" type="text" autocomplete="off" placeholder="博客简介">
          <template #prefix>
            <el-icon class="iconfont icon-mima" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="cover" class="el-form-item coverItem">
        <el-form ref="coverUpload" class="el-form elCoverForm" :model="coverForm" label-width="100px">
          <div class="coverItemText">请上传博客封面</div>
          <div class="coverItemMain">
            <el-upload class="avatar-uploader" :action="getServerUrl() + 'Category/uploadCover'" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="coverForm.cover" :src="imageUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Icon :iconHref="'#icon-tianjia2'" />
              </el-icon>
            </el-upload>
            <el-button class="coverItemBtn" @click="handleConfirm">确认上传</el-button>
          </div>
        </el-form>
        <el-input v-model="catagoryForm.cover" type="hidden"></el-input>
      </el-form-item>

      <el-form-item prop="userId">
        <el-input v-model="catagoryForm.userId" type="password" readonly autocomplete="off">
          <template #prefix>
            <el-icon class="iconfont icon-mima" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="categorySelf">
        <el-radio-group v-model="catagoryForm.categorySelf">
          <el-radio label="0" border>仅自己可见</el-radio>
          <el-radio label="1" border>他人亦可见</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click.prevent="handleCatagory">继续添加</el-button>
        <el-button @click="resetForm" style="margin-left: 60px">重置</el-button>
        <el-button @click="goback" style="margin-left: 60px">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
import requestUtil, { getServerUrl } from "@/util/request";
import Icon from "@/components/Icon.vue";
import { ref, onMounted, watch } from "vue";
import store from "@/store";
import { ElMessage, ElLoading, ElMessageBox } from "element-plus";
import router from "@/router";

const currentUser = ref(store.getters.GET_USERINFO);

const catagoryRef = ref(null);
const catagoryForm = ref({
  categoryId: "",
  sort: "",
  cover: "",
  categoryName: "",
  categoryBrief: "",
  categorySelf: "",
  userId: currentUser.value.userId,
});
/**获取指定id的博客部分信息 */
const getCurrentCategory = async () => {
  if (
    store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")
  ) {
    store.commit("RESET_NEWCATRGORYID", sessionStorage.getItem("newCategoryId")
    );
  }
  const theCategoryId = ref(store.getters.GET_NEWCATRGORYID);
  catagoryForm.value.userId = currentUser.value.userId;

  if (theCategoryId.value === null || theCategoryId.value === undefined) {
    // 添加新的博客，所以要获取新的id
    let result = await requestUtil.get("Category/addCategory/" + currentUser.value.userId + "/username/" + currentUser.value.username);
    if (result.data.code == 200) {
      let newId = result.data.categoryId;
      let newSort = result.data.newSort;
      store.commit("SET_NEWCATRGORYID", newId);
      catagoryForm.value.categoryId = newId;
      catagoryForm.value.sort = newSort;
    }
  } else {
    console.log("useid:" + currentUser.value.userId);
    console.log("name:" + currentUser.value.username);
    // 修改博客，获取对应博客的数据
    let response = await requestUtil.post("Category/findCategory/" + theCategoryId.value);
    catagoryForm.value = response.data.currentCategory;
    catagoryForm.value.categoryId = theCategoryId.value;
    coverForm.value.cover = catagoryForm.value.cover;
    imageUrl.value = getServerUrl() + "images/cover/" + coverForm.value.cover;
  }
};

onMounted(getCurrentCategory);
/**上传封面类 */
const imageUrl = ref("");
const coverUpload = ref(null);
const coverForm = ref({
  cover: "",
});

// imageUrl.value = getServerUrl() + 'images/cover/' + coverForm.value.cover;

const handleAvatarSuccess = (res) => {
  imageUrl.value = getServerUrl() + res.data.src;
  coverForm.value.cover = res.data.title;
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
  catagoryForm.value.cover = coverForm.value.cover;
  ElMessage.success("封面上传成功！");
};

/**提交博客表 */
const handleCatagory = () => {
  catagoryRef.value.validate(async (valid) => {
    let loginLoading = ElLoading.service({
      lock: true,
      text: "保存中...",
      background: "rgba(0, 0, 0, 0.7)",
      timer: 2000, // 动画时长为3s
    });
    if (valid) {
      /**需要修改更新博客接口 */
      let result = await requestUtil.post("Category/updateCategory", catagoryForm.value);
      if (result.data.code == 200) {
        loginLoading.close();
        router.replace("/blogModelAdd");
      }
      loginLoading.close();
      router.replace("/blogModelAdd");
    } else {
      ElMessage.error("认证失败!");
      loginLoading.close();
    }
  });
};

const checkCategoryname = async (rule, value, callback) => {
  const res = await requestUtil.get("Category/checkCategoryName/" + catagoryForm.value.categoryName);
  if (res.data.code == 500) {
    callback(new Error("该博客名已存在！"));
  } else {
    callback();
  }
}

const catagoryRules = {
  categoryName: [
    { required: true, trigger: "blur", message: "请输入新博客的名称" },
    { required: true, trigger: "blur", validator: checkCategoryname }
  ],
  categoryBrief: [
    { required: true, trigger: "blur", message: "请输入新博客的简介" },
  ],
};

const resetForm = () => {
  ElMessage.success("表单重置成功!");
  catagoryRef.value.resetFields();
};

function goback() {
  ElMessageBox.confirm(
    "此操作将返回上一页且该页面操作不会被保存，是否继续？",
    "提示",
    {
      distinguishCancelAndClose: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      sessionStorage.removeItem("newCategoryId");
      router.go(-1);
    })
    .catch(() => {
      // 用户取消了操作，不进行页面跳转
    });
}
</script>
<style >
.catagory-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("../assets/logo.png");
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

.catagory-form {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  width: 400px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.elCoverForm {
  text-align: center;
  padding-bottom: 10px;
  display: flex;
  align-items: center;
  justify-items: center;
  width: 100%;
}

.coverItemText {
  flex: 1;
  font-size: 16px;
  font-family: 楷体;
  letter-spacing: 5px;
  font-weight: bold;
}

.coverItemMain {
  flex: 3;
}

.coverItemBtn {
  margin-top: 5px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  font-size: 150px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>