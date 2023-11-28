import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 添加全局样式
import './assets/css/global.css'
// 引入iconfont
import './assets/font/iconfont.css'
import './assets/font/iconfont.js'

// 汉化
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import '@/router/permission'

const app = createApp(App)
app.use(ElementPlus, {
    locale: zhCn,
})
app.use(store);
app.use(router);
app.mount('#app');
