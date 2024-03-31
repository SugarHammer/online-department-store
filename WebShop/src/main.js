import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueI18n from 'vue-i18n'
import axios from 'axios';

Vue.use(ElementUI);
Vue.use(VueI18n)

// 配置axios
Vue.prototype.$axios = axios;
// 配置公共的url
axios.defaults.baseURL = '/api';
//axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.config.productionTip = false;

const i18n = new VueI18n({
    locale: 'zh-CN',    // 语言标识
    //this.$i18n.locale // 通过切换locale的值来实现语言切换
    messages: {
        'zh-CN': require('./common/lang/zh'),   // 中文语言包
        'en-US': require('./common/lang/en')    // 英文语言包
    }
})



new Vue({
    router,
    i18n,
    render: h => h(App)
}).$mount('#app');

router.beforeEach((to, from, next) => {
    if (to.meta.auth) {
        if (localStorage.getItem('user')) {
            next();
        } else {
            alert("请先登陆！");
            next(false);
        }
    } else {
        next();
    }
});