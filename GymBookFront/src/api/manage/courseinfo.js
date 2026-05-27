import request from '@/utils/request'

// 查询课程信息列表
export function listCourseinfo(query) {
  return request({
    url: '/manage/courseinfo/list',
    method: 'get',
    params: query
  })
}

// 查询场馆名
export function VenueDict(query) {
  return request({
    url: '/manage/courseinfo/venueName',
    method: 'get',
    params: query
  })
}

// 查询课程信息详细
export function getCourseinfo(id) {
  return request({
    url: '/manage/courseinfo/' + id,
    method: 'get'
  })
}

// 新增课程信息
export function addCourseinfo(data) {
  return request({
    url: '/manage/courseinfo',
    method: 'post',
    data: data
  })
}

// 修改课程信息
export function updateCourseinfo(data) {
  return request({
    url: '/manage/courseinfo',
    method: 'put',
    data: data
  })
}

// 删除课程信息
export function delCourseinfo(id) {
  return request({
    url: '/manage/courseinfo/' + id,
    method: 'delete'
  })
}
