<template>
  <div ref="chartRef" style="width: 550px; height: 360px"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
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
const chartRef = ref(null)

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "评分对比"},
  tooltip: { trigger: "item"},
  xAxis: { type: "category", data: ["A", "B", "C"] },
  yAxis: { type: "value" },
  series: [{ data: [120, 200, 150], type: "bar" }]
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
  xAxis: { type: "category", data: props.cates }, // 教练和平均
    series: [{ data: props.data, type: "bar" }]   // 评分
  });

};

// 组件加载时初始化图表
onMounted(() => {
  console.log("CoachRatingChart 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>