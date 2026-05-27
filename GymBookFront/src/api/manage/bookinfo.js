import request from '@/utils/request'

// 查询预约信息列表
export function listBookinfo(query) {
  return request({
    url: '/manage/bookinfo/list',
    method: 'get',
    params: query
  })
}

// 查询课程名
export function CourseDict(query) {
  return request({
    url: '/manage/bookinfo/courseName',
    method: 'get',
    params: query
  })
}

// 查询教练名
export function CoachDict(query) {
  return request({
    url: '/manage/bookinfo/coachName',
    method: 'get',
    params: query
  })
}

// 查询场馆名
export function VenueDict(query) {
  return request({
    url: '/manage/bookinfo/venueName',
    method: 'get',
    params: query
  })
}

// 查询预约信息详细
export function getBookinfo(id) {
  return request({
    url: '/manage/bookinfo/' + id,
    method: 'get'
  })
}

// 新增预约信息
export function addBookinfo(data) {
  return request({
    url: '/manage/bookinfo',
    method: 'post',
    data: data
  })
}

// 修改预约信息
export function updateBookinfo(data) {
  return request({
    url: '/manage/bookinfo',
    method: 'put',
    data: data
  })
}

// 删除预约信息
export function delBookinfo(id) {
  return request({
    url: '/manage/bookinfo/' + id,
    method: 'delete'
  })
}
