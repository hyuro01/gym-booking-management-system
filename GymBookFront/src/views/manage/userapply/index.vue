<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="申请预约的用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入申请预约的用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的场馆" prop="venueId">
        <el-input
          v-model="queryParams.venueId"
          placeholder="请输入预约的场馆"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的课程" prop="courseId">
        <el-input
          v-model="queryParams.courseId"
          placeholder="请输入预约的课程"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的教练" prop="coachId">
        <el-input
          v-model="queryParams.coachId"
          placeholder="请输入预约的教练"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的时间" prop="bookTime">
        <el-date-picker clearable
          v-model="queryParams.bookTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择预约的时间">
        </el-date-picker>
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
          v-hasPermi="['manage:userapply:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userapplyList" @selection-change="handleSelectionChange">
      <el-table-column label="预约id" align="center" prop="id" />
      <el-table-column label="申请预约的用户" align="center" prop="nickName" />
      <el-table-column label="预约的场馆" align="center" prop="venue.name">
        <template #default="scope">{{ scope.row.venue.name || '-' }}</template>
      </el-table-column>
      <el-table-column label="预约的课程" align="center" prop="courseName">
        <template #default="scope">{{ scope.row.courseName || '-' }}</template>
      </el-table-column>
      <el-table-column label="预约的教练" align="center" prop="coachName">
        <template #default="scope">{{ scope.row.coachName || '-' }}</template>
      </el-table-column>
      <el-table-column label="预约申请的状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="book_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="预约的时间" align="center" prop="bookTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.bookTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:userapply:edit']">审核</el-button>
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

    <!-- 添加或修改用户预约对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="userapplyRef" :model="form" :rules="rules" label-width="130px">

        <el-form-item label="预约申请的状态" prop="status">
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

<script setup name="Userapply">
import { listUserapply, getUserapply, delUserapply, addUserapply, updateUserapply } from "@/api/manage/userapply";

const { proxy } = getCurrentInstance();
const { book_status } = proxy.useDict('book_status');
const options = ref([
  { key: 0, label: '待审核'},
  { key: 1, label: '已通过'},
  { key: 2, label: '已取消'},
]);

const userapplyList = ref([]);
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
    userId: null,
    venueId: null,
    courseId: null,
    coachId: null,
    status: null,
    bookTime: null,
    nickName: null,
    venueName: null,
    courseName: null,
    coachName: null
  },
  rules: {
    userId: [
      { required: true, message: "申请预约的用户不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "预约申请的状态不能为空", trigger: "change" }
    ],
    bookTime: [
      { required: true, message: "预约的时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户预约列表 */
function getList() {
  loading.value = true;
  listUserapply(queryParams.value).then(response => {
    console.log("response:",response);
    userapplyList.value = response.rows;
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
    userId: null,
    venueId: null,
    courseId: null,
    coachId: null,
    status: null,
    bookTime: null,
    createTime: null,
    nickName: null,
    venueName: null,
    courseName: null,
    coachName: null
  };
  proxy.resetForm("userapplyRef");
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
  title.value = "添加用户预约";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getUserapply(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改用户预约";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userapplyRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUserapply(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUserapply(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除用户预约编号为"' + _ids + '"的数据项？').then(function() {
    return delUserapply(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/userapply/export', {
    ...queryParams.value
  }, `userapply_${new Date().getTime()}.xlsx`)
}

getList();
</script>
