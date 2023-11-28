<template>
  <div class="catagoryModelAddPage">
    <el-container class="catagoryModelInfoAdd">
      <!--头部 展示所有的部件-->
      <el-header class="modelInfoHeader">
        <div class="headerSroll" :class="{ headerSrollFlex: modelList.length > 9 }">
          <el-scrollbar>
            <div class="scrollbar-flex-content">
              <div v-for="(item, index) in modelList" :key="index" class="scrollbar-item">
                <div class="scrollbarItemBox">
                  <div class="scrollbarItemBoxLeft">
                    <div class="scrollbarItemBoxText" @click="handleDialogValue(item.modelTitle, item.id)">
                      {{ item.modelTitle }}
                    </div>
                  </div>
                  <div class="scrollbarItemBoxRight">
                    <el-popconfirm title="您确定要删除这个模板吗？" @confirm="removeModelItem(item.id)">
                      <template #reference>
                        <Icon class="scrollbarItemBoxIcon" style="font-size: 25px" id="ForIcon" :iconHref="'#icon-shanchu'" />
                      </template>
                    </el-popconfirm>
                  </div>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
        <div class="headerSrollItem">
          <!--添加图片-->
          <div class="scrollbar-item" @click="handleDialogImgValue">
            图片上传
          </div>
          <!--添加新的css模板-->
          <div class="scrollbar-item">
            <Icon class="mainLForItemIcon" style="font-size: 45px" :iconHref="'#icon-tianjia'" @click="handleModelDialogValue()" />
          </div>
        </div>
      </el-header>
      <!--主体部分-->
      <el-main id="classA" class="modelInfoMain">
        <!--左边部分用于显示组件和内容-->
        <div class="mainL">
          <div class="mainLMain">
            <TransitionGroup name="list" tag="div" class="container">
              <div class="mainLFor" v-for="(item, arrNum) in arr" :key="arrNum" draggable="true" @dragstart="dragstart($event, arrNum)" @dragenter="dragenter($event, arrNum)" @dragend="dragend" @dragover="dragover">
                <div class="mainLForItem">
                  <div class="mainLForItemOne">
                    <Icon class="mainLForItemIcon" style="font-size: 20px" :iconHref="'#icon-bianji-copy'" @click="removeItem(arrNum)" />
                  </div>
                  <div class="mainLForItemTwo">{{ item.arrTitle }}</div>
                  <div class="mainLForItemThree" v-if="containsImg(item.arrPut).hasImage" v-html="containsImg(item.arrPut).value"></div>
                  <div class="mainLForItemThree" v-else>
                    {{ containsImg(item.arrPut).value }}
                  </div>
                  <div class="mainLForItemFor">
                    <Icon id="ForIcon" style="font-size: 20px" :iconHref="'#icon-tabguanbi'" @click="removeItem(arrNum)" />
                  </div>
                </div>
              </div>
            </TransitionGroup>
          </div>
          <div class="mainLFoot">
            <!-- 测试提交 -->
            <el-button type="danger" @click="submitArr()"> 提交 </el-button>
            <el-button type="danger" @click="submitTest()">保存草稿</el-button>
            <el-button type="danger" @click="TurnToManager()"> 退出 </el-button>
          </div>
        </div>
        <!--右边部分用于展示html格式结果-->
        <div class="mainR">
          <div class="mainRFor" v-for="(item, arrNum) in arr" :key="arrNum">
            <div v-html="
                highlight(
                  item.arrTitle,
                  item.arrPut,
                  item.arrCssL,
                  item.arrCssR
                )
              "></div>
          </div>
        </div>
      </el-main>
    </el-container>
    <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :dialogTitle="dialogTitle" :dialogModelId="dialogModelId" @inputStr="inputStr" />
    <modelDialog v-model="modelDialogVisible" :modelDialogVisible="modelDialogVisible" @getModelList="getModelList" />
    <imgDialog v-model="dialogImgVisible" :dialogImgVisible="dialogImgVisible" @inputStr="inputStr" />
  </div>
</template>

<script setup>
import requestUtil from "@/util/request";
import Dialog from "../components/blog/dialog.vue";
import modelDialog from "../components/blog/categoryModelAddDialog.vue";
import imgDialog from "../components/blog/dialogImg.vue";
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import store from "@/store";
import router from "@/router";
/* 获取 模板信息的数据 如果有之前填写的使用的数据则加载 */
const modelList = ref([]);
const imgList = ref([]);
const completeModelList = reactive([]);
const categoryModelList = reactive([]);
const listId = ref(0);
const theCategoryId = ref();

const getModelList = async () => {
  if (
    store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")
  ) {
    store.commit(
      "RESET_NEWCATRGORYID",
      sessionStorage.getItem("newCategoryId")
    );
  }
  theCategoryId.value = store.getters.GET_NEWCATRGORYID;

  try {
    const response = await requestUtil.get("blogModel/list");
    modelList.value = response.data.modelList.filter((item) => item.id !== 9);
    imgList.value = response.data.modelList.filter((item) => item.id == 9);
  } catch (error) {
    console.error(error);
  }
};

