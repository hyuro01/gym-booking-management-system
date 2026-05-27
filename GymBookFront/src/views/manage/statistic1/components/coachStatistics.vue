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
  cates: {
    type: Array,
    default: () => []
  },
  data: {
    type: Array,
    default: () => []
  },
})


// 定义 DOM 元素引用
const chartRef = ref(null);

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "教练预约量统计"},
  tooltip: { trigger: "item"},
  xAxis: { type: "category", data: ["A", "B", "C"] },
  yAxis: { type: "value" },
  series: [{ data: [1, 0, 2], type: "bar" }]
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
    xAxis: { type: "category", data: props.cates }, // 教练名
    series: [{ data: props.data, type: "bar" }]   // 预约量
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("CoachStatistics 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>
