import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import router from './router/index.js'
import './axios/axiosConfig'
import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import "./styles/theme.css"
import "./styles/layout.css"
import "./styles/page-polish.css"
import "./styles/page-system.css"

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueRouter);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

