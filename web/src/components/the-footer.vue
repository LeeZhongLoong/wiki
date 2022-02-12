<template>
<!--  设置尾部组件-->
  <!--    公共的尾部-->
  <a-layout-footer style="text-align: center">

   <div v-show="user.id">
     欢迎:{{user.name}}
   </div>
    第一个实战项目Wiki电子书
    <br>
    <hr>
    备案：
    <img src="../assets/webicon.png" alt="avatar">
    <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=cdblog.live">黔ICP备2021010120号-2</a>

  </a-layout-footer>
</template>

<!--设置组件-->
<script lang="ts">
import {computed, defineComponent, onMounted} from "vue";
import store from "@/store";
import {Tool} from "@/util/tool";

export default defineComponent({
//  文件名字
  name:'the-footer',
  setup(){
    const user=computed(()=> store.state.user)

    let websocket:any;
    //传给服务端的token
    let token:any;

    //这四个方法不用我们调用，是websocket内部根据当前时间触发自动调用的
    const onOpen = () => {
      console.log('WebSocket连接成功,状态码:',websocket.readyState);
    };
    const onMessage=(event : any)=>{
      console.log('WebSocket收到消息:',event.data);
    };
    const onError = () => {
      console.log('WebSocket连接错误,状态码:',websocket.readyState);
    };
    const onClose = () => {
      console.log('WebSocket连接关闭,状态码:',websocket.readyState);
    }

    const initWebSocket = () => {
    //  连接成功
      websocket.onopen=onOpen;
    //  收到消息的回调
      websocket.onmessage=onMessage;
    //  连接错误
      websocket.onerror=onError;
    //  连接关闭的回调
      websocket.onclose=onClose;
    }

    onMounted(()=>{
      //初始化webSocket
      //WebSocket
      if ('WebSocket' in window){
        token=Tool.uuid(10);
      //  连接地址:ws://127.0.0.1:8880/wa/xxx
        websocket=new WebSocket(process.env.VUE_APP_WS_SERVER+'/ws/'+token);
        initWebSocket();
      }else {
        alert('当前浏览器 不支持');
      //  关闭
      //   websocket.close();
      }
    });
    return{
      user
    }
  }
});
</script>