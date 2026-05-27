<template>
  <div class="app-container">
    <el-card class="tip-card">
      <template #header>
        <div class="tip-header">
          <el-icon><InfoFilled /></el-icon>
          <span class="tip-title">温馨提示</span>
        </div>
      </template>
      <div class="tip-content">
        <div class="tip-item">
          <el-icon><Document /></el-icon>
          <span>本模块存放您的学员信息，方便您随时查看学员情况，助力更精准的课程安排！</span>
        </div>
        <div class="tip-item">
          <el-icon><Service /></el-icon>
          <span>如果学员长时间未参加课程，可主动联系他们，了解情况并提供合理建议。</span>
        </div>
        <div class="tip-item">
          <el-icon><Timer /></el-icon>
          <span>定期关注学员的训练进度，为他们制定更合适的训练计划，提高课程效果。</span>
        </div>
        <div class="tip-item">
          <el-icon><ChatDotRound /></el-icon>
          <span>鼓励学员反馈训练感受，增进教练与学员之间的信任，提高留存率。</span>
        </div>
        <div class="tip-item">
          <el-icon><Notebook /></el-icon>
          <span>建议记录学员的训练偏好、健康状况等，帮助制定更个性化的训练方案。</span>
        </div>
      </div>
    </el-card>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange" style="margin-top: 20px;">

      <el-table-column label="课程名" align="center" prop="name" />
      <el-table-column label="学员名称" align="center" prop="nickName" />
      <el-table-column label="学员电话" align="center" prop="phonenumber" />
      <el-table-column label="学员邮箱" align="center" prop="email" />
      <el-table-column label="报名时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学员管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="studentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="form.name" placeholder="请输入课程名" />
        </el-form-item>
        <el-form-item label="授课教练id" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入授课教练id" />
        </el-form-item>
        <el-form-item label="开课状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in course_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="课程器材" prop="equipment">
          <el-radio-group v-model="form.equipment">
            <el-radio
              v-for="dict in equipment"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="最大报名人数" prop="capacity">
          <el-input v-model="form.capacity" placeholder="请输入最大报名人数" />
        </el-form-item>
        <el-form-item label="开课场馆id" prop="venueId">
          <el-input v-model="form.venueId" placeholder="请输入开课场馆id" />
        </el-form-item>
        <el-form-item label="开课时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开课时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="closeTime">
          <el-date-picker clearable
            v-model="form.closeTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.tip-card {
  max-width: 680px;
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.tip-header {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #409EFF;
}

.tip-title {
  font-size: 18px;
  font-weight: 600;
}

.tip-content {
  padding: 10px 0;
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  padding: 12px 0;
  font-size: 15px;
  line-height: 1.6;
  color: #606266;
  border-bottom: 1px dashed #EBEEF5;
}

.tip-item:last-child {
  border-bottom: none;
}

.tip-item .el-icon {
  margin-top: 3px;
  font-size: 18px;
  color: #409EFF;
}

:deep(.el-card__header) {
  padding: 15px 20px;
  border-bottom: 1px solid #EBEEF5;
  background-color: #F8F9FB;
}

/* 鼠标悬停效果 */
.tip-item:hover {
  background-color: #F8F9FB;
  transition: background-color 0.3s ease;
}
</style>

<script setup name="Student">
import { listStudent, getStudent, delStudent, addStudent, updateStudent } from "@/api/manage/student";
import useUserStore from '@/store/modules/user'
import { 
  InfoFilled,
  Document,
  Service,
  Timer,
  Aim,
  ChatDotRound,
  Notebook
} from '@element-plus/icons-vue'

const userStore = useUserStore()
const { proxy } = getCurrentInstance();
const { course_status, equipment } = proxy.useDict('course_status', 'equipment');

const studentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    coachId: Number(userStore.id),
    status: null,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    nickName: null,
    phonenumber: null,
    email: null,
    createTime: null,
  },
  rules: {
    name: [
      { required: true, message: "课程名不能为空", trigger: "blur" }
    ],
    equipment: [
      { required: true, message: "课程器材不能为空", trigger: "change" }
    ],
    capacity: [
      { required: true, message: "最大报名人数不能为空", trigger: "blur" }
    ],
    venueId: [
      { required: true, message: "开课场馆id不能为空", trigger: "blur" }
    ],
    startTime: [
      { required: true, message: "开课时间不能为空", trigger: "blur" }
    ],
    closeTime: [
      { required: true, message: "结束时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询学员管理列表 */
function getList() {
  loading.value = true;
  listStudent(queryParams.value).then(response => {
    studentList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    name: null,
    coachId: Number(userStore.id),
    status: null,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    createTime: null,
    nickName: null,
    phonenumber: null,
    email: null,
    createTime: null,
  };
  proxy.resetForm("studentRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加学员管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getStudent(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改学员管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["studentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateStudent(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addStudent(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/student/export', {
    ...queryParams.value
  }, `student_${new Date().getTime()}.xlsx`)
}

getList();
</script>
