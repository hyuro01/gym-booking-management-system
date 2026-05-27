import request from '@/utils/request'

// 查询课程数据列表
export function listStatistic1(query) {
  return request({
    url: '/manage/statistic1/list',
    method: 'get',
    params: query
  })
}

// 查询教练数据列表
export function coachStatistic1(query) {
  return request({
    url: '/manage/statistic1/coach',
    method: 'get',
    params: query
  })
}

// 查询场馆数据列表
export function venueStatistic1(query) {
  return request({
    url: '/manage/statistic1/venueCount',
    method: 'get',
    params: query
  })
}

// 查询预约状态数据列表
export function bookStatusStatistic1(query) {
  return request({
    url: '/manage/statistic1/bookStatus',
    method: 'get',
    params: query
  })
}

// 查询课程状态数据列表
export function courseStatusStatistic1(query) {
  return request({
    url: '/manage/statistic1/courseStatus',
    method: 'get',
    params: query
  })
}