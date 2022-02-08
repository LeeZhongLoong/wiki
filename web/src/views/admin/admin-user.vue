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
          <a-input v-model:value="param.loginName" placeholder="登录名">
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
          :data-source="users"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template v-slot:action="{text,record}">
<!--          <a-space>两个按钮用空格分开-->
          <a-space size="small">
<!--            添加事件-->
             <a-button type="primary">
               重置密码
             </a-button>
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
      title="用户表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
<!--    表单-->
    <a-form :model="user" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="登录名">
        <a-input v-model:value="user.loginName" :disabled="!!user.id" />
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="user.name" />
      </a-form-item>
      <a-form-item label="密码">
        <a-input v-model:value="user.password" />
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
// import level = IDrawerProps.level;
//导入工具

declare let hexMd5:any;
declare let KEY:any;

export default defineComponent({
  name:'AdminEbook',
  setup() {
    //根据名字查询
    const param=ref();
    param.value={};
    const users=ref();
    //分页的变量
    const pagination=ref({
      //当前页
      current:1,
      //每页的行数
      pageSize:10,
      //总页数
      total:0
    });
    //等待框的初始值
    const loading=ref(false);
    const columns = [
      {
        //表格的头
        title: '登录名',
        dataIndex: 'loginName',
      },
      {
        title: '名称',
        dataIndex: 'name',
      },
      {
        title: '密码',
      //  渲染分类
        dataIndex:'password'
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
      //查询之前先清空，可以不清空
      users.value=[];
      axios.get("/user/list", {
        params:{
          page:params.page,
          size:params.size,
          loginName:param.value.loginName
        }
      }).then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          users.value=data.content.list;
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
    const user=ref();
    //是否显示弹出框
    const modalVisible=ref(false);
    //等待状态
    const modalLoading=ref(false);
    //编辑表单保存的方法
    const handleModalOk=()=>{
      //进入等待状态
      modalLoading.value=true;
      // user.value.password=hexMd5(user.value.password+KEY);
      //提交保存ebook为用户输入的内容
      axios.post("/user/save ",user.value).then((response)=>{
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
     * 编辑
     */
    const edit = (record:any) => {
      modalVisible.value=true;
      user.value=Tool.copy(record);
    }
    /**
     * 新增
     */
    const add = () => {
      modalVisible.value=true;
      user.value={};
    }
    /**
     * 删除用户
     */
    const handleDelete = (id:number) => {
      axios.delete("/user/delete/"+id).then((response)=>{
        const data=response.data;
        if (data.success){
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize
          });
        }else {
          message.error(data.members);
        }
      });
    };
    //初始的方法
    onMounted(function (){
      handleQuery({
        page:1,
        size:pagination.value.pageSize
      })
    });
    //返回所有的参数
    return {
      //返回分类
      pagination,
      user,
      users,
      columns,
      loading,

      handleTableChange,
      //查询
      handleQuery,

      modalVisible,
      modalLoading,
      handleModalOk,
      handleDelete,
      param,
      add,
      edit
    }
  },
});

</script>
