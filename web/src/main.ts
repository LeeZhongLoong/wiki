import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
//导入图标
import * as Icons from '@ant-design/icons-vue';
import axios from "axios";
//统一访问路径
axios.defaults.baseURL=process.env.VUE_APP_URL;
/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config){
    console.log('请求参数:',config);
    return config;
},function (error){
    console.log("请求返回错误",error)
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response){
    console.log("返回结果",response);
    return response;
},function (error){
    console.log("返回错误",error)
    return Promise.reject(error);
});



const app=createApp(App)
app.use(Antd).use(store).use(router).mount('#app')

//全局使用图标
const icons:any=Icons;
for (const i in icons) {
    app.component(i,icons[i]);
}
//打印日志
console.log("服务端:",process.env.VUE_APP_URL);
console.log("环境:",process.env.NODE_ENV);

