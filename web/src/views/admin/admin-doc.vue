<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight:'280px'}">
<!--      columns每一列，row 每一行的id :data-source从后台拿出来的数据,:pagination 分页,:loading 加载的情况等待框
          @change 当表格变化时要调用的函数
-->
<!--      添加新增的按钮-->
      <a-row :gutter="24">
        <a-col :span="8">
          <p>
            <a-form
                layout="inline"
                :model="param"
            >
              <a-form-item>
                  <a-button
                      type="primary"
                      @click="handleQuery()">
                    查询
                  </a-button>
              </a-form-item>
              <a-form-item>
                  <a-button type="primary" @click="add()">
                    新增
                  </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-table
              v-if="level1.length>0"
              :columns="columns"
              :row-key="record =>record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
              :size="small"
              :defaultExpandAllRows="true "
          >
            <!--        渲染用v-slot： 和# 把表格中的内容渲染为特定的对象-->
            <!--        第一个渲染封面-->
<!--            <template v-slot:name="{ text:record }">-->
<!--              {{record.sort}} {{text}}-->
<!--            </template>-->
            <!--        第二个渲染，按钮-->
            <template v-slot:action="{text,record}">
              <!--          <a-space>两个按钮用空格分开-->
              <a-space size="small">
                <!--            添加事件-->
                <a-button type="primary" @click="edit(record)" size="small">
                  编辑
                </a-button>
                <a-popconfirm
                    title="删除后不可恢复，确认删除？"
                    ok-text="确认"
                    cancel-text="误点"
                    @confirm="handleDelete(record.id)"
                >
                  <a-button type="danger" size="small">
                    删除
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>
        <a-col :span="16">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleSave()">
                  保存
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-form :model="doc" layout="vertical">
            <a-form-item>
              <a-input v-model:value="doc.name" placeholder="名称" />
            </a-form-item>
            <a-form-item>
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
            <a-form-item label="顺序">
              <a-input v-model:value="doc.sort" placeholder="顺序"/>
            </a-form-item>
<!--            添加文本预浏览-->
            <a-form-item>
              <a-button type="primary" @click="handlePreviewContent">
                <EyeOutlined/> 内容预览
              </a-button>
            </a-form-item>
            <!--      添加富文本-->
            <a-form-item>
              <div id="content"></div>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>
<!--      增加抽屉组件-->
      <a-drawer
          width="900"
          placement="right"
          :closble="false"
          :visible="drawerVisible"
          @close="onDrawerClose"
          class="custom-class"
          style="color: red"
          title="内容预览"
      >
        <div class="wangeditor" :innerHTML="previewHtml"></div>
      </a-drawer>
    </a-layout-content>
  </a-layout>
<!--  弹出框-->
<!--  <a-modal-->
<!--      v-model:visible="modalVisible"-->
<!--      title="文档表单"-->
<!--      :confirm-loading="modalLoading"-->
<!--      @ok="handleModalOk"-->
<!--  >-->

<!--    &lt;!&ndash;    表单&ndash;&gt;-->
<!--  </a-modal>-->
</template>
<script lang="ts">

//导入
import {createVNode, defineComponent, onMounted, ref} from 'vue';
//导入axios
import axios from 'axios';
//导入消息组件
import {message, Modal} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import {ExclamationCircleOutlined} from "@ant-design/icons-vue";
//导入工具
import E from "wangeditor";



