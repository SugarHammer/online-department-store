import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import{request} from "../network/request";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../components/login/Login')
  },
  {
    path: '/backstage',
    name: 'BackStage',
    component: () => import('../components/backstage/BackStageController'),
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../components/backstage/Home')
      },
      {
        path: 'userInfo',
        name: 'UserInfo',
        component: () => import('../components/backstage/UserInfo'),
        meta:{
          keepAlive :true //需要被缓存
        }
      },
      {
        path: 'goodsInfo',
        name: 'GoodsInfo',
        component: () => import('../components/backstage/GoodsInfo')
      },
      {
        path: 'cartInfo',
        name: 'CartInfo',
        component: () => import('../components/backstage/CartInfo')
      },
      {
        path: 'orderInfo',
        name: 'OrderInfo',
        component: () => import('../components/backstage/OrderInfo')
      },
      {
        path: 'advertInfo',
        name: 'AdvertInfo',
        component: () => import('../components/backstage/AdvertInfo')
      },
      {
        path: 'commentInfo',
        name: 'CommentInfo',
        component: () => import('../components/backstage/CommentInfo')
      },
      {
        path: 'storeInfo',
        name: 'StoreInfo',
        component: () => import('../components/backstage/StoreInfo')
      },
      {
        path: 'userAnalyze',
        name: 'UserAnalyze',
        component: () => import('../components/backstage/UserAnalyze')
      },
      {
        path: 'goodsAnalyze',
        name: 'GoodsAnalyze',
        component: () => import('../components/backstage/GoodsAnalyze')
      },
      {
        path: 'orderAnalyze',
        name: 'OrderAnalyze',
        component: () => import('../components/backstage/OrderAnalyze')
      },
      {
        path: 'emptyData',
        name: 'EmptyData',
        component: () => import('../components/backstage/EmptyData')
      },
      {
        path: 'tableRowCount',
        name: 'TableRowCount',
        component: () => import('../components/backstage/TableRowCount')
      },
    ]
  }
]

const router = new VueRouter({
  // mode: 'history',
  routes
})
router.beforeEach((to, from, next) => {
  if (to.matched[0].path === '/backstage'){
    request({
      url: 'login/checkLogin'
    }).then( res => {
      if( res.code === '000'){
        next();
      } else {
        next('/login')
      }
    }).catch( err => {next('/login')})
  } else {
    next();
  }
})

export default router
