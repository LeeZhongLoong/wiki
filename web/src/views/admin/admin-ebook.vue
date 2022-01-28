<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight:'550px'}">
<!--      columns每一列，row 每一行的id :data-source从后台拿出来的数据,:pagination 分页,:loading 加载的情况等待框
          @change 当表格变化时要调用的函数
-->
<!--      添加新增的按钮-->
      <p>
        <a-form
        layout="inline"
        :model="param"
        >
        <a-form-item>
          <a-input v-model:value="param.name" placeholder="名称">
          </a-input>
        </a-form-item>
        <a-form-item>
        <a-space>
          <a-button
              type="primary"
              @click="handleQuery({page:1,size:pagination.pageSize})">
            查询
          </a-button>
          <a-button type="primary" @click="add()">
            新增
          </a-button>
        </a-space>
        </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :row-key="record =>record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
<!--        渲染用v-slot： 和# 把表格中的内容渲染为特定的对象-->
<!--        第一个渲染封面-->
        <template #cover="{ text:cover }">
          <img v-if="cover" :src="cover" alt="avatar">
        </template>
<!--        增加分类渲染-->
        <template v-slot:category="{text,record}">
          <span>{{ getCategoryName(record.category1Id)}}/{{getCategoryName(record.category2Id)}}</span>
        </template>
<!--        第二个渲染，按钮-->
<!--        text与record一样 如果要渲染text中的值使用text:text中值的名字
            如text:cover 获取text中cover的值并渲染
-->
        <template v-slot:action="{text,record}">
<!--          <a-space>两个按钮用空格分开-->
          <a-space size="small">
<!--            添加事件-->
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
<!--            添加点击事件-->
<!--            删除确认框-->
<!--            @cancel="cancel" 是取消的事件-->
            <a-popconfirm
                title="删除后不可恢复，确认删除？"
                ok-text="确认"
                cancel-text="误点"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
<!--  弹出框-->
  <a-modal
      v-model:visible="modalVisible"
      title="电子书表单"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
<!--    表单-->
    <a-form :model="ebook" :label-col="{span:6}" :wrapper-col="wrapperCol">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label:'name',value:'id',children:'children'}"
            :options="level1"
        />
<!--        <a-input v-model:value="ebook.category1Id" />-->
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">

//导入
import { defineComponent,onMounted,ref } from 'vue';
//导入axios
import axios from 'axios';
//导入消息组件
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {IDrawerProps} from "ant-design-vue/es/vc-drawer/src/IDrawerPropTypes";
import level = IDrawerProps.level;
//导入工具

