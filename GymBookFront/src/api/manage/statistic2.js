import request from '@/utils/request'

// 查询用户体重变化
export function listStatistic2(query) {
  return request({
    url: '/manage/statistic2/list',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询用户bmi变化
export function bmiStatistic(query) {
  return request({
    url: '/manage/statistic2/bmi',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询用户健身目标完成情况
export function goalStatistic(query) {
  return request({
    url: '/manage/statistic2/goal',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询用户全年健身房预约情况
export function bookStatistic(query) {
  return request({
    url: '/manage/statistic2/booktime',  // 后端接口地址
    method: 'get',  // 请求方式
    params: query  // 请求参数（例如开始时间和结束时间）
  });
}

// 查询个人数据统计详细
export function getStatistic2(id) {
  return request({
    url: '/manage/statistic2/' + id,
    method: 'get'
  })
}

