import router from "@/router/index";
import store from "@/store";
import { ElMessage } from "element-plus";

router.beforeEach((to, from, next) => {
    const whiteList = ['/login', '/register', '/test']
    let userInfo = store.getters.GET_USERINFO;
    if (JSON.stringify(userInfo) != "{}") {
        next();
    } else {
        if (whiteList.includes(to.path)) {
            next();
        } else {
            ElMessage("请先登录");
            next("/login");
        }
    }
})