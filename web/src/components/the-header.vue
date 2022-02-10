<!--提取头部组件-->
<template>
  <!--    公共的头部-->
  <a-layout-header class="header">
    <div class="logo" />
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }">
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
      <a class="login-menu" v-show="user.id">
        你好哇~:{{user.name}}
      </a>
      <a class="login-menu" @click="showLoginModal" v-show="!user.id">
        <rocket-outlined/>
        <a-space>
          登录
        </a-space>
      </a>
    </a-menu>
    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
        ok-text="出发!"
        cancel-text="再想想~"
    >
      <!--    表单-->
      <a-form :model="loginUser" :label-col="{span:6}" :wrapper-col="{span:18}">
        <a-form-item label="用户名">
          <a-input v-model:value="loginUser.loginName" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>

<!--定义的组件-->
<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
//密码加密
declare let hexMd5:any;
//盐值
declare let KEY:any;

export default defineComponent({
  //组件名字
  name: 'the-header',
  setup(){
    //用来显示登录名的变量
    const user=computed(()=> store.state.user)
    //用来登录的变量
    const loginUser=ref({
      loginName:"test",
      password:"test123"
    });
   // 显示框变量
   const loginModalVisible=ref(false);
   //loading状态
   const loginModalLoading=ref(false);
   //显示模块框
   const showLoginModal = () => {
     loginModalVisible.value=true;
   };

    //登录方法
    const login = () => {
      console.log("开始登录")
      loginModalLoading.value=true;
      loginUser.value.password=hexMd5(loginUser.value.password+KEY);
      axios.post('user/login',loginUser.value).then((response)=>{
        loginModalLoading.value=false;
        const data=response.data;
        if (data.success){
          loginModalVisible.value=false;
          message.success("已安全着陆~！");
          //commit触发store中mutations内的方法, 登录成功后给全局变量赋值
          store.commit("setUser",user.value);
        }else {
          message.error(data.message);
        }
      });
    };
   return{
      loginModalVisible,
      loginModalLoading,
      loginUser,
      user,
      showLoginModal,
      login

   };
  },
});
</script>

<style>
.login-menu{
  position: absolute;
  left: 1300px;
  /*float: right;*/
  color: white;
}
</style>