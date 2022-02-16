
<template>
<h1>欢迎来到龙的第第一个实战页面,阿拉啦啦啦</h1>
</template>

<!--定义的组件-->
<script lang="ts">
import {defineComponent} from 'vue';

//密码加密
declare let hexMd5:any;
//盐值
declare let KEY:any;

export default defineComponent({
  //组件名字
  name: 'the-welcome',
  setup(){
    return{

    }
  }
});
</script>
