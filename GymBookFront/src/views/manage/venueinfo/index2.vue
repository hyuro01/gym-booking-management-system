<template>
  <div class="app-container">
    <el-steps
      class="mb-4"
      style="max-width: 900px"
      :space="200"
      :active="1"
      simple
    >
      <el-step title="支持新增场馆" :icon="Plus" />
      <el-step title="支持修改信息" :icon="Edit" />
      <el-step title="支持场馆删除" :icon="Delete" />
    </el-steps>


    <el-divider />

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:venueinfo:add']"
        >新增</el-button>
      </el-col>

    </el-row>

    <el-divider />


    <el-collapse 
      v-for="item in venueinfoList" 
      :key="item.id"
      v-model="activeNames"
    >
      <el-collapse-item :title="item.name"  name="1">
        <div>
          最大容纳人数：{{ item.capacity }}人
        </div>
        <div>
          场馆营业时间：{{ item.openTime }}-{{ item.closeTime }}
        </div>
        <div>
          健身器材：
          <dict-tag
            :options="equipment"
            :value="item.info ? item.info.split(',') : []"
            style="display: inline;"
          />
        </div>
        <div>
          场馆营业状态：
          <dict-tag
            :options="venue_status"
            :value="item.status"
            style="display: inline;"
          />
        </div>
        <div>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(item)" v-hasPermi="['manage:venueinfo:edit']">修改</el-button>
        </div>
        <div>
          <el-button link type="primary" icon="Delete" @click="handleDelete(item)" v-hasPermi="['manage:venueinfo:remove']">删除</el-button>
        </div>
      </el-collapse-item>
    </el-collapse>


    <!-- 添加或修改场馆设置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="venueinfoRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="场馆名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入场馆名称" />
        </el-form-item>
        <el-form-item label="容纳人数" prop="capacity">
          <el-input v-model="form.capacity" placeholder="请输入最大容纳人数" />
        </el-form-item>
        <el-form-item label="营业开始时间" prop="openTime">
          <el-time-picker clearable
            v-model="form.openTime"
            type="time"
            value-format="HH:mm:ss"
            placeholder="请选择营业开始时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="营业结束时间" prop="closeTime">
          <el-time-picker clearable
            v-model="form.closeTime"
            type="time"
            value-format="HH:mm:ss"
            placeholder="请选择营业结束时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="场馆器材" prop="info">
          <el-checkbox-group v-model="form.info">
            <el-checkbox
              v-for="dict in equipment"
              :key="dict.value"
              :label="dict.value">
              {{dict.label}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="营业状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in venue_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
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

<script setup name="Venueinfo">
import { listVenueinfo, getVenueinfo, delVenueinfo, addVenueinfo, updateVenueinfo } from "@/api/manage/venueinfo";
import { Delete, Edit, Plus } from "@element-plus/icons-vue/global";

const { proxy } = getCurrentInstance();
const { venue_status, equipment } = proxy.useDict('venue_status', 'equipment');

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
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询场馆设置列表 */
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
    info: [],
    status: null,
    createTime: null
  };
  proxy.resetForm("venueinfoRef");
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
  title.value = "添加场馆";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getVenueinfo(_id).then(response => {
    form.value = response.data;
    form.value.info = form.value.info.split(",");
    open.value = true;
    title.value = "修改场馆";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["venueinfoRef"].validate(valid => {
    if (valid) {
      form.value.info = form.value.info.join(",");
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
  proxy.$modal.confirm('是否确认删除场馆设置编号为"' + _ids + '"的数据项？').then(function() {
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
