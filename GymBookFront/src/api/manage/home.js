import request from '@/utils/request'

// 查询最新公告
export function lateNotice(query) {
  return request({
    url: '/index/late',
    method: 'get',
    params: query
  })
}

// 查询最多预约的课程
export function mostCourse(query) {
  return request({
    url: '/index/mostCourse',
    method: 'get',
    params: query
  })
}

// 查询最多预约的教练
export function mostCoach(query) {
  return request({
    url: '/index/mostCoach',
    method: 'get',
    params: query
  })
}

// 查询最多预约的场馆
export function mostVenue(query) {
  return request({
    url: '/index/mostVenue',
    method: 'get',
    params: query
  })
}
