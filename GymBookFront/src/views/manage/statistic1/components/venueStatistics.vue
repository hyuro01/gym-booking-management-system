<template>
  <div id="myChart" ref="chartRef" style="width: 550px; height: 360px"></div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
// 引入echarts主题
import theme from "@/assets/styles/westeros.json";

echarts.registerTheme('westeros', theme)

const props = defineProps({
  cates: {  // 日期
    type: Array,
    default: () => []
  },
  data: {   // 预约量
    type: Array,
    default: () => []
  },
  name: {   // 场馆名
    type: Array,
    default: () => []
  }
})


// 定义 DOM 元素引用
const chartRef = ref(null);

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "场馆预约量变化统计"},
  tooltip: { trigger: "axis"},
  legend: { data: ["1", "2", "3", "4"]},
  xAxis: { type: "category", data: ["A", "B", "C"] },
  yAxis: { type: "value" },
  series: [
      { 
        name: "1",
        stack: "total",
        data: [2, 3, 1], 
        type: "line" 
      },
      {
        name: "2",
        type: "line",
        stack: "total",
        data: [1, 0, 0],
      },
      {
        name: "3",
        type: "line",
        stack: "total",
        data: [2, 9, 4], 
      },
      {
        name: "4",
        type: "line",
        stack: "total",
        data: [5, 0, 3],
      },
  ]
};

// 初始化图表
const initChart = () => {
  if (!chartRef.value) {
    console.error("chartRef.value 为空，无法初始化 ECharts");
    return;
  }
  if(!myChart){
    myChart = echarts.init(chartRef.value,"westeros");  // 传入数据，使用主题
  }
  myChart.setOption({
    ...chartOptions,
  xAxis: { type: "category", data: props.cates }, // X轴是日期
  series: props.name.map((venue, index) => ({   // 动态存入数据
      name: venue,
      type: "line",
      data: props.data[index] // 每个场馆对应的预约量
    }))
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("VenueStatistics 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>
