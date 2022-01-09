<template>
  <a-layout>
<!--    公共的头部-->
    <a-layout-header class="header">
      <div class="logo" />
      <a-menu
          v-model:selectedKeys="selectedKeys1"
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">nav 1</a-menu-item>
        <a-menu-item key="2">nav 2</a-menu-item>
        <a-menu-item key="3">nav 3</a-menu-item>
      </a-menu>
    </a-layout-header>
<!--    公共动态链接-->
<!--      变化的部分动态链接-->
      <router-view/>
<!--    公共的尾部-->
    <a-layout-footer style="text-align: center">
      第一个实战项目Wiki电子书
      <br>
      <hr>
<!--      <img alt="备案图标" src="webicon.png">-->
      备案号：
      <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=cdblog.live">黔ICP备2021010120号-2</a>
    </a-layout-footer>
  </a-layout>
</template>

<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>
