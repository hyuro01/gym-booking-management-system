<template>
  <div class="app-container">


    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:courseinfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:courseinfo:edit']"
        >修改</el-button>
      </el-col>
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

    <!-- 添加或修改课程设置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseinfoRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="form.name" placeholder="请输入课程名" />
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
        <el-form-item label="开课场馆" prop="venueId">
          <el-select v-model="form.venueId" placeholder="请选择开课场馆">
        <el-option 
          v-for="(name, index) in venueNames" 
          :key="venueIds[index]" 
          :label="name" 
          :value="venueIds[index]"
        />
      </el-select>
        </el-form-item>
        <el-form-item label="开课时间" prop="startTime">
          <el-time-picker clearable
            v-model="form.startTime"
            type="time"
            value-format="HH:mm:ss"
            placeholder="请选择开课时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="closeTime">
          <el-time-picker clearable
            v-model="form.closeTime"
            type="time"
            value-format="HH:mm:ss"
            placeholder="请选择结束时间">
          </el-time-picker>
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

<script setup name="Courseinfo">
import { listCourseinfo, getCourseinfo, delCourseinfo, addCourseinfo, updateCourseinfo,VenueDict } from "@/api/manage/courseinfo";
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
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

// 存放场馆数据
const venueIds = ref([]);
const venueNames = ref([]);

const data = reactive({
  form: {userId: Number(userStore.id),},
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
    venueName: null,
    coachName: null
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
      { required: true, message: "开课场馆不能为空", trigger: "blur" }
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

/** 查询课程设置列表 */
function getList() {
  loading.value = true;
  listCourseinfo(queryParams.value).then(response => {
    courseinfoList.value = response.rows;
    total.value = response.total;
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
    name: null,
    coachId: Number(userStore.id),
    status: 0,
    equipment: null,
    capacity: null,
    venueId: null,
    startTime: null,
    closeTime: null,
    createTime: null
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
  title.value = "添加课程设置";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseinfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课程设置";
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
  proxy.$modal.confirm('是否确认删除课程设置编号为"' + _ids + '"的数据项？').then(function() {
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
