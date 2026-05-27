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
  cates: {    // 存放设置类型
    type: Array,
    default: () => []
  },
  data: {     // 数量
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
    text: "课程设置状态统计",
    left: 'center'
   },
  tooltip: { trigger: "item" },
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
      name: '课程设置状态',
      radius: "50%",  // 让饼图缩小，避免占满整个区域
      center: ["55%", "60%"],  // 让饼图稍微下移，防止与标题重叠
      data: props.cates.map((name, index) => ({   // 动态存入数据
        name: name,
        value: props.data[index]
      })),
      type: "pie" 
    }]
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("CourseStatusStatistics 组件已挂载！");
  // initChart();
});


watch([() => props.cates, () => props.data], ([newCates, newData]) => {
  console.log("监听到数据更新:", newCates, newData);
  initChart();
});

</script>
