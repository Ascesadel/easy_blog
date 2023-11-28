<!-- 查看博客的详情 -->
<template>
  <div class="colorBg">
    <div class="infoPage">
      <div class="container">
        <!--头部-->
        <el-header class="infoHeader">
          <div class="infoName" id="Geats">{{ categoryInfo.categoryName }}</div>
          <div class="infoBrief">
            <div class="infoBriefBox">
              <div class="infoBriefBtn">
                <el-button class="btn" type="danger" @click="TurnToManager()">
                  返回
                </el-button>
              </div>
              <div class="infoBriefText">
                {{ categoryInfo.categoryBrief }}
              </div>
            </div>
          </div>
          <div class="infoUser">
            <div class="infoAuthor">作者：{{ categoryInfo.username }}</div>
            <div class="infoTime">创建时间：{{ categoryInfo.createTime }}</div>
          </div>
        </el-header>
        <!--主体部分-->
        <el-main class="main">
          <div class="mainForBox">
            <div class="mainFor" v-for="(item, infoListNum) in infoList" :key="infoListNum">
              <div v-html="
                  highlight(
                    item.infoListTitle,
                    item.infoListPut,
                    item.infoListCssL,
                    item.infoListCssR
                  )
                "></div>
            </div>
          </div>
          <div class="mainFooter">
            <div class="footerL">
              <div v-if="prevCategorySort != -1" @click="TurnToPrevCategory">
                上一章:{{ prevCategoryName }}
              </div>
              <div v-else>已经到顶了</div>
            </div>
            <div class="footerR">
              <div v-if="nextCategorySort != -1" @click="TurnToNextCategory">
                下一章:{{ nextCategoryName }}
              </div>
              <div v-else>已经到底了</div>
            </div>
          </div>
        </el-main>
      </div>
    </div>
  </div>
</template>

<script setup>
import requestUtil from "@/util/request";
import { ref, onMounted, reactive } from "vue";
import store from "@/store";
import { ElMessage } from "element-plus";
import router from "@/router";

const theCategoryId = ref(store.getters.GET_NEWCATRGORYID);
const managerType = ref(sessionStorage.getItem("managerType"));
const currentUser = ref(store.getters.GET_USERINFO);
const currentUserId = currentUser.value.userId;

/**获取指定id的博客部分信息 */
const categoryInfo = ref([]);
const getCurrentCategoryUser = async () => {
  if (
    store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")
  ) {
    store.commit(
      "RESET_NEWCATRGORYID",
      sessionStorage.getItem("newCategoryId")
    );
    theCategoryId.value = sessionStorage.getItem("newCategoryId");
  }

  let result = await requestUtil.post("Category/findCategoryInfo/" + theCategoryId.value);
  if (result.data.code == 200) {
    categoryInfo.value = result.data.categoryInfoVo;
  } else {
    ElMessage.error("当前页面数据不存在！！！");
  }
};
/**获取指定id博客内容 */
const modelList = ref([]);
const categoryModelList = reactive([]);
const listId = ref(0);
const infoList = reactive([]);
const getCurrentCategoryData = async () => {
  // 获取所有模板信息
  const response = await requestUtil.get("blogModel/list");
  modelList.value = response.data.modelList;

  const result = await requestUtil.post("CategoryModel/list/" + theCategoryId.value);
  categoryModelList.value = result.data.categoryModelList;

  categoryModelList.value.forEach((item, index) => {
    listId.value++;
    const targetModel = modelList.value.find(
      (model) => model.id === item.modelId
    );
    if (targetModel) {
      infoList.push({
        infoListNum: listId.value,
        infoListTitle: targetModel.modelTitle,
        infoListModelId: item.modelId,
        infoListCssL: targetModel.cssLeft,
        infoListPut: item.content,
        infoListCssR: targetModel.cssRight,
      });
    }
  });
};

/**获取其他博客的信息(上一章、下一章) */
const prevCategory = ref("");
const nextCategory = ref("");
const prevCategoryName = ref();
const nextCategoryName = ref();
const prevCategorySort = ref(0);
const nextCategorySort = ref(0);
const prevCategoryId = ref(0);
const nextCategoryId = ref(0);
const getPageCategoryData = async () => {
  // 获取所有模板信息
  const response = await requestUtil.get("Category/categoryInfoPage/" + theCategoryId.value + "/userId/" + currentUserId + "/managerType/" + managerType.value);
  if (response.data.code == 200) {
    prevCategory.value = response.data.prevCategory;
    nextCategory.value = response.data.nextCategory;
    prevCategory.value.forEach((item) => {
      prevCategoryName.value = item.categoryName;
      prevCategorySort.value = item.sort;
      prevCategoryId.value = item.categoryId;
    });

    nextCategory.value.forEach((item) => {
      nextCategoryName.value = item.categoryName;
      nextCategorySort.value = item.sort;
      nextCategoryId.value = item.categoryId;
    });
  } else {
    ElMessage.error(response.data.msg);
  }
};

onMounted(async () => {
  await getCurrentCategoryUser();
  await getCurrentCategoryData();
  await getPageCategoryData();
});

/* 转换显示html格式 */
function highlight(title, str, left, right) {
  const infoList = str.split(" ");
  let strHtml = "";
  if (title == "上传图片") {
    strHtml = "<" + left + ">" + str + right + ">";
  } else {
    infoList.forEach((word) => {
      strHtml += `<${left}>${word}</${right}>`;
    });
  }
  return strHtml;
}

const TurnToManager = () => {
  sessionStorage.removeItem("newCategoryId");
  router.go(-1);
};

const TurnToPrevCategory = async () => {
  sessionStorage.removeItem("newCategoryId");
  store.commit("SET_NEWCATRGORYID", prevCategoryId.value);
  await getCurrentCategoryUser();
  infoList.splice(0);
  await getCurrentCategoryData();
  await getPageCategoryData();
};

const TurnToNextCategory = async () => {
  sessionStorage.removeItem("newCategoryId");
  store.commit("SET_NEWCATRGORYID", nextCategoryId.value);
  await getCurrentCategoryUser();
  // 清空数组
  infoList.splice(0);
  await getCurrentCategoryData();
  await getPageCategoryData();
};
</script>
<style>
.colorBg {
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

.infoPage {
  height: 100%;
  width: 96%;
  margin: 0 auto;
}

.container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

/**头部的css */

.infoHeader {
  width: 100%;
  height: 20%;
  border-radius: 30px;
  padding-left: 0px;
  padding-right: 0px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: antiquewhite;
}

.infoName,
.infoUser {
  flex: 1;
  display: flex;
}

.infoName {
  font-size: 22px;
  font-weight: bold;
  letter-spacing: 5px;
}

#Geats {
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

.infoBrief {
  width: 100%;
  flex: 2;
  color: rgb(177, 177, 170);
  font-weight: bold;
}

.infoBriefBox {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 7fr;
  grid-template-rows: 1fr;
}

.infoBriefBtn,
.infoBriefText {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.infoUser {
  width: 100%;
}

.infoAuthor,
.infoTime {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

/**主体 */
.main {
  width: 100%;
  height: 80%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background-color: white;
}

.mainForBox {
  width: 100%;
  height: 92%;
}

.mainFor {
  width: 100%;
}

.mainFooter {
  height: 8%;
  width: 100%;
  display: flex;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 5px;
}

.footerL,
.footerR {
  flex: 1;
}
</style>