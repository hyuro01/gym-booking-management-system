import request from '@/utils/request'

// 查询用户预约列表
export function listUserapply(query) {
  return request({
    url: '/manage/userapply/list',
    method: 'get',
    params: query
  })
}

// 查询用户预约详细
export function getUserapply(id) {
  return request({
    url: '/manage/userapply/' + id,
    method: 'get'
  })
}

// 新增用户预约
export function addUserapply(data) {
  return request({
    url: '/manage/userapply',
    method: 'post',
    data: data
  })
}

// 修改用户预约
export function updateUserapply(data) {
  return request({
    url: '/manage/userapply',
    method: 'put',
    data: data
  })
}

// 删除用户预约
export function delUserapply(id) {
  return request({
    url: '/manage/userapply/' + id,
    method: 'delete'
  })
}
