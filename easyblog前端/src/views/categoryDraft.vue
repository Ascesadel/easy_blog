<template>
  <div class="page">
    <el-container>
      <!--头部-->
      <el-header class="managerAllHeaderView">
        <div class="leftView">
          <el-popconfirm title="您确定要删除这个博客草稿吗？" @confirm="handleDelete(null)">
            <template #reference>
              <el-button type="danger" :disabled="delBtnStatus">
                <Icon :iconHref="'#icon-shanchu-copy'" />&nbsp;&nbsp;批量删除
              </el-button>
            </template>
          </el-popconfirm>
        </div>
        <div class="rightView">
          <el-button type="danger" @click="BackToManager()"> 返回博客页面 </el-button>
        </div>
      </el-header>
      <!--主体部分-->
      <el-main class="el-main">
        <el-table ref="multipleTableRef" id="categoryTable" stripe :data="categoryVoList" style="width: 100%" class="categoryModel" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column label="封面" width="120">
            <template v-slot="scope">
              <img :src="getServerUrl() + 'images/cover/' + scope.row.cover" width="50" height="50" />
            </template>
          </el-table-column>
          <el-table-column property="categoryName" label="标题" width="120" />
          <el-table-column property="categoryBrief" label="简介" min-width="120" show-overflow-tooltip />
          <el-table-column prop="categoryType" label="查看权限" width="200" min-width="200" align="center">
            <template v-slot="{ row }">
              <el-switch v-model="row.categoryType" @change="categoryTypeChangeHandle(row)" active-text="共享" inactive-text="仅自己可见" active-value="1" inactive-value="0">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="action" label="操作" width="400" min-width="400" fixed="right" align="center">
            <template v-slot="scope">
              <div class="slotBox">
                <div class="slotBtnBoxOne">
                  <el-button class="catBtn" type="primary" @click="changeTypeId(scope.row.categoryId)">
                    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;设为博客
                  </el-button>
                </div>
                <div class="slotBtnBoxTwo" v-if="scope.row.userId == userid">
                  <el-button class="btnTwo" type="primary" @click="handleCatrgoryValue(scope.row.categoryId)">
                    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;编辑
                  </el-button>
                </div>
                <div class="slotBtnBoxFour" v-if="scope.row.userId == userid">
                  <el-popconfirm title="您确定要删除这些草稿吗？" @confirm="handleDelete(scope.row.categoryId)">
                    <template #reference>
                      <el-button type="danger">
                        <Icon :iconHref="'#icon-shanchu-copy'" />&nbsp;&nbsp;删除
                      </el-button>
                    </template>
                  </el-popconfirm>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>
  
  <script setup>
import requestUtil, { getServerUrl } from "@/util/request";
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted } from "vue";
import store from "@/store";
import router from "@/router";
import { ElMessage } from "element-plus";

let currentUser = ref(store.getters.GET_USERINFO);
let managerType = ref(sessionStorage.getItem("managerType"));

const categoryVoList = ref([]);
const userid = ref(0);

const getUserCategoryList = async () => {
  userid.value = currentUser.value.userId;
  while (categoryVoList.value.length > 0) {
    categoryVoList.value.pop();
  }
  // categoryVoList.value = [];
  let result = await requestUtil.get("Category/userDraftCategoryList/" + userid.value);
  if (result.data.code == 200) {
    categoryVoList.value = result.data.categoryList;
  }
};

const BackToManager = () => {
  router.push("/manager");
};

/**修改指定id的博客 */
const handleCatrgoryValue = (id) => {
  store.commit("SET_NEWCATRGORYID", id);
  router.push("/blogAdd");
};
/**更新状态栏 */
const categoryTypeChangeHandle = async (row) => {
  let res = await requestUtil.get("Category/updateCategoryType/" + row.categoryId + "/categoryType/" + row.categoryType);
  if (res.data.code == 200) {
    ElMessage.success("执行成功!");
  } else {
    ElMessage.error(res.data.msg);
    getUserCategoryList();
  }
};

onMounted(async () => {
  await getUserCategoryList();
});

