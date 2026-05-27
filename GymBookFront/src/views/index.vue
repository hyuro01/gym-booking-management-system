<template>
  <div class="app-container home">

    <el-collapse v-model="activeNames">
      <el-collapse-item :title="noticeTitle">
        <!-- 使用动态样式 -->
      <template #title>
        <span :style="{ color: '#DE8692', fontSize: '15px' }">{{ noticeTitle }}</span>
      </template>
        <div v-html="noticeContent || '内容加载中...'"></div>
        <div v-html="createTime || '内容加载中...'"></div>
      </el-collapse-item>
    </el-collapse>

    <!-- 轮播图 -->
    <div class="block text-center" style="display: flex; justify-content: center; align-items: flex-end; gap: 20px;">
      <el-carousel style="width: 500px; height: 300px;" indicator-position="outside">
        <el-carousel-item v-for="(item, index) in images" :key="index">
          <el-image :src="item" fit="cover" style="width: 100%; height: 100%;" />
        </el-carousel-item>
      </el-carousel>
      <div>
        <!-- 加载状态为true时显示 skeleton，加载完成后显示实际内容 -->
        <el-skeleton v-if="loading" :rows="5" style="width: 700px" animated />
    
        <!-- 这里是加载完成后显示的内容 -->
        <div v-if="!loading" style="text-align: left; font-size: 15px;">
          <h3 style="font-size: 24px; font-weight: bold; margin-bottom: 20px;">健身Tips</h3>
          <ul>
            <li v-for="(tip, index) in tipsContent" :key="index" style="margin-bottom: 10px;">
              {{ tip }}
            </li>
          </ul>
        </div>
      </div>
    </div>

    <el-divider />

    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>今日推荐课程</span>
            </div>
          </template>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i>
              <a v-html="course.name"></a>
            </p>
            <p>
              <i class="el-icon-user-solid"></i> 授课教练：
              <a v-html="course.coachName"></a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 场馆：
              <a v-html="course.venueName"></a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 最大报名人数：
              <a v-html="course.capacity"></a>
              <a>人</a>
            </p>
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <i class="el-icon-chat-dot-round"></i>训练器材：
              <a style="display: flex; align-items: center;">
                <dict-tag :options="equipment" :value="course.equipment" style="display: inline;"/>
              </a>
            </p>
            <p>
              <i class="el-icon-money"></i> 授课时间：每周末
              <a v-html="course.startTime"></a>
              -
              <a v-html="course.closeTime"></a>
            </p>
          </div>
        </el-card>
      </el-col>
      
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>今日推荐场馆</span>
            </div>
          </template>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i>
              <a v-html="venue.name"></a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 最大容纳人数：
              <a v-html="venue.capacity"></a>
              <a>人</a>
            </p>
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <i class="el-icon-chat-dot-round"></i>健身器材：
              <a style="display: flex; align-items: center;">
                <dict-tag
                  :options="equipment"
                  :value="venue.info ? venue.info.split(',') : []"
                  style="display: inline;"
                />
              </a>
            </p>
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <i class="el-icon-chat-dot-round"></i>营业状态：
              <a style="display: flex; align-items: center;">
                <dict-tag
                  :options="venue_status"
                  :value="venue.status"
                  style="display: inline;"
                />
              </a>
            </p>
            <p>
              <i class="el-icon-money"></i> 营业时间：
              <a v-html="venue.openTime"></a>
              -
              <a v-html="venue.closeTime"></a>
            </p>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>今日推荐教练</span>
            </div>
          </template>
          <div class="body">
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <el-image style="width:70px" :src="'http://localhost:8080' + user.avatar"  />
            </p>
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <i class="el-icon-s-promotion"></i>
              <a v-html="user.nickName"></a>
            </p>
            <p style="display: flex; align-items: center; flex-wrap: nowrap;">
              <i class="el-icon-s-promotion"></i>
              <a style="display: flex; align-items: center;">
                <dict-tag :options="sys_user_sex" :value="user.sex" style="display: inline;"/>
              </a>
            </p>
            <p>
              <i class="el-icon-s-promotion"></i> 手机号码：
              <a v-html="user.phonenumber"></a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 邮箱：
              <a v-html="user.email"></a>
            </p>

          </div>
        </el-card>
      </el-col>
      
    </el-row>
  </div>
