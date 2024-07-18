import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js'
import { LoadingPlugin } from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/css/index.css';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import dayjs from 'dayjs'

createApp(App)
    .use(router).use(store).use(LoadingPlugin).use(dayjs).mount('#app')
