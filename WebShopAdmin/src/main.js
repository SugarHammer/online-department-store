import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import global from './global/global'
import VueI18n from 'vue-i18n'

//bootstrap
import 'bootstrap'
import $ from 'jquery'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'


//element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/element-variables.scss'

//v-charts
import VCharts from 'v-charts'


//style
import './assets/css/reset.css'
import './assets/css/index.css'


Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(global)
Vue.use(VCharts)
Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: 'zh-CN',    // 语言标识
  //this.$i18n.locale // 通过切换locale的值来实现语言切换
  messages: {
    'zh-CN': require('./common/lang/zh'),   // 中文语言包
    'en-US': require('./common/lang/en')    // 英文语言包
  }
})


new Vue({
  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
