import request from '@/utils/request'

// 查询教练评分
export function listStatistic3(query) {
  return request({
    url: '/manage/statistic3/list',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询教练开设健身课程状态
export function coachCourseStatus(query) {
  return request({
    url: '/manage/statistic3/coachCourseStatus',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询健身课程报名情况
export function courseStudent(query) {
  return request({
    url: '/manage/statistic3/courseStudent',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询教练预约趋势
export function coachStudent(query) {
  return request({
    url: '/manage/statistic3/coachStudent',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询个人数据统计详细
export function getStatistic3(id) {
  return request({
    url: '/manage/statistic3/' + id,
    method: 'get'
  })
}

