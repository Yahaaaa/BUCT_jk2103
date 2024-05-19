import { createApp } from 'vue'
import { ref } from 'vue';
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/js/bootstrap.min.js'
// import VueImageZoomer from 'vue-image-zoomer'
// import 'vue-image-zoomer/dist/style.css';
const isLoggedIn=ref(false);
const UserId=ref("0");
const app=createApp(App)
app.use(router);
app.use(store);
app.use(ElementPlus)
// app.provide('$Url', (String)('http://106.53.175.27:8000'));
// app.provide('$Url', (String)('http://localhost:8443'));
app.provide('$Url', (String)('http://localhost:8080'));
// app.provide('$isLoggedIn', isLoggedIn);
// app.provide('$UserId',UserId)
app.mount('#app')


