<template>
    <div class="statistic-container" >
      <!-- 统计卡片 -->
      <StatisticCards />

      <section class="charts-container"  style="height: 100%; display: flex; justify-content: space-around;">
        <!-- 左容器 -->
        <section class="left-container"  style="flex: 1; margin: 10px;">
          <div class="chart-card">
            <CoachRatingChart :cates="coachName" :data="coachRate" />
          </div>
          <div class="chart-card">
            <CourseStudentsChart :cates="courseName" :data="courseNum" />
          </div>
    </section>

    <!-- 右容器 -->
    <section class="right-container" style="flex: 1; margin: 10px;">
      <div class="chart-card">

        <CourseStatusPieChart :cates="courseStatusName" :data="courseStatusNum" />
      </div>
      <div class="chart-card">

        <BookingTrendChart :cates="coachDates" :data="coachData" />
      </div>
    </section>
  </section>
    </div>



</template>

<script setup name="Statistic3">
import { ref, reactive, getCurrentInstance } from 'vue'
import { useTransition } from '@vueuse/core'
import { Document, Star, Calendar, Trophy } from '@element-plus/icons-vue'
import useUserStore from '@/store/modules/user'
import { listStatistic3,coachCourseStatus,courseStudent,coachStudent, } from "@/api/manage/statistic3"
import {
  get1stAndToday,
  past7Day,
  past30Day,
  pastWeek,
  pastMonth,
} from '@/utils/formValidate'

// 导入图表组件
import CoachRatingChart from './components/CoachRatingChart.vue'
import CourseStudentsChart from './components/CourseStudentsChart.vue'
import CourseStatusPieChart from './components/CourseStatusPieChart.vue'
import BookingTrendChart from './components/BookingTrendChart.vue'
import StatisticCards from "@/views/manage/statistic3/components/statisticCards.vue";

const { proxy } = getCurrentInstance()
const userStore = useUserStore()

const statistic3List = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 预约状态名
const {  course_status } = proxy.useDict('course_status');

// 存放教练评分数据
const coachName = ref([]);
const coachRate = ref([]);

// 存放健身课程设置状态数据
const courseStatusName = ref([]);
const courseStatusNum = ref([]);

// 存放课程数据
const courseNum = ref([]);
const courseName = ref([]);

// 存放教练预约数据
const coachDates = ref([]);
const coachData = ref([]);

// 使用 `past7Day` 获取过去7天的日期
const getPast7Days = () => {
  const [begin, end] = past7Day(); // 获取过去7天的开始和结束日期
  queryParams.value.begin = begin;
  queryParams.value.end = end;
};


// 查询参数
const queryParams = ref({
  userId: userStore.id,
  begin: null,
  end: null,
  year: new Date().getFullYear()
})

// 初始化数据
function getList(){
  loading.value = true;

  // 获取过去7天的日期范围
  getPast7Days();

  // 读取评分数据
  listStatistic3(queryParams.value)
    .then(response => {
      console.log("响应数据：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      coachName.value = data.nameList.split(","); // 转换为数组
      coachRate.value = data.rateList.split(",").map(item => parseFloat(item)); // 转换为数字数组

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

  coachCourseStatus(queryParams.value)
    .then(response => {
      console.log("课程状态：", response);  // 打印响应内容
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

  courseStudent(queryParams.value)
    .then(response => {
      console.log("课程报名量：", response);  // 打印响应内容
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

  coachStudent(queryParams.value)
    .then(response => {
      console.log("教练预约量：", response);  // 打印响应内容
      const data = response.data; // 假设后端返回的数据是 data 字段
      coachDates.value = data.dateList.split(","); // 转换为数组
      coachData.value = data.bookList.split(",").map(item => parseInt(item)); // 转换为数字数组

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

// 页面加载时获取数据
getList()
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