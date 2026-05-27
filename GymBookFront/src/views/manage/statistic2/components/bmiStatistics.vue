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
  }
})


// 定义 DOM 元素引用
const chartRef = ref(null);

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "BMI变化统计"},
  tooltip: { trigger: "axis"},
  legend: { data: ["用户bmi", "偏瘦", "正常", "超重"]},
  xAxis: { type: "category", data: ["A", "B", "C"] },
  yAxis: { type: "value" },
  series: [
      { 
        name: "用户bmi",
        stack: "total",
        data: [120, 200, 150], 
        type: "line" 
      },
      {
        name: "偏瘦",
        type: "line",
        stack: "total",
        areaStyle: {},  // 堆叠图
        data: [18.5, 18.5, 18.5], // 固定的18.5数据
        lineStyle: { type: "dashed" },  // 设置线的样式为虚线
      },
      {
        name: "正常",
        type: "line",
        stack: "total",
        areaStyle: {},
        data: [23.9, 23.9, 23.9], // 固定的23.9数据
        lineStyle: { type: "dashed" },
      },
      {
        name: "超重",
        type: "line",
        stack: "total",
        areaStyle: {},
        data: [27.9, 27.9, 27.9], // 固定的27.9数据
        lineStyle: { type: "dashed" },
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
  xAxis: { type: "category", data: props.cates },
    series: [
      { name: "用户bmi", data: props.data, type: "line" },
      {
        name: "偏瘦",
        type: "line",
        stack: "total",
        areaStyle: {},
        data: new Array(props.cates.length).fill(18.5), // 固定的18.5数据
        lineStyle: { type: "dashed" },
      },
      {
        name: "正常",
        type: "line",
        stack: "total",
        areaStyle: {},
        data: new Array(props.cates.length).fill(23.9), // 固定的23.9数据
        lineStyle: { type: "dashed" },
      },
      {
        name: "超重",
        type: "line",
        stack: "total",
        areaStyle: {},
        data: new Array(props.cates.length).fill(27.9), // 固定的27.9数据
        lineStyle: { type: "dashed" },
      },

    ]
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("BMIStatistics 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>
