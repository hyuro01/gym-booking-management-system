<template>
  <div id="myChart" ref="chartRef" style="width: 550px; height: 365px"></div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
// 水波图
import 'echarts-liquidfill';
// 引入echarts主题
import theme from "@/assets/styles/westeros.json";

echarts.registerTheme('westeros', theme)

const props = defineProps({
  cates: {
    type: Array,
    default: () => []
  },
  data: {
    type: Array,
    default: () => []
  }
})


// 定义 DOM 元素引用
const chartRef = ref(null);

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "健身目标进度"},
  tooltip: {
    trigger: 'item', // 提示框触发方式
  },
  series: [
    {
      name: '健身目标完成度',
      type: 'liquidFill', // 水波图
      radius: '60%',
      data: [0.75],
    }
  ]
};

// 初始化图表
const initChart = () => {
  if (!chartRef.value) {
    console.error("chartRef.value 为空，无法初始化 ECharts");
    return;
  }
  if(!myChart){
    myChart = echarts.init(chartRef.value, 'westeros');
  }
  myChart.setOption({
    ...chartOptions,
    series: [
      { 
        name: '健身目标完成度',
        data: props.data, 
        type: "liquidFill",
        radius: '60%',
        backgroundStyle: {
              borderWidth: 1,
              color: "#93b7e3", //水球图内部背景色
        },
        outline: { 
          borderDistance: 10, 
          itemStyle: {
            borderWidth: 4,
            borderColor: "#9ea3f3", // 水球图外部圈颜色
          },
        },
        color: [ //波浪颜色渐变
              {
                type: "linear",
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 1,
                    color: "#edafda", //下
                  },
                  {
                    offset: 0,
                    color: "#a5e7f0",
                  },
                ],
                globalCoord: false,
              },
            ],
       }
      ]
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("GoalStatistics 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>