</template>

<script setup name="Index">
import { onMounted,ref } from 'vue';
import { lateNotice, mostCourse, mostCoach, mostVenue } from "@/api/manage/home";


const { proxy } = getCurrentInstance();
const { sys_notice_status, sys_notice_type, equipment, sys_user_sex, venue_status } = proxy.useDict("sys_notice_status", "sys_notice_type", 'equipment', 'sys_user_sex', 'venue_status');


const version = ref('3.8.9')

const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const noticeTitle = ref([]);
const noticeContent = ref([]);
const createTime = ref([]);

const course = ref([]);
const user = ref([]);
const venue = ref([]);

const images = ref([
  "http://localhost:8080/profile/banner/banner1.jpg",
  "http://localhost:8080/profile/banner/banner2.jpg",
  "http://localhost:8080/profile/banner/banner3.jpg",
  "http://localhost:8080/profile/banner/banner4.jpg",
  "http://localhost:8080/profile/banner/banner5.jpg",
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    noticeTitle: undefined,
    noticeContent:'',
    createBy: undefined,
    status: undefined
  },
  rules: {
    noticeTitle: [{ required: true, message: "公告标题不能为空", trigger: "blur" }],
    noticeType: [{ required: true, message: "公告类型不能为空", trigger: "change" }]
  },
});

const { queryParams, form, rules } = toRefs(data);

// 5条健身tips
const tipsContent = ref([
  "1.健身前一小时，不要吃不易消化的食品（油脂类、肉类、酒类），可以吃适当的碳水化合物。",
  "2.健身前要充分热身，健身后要充分拉伸（超级重要）。",
  "3.增肌人群，健身后一定要补充足够的蛋白质。",
  "4.训练过程中不能不喝水，遵循少量多次。",
  "5.训练后不要立即洗澡，以免造成体内湿气无法排除；洗澡时水温不宜过高，以免造成免疫力下降。",
  "6.器械训练时尽量不要长时间憋气，动作要慢，体会肌肉的发力（憋气容易让体内压力增加造成内脏出血）。",
  "7.隔一段时间改变自己的训练计划，身体适应性很强，不改变，进步会变慢或者进入平台期。",
  "8.训练结束后请将器械归位，健身的人不仅该有强大的自制力，意志力，更该有良好的素质和教养。"
]);

onMounted(() => {
  // 模拟数据加载过程，1秒后加载完成
  setTimeout(() => {
    loading.value = false;
  }, 1000);
});

/** 查询公告、课程、场馆、教练信息 */
function getList() {
  loading.value = true;
  lateNotice(queryParams.value).then(response => {
    // 打印响应内容
    console.log("公告:",response);
    const data = response.data;
    noticeTitle.value=data.noticeTitle;
    noticeContent.value=data.noticeContent;
    createTime.value=data.createTime;
    console.log("noticeTitle:",noticeTitle);

  });

  mostCourse(queryParams.value).then(response => {
    // 打印响应内容
    console.log("课程1:",response);
    const data = response.data;
    course.value=response.data;
    console.log("课程2:",course);
  });

  mostCoach(queryParams.value).then(response => {
    // 打印响应内容
    console.log("教练:",response);
    const data = response.data;
    user.value=response.data;
    console.log("教练2:",user);
  });

  mostVenue(queryParams.value).then(response => {
    // 打印响应内容
    console.log("场馆1:",response);
    const data = response.data;
    venue.value=response.data;
    console.log("场馆2:",venue);
  });
}


function goTarget(url) {
  window.open(url, '__blank')
}



getList();
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

