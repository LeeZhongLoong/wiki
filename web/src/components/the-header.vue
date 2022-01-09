<!--提取头部组件-->
<template>
  <!--    公共的头部-->
  <a-layout-header class="header">
    <div class="logo" />
    <a-menu
        v-model:selectedKeys="selectedKeys1"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="1">nav 111</a-menu-item>
      <a-menu-item key="2">nav 2</a-menu-item>
      <a-menu-item key="3">nav 3</a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<!--定义的组件-->
<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  //组件名字
  name: 'the-header'
});
</script>