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
        <a-space>
          <a-button
              type="primary"
              @click="handleQuery()">
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
          :data-source="level1"
          :loading="loading"
          :pagination="false"
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
      title="文档表单"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
<!--    表单-->
    <a-form :model="doc" :label-col="{span:6}" :wrapper-col="wrapperCol">
      <a-form-item label="名称">
        <a-input v-model:value="doc.name" />
      </a-form-item>
      <a-form-item label="父文档">
        <a-tree-select
            v-model:value="doc.parent"
            style="width: 100%"
            :dropdown-style="{maxHeight:'400px',overflow:'auto'}"
            :tree-data="treeSelectData"
            placeholder="请选择父文档"
            tree-default-expand-all
            :replaceFields="{title:'name',key:'id',value:'id'}"
        >

        </a-tree-select>
      </a-form-item>
      <a-form-item label="父文档">
<!--        <a-input v-model:value="doc.parent" />-->
        <a-select
            ref="select"
            v-model:value="doc.parent"
        >
<!--          0为一级文档-->
          <a-select-option value="0">
            无
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="doc.id ===c.id">
            {{c.name}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="doc.sort" />
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
//导入工具

export default defineComponent({
  name:'AdminDoc',
  setup() {
    //根据名字查询
    const param=ref();
    param.value={};
    //后端传过来的数据
    const docs=ref();
    //等待框的初始值
    const loading=ref(false);
    const columns = [
      {
        title: '名称',
        //数据库中的值
        dataIndex: 'name',
      },
      {
        title: '父文档',
        key: 'parent',
        dataIndex:'parent',
      },
      {
        title: '顺序',
        dataIndex:'sort',
      },
      {
        title: 'Action',
        key: 'action',
        //两个按钮渲染
        slots: {customRender: 'action'}
      }
    ];
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
  //  数据查询
    const handleQuery=()=>{
      //让查询之前有数据等待样式
      loading.value=true;
      level1.value=[];
      axios.get("/doc/all").then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          docs.value=data.content;
          console.log("原始数组:",docs.value);

          //level1定义为数组
          level1.value=[];
          level1.value=Tool.array2Tree(docs.value,0);
          console.log("树形结构：",level1);
        }else{
          message.error(data.message);
        }
      })
    }
    //编辑表单
    //初始后台变量
    //因为数选择组件的属性状态，会随当前编辑的节点而变化，所以单独声明一个响应式变量
    const treeSelectData=ref();
    treeSelectData.value=[];
    const doc=ref();
    //是否显示弹出框
    const modalVisible=ref(false);
    //等待状态
    const modalLoading=ref(false);
    //编辑表单保存的方法
    const handleModalOk=()=>{
      //进入等待状态
      modalLoading.value=true;
      //提交保存doc为用户输入的内容
      axios.post("/doc/save",doc.value).then((response)=>{
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
          handleQuery();
        }else {
          message.error(data.message);
        }
      });
    };


    /**
     * 将某个节点及其子节点全部置为disabled
     */
    const setDisable = (treeSelectData:any,id:any) => {
    //  遍历数组，既遍历某一层节点
      for (let i = 0; i < treeSelectData.length; i++) {
        const node=treeSelectData[i];
        if (node.id===id){
        //  如果当前节点就是目标节点
          console.log("disable",node);
        //  将目标节点置为disable
          node.disabled=true;
        //  遍历所有子节点,将所有子节点全部置为disable
          const children=node.children;
          if (Tool.isNotEmpty(children)){
            for (let j = 0; j < children.length; j++) {
              setDisable(children,children[j].id);
            }
          }
        }else {
        //  如果当前节点不是目标节点，则到其子节点再找找看
          const children=node.children;
          if (Tool.isNotEmpty(children)){
            setDisable(children,id);
          }
        }
      }
    }

    /**
     * 编辑的方法
     */
    const edit=(record:any)=>{
      modalVisible.value=true;
      doc.value=Tool.copy(record);
    //  不能选择当前节点及其所有子节点，作为父节点。
      treeSelectData.value=Tool.copy(level1.value);
      setDisable(treeSelectData.value,record.id);
    //  为选择树添加一个“无”
      treeSelectData.value.unshift({id:0,name:'无'});

    };
    /**
     * 新增的方法
     * @param record
     */
    const add=()=>{
      modalVisible.value=true;
      doc.value={};
      treeSelectData.value=Tool.copy(level1.value);
    //  为选择树添加一个“无”,在之前添加
      treeSelectData.value.unshift({id:0,name:'无'});

    };

    /**
     * 删除的方法
     */
    const handleDelete=(id:number)=>{
      axios.delete("doc/delete/"+id).then((response)=>{
        const data=response.data;
        //删除成功
        if (data.success){
        //  重新加载列表
          handleQuery()
        }
      })
    }
    //初始的方法
    onMounted(function (){
      //只在方法内调用
      handleQuery();
    });
    //返回所有的参数
    return {
      // docs,
      //返回数组树
      level1,
      columns,
      loading,
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
      doc,
    //  更改后的leve1数据
      treeSelectData
    }
  },
});

</script>
