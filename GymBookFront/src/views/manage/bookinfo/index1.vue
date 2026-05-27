<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="120px">

      <el-form-item label="预约的场馆" prop="venueName">
        <el-input
          v-model="queryParams.venueName"
          placeholder="请输入预约的场馆"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的课程" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入预约的课程"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约的教练" prop="coachName">
        <el-input
          v-model="queryParams.coachName"
          placeholder="请输入预约的教练"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约申请的状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择预约申请的状态" clearable style="width: 200px">
          <el-option
            v-for="dict in book_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookinfoList" @selection-change="handleSelectionChange">
      <el-table-column label="预约的时间" align="center" prop="bookTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.bookTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预约的场馆" align="center" prop="venueName">
        <template #default="scope">{{ scope.row.venueName || '-' }}</template>
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

    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改预约信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="bookinfoRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="申请预约的用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入申请预约的用户" />
        </el-form-item>
        <el-form-item label="预约的场馆" prop="venueId">
          <el-input v-model="form.venueId" placeholder="请输入预约的场馆" />
        </el-form-item>
        <el-form-item label="预约的课程" prop="courseId">
          <el-input v-model="form.courseId" placeholder="请输入预约的课程" />
        </el-form-item>
        <el-form-item label="预约的教练" prop="coachId">
          <el-input v-model="form.coachId" placeholder="请输入预约的教练" />
        </el-form-item>
        <el-form-item label="预约申请的状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in book_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="预约的时间" prop="bookTime">
          <el-date-picker clearable
            v-model="form.bookTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择预约的时间">
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

<script setup name="Bookinfo">
import { listBookinfo, getBookinfo, delBookinfo, addBookinfo, updateBookinfo } from "@/api/manage/bookinfo";
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const { proxy } = getCurrentInstance();
const { book_status } = proxy.useDict('book_status');

const bookinfoList = ref([]);
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
    userId: Number(userStore.id),
    venueId: null,
    courseId: null,
    coachId: null,
    status: null,
    bookTime: null,
    nickName: null,
    coachName: null,
    courseName: null,
    venueName: null,
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

/** 查询预约信息列表 */
function getList() {
  loading.value = true;
  // 获取当前登录的用户id
  console.log("userinfo:",userStore.getInfo())
  listBookinfo(queryParams.value).then(response => {
    // 打印传入的条件
    console.log('queryParams', queryParams);
    bookinfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    console.log("response:",response);
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
    userId: Number(userStore.id),
    venueId: null,
    courseId: null,
    coachId: null,
    status: null,
    bookTime: null,
    createTime: null,
    nickName: null,
    coachName: null,
    courseName: null,
    venueName: null,
  };
  proxy.resetForm("bookinfoRef");
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

/** 提交按钮 */
function submitForm() {
  proxy.$refs["bookinfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateBookinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addBookinfo(form.value).then(response => {
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
  proxy.download('manage/bookinfo/export', {
    ...queryParams.value
  }, `bookinfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
