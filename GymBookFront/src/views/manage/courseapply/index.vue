<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="课程名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入课程名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="授课教练" prop="coachName">
        <el-input
          v-model="queryParams.coachName"
          placeholder="请输入授课教练"
          clearable
          @keyup.enter="handleQuery"
        />
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
      <el-form-item label="最大报名人数" prop="capacity">
        <el-input
          v-model="queryParams.capacity"
          placeholder="请输入最大报名人数"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['manage:courseapply:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseapplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程id" align="center" prop="id" />
      <el-table-column label="课程名" align="center" prop="name" />
      <el-table-column label="授课教练" align="center" prop="user.nickName" />
      <el-table-column label="开课状态" align="center" prop="status">
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

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:courseapply:edit']">审核</el-button>
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

    <!-- 添加或修改课程申请对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseapplyRef" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="开课状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in options"
              :key="dict.value"
              :label="dict.key"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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

<script setup name="Courseapply">
import { listCourseapply, getCourseapply, delCourseapply, addCourseapply, updateCourseapply } from "@/api/manage/courseapply";

const { proxy } = getCurrentInstance();
const { course_status, equipment } = proxy.useDict('course_status', 'equipment');
const options = ref([
  { key: 0, label: '申请中'},
  { key: 1, label: '已发布'},
  { key: 2, label: '已取消'},
]);

const courseapplyList = ref([]);
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
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询课程申请列表 */
function getList() {
  loading.value = true;
  listCourseapply(queryParams.value).then(response => {
    courseapplyList.value = response.rows;
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
    coachId: null,
    status: null,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    createTime: null,
    venueName: null,
    coachName: null,
  };
  proxy.resetForm("courseapplyRef");
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
  title.value = "添加课程申请";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseapply(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课程申请";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseapplyRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourseapply(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourseapply(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除课程申请编号为"' + _ids + '"的数据项？').then(function() {
    return delCourseapply(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/courseapply/export', {
    ...queryParams.value
  }, `courseapply_${new Date().getTime()}.xlsx`)
}

getList();
</script>