export default defineComponent({
  name:'AdminEbook',
  setup() {
    //根据名字查询
    const param=ref();
    param.value={};
    //后端传过来的数据
    const ebooks=ref();
    //分页的变量
    const pagination=ref({
      //当前页
      current:1,
      //每页的行数
      pageSize:4,
      //总页数
      total:0
    });
    //等待框的初始值
    const loading=ref(false);
    const columns = [
      {
        //表格的头
        title: '封面',
        //表头下的表内容，他的值是数据库中的名字，每一个列中的数值
        dataIndex: 'cover',
        //图片渲染
        slots: { customRender: 'cover' },
      },
      {
        title: '名称',
        //数据库中的值
        dataIndex: 'name',
      },
      {
        title: '分类',
      //  渲染分类
        slots: {customRender: 'category'}
      },
      {
        title: '文档数',
        key: 'docCount',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        key: 'viewCount',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        key: 'voteCount',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        //两个按钮渲染
        slots: {customRender: 'action'}
      }
    ];
  //  数据查询
    const handleQuery=(params:any)=>{
      //让查询之前有数据等待样式
      loading.value=true;
      axios.get("/ebook/list", {
        params:{
          page:params.page,
          size:params.size,
          name:param.value.name
        }
      }).then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          ebooks.value=data.content.list;

          //  重置分页按钮
          pagination.value.current=params.page;
          pagination.value.total=data.content.total;
        }else{
          message.error(data.message);
        }
      })
    }
  //  表格点击页码时触发点击下一页上一页
    const handleTableChange=(pagination:any)=>{
      console.log("看看自带的分页参数有啥:"+pagination);
      handleQuery({
        page:pagination.current,
        size:pagination.pageSize
      });
    };

    //编辑表单
    /**
     * 数组[100,101]对应：前端开发/Vue
     */
    const categoryIds=ref();
    //初始后台变量
    const ebook=ref();
    //是否显示弹出框
    const modalVisible=ref(false);
    //等待状态
    const modalLoading=ref(false);
    //编辑表单保存的方法
    const handleModalOk=()=>{
      //进入等待状态
      modalLoading.value=true;
      //把一级分类赋值给category1Id
      ebook.value.category1Id=categoryIds.value[0];
      //把二级分类赋值给category2Id
      ebook.value.category2Id=categoryIds.value[1];
      //提交保存ebook为用户输入的内容
      axios.post("/ebook/save",ebook.value).then((response)=>{
        //关闭等待状态
        modalLoading.value=false;
        //获取返回的值
        const data=response.data;
        //判断是否修改成功
        if (data.success){
          //保存成功
          //关闭弹出框
          modalVisible.value=false;

        //  重新查询当前页
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize,
          });
        }else {
          message.error(data.message);
        }
      });
    };
    /**
     * 编辑的方法
     */
    const edit=(record:any)=>{
      modalVisible.value=true;
      ebook.value=Tool.copy(record);
    //  把一级分类和二级分类显示出来
      categoryIds.value=[ebook.value.category1Id,ebook.value.category2Id];
    };
    /**
     * 新增的方法
     * @param record
     */
    const add=()=>{
      modalVisible.value=true;
      ebook.value={};
    };

    /**
     * 删除的方法
     */
    const handleDelete=(id:number)=>{
      axios.delete("ebook/delete/"+id).then((response)=>{
        const data=response.data;
        //删除成功
        if (data.success){
        //  重新加载列表
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize
          });
        }else{
          message.error(data.message);
        }
      })
    }

    //定义分类
    const level1=ref();

    //将分类置为全局变量,只在js中使用不在html中使用用let定义
    let categorys:any;
    /**
     * 查询所有的分类
     */
    const handlerQueryCategory=()=>{
    //  加载框
      loading.value=true; //开启加载框
      //获取分类数据
      axios.get("/category/all").then((response)=>{
        loading.value=false; //关闭加载框
        //赋值
        const data=response.data;
        if (data.success){
          categorys=data.content;
          console.log("原始数据:",categorys);

          //给分类赋值
          level1.value=[];
          //使用工具递归把数组转化为数据树
          level1.value=Tool.array2Tree(categorys,0);
          console.log("树形结构:",level1.value);
        }else {
          message.error(data.message);
        }
      });
    };
    /**
     * 获取分类名字的方法
     */
    const getCategoryName=(cid:number)=>{
      let result="";
      //遍历categorys中的id如果与显示的id相同返回id中的名字
      categorys.forEach((item:any)=>{
        if(item.id===cid){
          result=item.name;
        }
      });
      return result;
    };
    //初始的方法
    onMounted(function (){
      //初始分类方法
      handlerQueryCategory();
      //只在方法内调用
      handleQuery({
      //  初始查询第一页
        page:1,
        //初始化大小
        size:pagination.value.pageSize
      });
    });
    //返回所有的参数
    return {
      //返回分类
      categoryIds,
      level1,
      ebooks,
      pagination,
      columns,
      loading,

      //获取分类的名字
      getCategoryName,

      handleTableChange,
    //  编辑表单的参数
      edit,
      //新增
      add,
      //删除
      handleDelete,

      //查询
      handleQuery,

      modalVisible,
      modalLoading,
      handleModalOk,
      param,

    //  查出来的数据
      ebook

    }
  },
});

</script>
