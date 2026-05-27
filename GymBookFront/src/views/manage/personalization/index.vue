<template>
  <div class="app-container">
    <h2 class="page-title">今日推荐课程</h2>
    <div class="course-list">
      <el-descriptions
        v-for="(course, index) in courseList"
        :key="index"
        direction="vertical"
        border
        class="course-description"
      >
        <el-descriptions-item width="45%" label="课程名">
          <span class="course-name">{{ course.name }}</span>
        </el-descriptions-item>
        <el-descriptions-item width="30%" label="授课教练">
          <span class="coach-name">{{ course.coachName }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="开课场馆">
          <span class="venue-name">{{ course.venueName }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="课程器材">
          <dict-tag :options="equipment" :value="course.equipment" />
        </el-descriptions-item>
        <el-descriptions-item label="开课时间">
          <span class="time">{{ parseTime(course.startTime, '{h}:{i}') }} - {{ parseTime(course.closeTime, '{h}:{i}') }}</span>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<style scoped>
.app-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 20px;
}

.course-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.course-description {
  padding: 15px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(208, 215, 228, 0.1);
}

.course-name, .coach-name, .venue-name, .time {
  font-size: 16px;
  color: #606266;
}

:deep(.el-descriptions__label) {
  font-weight: bold;
  color: #409EFF;
}

:deep(.el-descriptions__item) {
  margin-bottom: 10px;
}

:deep(.el-descriptions__border) {
  border-color: #ebeef5;
}
</style>

<script setup name="Personalization">
import { listPersonalization } from "@/api/manage/personalization";
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const { proxy } = getCurrentInstance();
const { goal } = proxy.useDict('goal');
const { equipment } = proxy.useDict('equipment');


const courseList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const userId = ref(0); // 用于存储 userId，格式为数字

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: Number(userStore.id),
    courseId: null,
    name: null,
    equipment: null,
    startTime: null,
    closeTime: null,
    venueName: null,
    coachName: null,
  },
  rules: {
    userId: [
      { required: true, message: "数据所属用户id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询列表 */
function getList() {
  loading.value = true;
  // 获取当前登录的用户id
  console.log("userinfo:",userStore.getInfo())


  listPersonalization(queryParams.value).then(response => {
    // 打印传入的条件
    console.log('queryParams', queryParams);
    courseList.value = response.data;
    total.value = response.total;
    loading.value = false;
    console.log("response:", response);  // 打印响应内容
    console.log("courseList:", courseList);
  });
}


// 表单重置
function reset() {
  form.value = {
    id: null,
    userId: Number(userStore.id),
    courseId: null,
    name: null
  };
  proxy.resetForm("healthRef");
}


getList();
</script>
