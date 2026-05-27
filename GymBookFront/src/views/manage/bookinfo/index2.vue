<template>
  <div class="app-container">

    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>预约申请</h3>
        </div>
      </template>
      <div class="body">
        <p>
          <i class="el-icon-chat-dot-round"></i> 健身房客户可以在这里申请预约您喜欢的场馆、健身教练或健身课程。
        </p>
        <p>
          <i class="el-icon-chat-dot-round"></i> 诚挚感谢您对健身房的信赖！><
        </p>
      </div>

  </el-card>


  <el-form ref="bookinfoRef" :model="form" :rules="rules" label-width="100px" style="margin: 30px;">

    <el-form-item label="预约的场馆" prop="venueId">
      <el-select v-model="form.venueId" placeholder="请选择预约的场馆">
        <el-option 
          v-for="(name, index) in venueNames" 
          :key="venueIds[index]" 
          :label="name" 
          :value="venueIds[index]"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="预约的课程" prop="courseId">
      <el-select v-model="form.courseId" placeholder="请选择预约的课程">
        <el-option 
          v-for="(name, index) in courseNames" 
          :key="courseIds[index]" 
          :label="name" 
          :value="courseIds[index]"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="预约的教练" prop="coachId">
      <el-select v-model="form.coachId" placeholder="请选择预约的教练">
        <el-option 
          v-for="(name, index) in coachNames" 
          :key="coachIds[index]" 
          :label="name" 
          :value="coachIds[index]"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="预约的时间" prop="bookTime">
      <el-date-picker clearable
        v-model="form.bookTime"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择预约的时间">
      </el-date-picker>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="reset">重 置</el-button>
    </el-form-item>
  </el-form>



  </div>
</template>

<script setup name="Bookinfo">
import { listBookinfo, getBookinfo, delBookinfo, addBookinfo, updateBookinfo, CourseDict, CoachDict, VenueDict } from "@/api/manage/bookinfo";
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

// 存放课程数据
const courseIds = ref([]);
const courseNames = ref([]);
// 存放教练数据
const coachIds = ref([]);
const coachNames = ref([]);
// 存放场馆数据
const venueIds = ref([]);
const venueNames = ref([]);

const data = reactive({
  form: {
    userId: Number(userStore.id),
  },
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
    venueId: [
      { required: true, message: "预约场馆不能为空", trigger: "blur" }
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
  listBookinfo(queryParams.value).then(response => {
    bookinfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });

  // 获取课程名
  CourseDict(queryParams.value).then(response => {
    console.log("响应数据：", response);  // 打印响应内容
    const data = response.data; // 假设后端返回的数据是 data 字段
    courseIds.value=data.idList.split(","); // 转换为数组
    courseNames.value=data.nameList.split(","); // 转换为数组
    console.log("courseNames:", courseNames);  // 打印响应内容
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

  // 获取教练名
  CoachDict(queryParams.value).then(response => {
    //console.log("响应数据：", response);  // 打印响应内容
    const data = response.data; // 假设后端返回的数据是 data 字段
    coachIds.value=data.idList.split(","); // 转换为数组
    coachNames.value=data.nameList.split(","); // 转换为数组
    console.log("coachNames:", coachNames);  // 打印响应内容
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

    // 获取场馆名
    VenueDict(queryParams.value).then(response => {
    //console.log("响应数据：", response);  // 打印响应内容
    const data = response.data; // 假设后端返回的数据是 data 字段
    venueIds.value=data.idList.split(","); // 转换为数组
    venueNames.value=data.nameList.split(","); // 转换为数组
    console.log("venueNames:", venueNames);  // 打印响应内容
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



/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加预约信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getBookinfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改预约信息";
  });
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

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除预约信息编号为"' + _ids + '"的数据项？').then(function() {
    return delBookinfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/bookinfo/export', {
    ...queryParams.value
  }, `bookinfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
