<template>
  <div class="weather">
    <div class="weatherHeader">

      <div class="weatherHeaderTop">
        <div class="weatherHeaderTopLeft">
          <el-button class="backBtn" type="primary" @click="BackToCenter()">返回个人中心</el-button>
        </div>
        <div class="weatherHeaderTopRight">
          <div class="weatherHeaderTopRightLeft">
            <el-select v-model="choosedArea" class="m-2" style="width: 200px;" clearable placeholder="请选择地区">
              <el-option v-for="(item,index) in areaList" :key="index" :label="item.area" :value="item.area" />
            </el-select>
          </div>
          <div class="weatherHeaderTopRightMid">
            <el-select v-model="choosedCity" class="m-2" style="width: 200px;" placeholder="请选择城市" :disabled="!choosedArea">
              <el-option v-for="(item,index) in cityList" :key="index" :label="item.city" :value="item.value" @click="getLabel(item.city)" />
            </el-select>
          </div>
          <div class="weatherHeaderTopRightRight">
            <el-button type="primary" style="width: 120px;" @click="getWaetherInfo()" :disabled="!choosedCity">查询</el-button>
          </div>

        </div>
      </div>
      <div class="weatherHeaderMid">
        <meteorBg class="weatherHeaderBg" />
      </div>
    </div>
    <div class="weatherMain" v-loading="loading" element-loading-text="正在加载天气数据..." element-loading-background="rgba(122, 122, 122, 0.8)">
      <div class="weatherMainBox">
        <div class="weatherMainHeader" v-if="pyFlag">
          <div class="currentWeatherBox">
            <div class="currentWeatherBoxMain">{{choosedCityName}} 当前温度:{{ currentTem.temperature }}度 </div>
            <div class="currentWeatherBoxFooter">
              <div class="currentWeatherBoxFooterText">当前风向:{{ currentTem.windDirection }} </div>
              <div class="currentWeatherBoxFooterText">当前风速:{{ currentTem.windSpeed }} m/s</div>
            </div>
            <div style="position: absolute;right: 12%;">最后更新时间:{{ currentTem.lastUpdate }} </div>
          </div>
        </div>
        <div class="weatherMainBody">
          <div v-for="(item,index) in weatherLisets" :key="index" class="weatherMainItem">
            <div :class="index%2==0 ? 'watherBox':'watherBox2'">
              <div class="watherBoxItem">
                <div class="watherBoxItemLeft">
                  <Icon :iconHref="'#icon-shijian'" />
                </div>
                <div class="watherBoxItemMid">时间:</div>
                <div class="watherBoxItemRight">{{ item.time }}</div>
              </div>
              <div class="watherBoxItem">
                <div class="watherBoxItemLeft">
                  <Icon :iconHref="'#icon-wenduji'" />
                </div>
                <div class="watherBoxItemMid">温度:</div>
                <div class="watherBoxItemRight">{{ item.temperature }}度</div>
              </div>
              <div class="watherBoxItem">
                <div class="watherBoxItemLeft">
                  <Icon :iconHref="'#icon-fengxiang'" />
                </div>
                <div class="watherBoxItemMid">风向:</div>
                <div class="watherBoxItemRight">{{ item.wind }}</div>
              </div>
              <div class="watherBoxItem">
                <div class="watherBoxItemLeft">
                  <Icon :iconHref="'#icon-fengsu'" />
                </div>
                <div class="watherBoxItemMid">风速:</div>
                <div class="watherBoxItemRight">{{ item.velocity }} m/s</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted, watch } from "vue";
