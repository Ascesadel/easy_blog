import { createRouter, createWebHashHistory } from 'vue-router'
import Register from '../views/Register.vue'
import test from '../views/TestDr.vue'

const routes = [
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/homeIndex.vue'),
    redirect: '/manager',
    children: [
      {
        path: '/manager',
        name: '个人博客',
        component: () => import('../views/categoryManager.vue'),
      },
      {
        path: '/blogAdd',
        name: '新增博客',
        component: () => import('../views/categoryAdd.vue'),
      },
      {
        path: '/blogModelAdd',
        name: '博客的模板',
        component: () => import('../views/categoryModelAdd.vue'),
      },
      {
        path: '/allManager',
        name: '全部博客',
        component: () => import('../views/categoryAllManager.vue'),
      },
      {
        path: '/info',
        name: '博客详情',
        component: () => import('../views/categoryInfo.vue'),
      },
      {
        path: '/userCenter',
        name: '用户个人中心',
        component: () => import('../views/userCenter.vue'),
      },
      {
        path: '/draft',
        name: '博客草稿',
        component: () => import('../views/categoryDraft.vue'),
      },
      {
        path: '/city',
        name: '天气预报',
        component: () => import('../views/weatherReport'),
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/test',
    name: 'test',
    component: test
  },
  // {
  //   path: '/blogModelAdd',
  //   name: '博客的模板',
  //   component: test
  // },
  // {
  //   path: '/blogAdd',
  //   name: '新增博客',
  //   component: () => import('../views/categoryManager.vue')
  // },
  // {
  //   path: '/manager',
  //   name: '个人博客',
  //   component: () => import('../views/categoryManager.vue')
  // },
  // {
  //   path: '/allManager',
  //   name: '全部博客',
  //   component: () => import('../views/categoryAllManager.vue')
  // },
  // {
  //   path: '/info',
  //   name: '博客详情',
  //   component: () => import('../views/categoryInfo.vue')
  // },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
