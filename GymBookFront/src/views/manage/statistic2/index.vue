<template>
  <div class="statistic-container">
    <!-- 统计卡片 -->
    <StatisticCards />
    
    <!-- 图表区域 -->
    <section class="charts-container">
      <!-- 左容器 -->
      <section class="left-container">
        <div class="chart-card">
          <WeightStatistics :cates="weightDates" :data="weightData" />
        </div>
        <div class="chart-card">
          <BMIStatistics :cates="bmiDates" :data="bmiData" />
        </div>
      </section>

      <!-- 右容器 -->
      <section class="right-container">
        <div class="chart-card">
          <GoalStatistics :cates="bmiDates" :data="goalData" />
        </div>
        <div class="chart-card">
          <BookStatistics :cates="bookDates" :data="bookData" />
        </div>
      </section>
    </section>
  </div>
</template>

<script setup name="Statistic2">
import { listStatistic2, getStatistic2, bmiStatistic, goalStatistic, bookStatistic } from "@/api/manage/statistic2";
import WeightStatistics from "@/views/manage/statistic2/components/weightStatistics.vue";
import BMIStatistics from "@/views/manage/statistic2/components/bmiStatistics.vue";
import GoalStatistics from "@/views/manage/statistic2/components/goalStatistics.vue";
import BookStatistics from "@/views/manage/statistic2/components/bookStatistics.vue";
import StatisticCards from "@/views/manage/statistic2/components/statisticCards.vue";

import {
  get1stAndToday,
  past7Day,
  past30Day,
  pastWeek,
  pastMonth,
} from '@/utils/formValidate'

import useUserStore from '@/store/modules/user'

const userStore = useUserStore()

const { proxy } = getCurrentInstance();

const statistic2List = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
// 存放体重数据
const weightData = ref([]);
const weightDates = ref([]);
//存放bmi数据
const bmiData = ref([]);
const bmiDates = ref([]);
// 存放goal数据
const goalData = ref([]);
// 存放预约数据
const bookDates = ref([]);
const bookData = ref([]);

const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const state = reactive({
  dateList: [],
  weightList: [],
  loading: false,
});

const data = reactive({
  form: {},
  queryParams: {
    userId: Number(userStore.id),
    begin: null,
    end: null,
    year: new Date().getFullYear(), // 今年的年份
  }
});

const { queryParams, form } = toRefs(data);

// 使用 `past7Day` 获取过去7天的日期
const getPast7Days = () => {
  const [begin, end] = past7Day(); // 获取过去7天的开始和结束日期
  queryParams.value.begin = begin;
  queryParams.value.end = end;
};

/** 查询个人数据统计列表 */
function getList() {
  loading.value = true;

  // 获取过去7天的日期范围
  getPast7Days();

  listStatistic2(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      weightDates.value = data.dateList.split(","); // 转换为数组
      weightData.value = data.weightList.split(",").map(item => parseFloat(item)); // 转换为数字数组

    })
    .catch(error => {
      console.error("请求失败，错误信息：", error.message);
      if (error.response) {
        console.error("错误响应：", error.response);
        console.error("响应数据：", error.response.data);
      }
    if (error.request) {
      console.error("请求没有响应：", error.request);
    }
    loading.value = false;
  });

  bmiStatistic(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      bmiDates.value = data.dateList.split(","); // 转换为数组
      bmiData.value = data.bmiList.split(",").map(item => parseFloat(item)); // 转换为数字数组
      console.log("bmiData: ",bmiData);
    })
    .catch(error => {
      console.error("请求失败，错误信息：", error.message);
      if (error.response) {
        console.error("错误响应：", error.response);
        console.error("响应数据：", error.response.data);
      }
    if (error.request) {
      console.error("请求没有响应：", error.request);
    }
    loading.value = false;
  });

  goalStatistic(queryParams.value)
  .then(response => {
    console.log("响应数据：", response);  // 打印响应内容
    const data = response.data; // 假设后端返回的数据是 data 字段
    goalData.value[0] = (data.percent)/100; // 转换为数字数组
    console.log("goal: ",goalData);
  })
  .catch(error => {
      console.error("请求失败，错误信息：", error.message);
      if (error.response) {
        console.error("错误响应：", error.response);
        console.error("响应数据：", error.response.data);
      }
    if (error.request) {
      console.error("请求没有响应：", error.request);
    }
    loading.value = false;
  });

  bookStatistic(queryParams.value)
  .then(response => {
    console.log("响应数据：", response);  // 打印响应内容
    const data = response.data; // 假设后端返回的数据是 data 字段
    bookDates.value = data.dateList.split(","); // 转换为数组
    bookData.value = data.bookList.split(",").map(item => parseInt(item)); // 转换为数字数组
    console.log("book: ",bookData);
  })
  .catch(error => {
      console.error("请求失败，错误信息：", error.message);
      if (error.response) {
        console.error("错误响应：", error.response);
        console.error("响应数据：", error.response.data);
      }
    if (error.request) {
      console.error("请求没有响应：", error.request);
    }
    loading.value = false;
  });



}


getList();


</script>

<style lang="scss" scoped>
.statistic-container {
  padding: 20px;
  min-height: 100%;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe234 100%);
}

.charts-container {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  
  .left-container,
  .right-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .chart-card {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    border-radius: 16px;
    padding: 20px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: transform 0.3s ease;
    
    &:hover {
      transform: translateY(-5px);
    }
  }
}


</style>