<template>
  <div class="managerPage">
    <el-container>
      <!--头部-->
      <el-header class="managerHeaderView">
        <div class="leftView">
          <el-popconfirm title="您确定要删除这条记录吗？" @confirm="handleDelete(null)">
            <template #reference>
              <el-button type="danger" :disabled="delBtnStatus">
                <Icon :iconHref="'#icon-shanchu-copy'" />&nbsp;&nbsp;批量删除
              </el-button>
            </template>
          </el-popconfirm>
        </div>
        <div class="rightView">
          <el-button type="danger" @click="TurnToManager()">
            返回
          </el-button>
          <el-button type="danger" @click="addCategory()"> 草稿箱 </el-button>
          <el-button type="danger" @click="addCategory()"> 添加 </el-button>
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
          <el-table-column prop="categorySelf" label="查看权限" width="200" min-width="200" align="center">
            <template v-slot="{ row }">
              <el-switch v-model="row.categorySelf" @change="categorySelfChangeHandle(row)" active-text="共享" inactive-text="仅自己可见" active-value="0" inactive-value="1">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="action" label="操作" width="400" min-width="400" fixed="right" align="center">
            <template v-slot="scope">
              <div class="slotBox">
                <div class="slotBtnBoxOne">
                  <el-button class="catBtn" type="primary" @click="postInfoId(scope.row.categoryId)">
                    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;详情
                  </el-button>
                </div>
                <div class="slotBtnBoxTwo">
                  <el-button class="btnTwo" type="primary" @click="handleCatrgoryValue(scope.row.categoryId)">
                    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;编辑
                  </el-button>
                </div>
                <div class="slotBtnBoxThree" v-if="scope.row.sort != 0">
                  <el-button type="primary" class="bearBtn" @click="handleListTop(scope.$index)">
                    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;置顶
                  </el-button>
                </div>
                <div class="slotBtnBoxFour">
                  <el-popconfirm title="您确定要删除这条记录吗？" @confirm="handleDelete(scope.row.categoryId)">
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
import { ElMessage, ElLoading } from "element-plus";
import Sortable from "sortablejs";

let currentUser = ref(store.getters.GET_USERINFO);
let managerType = ref(sessionStorage.getItem("managerType"));

const categoryVoList = ref([]);
const categoryId = ref(0);
const getCategoryList = async () => {
  const response = await requestUtil.get("Category/voList");
  categoryVoList.value = response.data.categoryVoList;
};
getCategoryList();

const TurnToManager = () => {
  store.commit("SET_MANAGERTYPE", 0);
  router.push("/manager");
};

const addCategory = () => {
  router.push("/blogAdd");
};
/**修改指定id的博客 */
const handleCatrgoryValue = (id) => {
  store.commit("SET_NEWCATRGORYID", id);
  router.push("/blogAdd");
};
/**更新状态栏 */
const categorySelfChangeHandle = async (row) => {
  let res = await requestUtil.get("Category/updateCategorySelf/" + row.categoryId + "/categorySelf/" + row.categorySelf);
  if (res.data.code == 200) {
    ElMessage.success("执行成功!");
  } else {
    ElMessage.error(res.data.msg);
    getCategoryList();
  }
};
/**测试表单行拖拽排序 */

function setSort() {
  const el = document.querySelector("#categoryTable table tbody");
  new Sortable(el, {
    sort: true,
    ghostClass: "sortable-ghost",
    onEnd: async (e) => {
      const targetRow = categoryVoList.value.splice(e.oldIndex, 1)[0];
      categoryVoList.value.splice(e.newIndex, 0, targetRow);
      // 更新sort属性
      categoryVoList.value = categoryVoList.value.map((item, index) => ({
        ...item,
        sort: index,
      }));
      let result = await requestUtil.post(
        "Category/updateVoList",
        categoryVoList.value
      );
      let data = result.data;
      if (data.code == 200) {
        let loginLoading = ElLoading.service({
          lock: true,
          text: "序列保存中...",
          background: "rgba(0, 0, 0, 0.7)",
          timer: 3000, // 动画时长为3s
        });
        categoryVoList.value = [];
        getCategoryList();
        loginLoading.close();
        ElMessage.success(data.msg);
      } else {
        ElMessage.error(data.msg);
      }
    },
  });
}

onMounted(() => {
  setSort();
});

/**内测 置顶功能 https://blog.csdn.net/u014643282/article/details/100576321*/
async function handleListTop(index) {
  categoryVoList.value.unshift(categoryVoList.value[index]);
  categoryVoList.value.splice(index + 1, 1);
  categoryVoList.value = categoryVoList.value.map((item, index) => ({
    ...item,
    sort: index,
  }));
  let result = await requestUtil.post(
    "Category/updateVoList",
    categoryVoList.value
  );
  let data = result.data;
  if (data.code == 200) {
    getCategoryList();
    ElMessage.success("修改置顶成功！");
  } else {
    ElMessage.error("修改置顶失败");
  }
  console.log(categoryVoList.value);
}

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
    getCategoryList();
  } else {
    ElMessage.error(res.data.msg);
  }
};

/** 页面跳转到详情加传递id */
const postInfoId = (id) => {
  store.commit("SET_NEWCATRGORYID", id);
  router.push("/info");
};
</script>
<style>
.managerPage {
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
  background-position: 0 0, 60px 80px, 110px 250px, 100px 130px;
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

/**头部的css */

.el-header {
  width: 100%;
  height: 8%;
  padding-left: 0px;
  padding-right: 0px;
  display: flex;
}

.managerHeaderView {
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