<template>
  <a-layout>
<!--    公共的头部-->
<!--    使用头部组件-->
    <the-header></the-header>
<!--    公共动态链接-->
<!--      变化的部分动态链接-->
      <router-view/>
<!--    公共的尾部-->
<!--    引入尾部组件-->
    <the-footer></the-footer>
  </a-layout>
</template>

<!--导入组件-->
<script lang="ts">
import { defineComponent } from 'vue';
//导入组件不能用横线要用驼峰式命名
//头部组件
import TheHeader from '@/components/the-header.vue';
//尾部组件
import TheFooter from '@/components/the-footer.vue';
export default defineComponent({
  //组件名字
  name: 'app',
  components:{
    TheHeader,
    TheFooter
  },
});
</script>

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
