
<template>
  <div>
<!--    总的点赞数和阅读数-->
    <a-row>
       <a-col :span="24">
<!--         a-card 卡片效果-->
         <a-card>
           <a-row>
             <a-col :span="8">
               <a-statistic title="总阅读量" :value="statistic.viewCount">
                 <template #suffix>
                   <UserOutlined/>
                 </template>
               </a-statistic>
             </a-col>
             <a-col :span="8">
               <a-statistic title="总点赞量" :value="statistic.voteCount">
                 <template #suffix>
                   <like-outlined/>
                 </template>
               </a-statistic>
             </a-col>
             <a-col :span="8">
               <a-statistic title="点赞率" :value="statistic.voteCount/statistic.viewCount"
                            :precision="2"
                            suffix="%"
                            :value-style="{color:'#cf1322'}">
                 <template #suffix>
                   <like-outlined/>
                 </template>
               </a-statistic>
             </a-col>
           </a-row>
         </a-card>
       </a-col>
    </a-row>
    <br>
<!--    今日的点赞数和阅读数，还有增长率-->
    <a-row :gutter="16">
      <a-col :span="12">
        <a-card>
          <a-row>
            <a-col :span="12">
              <a-statistic title="今日阅读" :value="statistic.todayViewCount" style="margin-right: 50px">
                <template #suffix>
                  <UserOutlined/>
                </template>
              </a-statistic>
            </a-col>
            <a-col :span="12">
              <a-statistic title="今日点赞" :value="statistic.todayVoteCount">
                <template #suffix>
                  <like-outlined/>
                </template>
              </a-statistic>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="12">
          <a-card>
              <a-row>
                <a-col :span="12">
                  <a-statistic title="预计今日阅读"
                               :value="statistic.todayViewIncrease"
                               :value-style="{color : '#0000ff'}">
                    <template #suffix>
                      <UserOutlined/>
                    </template>
                  </a-statistic>
                </a-col>
                <a-col :span="12">
                  <a-statistic
                    title="预计今日阅读增长"
                    :value="statistic.todayViewIncreaseRateAbs"
                    :precision="2"
                    suffix="%"
                    class="demo-class"
                    :value-style="statistic.todayViewIncreaseRate<0?{color :'#3f8600'}:{color:'#cf1322'}">
                    <template #prefix>
                      <arrow-down-outlined v-if="statistic.todayViewIncreaseRate<0"/>
                      <arrow-up-outlined v-if="statistic.todayViewIncreaseRate>=0"/>
                    </template>
                  </a-statistic>
                </a-col>
              </a-row>
            </a-card>
          </a-col>
        </a-row>
    <br>
    <a-row>
      <a-col :span="24">
        <div id="main" style="width: 100%;height:300px;"></div>
      </a-col>
    </a-row>
  </div>
</template>

<!--定义的组件-->
<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
//echarts表单

declare let echarts:any;


export default defineComponent({
  //组件名字
  name: 'the-welcome',
  setup(){
    const statistic=ref();
    statistic.value={};
    const getStatistic = () => {
      axios.get('/ebook-snapshot/get-statistic').then((response)=>{
        const data=response.data;
        if (data.success){
          const statisticResp=data.content;
          if(statisticResp[1] ===undefined){
            statistic.value.viewCount=statisticResp[0].viewCount;
            statistic.value.voteCount=statisticResp[0].voteCount;
            statistic.value.todayViewCount=statisticResp[0].viewIncrease;
            statistic.value.todayVoteCount=statisticResp[0].voteIncrease;
          }else {
            statistic.value.viewCount=statisticResp[1].viewCount;
            statistic.value.voteCount=statisticResp[1].voteCount;
            statistic.value.todayViewCount=statisticResp[1].viewIncrease;
            statistic.value.todayVoteCount=statisticResp[1].voteIncrease;
          }

        //  按分钟计算当前时间点，占一天的百分比
          const now=new Date();
          const nowRate=(now.getHours()*60+now.getMinutes())/(60*24);
          statistic.value.todayViewIncrease=parseInt(String(statisticResp[1].viewIncrease/nowRate));
          //todayViewIncreaseRate:今日预计增长率
          statistic.value.todayViewIncreaseRate=(statistic.value.todayViewIncrease-statisticResp[0].viewIncrease)/statisticResp[0].viewIncrease*100;
          statistic.value.todayViewIncreaseRateAbs=Math.abs(statistic.value.todayViewIncreaseRate);
        }
      });
    };

    //初始化折线图
    const init30DayEcharts = (list:any) => {
    //  基于准备好的dom，初始化echarts实例
      const myChart=echarts.init(document.getElementById('main'));
      //横轴
      const xAxis=[];
      //纵轴
      const seriesView=[];
      const seriesVote=[];
      for (let i = 0; i < list.length; i++) {
        const record=list[i];
        //横轴是天日期
        xAxis.push(record.date);
        //纵轴阅读数
        seriesView.push(record.viewIncrease);
        //纵轴点赞数
        seriesVote.push(record.voteIncrease);
      }
    //  指定图表的配置项和数据
      const option = {
        title: {
          text: '30天趋势图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['总阅读量','总点赞量']
        },
        grid: {
          //图标左边距离
          left: '1%',
          //图标右边距离
          right: '3%',
          //图标下边距离
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        //x轴
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        //y轴
        series: [
          {
            name: '总阅读数',
            type: 'line',
            // stack: 'Total',
            data: seriesView,
            smooth:true
          },
          {
            name: '总点赞数',
            type: 'line',
            // stack: 'Total',
            data: seriesVote,
            smooth:true
          }
        ]
      };
    //  渲染图标出来
      myChart.setOption(option);
    }

    //渲染图标前先去后端获取30天的数据
    const get30Statistic = () => {
      axios.get('/ebook-snapshot/get-30-statistic').then((response)=>{
        const data=response.data;
        if (data.success){
          const statisticList=data.content;
          //渲染图表
          init30DayEcharts(statisticList);
        }
      });
    };

    onMounted(()=>{
      //获取今日和昨天的阅读数和点赞数
      getStatistic();
      //获取30天记录
      get30Statistic();
    });
    return{
      statistic
    }
  }
});
</script>
