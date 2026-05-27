<template>
  <div class="statistic-container">
    <!-- 统计卡片 -->
    <StatisticCards />
  <section class="charts-container" style="height: 100%; display: flex; justify-content: space-around;">

  
    <!-- 左容器 -->
    <section class="left-container" style="flex: 1; margin: 10px;">
      <div class="chart-card">
        <CourseStatistics :cates="courseName" :data="courseNum" />
      </div>
      <div class="chart-card">
        <CoachStatistics :cates="coachName" :data="coachNum" />
      </div>

     
    </section>

    <!-- 右容器 -->
    <section class="right-container" style="flex: 1; margin: 10px;">
      <div class="chart-card">
        <VenueStatistics :cates="venueDates" :data="venueNum" :name="venueName" />
      </div>
      <div class="chart-card">
        <section class="right-bottom" style="flex: 1; margin: 10px; display: flex; justify-content: space-between;">
          <BookStatusStatistics :cates="bookStatusName" :data="bookStatusNum" style="width: 45%;"/>
          <CourseStatusStatistics :cates="courseStatusName" :data="courseStatusNum"  style="width: 45%;"/>
        </section>
      </div>
 

      
    </section>

  </section>

  </div>



</template>

<script setup name="Statistic1">
import { listStatistic1, coachStatistic1, venueStatistic1, bookStatusStatistic1, courseStatusStatistic1 } from "@/api/manage/statistic1";
import CourseStatistics from "@/views/manage/statistic1/components/courseStatistics.vue";
import CoachStatistics from "@/views/manage/statistic1/components/coachStatistics.vue";
import VenueStatistics from "@/views/manage/statistic1/components/venueStatistics.vue";
import BookStatusStatistics from "@/views/manage/statistic1/components/bookStatusStatistics.vue";
import CourseStatusStatistics from "@/views/manage/statistic1/components/courseStatusStatistics.vue";
import StatisticCards from "@/views/manage/statistic1/components/statisticCards.vue";

import {
  get1stAndToday,
  past7Day,
  past30Day,
  pastWeek,
  pastMonth,
} from '@/utils/formValidate'
import { ref } from 'vue'
import { useTransition } from '@vueuse/core'

const source = ref(0)
const outputValue = useTransition(source, {
  duration: 1500,
})
source.value = 172000

const statistics = ref([
  { title: "总课程数", value: 12 },
  { title: "总教练数", value: 4 },
  { title: "总场馆数", value: 6 },
  { title: "总预约数", value: 34 }
]);

const { proxy } = getCurrentInstance();
// 预约状态名
const { book_status, course_status } = proxy.useDict('book_status', 'course_status');

const statistic1List = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 存放课程数据
const courseNum = ref([]);
const courseName = ref([]);

// 存放教练数据
const coachNum = ref([]);
const coachName = ref([]);

// 存放场馆数据
const venueNum = ref([]);
const venueName = ref([]);
const venueDates = ref([]);

// 存放预约状态数据
const bookStatusName = ref([]);
const bookStatusNum = ref([]);

// 存放设置状态数据
const courseStatusName = ref([]);
const courseStatusNum = ref([]);

const data = reactive({
  form: {},
  queryParams: {
    userId: null,
    begin: null,
    end: null,
    year: new Date().getFullYear(), // 今年的年份
  },
});

const { queryParams, form } = toRefs(data);

// 使用 `past7Day` 获取过去7天的日期
const getPast7Days = () => {
  const [begin, end] = past7Day(); // 获取过去7天的开始和结束日期
  queryParams.value.begin = begin;
  queryParams.value.end = end;
};

/** 查询课程报名数据 */
function getList() {
  loading.value = true;

  // 获取过去7天的日期范围
  getPast7Days();

  // 读取课程预约量
  listStatistic1(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      courseName.value = data.nameList.split(","); // 转换为数组
      courseNum.value = data.numberList.split(",").map(item => parseInt(item)); // 转换为数字数组

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

  // 读取教练预约量
  coachStatistic1(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      coachName.value = data.nameList.split(","); // 转换为数组
      coachNum.value = data.numberList.split(",").map(item => parseInt(item)); // 转换为数字数组
    
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

    // 读取场馆预约量
    venueStatistic1(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      venueName.value = data.nameList.split(","); // 转换为数组
      venueDates.value = data.dateList.split(","); // 转换为数组
      venueNum.value = data.numberList; // 数组
      console.log("number:", venueNum.value);
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

    // 读取预约类型和数量
    bookStatusStatistic1(queryParams.value)
    .then(response => {
      console.log("预约类型响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      bookStatusName.value = data.nameList.split(",").map(item => book_status.value[item].label); // 转换为对应的状态中文名数组
      bookStatusNum.value = data.numberList.split(",").map(item => parseInt(item)); // 转换为数字数组

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

    // 读取设置状态类型和数量
    courseStatusStatistic1(queryParams.value)
    .then(response => {
      console.log("课程设置状态响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      courseStatusName.value = data.nameList.split(",").map(item => course_status.value[item].label); // 转换为对应的状态中文名数组
      courseStatusNum.value = data.numberList.split(",").map(item => parseInt(item)); // 转换为数字数组

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