/**重新拼接(获取之前的博客内容) */
const getModelData = async () => {
  try {
    completeModelList.value = [...modelList.value, ...imgList.value];
    const result = await requestUtil.post(
      "CategoryModel/list/" + theCategoryId.value
    );
    categoryModelList.value = result.data.categoryModelList;

    categoryModelList.value.forEach((item, index) => {
      listId.value++;
      const targetModel = completeModelList.value.find(
        (model) => model.id === item.modelId
      );
      if (targetModel) {
        arr.push({
          arrNum: listId.value,
          arrTitle: targetModel.modelTitle,
          arrModelId: item.modelId,
          arrCssL: targetModel.cssLeft,
          arrPut: item.content,
          arrCssR: targetModel.cssRight,
        });
      }
    });
  } catch (error) {
    console.error(error);
  }
};

onMounted(async () => {
  await getModelList();
  await getModelData();
});
/** 结束 */

/**html格式特殊字符转义 */
function escapeHtml(str) {
  return str
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/ /g, "&nbsp;")
    .replace(/'/g, "&#39;");
}

/* 转换显示html格式 */
function highlight(title, str, left, right) {
  const arr = str.split(" ");
  let strHtml = "";
  if (title == "上传图片") {
    strHtml = "<" + left + ">" + str + right + ">";
  } else {
    arr.forEach((word) => {
      strHtml += `<${left}>${word}</${right}>`;
    });
  }
  return strHtml;
}
/** 结束 */
/**将数据传递到dialog中 */
const dialogVisible = ref(false);
const dialogTitle = ref("");
const dialogModelId = ref(0);
const handleDialogValue = (title, modelId) => {
  dialogTitle.value = title;
  dialogModelId.value = modelId;
  dialogVisible.value = true;
};

/**将数据传递到dialog中 */
const modelDialogVisible = ref(false);
const handleModelDialogValue = () => {
  modelDialogVisible.value = true;
};

/**将数据传递到dialog中 */
const dialogImgVisible = ref(false);
const dialogImgTitle = ref("");
const dialogImgModelId = ref(0);
const handleDialogImgValue = (title, modelId) => {
  dialogImgTitle.value = title;
  dialogImgModelId.value = modelId;
  dialogImgVisible.value = true;
};

/**组装数据 */
const arr = reactive([]);
const halfArr = reactive([]);
const getStr = ref("");
const arrPut = ref("");
const arrTitle = ref("");
const arrNum = ref(0);
const arrCssL = ref("");
const arrCssR = ref("");
const inputStr = (value) => {
  listId.value++;
  dragIndex++;
  let imgHtml = "";
  const { inputStr, title, modelId } = value;
  halfArr.value = modelList.value.find((model) => model.id === modelId);
  if (halfArr.value) {
    getStr.value = inputStr;
  } else {
    halfArr.value = imgList.value.find((model) => model.id === modelId);
    getStr.value = inputStr;
  }
  const newArr = {
    arrNum: listId.value,
    arrTitle: title,
    arrModelId: modelId,
    arrCssL: halfArr.value.cssLeft,
    arrPut: getStr.value,
    arrCssR: halfArr.value.cssRight,
  };
  arr.push(newArr);
};

/**左侧显示图片文本修改为显示图片 */
function containsImg(str) {
  if (str.indexOf("<img") !== -1) {
    // 存在图片
    let startIndex = str.indexOf('src="') + 5;
    let endIndex = str.indexOf('"', startIndex);
    const imagePath = str.substring(startIndex, endIndex);
    let newImgHtml = '<img style="height: 100%;width: 100%;object-fit: contain" src="' + imagePath + '" />';
    return { hasImage: true, value: newImgHtml };
  } else {
    // 不存在图片
    return { hasImage: false, value: str };
  }
}

/** 测试提交 */
const submitArr = async () => {
  // 检查 arr 是否为空
  if (!arr || arr.length === 0) {
    ElMessage.warning("请先添加博客内容");
    return;
  }

  const categoryModelArr = arr.map((item, index) => ({
    categoryId: theCategoryId.value,
    modelId: item.arrModelId,
    modelSort: index,
    content: item.arrPut,
  }));

  let result = await requestUtil.post("CategoryModel/save", categoryModelArr);
  let data = result.data;
  if (data.code == 200) {
    let updateSelf = await requestUtil.get("Category/changeCategoryType/" + theCategoryId.value);
    let newData = updateSelf.data;
    if (newData.code == 200) {
      ElMessage.success(data.msg);
      sessionStorage.removeItem("newCategoryId");
      router.replace("/manager");
    }
  } else {
    ElMessage.error(data.msg);
  }
};

