import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Home from '@/components/Home'
import LibraryIndex from '@/components/library/LibraryIndex'
import store from "../stroe";

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    // 下面都是固定的写法
    {
      path: '/',
      name: 'AppIndex',
      redirect: '/index',
      component: AppIndex,
      meta:{
        requireAuth: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/library',
          name: 'Library',
          component: LibraryIndex,
          meta: {
            requireAuth: true
          }
        }
      ]
    }
  ]
})

router.beforeEach((to, from , next) =>{
  if (to.meta.requireAuth) {
    if (store.state.user.username){
      next()
    }else{
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  }else{
    next()
  }
})

export default router
