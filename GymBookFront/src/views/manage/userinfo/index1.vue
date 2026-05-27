<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="用户账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="用户邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入用户邮箱"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="手机号码" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>

      <el-form-item label="用户类型" prop="roleName">
        <el-select v-model="queryParams.roleName" placeholder="请选择用户类型" style="width: 200px" clearable>
          <el-option
            v-for="dict in user_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.label"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="用户性别" prop="sex" >
        <el-select v-model="queryParams.sex" placeholder="请选择用户性别" style="width: 200px"  clearable>
          <el-option
            v-for="dict in sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>



      <el-form-item label="最后登录时间" prop="loginDate">
        <el-date-picker clearable
          v-model="queryParams.loginDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择最后登录时间">
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
          v-hasPermi="['manage:userinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户账号" align="center" prop="userName" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="用户邮箱" align="center" prop="email" />
      <el-table-column label="手机号码" align="center" prop="phonenumber" />
      <el-table-column label="用户性别" align="center" prop="sex">
        <template #default="scope">
          <dict-tag :options="sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="头像" align="center" prop="avatar" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.avatar" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="最后登录IP" align="center" prop="loginIp" />
      <el-table-column label="最后登录时间" align="center" prop="loginDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.loginDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户类型" align="center" prop="roleName" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="userinfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户账号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户账号" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入用户邮箱" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phonenumber">
          <el-input v-model="form.phonenumber" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="用户性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择用户性别">
            <el-option
              v-for="dict in sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像地址" prop="avatar">
          <image-upload v-model="form.avatar"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="最后登录IP" prop="loginIp">
          <el-input v-model="form.loginIp" placeholder="请输入最后登录IP" />
        </el-form-item>
        <el-form-item label="最后登录时间" prop="loginDate">
          <el-date-picker clearable
            v-model="form.loginDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择最后登录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Userinfo">
import { listUserinfo, getUserinfo, delUserinfo, addUserinfo, updateUserinfo } from "@/api/manage/userinfo";

const { proxy } = getCurrentInstance();
const { sys_user_sex, user_type } = proxy.useDict('sys_user_sex', 'user_type');

const userinfoList = ref([]);
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
    userName: null,
    nickName: null,
    email: null,
    phonenumber: null,
    sex: null,
    avatar: null,
    password: null,
    status: null,
    delFlag: null,
    loginIp: null,
    loginDate: null,
    remark: null,
    roleId: null,
    roleName: null,
  },
  rules: {
    userName: [
      { required: true, message: "用户账号不能为空", trigger: "blur" }
    ],
    nickName: [
      { required: true, message: "用户昵称不能为空", trigger: "blur" }
    ],
    password: [
      { required: true, message: "密码不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户信息列表 */
function getList() {
  loading.value = true;
  listUserinfo(queryParams.value).then(response => {
    // 打印传入的条件
    console.log('queryParams', queryParams);
    userinfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
        // 打印返回的数据
    console.log('response', response);
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
    userId: null,
    deptId: null,
    userName: null,
    nickName: null,
    userType: null,
    email: null,
    phonenumber: null,
    sex: null,
    avatar: null,
    password: null,
    status: null,
    delFlag: null,
    loginIp: null,
    loginDate: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    roleId: null,
    roleName: null,
  };
  proxy.resetForm("userinfoRef");
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
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加用户信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _userId = row.userId || ids.value
  getUserinfo(_userId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改用户信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["userinfoRef"].validate(valid => {
    if (valid) {
      if (form.value.userId != null) {
        updateUserinfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUserinfo(form.value).then(response => {
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
  const _userIds = row.userId || ids.value;
  proxy.$modal.confirm('是否确认删除用户信息编号为"' + _userIds + '"的数据项？').then(function() {
    return delUserinfo(_userIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/userinfo/export', {
    ...queryParams.value
  }, `userinfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
