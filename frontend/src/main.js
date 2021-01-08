import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Axios from 'axios'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'

Vue.config.productionTip = false;

// Bootstrap
Vue.use(BootstrapVue);
Vue.use(IconsPlugin)

Vue.prototype.$http = Axios;
const token = localStorage.getItem('token')

if (token) {
    Vue.prototype.$http.defaults.headers.common['Authorization'] = token
}

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');

