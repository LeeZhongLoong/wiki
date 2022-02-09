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
      <a class="login-menu" @click="showLoginModal">
        <a-space>
          <rocket-outlined/>
          <span>登录</span>
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
          <a-input v-model:value="loginUser.password" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>

<!--定义的组件-->
<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';

export default defineComponent({
  //组件名字
  name: 'the-header',
  setup(){
    const loginUser=ref({
      loginName:"test",
      password:"test"
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
    };
   return{
      loginModalVisible,
      loginModalLoading,
      loginUser,
      showLoginModal,
      login,
   };
  },
});
</script>

<style>
.login-menu{
  position: relative;
  left: 1300px;
  color: white;
  float: right;
}
</style>