import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入组件
import Antd from 'ant-design-vue';
//引入css
import 'ant-design-vue/dist/antd.css';

//使用一下
createApp(App).use(store).use(router).use(Antd).mount('#app');
