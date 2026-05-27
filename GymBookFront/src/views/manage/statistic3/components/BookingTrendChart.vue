<template>
  <div ref="chartRef" style="width: 550px; height: 360px"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import theme from "@/assets/styles/westeros.json"

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
  title: { text: "预约数量趋势"},
  tooltip: { trigger: "axis"},
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: { type: "category", data: ["A", "B", "C"] },
  yAxis: { type: "value" },
  series: [
      { 
        name: "a",
        stack: "total",
        data: [120, 200, 150], 
        type: "line" 
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
    myChart = echarts.init(chartRef.value,"westeros");  // 传入数据，使用主题
  }
  myChart.setOption({
    ...chartOptions,
  xAxis: { 
    type: "category", 
    data: props.cates, 
    boundaryGap: false, 
  },
  yAxis: {
    type: 'value',
    name: '预约数'
  },
  series: [
      {
        name: '预约数量',
        type: 'line',
        smooth: true,
        data: props.data,
        symbol: 'circle',
        symbolSize: 8,
        areaStyle: {}
      }
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
