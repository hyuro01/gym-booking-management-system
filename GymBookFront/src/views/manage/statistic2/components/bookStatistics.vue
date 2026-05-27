<template>
  <div id="myChart" ref="chartRef" style="width: 600px; height: 550px"></div>
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


function getVirtualData(dateList, bookList) {
  const data = [];
  for (let i = 0; i < dateList.length; i++) {
    data.push([dateList[i], bookList[i]]); // 直接使用后端返回的数据
  }
  return data;
}

// 绑定 ECharts 配置
const chartOptions = {
  title: { text: "全年健身房预约情况统计"},
  tooltip: { trigger: "item"},
  visualMap: [{
      type: 'piecewise',
      pieces: [{
        value: 0,
        color: '#f0f0f2'
      }, {
        min: 1,
        max: 3,
        color: '#daf3f0'
      }, {
        min: 4,
        max: 6,
        color: '#cceeea'
      }, {
        min: 7,
        max: 10,
        color: '#99ddd4'
      }, {
        min: 11,
        max: 15,
        color: '#73c0de'
      }, {
        min: 15,
        color: '#73c0de'
      }],
      orient: 'horizontal',
      left: 'center',
      top: 65
    }],
    calendar: {
    top: 120,
    left: 60,
    right: 60,
    cellSize: [50, 30],
    range: '2025',
    splitLine: {
        show: false
      },
    itemStyle: {    // 每个数据之间的间隔
      borderWidth: 3,
      borderColor:'#fff',
    },
    yearLabel: { show: false }
  },
  series: {   // 热力图+日历坐标系
    type: 'heatmap',
    coordinateSystem: 'calendar',
    data: []    // 数据是二维数组[date, number]
  }
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
      { data: getVirtualData(props.cates,props.data),     
        type: 'heatmap',
        coordinateSystem: 'calendar', 
      }
    ]
  });
};

// 组件加载时初始化图表
onMounted(() => {
  console.log("BookStatistics 组件已挂载！");
  // initChart();
});

watch(() => props.cates, (val) => {
  console.log(props)
  initChart()
})
</script>
