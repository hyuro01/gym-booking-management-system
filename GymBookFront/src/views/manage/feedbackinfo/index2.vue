<template>
  <div class="app-container">

    <el-card style="color: #DE8692; font-size: 15px; font-weight: bold; width: 45%" shadow="always">
      您的反馈对其他用户和教练很重要，请留下您的真实感受！😊
    </el-card>


    <el-descriptions 
      v-for="item in bookList" 
      :key="item.id" 
      direction="vertical"
      :size="size"
      :column="4" 
      style="margin: 50px; width: 80%;"
      border
>
  <el-descriptions-item label="预约时间" width="250px">
    {{ parseTime(item.bookTime, '{y}-{m}-{d} {h}:{i}') }}
  </el-descriptions-item>
  
  <el-descriptions-item label="预约场馆">
    {{ item.venueName || '-' }}
  </el-descriptions-item>
  
  <el-descriptions-item label="预约课程">
    {{ item.courseName || '-' }}
  </el-descriptions-item>
  
  <el-descriptions-item label="预约教练">
    {{ item.coachName || '-' }}
  </el-descriptions-item>
  
  <el-descriptions-item label="预约状态">
    <dict-tag :options="book_status" :value="item.status"/>
  </el-descriptions-item>
  
  <el-descriptions-item label="操作">
    <el-button type="primary" icon="Edit" @click="handleAdd(item)" text>评价</el-button>
  </el-descriptions-item>
</el-descriptions>

 
      
    <!-- 添加或修改查看评价对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="feedbackinfoRef" :model="form" :rules="rules" label-width="100px">

        <el-form-item label="评分" prop="rate">
          <el-rate v-model="form.rate" :max="5" :allow-half="false" />
        </el-form-item>
        <el-form-item label="评价内容" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入评价内容" />
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
import { listFeedbackinfo, getFeedbackinfo, delFeedbackinfo, addFeedbackinfo, updateFeedbackinfo, endbook } from "@/api/manage/feedbackinfo";
import useUserStore from '@/store/modules/user'


const userStore = useUserStore()

const { proxy } = getCurrentInstance();
const { book_status } = proxy.useDict('book_status');
const feedbackinfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 预约
const bookList = ref([]);

const data = reactive({
  form: {
    userId: Number(userStore.id),
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: Number(userStore.id),
    courseId: null,
    coachId: null,
    venueId: null,
    rate: null,
    remark: null,
    createTime: null
  },
  rules: {
    userId: [
      { required: true, message: "评价用户不能为空", trigger: "blur" }
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

  endbook(queryParams.value).then(response => {
    console.log("响应数据：", response);  // 打印响应内容
    bookList.value = response.data;
    console.log("bookList:", bookList);  // 打印响应内容
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
    courseId: null,
    coachId: null,
    venueId: null,
    rate: null,
    remark: null,
    createTime: null
  };
  proxy.resetForm("feedbackinfoRef");
}

/** 新增按钮操作 */
function handleAdd(row) {
  reset();

  // 传入当前查看的场馆、健身课程、教练id
  form.value.courseId = row.courseId;
  form.value.coachId = row.coachId;
  form.value.venueId = row.venueId;
  console.log("form:",form);

  open.value = true;
  title.value = "添加评价";
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


getList();
</script>