/**批量删除 */
const delBtnStatus = ref(true);
const multipleSelection = ref([]);
const handleSelectionChange = (selection) => {
  multipleSelection.value = selection;
  delBtnStatus.value = selection.length == 0;
};

const handleDelete = async (id) => {
  var ids = [];
  if (id) {
    ids.push(id);
  } else {
    multipleSelection.value.forEach((row) => {
      ids.push(row.categoryId);
    });
  }
  const res = await requestUtil.post("Category/delete", ids);
  if (res.data.code == 200) {
    ElMessage.success("删除成功!");
    getUserCategoryList();
  } else {
    ElMessage.error(res.data.msg);
  }
};

/** 将草稿设置为博客正文 */
const changeTypeId = async (id) => {
  let res = await requestUtil.get("Category/changeCategoryType/" + id);
  if (res.data.code == 200) {
    ElMessage.success("执行成功!");
  } else {
    ElMessage.error("修改博客分类出现错误，请联系工作人员");
  }
  getUserCategoryList();
};
  </script>
  <style>
.page {
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

.managerAllHeaderView {
  display: grid;
  grid-template-rows: 1fr;
  grid-template-columns: 1fr 1fr;
}

.leftView,
.rightView {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/**头部的css */

.el-header {
  width: 100%;
  height: 8%;
  padding-left: 0px;
  padding-right: 0px;
  display: flex;
}

/**主体部分css */

.el-main {
  height: 80%;
  width: 80%;
}

.categoryModel {
  height: 80%;
  width: 100%;
  display: flex;
}

.categoryModelImg,
.categoryModelName,
.categoryModelBrief,
.categoryModelAction {
  display: flex;
  align-items: center;
  justify-content: center;
}

.categoryModelImg {
  flex: 2;
}

.categoryModelName {
  flex: 3;
}

.categoryModelBrief {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 5;
}

.categoryModelAction {
  flex: 1;
}

.categoryTypeShare {
  width: 200px;
  height: 55px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/**设置按钮的样式 */
.slotBox {
  height: 100%;
  width: 100%;
  display: flex;
}

.slotBtnBoxOne,
.slotBtnBoxTwo,
.slotBtnBoxThree,
.slotBtnBoxFour {
  flex: 1;
}

.el-table .cell {
  overflow: visible;
}

.btnTwo {
  position: inherit;
}

/**尖耳朵样式 */
.catBtn {
  position: relative;
  border: none;
  background-color: #009688;
  color: white;
  cursor: pointer;
  transition: all 0.3s;
}

.catBtn::before,
.catBtn::after {
  content: "";
  position: absolute;
  background-color: inherit;
  transition: all 0.3s;
}

.catBtn::before {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  left: -17px;
  top: -15px;
  rotate: -50deg;
}

.catBtn::after {
  clip-path: polygon(50% 0, 30% 100%, 80% 100%);
  width: 20px;
  height: 20px;
  right: -17px;
  top: -15px;
  rotate: 50deg;
}

.catBtn:hover {
  background-color: #00796b;
}

.catBtn:hover::before,
.catBtn:hover::after {
  background-color: #fff;
}

/**圆耳朵样式 */

.bearBtn {
  position: relative;
  border: none;
  background-color: #009688;
  color: white;
  cursor: pointer;
  transition: all 0.3s;
}

.bearBtn::before,
.bearBtn::after {
  content: "";
  position: absolute;
  background-color: inherit;
  transition: all 0.3s;
}

.bearBtn::before {
  clip-path: ellipse(15% 50% at 50% 50%);
  width: 20px;
  height: 20px;
  left: -17px;
  top: -15px;
  rotate: -50deg;
}

.bearBtn::after {
  clip-path: ellipse(15% 50% at 50% 50%);
  width: 20px;
  height: 20px;
  right: -17px;
  top: -15px;
  rotate: 50deg;
}

.bearBtn:hover {
  background-color: #00796b;
}

.bearBtn:hover::before,
.bearBtn:hover::after {
  background-color: #fff;
}
</style>