<!--template只允许下面有一个结点-->
<template>
  <a-layout>
<!--    页面左边导航-->
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
<!--    页面右边内容-->
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight:'550px'}">
<!--      让他并排显示 column:3一行变为三列,gutter20每列间距为20-->
<!--      分页 -->
      <a-list item-layout="vertical" size="large"
              :grid="{ gutter:20,column:3}"
              :data-source="ebooks"
              :loading="loading"
      >

        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span v-for="{ type, text } in actions" :key="type">
                <component v-bind:is="type" style="margin-right: 8px" />
                {{ text }}
              </span>
            </template>
<!--            <template #extra>-->
<!--              <img-->
<!--                  width="272"-->
<!--                  alt="logo"-->
<!--                  src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"-->
<!--              />-->
<!--            </template>-->
            <a-list-item-meta :description="item.description">
<!--              标题-->
              <template #title>
                <a :href="item.href">{{ item.name }}</a>
              </template>
<!--              内容简介-->
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>
<!--            {{ item.content }}-->
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts">

//添加声明周期onMounted
import {defineComponent, onMounted, ref,reactive,toRef} from "vue";
//导入axios库
import axios from "axios";

const listData: Record<string, string>[] = [];

// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
//     description:
//         'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//         'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }

export default defineComponent({
  name:'Home',
  setup(){
    // console.log("111"+process.env.VUE_APP_URL);
    //1、第一种方法,将数据变为响应式数据，动态数据
    const ebooks=ref();
    //2、第二种方法
    // const ebooks1=reactive({books:[]});
    //加载框
    const loading=ref(false);
    //初始化逻辑
    onMounted(()=>{
      //设置加载框的值
      loading.value=true;
      //在main中添加了axios统一访问名
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:1000
        }
      }).then((response)=>{
        loading.value=false;
        const data=response.data;
        ebooks.value=data.content.list;
        // ebooks1.books=data.content;
      });
    });
    return {
      ebooks,
      // ebooks1:toRef(ebooks1,"books"),
      // listData,
      pagination : {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions: [
        { type: 'StarOutlined', text: '156' },
        { type: 'LikeOutlined', text: '156' },
        { type: 'MessageOutlined', text: '2' },
      ],
    //  返回加载框的参数
      loading
    }
  }
});
</script>
<!--改变图标的样式 scoped只在当前页面有效-->
<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;

  }
</style>


