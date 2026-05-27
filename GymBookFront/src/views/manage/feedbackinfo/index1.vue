<template>
  <div class="app-container">
    <el-card style="color: #DE8692; font-size: 15px; font-weight: bold; width: 45%" shadow="always">
      我们欢迎每一位顾客对我们的服务做出评价，您的反馈让健身房变得更好！
    </el-card>
    
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px" style="margin-top: 20px;">

      <el-form-item label="被评价课程" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入被评价课程"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被评价教练" prop="coachName">
        <el-input
          v-model="queryParams.coachName"
          placeholder="请输入被评价教练"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被评价场馆" prop="venueName">
        <el-input
          v-model="queryParams.venueName"
          placeholder="请输入被评价场馆"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="rate">
        <el-input
          v-model="queryParams.rate"
          placeholder="请输入评分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择评价时间">
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

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>



    <el-table v-loading="loading" :data="feedbackinfoList" @selection-change="handleSelectionChange">

      <el-table-column align="center" prop="id" />
      <el-table-column label="被评价课程" align="center" prop="courseName" >
        <template #default="scope">{{ scope.row.courseName || '-' }}</template>
      </el-table-column>
      <el-table-column label="被评价教练" align="center" prop="coachName" >
        <template #default="scope">{{ scope.row.coachName || '-' }}</template>
      </el-table-column>
      <el-table-column label="被评价场馆" align="center" prop="venueName" >
        <template #default="scope">{{ scope.row.venueName || '-' }}</template>
      </el-table-column>

      <el-table-column label="评分" width="200"  align="center" prop="rate">
        <template #default="scope" >
          <el-rate
            v-model="scope.row.rate"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value} 分"
          />
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="remark" >
        <template #default="scope">{{ scope.row.remark || '该用户没有填写评价内容' }}</template>
      </el-table-column>
      <el-table-column label="评价时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
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

    <!-- 添加或修改查看评价对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="feedbackinfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评价用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入评价用户id" />
        </el-form-item>
        <el-form-item label="被评价课程id" prop="courseId">
          <el-input v-model="form.courseId" placeholder="请输入被评价课程id" />
        </el-form-item>
        <el-form-item label="被评价教练id" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入被评价教练id" />
        </el-form-item>
        <el-form-item label="被评价场馆id" prop="venueId">
          <el-input v-model="form.venueId" placeholder="请输入被评价场馆id" />
        </el-form-item>
        <el-form-item label="评分" prop="rate">
          <el-input v-model="form.rate" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="内容" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入内容" />
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

<script setup name="Feedbackinfo">
import { listFeedbackinfo, getFeedbackinfo, delFeedbackinfo, addFeedbackinfo, updateFeedbackinfo } from "@/api/manage/feedbackinfo";

const { proxy } = getCurrentInstance();

const feedbackinfoList = ref([]);
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
    courseId: null,
    coachId: null,
    venueId: null,
    rate: null,
    remark: null,
    createTime: null,
    nickName: null,
    venueName: null,
    courseName: null,
    coachName: null
  },
  rules: {
    userId: [
      { required: true, message: "评价用户id不能为空", trigger: "blur" }
    ],
    rate: [
      { required: true, message: "评分不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询查看评价列表 */
function getList() {
  loading.value = true;
  listFeedbackinfo(queryParams.value).then(response => {
    feedbackinfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    console.log("rate",response);
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
    courseId: null,
    coachId: null,
    venueId: null,
    rate: null,
    remark: null,
    createTime: null,
    nickName: null,
    venueName: null,
    courseName: null,
    coachName: null
  };
  proxy.resetForm("feedbackinfoRef");
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
  title.value = "添加查看评价";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getFeedbackinfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改查看评价";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["feedbackinfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateFeedbackinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addFeedbackinfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除查看评价编号为"' + _ids + '"的数据项？').then(function() {
    return delFeedbackinfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/feedbackinfo/export', {
    ...queryParams.value
  }, `feedbackinfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
