<template>
  <div class="app-container">
    
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item title="记载当下" name="1">
        <div>
          你的身体数据不仅仅是一串数字，而是你坚持锻炼的见证！
        </div>
        <div>
          每一次记录，都是你对自己负责的表现，让自己看到进步，收获成就感。
        </div>
      </el-collapse-item>
      <el-collapse-item title="汲取动力" name="2">
        <div>
          给自己设定一个小目标，比如「一个月减掉2公斤」或「每天步行8000步」。 
        </div>
        <div>
          目标越清晰，行动越有方向，让健身变得更有意义！
        </div>
      </el-collapse-item>
      <el-collapse-item title="养成习惯" name="3">
        <div>
          健身不是一蹴而就，而是一个长期积累的过程。  
        </div>
        <div>
          记录你的每一次锻炼，让数据变成坚持下去的动力，未来的你一定会感谢现在的自己！
        </div>
      </el-collapse-item>

    </el-collapse>


    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px" style="margin-top: 20px;">
      <el-form-item label="体重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入体重"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身高" prop="height">
        <el-input
          v-model="queryParams.height"
          placeholder="请输入身高"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="健身目标" prop="goal">
        <el-select v-model="queryParams.goal" placeholder="请选择健身目标" clearable style="width: 200px">
          <el-option
            v-for="dict in goal"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:health:add']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:health:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="healthList" @selection-change="handleSelectionChange">
      <el-table-column align="center" prop="id" />
      <el-table-column label="记录时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="体重" align="center" prop="weight">
        <template #default="scope">
          {{ scope.row.weight }}kg
        </template>
      </el-table-column>
      <el-table-column label="身高" align="center" prop="height">
        <template #default="scope">
          {{ scope.row.height }}m
        </template>
      </el-table-column>
      <el-table-column label="健身目标" align="center" prop="goal" >
        <template #default="scope">
          <dict-tag :options="goal" :value="scope.row.goal"/>
        </template>
      </el-table-column>
      <el-table-column label="健身目标完成度" align="center" prop="percent">
        <template #default="scope">
          {{ scope.row.percent }}%
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

    <!-- 添加或修改健康管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="healthRef" :model="form" :rules="rules" label-width="110px">

        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="健身目标" prop="goal">
          <el-radio-group v-model="form.goal">
            <el-radio
              v-for="dict in goal"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >{{dict.label}}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="健身目标完成度" prop="percent">
          <div style="display: flex; align-items: center; width: 100%;">
            <el-slider v-model="form.percent" show-input />
          </div>
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

<script setup name="Health">
import { listHealth, getHealth, delHealth, addHealth, updateHealth } from "@/api/manage/health";
import useUserStore from '@/store/modules/user'
import { ref } from 'vue'

const activeName = ref('1')

const userStore = useUserStore()
const { proxy } = getCurrentInstance();
const { goal } = proxy.useDict('goal');

const healthList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const userId = ref(0); // 用于存储 userId，格式为数字

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: Number(userStore.id),
    weight: null,
    height: null,
    goal: null,
    percent: null,
  },
  rules: {
    userId: [
      { required: true, message: "数据所属用户id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询健康管理列表 */
function getList() {
  loading.value = true;
  // 获取当前登录的用户id
  console.log("userinfo:",userStore.getInfo())


  listHealth(queryParams.value).then(response => {
    // 打印传入的条件
    console.log('queryParams', queryParams);
    healthList.value = response.rows;
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
    userId: Number(userStore.id),
    weight: null,
    height: null,
    goal: null,
    percent: null,
    createTime: null
  };
  proxy.resetForm("healthRef");
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
  title.value = "添加健康记录";
}



/** 提交按钮 */
function submitForm() {
  proxy.$refs["healthRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHealth(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHealth(form.value).then(response => {
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
  proxy.download('manage/health/export', {
    ...queryParams.value
  }, `health_${new Date().getTime()}.xlsx`)
}

getList();
</script>
