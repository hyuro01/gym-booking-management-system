import request from '@/utils/request'

// 查询课程申请列表
export function listCourseapply(query) {
  return request({
    url: '/manage/courseapply/list',
    method: 'get',
    params: query
  })
}

// 查询课程申请详细
export function getCourseapply(id) {
  return request({
    url: '/manage/courseapply/' + id,
    method: 'get'
  })
}

// 新增课程申请
export function addCourseapply(data) {
  return request({
    url: '/manage/courseapply',
    method: 'post',
    data: data
  })
}

// 修改课程申请
export function updateCourseapply(data) {
  return request({
    url: '/manage/courseapply',
    method: 'put',
    data: data
  })
}

// 删除课程申请
export function delCourseapply(id) {
  return request({
    url: '/manage/courseapply/' + id,
    method: 'delete'
  })
}
