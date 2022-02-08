<template>
  <a-layout>
    <a-laout-content  :style="{background:'#fff',padding:'24px',margin:0,minHeight:'280px'}">
      <a-row>
        <a-col :span="6">
          <a-tree
            v-if="level1.length>0"
            :tree-data="level1"
            @select="onSelect"
            :replaceFields="{title:'name',key:'id',value:'id'}"
            :defaultExpandAll="true"
          >
          </a-tree>
        </a-col>
        <a-col :span="18">
          <div :innerHTML="html"></div>
        </a-col>
      </a-row>
    </a-laout-content>
  </a-layout>
</template>

<script lang="ts">

//导入
import {defineComponent, onMounted, ref} from 'vue';
//导入axios
import axios from 'axios';
//导入消息组件
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";



export default defineComponent({

  name:'Doc',
  setup() {
    //路由
    const route=useRoute();
    //后端传过来的数据
    const docs=ref();
    //等待框的初始值
    const loading=ref(false);
    //定义content变量
    const html=ref();

    //  数组树
    /**
     * 一级文档，children属性就是二级文档
     * [{
     *    id:"",
     *    name:"",
     *    children:[{
     *      id:"",
     *      name:"",
     *      }]
     *  }]
     */
        //一级文档树，children是二级树
    const level1=ref();
    level1.value=[];
    //  数据查询
    const handleQuery=()=>{
      //让查询之前有数据等待样式
      loading.value=true;
      axios.get("/doc/all/"+route.query.ebookId).then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          docs.value=data.content;
          console.log("原始数组:",docs.value);

          //level1定义为数组
          level1.value=[];
          level1.value=Tool.array2Tree(docs.value,0);
        }else{
          message.error(data.message);
        }
      })
    }

    /**
     * 内容查询
     */
    const handleQueryContent=(id:number)=>{
      axios.get("/doc/find-content/"+id).then((response)=>{
        const data=response.data;
        if (data.success){
          html.value=data.content;
        }else{
          message.error(data.message);
        }
      });
    };

    /**
     * 根据数组Id查询
     * @param selectedKeys
     * @param info
     */
    const onSelect = (selectedKeys:any,info:any) => {
      console.log('selected',selectedKeys,info);
      if (Tool.isNotEmpty(selectedKeys)){
        handleQueryContent(selectedKeys[0]);
      }
    //  加载内容
    };




    //初始的方法
    onMounted(function (){
      //只在方法内调用
      handleQuery();
    });
    //返回所有的参数
    return {
      //返回数组树
      level1,
      loading,
      html,
      onSelect

    }
  },
});

</script>

