<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight:'550px'}">
<!--      columns每一列，row 每一行的id :data-source从后台拿出来的数据,:pagination 分页,:loading 加载的情况等待框
          @change 当表格变化时要调用的函数
-->
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
<!--        第二个渲染，按钮-->
        <template v-slot:action="{text,record}">
<!--          <a-space>两个按钮用空格分开-->
          <a-space size="small">
<!--            添加事件-->
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <a-button type="danger">
                删除
              </a-button>
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
        <a-input v-model:value="ebook.category1Id" />
      </a-form-item>
      <a-form-item label="分类二">
        <a-input v-model:value="ebook.category2Id" />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">

//导入
import { defineComponent,onMounted,ref } from 'vue';
//导入axios
import axios from 'axios';

export default defineComponent({
  name:'AdminEbook',
  setup() {
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
        title: '分类一',
        key: 'category1Id',
        dataIndex:'category1Id',
      },
      {
        title: '分类二',
        dataIndex:'category2Id',
      },
      {
        title: '文档书',
        key: 'docCount',
      },
      {
        title: '阅读数',
        key: 'viewCount',
      },
      {
        title: '点赞数',
        key: 'voteCount',
      },
      {
        title: 'Action',
        key: 'action',
        //两个按钮渲染
        slots: {customRender: 'action'}
      }
    ];
  //  数据查询
    const handleQuery=(param:any)=>{
      //让查询之前有数据等待样式
      loading.value=true;
      axios.get("/ebook/list", {
        params:{
          page:param.page,
          size:param.size
        }
      }).then((response)=>{
        loading.value=false;
        const data=response.data;
        ebooks.value=data.content.list;

      //  重置分页按钮
        pagination.value.current=param.page;
        pagination.value.total=param.total;
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
      //提交保存ebook为用户输入的内容
      axios.post("/ebook/save",ebook.value).then((response)=>{
        //获取返回的值
        const data=response.data;
        //判断是否修改成功
        if (data.success){
          //保存成功

          //关闭弹出框
          modalVisible.value=false;
          //关闭等待状态
          modalLoading.value=false;
        //  重新查询当前页
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize,
          });
        }
      });
    };
    /**
     * 编辑的方法
     */
    const edit=(record:any)=>{
      modalVisible.value=true;
      ebook.value=record;
    };

    //初始的方法
    onMounted(function (){
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
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,

    //  编辑表单的参数
      edit,

      modalVisible,
      modalLoading,
      handleModalOk,

    //  查出来的数据
      ebook

    }
  },
});

</script>
