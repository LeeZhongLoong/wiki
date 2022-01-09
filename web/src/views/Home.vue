<!--template只允许下面有一个结点-->
<template>
  <a-layout>
<!--    页面右边导航-->
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
      >
        <a-sub-menu key="sub1">
          <template #title>
              <span>
                <user-outlined/>
                subnav 1修改成功啦
              </span>
          </template>
          <a-menu-item key="1">option1</a-menu-item>
          <a-menu-item key="2">option2</a-menu-item>
          <a-menu-item key="3">option3</a-menu-item>
          <a-menu-item key="4">option4</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #title>
              <span>
                <laptop-outlined />
                subnav 2
              </span>
          </template>
          <a-menu-item key="5">option5</a-menu-item>
          <a-menu-item key="6">option6</a-menu-item>
          <a-menu-item key="7">option7</a-menu-item>
          <a-menu-item key="8">option8</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub3">
          <template #title>
              <span>
                <notification-outlined />
                subnav 3
              </span>
          </template>
          <a-menu-item key="9">option9</a-menu-item>
          <a-menu-item key="10">option10</a-menu-item>
          <a-menu-item key="11">option11</a-menu-item>
          <a-menu-item key="12">option12</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
<!--    页面左边内容-->
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px'}">
      <pre>{{ ebooks }}{{ebooks1}}</pre>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">

//添加声明周期onMounted
import {defineComponent, onMounted, ref,reactive,toRef} from "vue";
//导入axios库
import axios from "axios";
export default defineComponent({
  name:'Home',
  setup(){
    console.log("setup");
    //1、第一种方法,将数据变为响应式数据，动态数据
    const ebooks=ref();
    //2、第二种方法
    const ebooks1=reactive({books:[]});
    //初始化逻辑
    onMounted(()=>{
      console.log("onMounted");
      axios.get("http://localhost:8880/ebook/list?name=Spring").then((response)=>{
        const data=response.data;
        ebooks.value=data.content;
        ebooks1.books=data.content;
        console.log(response);
      });
    });
    return {
      ebooks,
      ebooks1:toRef(ebooks1,"books")
    }
  }
})
</script>


