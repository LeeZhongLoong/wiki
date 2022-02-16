<!--template只允许下面有一个结点-->
<template>
  <a-layout>
<!--    页面左边导航-->
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
            <CalendarOutlined/>
            <span>欢迎</span>
        </a-menu-item>

        <a-sub-menu v-for="item in level1" :key="item.id">
<!--            一级菜单-->
          <template v-slot:title>
            <span><user-outlined/>{{item.name}}</span>
          </template>
<!--            二级菜单-->
          <a-menu-item v-for="child in item.children" :key="child.id">
          <MailOutlined/> <span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
<!--    页面右边内容-->
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight:'550px'}">
<!--      欢迎页面-->

      <div class="welcome" v-show="isShowWelcome">
        <the-welcome></the-welcome>
      </div>

<!--      让他并排显示 column:3一行变为三列,gutter20每列间距为20-->
<!--      分页 -->
      <a-list v-show="!isShowWelcome"
              item-layout="vertical" size="large"
              :grid="{ gutter:20,column:3}"
              :data-source="ebooks"
              :loading="loading"
      >

        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span>
                <component v-bind:is="'file-text-outlined'" style="margin-right: 8px" />
                {{ item.docCount }}
              </span>
              <span>
                <component v-bind:is="'LikeOutlined'" style="margin-right: 8px" />
                {{ item.voteCount }}
              </span>
              <span>
                <component v-bind:is="'eye-outlined'" style="margin-right: 8px" />
                {{ item.viewCount }}
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
                <router-link :to="'/doc?ebookId='+item.id">
                  {{item.name}}
                </router-link>
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
import {defineComponent, onMounted, ref} from "vue";
//导入axios库
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import TheWelcome  from '@/components/the-welcome.vue';

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
  components:{
    TheWelcome
  },
  setup(){
    // console.log("111"+process.env.VUE_APP_URL);
    //1、第一种方法,将数据变为响应式数据，动态数据
    const ebooks=ref();
    //2、第二种方法
    // const ebooks1=reactive({books:[]});
    //分类树形结构
    const level1 = ref();
    let categorys:any;
    /**
     * 查询所有分类的方法
     */
    const handleQueryCategory=()=>{
      axios.get("/category/all").then((response)=>{
        const data=response.data;
        if (data.success){
          categorys=data.content;
          console.log("原始数据："+categorys);
          level1.value=[]
          level1.value=Tool.array2Tree(categorys,0);
          console.log("树形结构：",level1);
        }else {
          message.error(data.message);
        }
      });
    };

    let categoryId2=0;
    const isShowWelcome=ref(true);
    const handleQueryEbook=()=>{
      //在main中添加了axios统一访问名
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:1000,
          categoryId2: categoryId2
        }
      }).then((response)=>{
        loading.value=false;
        const data=response.data;
        ebooks.value=data.content.list;
        // ebooks1.books=data.content;
      });
    };


    //点击事件
    const handleClick=(value:any)=>{
      if (value.key==='welcome'){
        isShowWelcome.value=true;
      }else {
        categoryId2=value.key;
        isShowWelcome.value=false;
        handleQueryEbook();
      }
    }

    //加载框
    const loading=ref(false);
    //初始化逻辑
    onMounted(()=>{
      //加载所有的分类
      handleQueryCategory();
      //设置加载框的值
      loading.value=true;
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
      // actions: [
      //   { type: 'StarOutlined', text: '156' },
      //   { type: 'LikeOutlined', text: '156' },
      //   { type: 'MessageOutlined', text: '2' },
      // ],
    //  返回加载框的参数
      loading,
    //  分类方法，数组树
      level1,
      handleClick,
      isShowWelcome
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


