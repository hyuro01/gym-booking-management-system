<template>
  <div id="myChart" ref="chartRef" style="width: 550px; height: 400px"></div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
// 引入echarts主题
import theme from "@/assets/styles/westeros.json";

echarts.registerTheme('westeros', theme)

const props = defineProps({
  cates: {    // 存放预约类型
    type: Array,
    default: () => []
  },
  data: {     // 预约量
    type: Array,
    default: () => []
  },
})


// 定义 DOM 元素引用
const chartRef = ref(null);

let myChart = null

// 绑定 ECharts 配置
const chartOptions = {
  title: { 
    text: "预约状态统计",
    left: 'center' 
  },
  tooltip: { trigger: "item" },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    { 
      data: [
        {value: 1, name: 'A'},
        {value: 3, name: 'B'},
      ], 
      type: "pie",
      radius: "50%", 
    }]
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
    legend:{
      data: props.cates,
      orient: 'vertical',
      left: 'left',
      top: '8%'
    },
    series: [{ 
      name: '预约状态',
      radius: ["30%", "50%"],  // 让饼图缩小，避免占满整个区域
      center: ["50%", "55%"],  // 让饼图稍微下移，防止与标题重叠
      data: props.cates.map((name, index) => ({   // 动态存入数据
        name: name,
        value: props.data[index]
      })),
      label: {
        fontSize: 14,        // 适当调整文字大小
      },
      type: "pie" 
    }]
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("BookStatusStatistics 组件已挂载！");
  // initChart();
});


watch([() => props.cates, () => props.data], ([newCates, newData]) => {
  console.log("监听到数据更新:", newCates, newData);
  initChart();
});

</script>
