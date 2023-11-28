import { createStore } from 'vuex'
import router from '@/router';

export default createStore({
  state: {
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")) || {},
    newCategoryId: sessionStorage.getItem("newCategoryId") || null,
    managerType: sessionStorage.getItem("managerType") || null
  },
  getters: {
    GET_USERINFO: state => {
      return state.userInfo;
    },
    GET_NEWCATRGORYID: state => {
      return state.newCategoryId || sessionStorage.getItem("newCategoryId");
    },
    GET_MANAGERTYPE: state => {
      return state.managerType || sessionStorage.getItem("managerType");
    }
  },
  mutations: {
    SET_USERINFO: (state, userInfo) => {
      const user = JSON.stringify(userInfo);
      state.userInfo = JSON.parse(user);
      sessionStorage.setItem("userInfo", user);
    },
    SET_NEWCATRGORYID: (state, newCategoryId) => {
      sessionStorage.setItem("newCategoryId", newCategoryId);
    },
    RESET_NEWCATRGORYID: (state, rightId) => {
      state.newCategoryId = rightId;
    },
    SET_MANAGERTYPE: (state, managerType) => {
      sessionStorage.setItem("managerType", managerType);
    }
  },
  actions: {
    logout() {
      window.sessionStorage.clear();
      router.replace("/login")
    }
  },
  modules: {
  }
})