/** 提交到草稿箱 */
const submitTest = async () => {
  // 检查 arr 是否为空
  if (!arr || arr.length === 0) {
    ElMessage.warning("请先添加博客内容");
    return;
  }

  const categoryModelArr = arr.map((item, index) => ({
    categoryId: theCategoryId.value,
    modelId: item.arrModelId,
    modelSort: index,
    content: item.arrPut,
  }));

  let result = await requestUtil.post("CategoryModel/save", categoryModelArr);
  let data = result.data;
  if (data.code == 200) {
    let updateSelf = await requestUtil.get("Category/draftCategoryType/" + theCategoryId.value);
    let newData = updateSelf.data;
    if (newData.code == 200) {
      ElMessage.success(data.msg);
      sessionStorage.removeItem("newCategoryId");
      router.replace("/manager");
    }
  } else {
    ElMessage.error(data.msg);
  }
};

/** 删除 */
function removeItem(index) {
  arr.splice(index, 1);
}

/** 删除某个模板信息 */
const removeModelItem = async (id) => {
  const res = await requestUtil.get("blogModel/deleteModel/" + id);
  if (res.data.code == 200) {
    ElMessage.success("删除模板成功!");
    getModelList();
  } else {
    ElMessage.error(res.data.msg);
  }
};

/** 拖拽方法 */
let dragIndex = 0;
function dragstart(e, index) {
  e.stopPropagation();
  dragIndex = index;
  setTimeout(() => {
    e.target.classList.add("moveing");
  }, 0);
}
function dragenter(e, index) {
  e.preventDefault();
  // 拖拽到原位置时不触发
  if (dragIndex !== index) {
    const source = arr[dragIndex];
    arr.splice(dragIndex, 1);
    arr.splice(index, 0, source);
    // 更新节点位置
    dragIndex = index;
  }
}
function dragover(e) {
  e.preventDefault();
  e.dataTransfer.dropEffect = "move";
}
function dragend(e) {
  e.target.classList.remove("moveing");
}

/**返回到管理的界面 */
const TurnToManager = () => {
  sessionStorage.removeItem("newCategoryId");
  router.push("/manager");
};
</script>
<style>
.catagoryModelAddPage {
  height: 100%;
  width: 100%;
}
.catagoryModelInfoAdd {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-rows: 1fr 7fr;
  grid-template-columns: 1fr;
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

.modelInfoHeader,
.modelInfoMain {
  height: 100%;
  width: 100%;
}

.modelInfoHeader {
  display: flex;
  align-items: center;
}

.blogTop {
  border: 1px solid;
}

.blogMain {
  border: 1px solid;
}

#classA {
  display: flex;
  flex-direction: row;
}

.mainL {
  flex: 2;
  margin-right: 5px;
  background-color: white;
}

/**主体的头部 */

.headerSroll {
  flex: 5;
}

.headerSrollFlex {
  flex: 7;
}

.headerSrollItem {
  flex: 1;
  display: flex;
}

/**header循环添加滑动块 */
.scrollbar-flex-content {
  display: flex;
  height: 100%;
}

.scrollbar-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 120px;
  height: 45px;
  margin: 10px 10px 0 10px;
  border-radius: 15px;
  background: var(--el-color-danger-light-9);
  overflow: hidden;
}

.scrollbarItemBox {
  height: 100%;
  width: 100%;
  display: flex;
}

.scrollbarItemBoxLeft {
  flex: 7;
  max-width: 95px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.scrollbarItemBoxText {
  height: 20px;
  width: 80%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: var(--el-color-danger);
}

.scrollbarItemBoxRight {
  flex: 1;
}

/**主体左边 */
.mainLMain {
  height: 92%;
  width: 100%;
}

.mainLFoot {
  height: 8%;
  width: 100%;
  text-align: center;
}

.mainLFor {
  width: 96%;
  max-width: 280px;
  height: 30px;
  line-height: 30px;
  margin: 0 auto;
  margin-top: 2px;
  border: 1px solid black;
}

.mainLForItem {
  height: 100%;
  width: 100%;
  display: flex;
}

#ForIcon {
  position: relative;
  float: right;
  right: 0;
  top: 0;
  font-size: 21px;
}

.mainLForItemOne,
.mainLForItemTwo,
.mainLForItemThree,
.mainLForItemFor {
  height: 100%;
  overflow: hidden;
}

.mainLForItemOne,
.mainLForItemFor {
  width: 10%;
}

.mainLForItemTwo {
  width: 45%;
  color: #f56c6c;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.mainLForItemThree {
  width: 35%;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/**主体右边 */
.mainR {
  flex: 8;
  margin-left: 5px;
  background-color: white;
}

.mainRFor {
  width: 100%;
}

/* 对移动中的元素应用的过渡 */
.list-move,
.list-enter-active,
.list-leave-active {
  transition: all 0.2s ease;
}
</style>