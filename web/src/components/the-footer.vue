<template>
<!--  设置尾部组件-->
  <!--    公共的尾部-->
  <a-layout-footer style="text-align: center">
    第一个实战项目Wiki电子书
    <br>
    <hr>
    备案：
    <img src="../assets/webicon.png" alt="avatar">
    <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=cdblog.live">黔ICP备2021010120号-2</a>

  </a-layout-footer>
</template>

<!--设置组件-->
<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
//  文件名字
  name:'the-footer'
});
</script>