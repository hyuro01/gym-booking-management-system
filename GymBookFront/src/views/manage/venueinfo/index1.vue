<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px" style="height: 50px">
      <el-form-item label="场馆名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入场馆名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="容纳人数" prop="capacity">
        <el-input
          v-model="queryParams.capacity"
          placeholder="请输入最大容纳人数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="场馆器材" prop="info">
        <el-select v-model="queryParams.info" placeholder="请选择场馆器材" style="width: 200px" clearable>
        <el-option
          v-for="dict in equipment"
          :key="dict.value"
          :label="dict.label"
          :value="dict.value"
        />
        </el-select>
      </el-form-item>

      <el-form-item label="场馆状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择场馆状态" style="width: 200px" clearable>
          <el-option
            v-for="dict in venue_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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

    <el-table v-loading="loading" :data="venueinfoList" @selection-change="handleSelectionChange">

      <el-table-column label="场馆名称" align="center" prop="name" />
      <el-table-column label="最大容纳人数" align="center" prop="capacity" />
      <el-table-column label="营业开始时间" align="center" prop="openTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.openTime, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="营业结束时间" align="center" prop="closeTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.closeTime, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="场馆器材" align="center" prop="info">
        <template #default="scope">
          <dict-tag :options="equipment" :value="scope.row.info ? scope.row.info.split(',') : []"/>
        </template>
      </el-table-column>
      <el-table-column label="场馆状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="venue_status" :value="scope.row.status"/>
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

    <!-- 添加或修改场馆信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="venueinfoRef" :model="form" :rules="rules" label-width="80px">
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

<script setup name="Venueinfo">
import { listVenueinfo, getVenueinfo, delVenueinfo, addVenueinfo, updateVenueinfo } from "@/api/manage/venueinfo";

const { proxy } = getCurrentInstance();
const { venue_status, equipment } = proxy.useDict('venue_status','equipment');

const venueinfoList = ref([]);
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
    capacity: null,
    openTime: null,
    closeTime: null,
    info: null,
    status: null,
  },
  rules: {
    name: [
      { required: true, message: "场馆名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询场馆信息列表 */
function getList() {
  loading.value = true;
  listVenueinfo(queryParams.value).then(response => {
    venueinfoList.value = response.rows;
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
    capacity: null,
    openTime: null,
    closeTime: null,
    info: null,
    status: null,
    createTime: null
  };
  proxy.resetForm("venueinfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  //console.log("查询条件:", queryParams.JSONString);
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
  title.value = "添加场馆信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getVenueinfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改场馆信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["venueinfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateVenueinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addVenueinfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除场馆信息编号为"' + _ids + '"的数据项？').then(function() {
    return delVenueinfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/venueinfo/export', {
    ...queryParams.value
  }, `venueinfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