import requestUtil, { getServerUrl } from '@/util/request'
import requestUtil2 from '@/util/requestPython'
import store from "@/store";
import router from "@/router";
import { ElMessage, ElLoading, ElMessageBox } from "element-plus";
import meteorBg from '../components/meteor.vue'
/**地区sel */
let choosedArea = ref()
let areaList = ref([])
/**城市sel */
let choosedCity = ref()
let cityList = ref([])
/**获取所有Area */
const getAreaList = async () => {
  let result = await requestUtil.post("city/getArea");
  let data = result.data;
  if (data.code == 200) {
    areaList.value = data.areas;
  } else {
    ElMessage.error("获取地区信息失败");
  }
};
/**获取所有city */
const getCityList = async () => {
  console.log(choosedArea.value)
  let result = await requestUtil.post("city/getCity?area=" + choosedArea.value);
  let data = result.data;
  if (data.code == 200) {
    cityList.value = data.citys;
  } else {
    ElMessage.error("获取城市信息失败");
  }
};

onMounted(() => {
  getWeather();
  getAreaList();
})

/**监听choosedArea */
watch(choosedArea,
  (newUserInfo, oldUserInfo) => {
    getCityList();
    choosedCity.value = null;
  }
);
// 获取天气信息
let weatherLisets = ref([])
let currentTem = ref()
let choosedCityName = ref('南京')
let loading = ref(false)
const getWaetherInfo = async () => {
  loading.value = true
  let result = await requestUtil2.get2("/getweatherbycode?code=" + choosedCity.value);
  let data = result.data;
  if (data.code == 200) {
    loading.value = false
    weatherLisets.value = data.data.lists;
    currentTem.value = data.data.dict;
    choosedCityName.value = labelName.value
  } else {
    console.log("连接天气接口失败")
  }
};

// 获取label内容
let labelName = ref()
function getLabel(name) {
  labelName.value = name
}

let pyFlag = ref(false)
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
      currentTem.value = data.data.dict;
      weatherLisets.value = data.data.lists;
    } else {
      console.log("连接天气接口失败")
    }
  } catch (error) {
    console.log("网络连接错误")
  }
};

const BackToCenter = () => {
  router.replace("/userCenter")
};
</script>
<style>
.weather {
  height: 100%;
  width: 100%;
  background-color: black;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 3fr 2fr;
}

/* 上半部分 */
.weatherHeader {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr 6fr;
}

.weatherHeaderTop {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 4fr;
  grid-template-rows: 1fr;
  border-bottom: 1px solid skyblue;
  box-sizing: border-box;
}

.weatherHeaderTopLeft,
.weatherHeaderTopRight {
  height: 100%;
  width: 100%;
}

.weatherHeaderTopLeft {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.weatherHeaderTopRight {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 3fr 3fr 2fr;
  grid-template-rows: 1fr;
}

.weatherHeaderTopRightLeft,
.weatherHeaderTopRightMid,
.weatherHeaderTopRightRight {
  height: 100%;
  width: 100%;
  display: grid;
  align-items: center;
  justify-content: center;
}

.weatherHeaderMid {
  height: 100%;
  width: 100%;
}

/* 下半部分 */
.weatherMain {
  height: 100%;
  width: 100%;
  border-top: 3px solid skyblue;
  box-sizing: border-box;
}

.weatherMainBox {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 3fr 4fr;
}

.weatherMainHeader {
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
  display: flex;
  align-items: center;
  justify-content: center;
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
  display: flex;
  align-items: center;
  justify-content: center;
}

.weatherMainBody {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr;
}

.weatherMainItem {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.watherBox,
.watherBox2 {
  height: 80%;
  width: 80%;
  text-align: center;
  color: snow;
  border: 2px solid gray;
  box-sizing: border-box;
  box-shadow: 1px 1px 0px 1px;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr 1fr 1fr 1fr;
}

.watherBox2 {
  background-color: rgba(189, 186, 186, 0.459);
}

.watherBoxItem {
  height: 100%;
  width: 100%;
  border-bottom: 1px solid rgb(241, 217, 163);
  box-sizing: border-box;
  display: grid;
  grid-template-columns: 1fr 2fr 3fr;
  grid-template-rows: 1fr;
}

.watherBoxItemLeft,
.watherBoxItemMid,
.watherBoxItemRight {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>