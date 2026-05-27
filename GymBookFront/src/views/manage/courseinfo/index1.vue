<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入课程名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="课程状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择课程状态" style="width: 200px" clearable>
          <el-option
            v-for="dict in course_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程器材" prop="equipment">
        <el-select v-model="queryParams.equipment" placeholder="请选择课程器材" style="width: 200px" clearable>
          <el-option
            v-for="dict in equipment"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="开课场馆" prop="venueName">
        <el-input
          v-model="queryParams.venueName"
          placeholder="请输入开课场馆"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:courseinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseinfoList" @selection-change="handleSelectionChange">
      
      <el-table-column label="课程id" align="center" prop="id" />
      <el-table-column label="课程名" align="center" prop="name" />
      <el-table-column label="授课教练" align="center" prop="coachName" />
      <el-table-column label="课程状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="course_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="课程器材" align="center" prop="equipment">
        <template #default="scope">
          <dict-tag :options="equipment" :value="scope.row.equipment"/>
        </template>
      </el-table-column>
      <el-table-column label="最大报名人数" align="center" prop="capacity" />
      <el-table-column label="开课场馆" align="center" prop="venueName" />
      <el-table-column label="开课时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="closeTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.closeTime, '{h}:{i}:{s}') }}</span>
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

   
  </div>
</template>

<script setup name="Courseinfo">
import { listCourseinfo, getCourseinfo, delCourseinfo, addCourseinfo, updateCourseinfo } from "@/api/manage/courseinfo";

const { proxy } = getCurrentInstance();
const { course_status, equipment } = proxy.useDict('course_status', 'equipment');

const courseinfoList = ref([]);
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
    coachId: null,
    status: null,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    venueName: null,
    coachName: null,
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

/** 查询课程信息列表 */
function getList() {
  loading.value = true;

  listCourseinfo(queryParams.value).then(response => {
    // 打印传入的条件
    console.log('queryParams', queryParams);

    courseinfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // 打印返回的数据
    console.log('response', response);
    console.log("courseinfo:",courseinfoList);
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
    coachId: null,
    status: null,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    createTime: null,
    venueName: null,
  };
  proxy.resetForm("courseinfoRef");
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
  title.value = "添加课程信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseinfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课程信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseinfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourseinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourseinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除课程信息编号为"' + _ids + '"的数据项？').then(function() {
    return delCourseinfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/courseinfo/export', {
    ...queryParams.value
  }, `courseinfo_${new Date().getTime()}.xlsx`)
}



getList();
</script>
