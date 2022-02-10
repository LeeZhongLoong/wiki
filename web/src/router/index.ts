import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import AdminUser from '../views/admin/admin-user.vue'
import AdminEbook from '../views/admin/admin-ebook.vue'
import AdminCategory from '../views/admin/admin-category.vue'
import AdminDoc from '../views/admin/admin-doc.vue'
import Doc from '../views/doc.vue'
import store from "@/store";
import {Tool} from "@/util/tool";


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //懒加载,用到才加载
    // component: () => import(/* webpackChunkName: "about" */ '../views/about.vue')
  },
  {
    path: '/admin/user',
    name: 'AdminUser',
    component: AdminUser,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/ebook',
    name: 'AdminEbook',
    component: AdminEbook,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/category',
    name: 'AdminCategory',
    component: AdminCategory,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/doc',
    name: 'AdminDoc',
    component: AdminDoc,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/doc',
    name: 'Doc',
    component: Doc
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
//路由拦截
//每一次，to：新路由，from：旧路由，next：是一个方法
router.beforeEach((to,from,next)=>{
//  要不要对meta，loginRequire属性做监控拦截，item是路由整个变量
  if (to.matched.some(function (item) {
    console.log("是否需要登录校验:",item.meta.loginRequire);
    return item.meta.loginRequire
  })){
    //拿出store中的user
    const loginUser=store.state.user;
    //如果为空就跳到首页
    if (Tool.isEmpty(loginUser)){
      console.log("用户未登录!");
      next('/');
    }else {
      next();
    }
  } else {
    next();
  }
});
export default router
