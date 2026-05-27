import request from '@/utils/request'

// 查询学员管理列表
export function listStudent(query) {
  return request({
    url: '/manage/student/list',
    method: 'get',
    params: query
  })
}

// 查询学员管理详细
export function getStudent(id) {
  return request({
    url: '/manage/student/' + id,
    method: 'get'
  })
}

// 新增学员管理
export function addStudent(data) {
  return request({
    url: '/manage/student',
    method: 'post',
    data: data
  })
}

// 修改学员管理
export function updateStudent(data) {
  return request({
    url: '/manage/student',
    method: 'put',
    data: data
  })
}

// 删除学员管理
export function delStudent(id) {
  return request({
    url: '/manage/student/' + id,
    method: 'delete'
  })
}