export default defineComponent({

  name:'AdminDoc',
  setup() {

    const route=useRoute();
    //根据名字查询
    const param=ref();
    param.value={};
    //后端传过来的数据
    const docs=ref();
    //等待框的初始值
    const loading=ref(false);
    //因为树选择组件的属性状态，会随着当前编辑的节点二变化，所以单独声明
    const treeSelectData=ref();
    // let editor:E;
    treeSelectData.value=[];

    const columns = [
      {
        title: '名称',
        //数据库中的值
        dataIndex: 'name',
        slots:{customRender:'name'}
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
    level1.value=[];
  //  数据查询
    const handleQuery=()=>{
      //让查询之前有数据等待样式
      loading.value=true;
      level1.value=[];
      axios.get("/doc/all/"+route.query.ebookId).then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          docs.value=data.content;
          console.log("原始数组:",docs.value);

          //level1定义为数组
          level1.value=[];
          level1.value=Tool.array2Tree(docs.value,0);
          console.log("树形结构：",level1);
        //  父文档下拉框初始化，相当于新增
          treeSelectData.value=Tool.copy(level1.value)||[];
          //未选择树添加一个’无‘
          treeSelectData.value.unshift({id:0,name:'无'});
        }else{
          message.error(data.message);
        }
      })
    }
    //查询content内容

    //编辑表单
    //初始后台变量
    //因为数选择组件的属性状态，会随当前编辑的节点而变化，所以单独声明一个响应式变量
    const doc=ref();
    doc.value={
      ebookId:route.query.ebookId
    };
    //是否显示弹出框
    // const modalVisible=ref(false);
    //等待状态
    const modalLoading=ref(false);
    const editor = new E('#content');
    editor.config.zIndex=0;
    // editor.config.zIndex=0;


    //编辑表单保存的方法
    const handleSave=()=>{
      //进入等待状态
      modalLoading.value=true;
      editor.txt.html();
      doc.value.content=editor.txt.html();
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
          // modalVisible.value=false;
          message.success("保存成功");
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
     * 删除整个树枝
     */
    //整个树的id
    const deleteIds:Array<string>=[];
    //整个树的名字
    const deleteNames:Array<string>=[];

    const getDeleteIds = (treeSelectData:any,id:any) => {
    //  遍历数组，既遍历某一层节点
      for (let i = 0; i < treeSelectData.length; i++) {
        const node=treeSelectData[i];
        if (node.id===id){
        //  如果当前节点就是目标节点
          console.log("disable",node);
        //  将目标节点置为disable
        //   node.disabled=true;
          deleteIds.push(id);
          deleteNames.push(node.name);
        //  遍历所有子节点,将所有子节点全部置为disable
          const children=node.children;
          if (Tool.isNotEmpty(children)){
            for (let j = 0; j < children.length; j++) {
              getDeleteIds(children,children[j].id);
            }
          }
        }else {
        //  如果当前节点不是目标节点，则到其子节点再找找看
          const children=node.children;
          if (Tool.isNotEmpty(children)){
            getDeleteIds(children,id);
          }
        }
      }
    }

    //查询content中的内容
    const handleQueryContent=()=>{
      axios.get("/doc/find-content/"+doc.value.id).then((response)=>{
        loading.value=false;
        const data=response.data;
        if (data.success){
          editor.txt.html(data.content);
        }else{
          message.error(data.message);
        }
      })
    }

    /**
     * 编辑的方法
     */
    const edit=(record:any)=>{
      //清空富文本
      editor.txt.html("");
      // modalVisible.value=true;
      doc.value=Tool.copy(record);
      handleQueryContent();
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
      //清空富文本
      editor.txt.html("");
      // modalVisible.value=true;
      doc.value={
        ebookId:route.query.ebookId
      };
      treeSelectData.value=Tool.copy(level1.value);
    //  为选择树添加一个“无”,在之前添加
      treeSelectData.value.unshift({id:0,name:'无'});

    };

    /**
     * 删除的方法
     */
    const handleDelete=(id:number)=>{
      deleteIds.length=0;
      deleteNames.length=0;
      getDeleteIds(level1.value,id)
      Modal.confirm({
        title:'重要提醒',
        icon:createVNode(ExclamationCircleOutlined),
        content:'将删除:【'+deleteNames.join(",")+"】删除后不可恢复，确认删除？",
        onOk(){
          axios.delete("doc/delete/"+deleteIds.join(",")).then((response)=>{
            const data=response.data;
            //删除成功
            if (data.success){
              //  重新加载列表
              handleQuery()
            }else {
              message.error(data.message);
            }
          });
        },
      })
    };

    //富文本预览
    //抽屉判断
    const drawerVisible=ref(false);
    //富文本内容
    const previewHtml=ref();
    const handlePreviewContent = () => {
      const html=editor.txt.html();
      previewHtml.value=html;
      drawerVisible.value=true;
    };

    //关闭富文本框抽屉
    const onDrawerClose = () => {
      drawerVisible.value=false;
    };

    //初始的方法
    onMounted(function (){
      //只在方法内调用
      handleQuery();
      // editor.config.zIndex=0;
      editor.create();
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

      // modalVisible,
      modalLoading,
      handleSave,
      param,

    //  查出来的数据
      doc,
    //  更改后的leve1数据
      treeSelectData,
    //  富文本开关变量
      drawerVisible,
    //  富文本内容
      previewHtml,
    //  预览富文本框抽屉
      handlePreviewContent,
    //  关闭抽屉方法
      onDrawerClose

    }
  },
});

</script>
