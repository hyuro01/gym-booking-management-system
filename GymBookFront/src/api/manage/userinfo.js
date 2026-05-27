import request from '@/utils/request'

// 查询用户信息列表
export function listUserinfo(query) {
  return request({
    url: '/manage/userinfo/list',
    method: 'get',
    params: query
  })
}

// 查询教练信息列表
export function listCoachinfo(query) {
  return request({
    url: '/manage/userinfo/coachlist',
    method: 'get',
    params: query
  })
}



// 查询用户信息详细
export function getUserinfo(userId) {
  return request({
    url: '/manage/userinfo/' + userId,
    method: 'get'
  })
}

// 新增用户信息
export function addUserinfo(data) {
  return request({
    url: '/manage/userinfo',
    method: 'post',
    data: data
  })
}

// 修改用户信息
export function updateUserinfo(data) {
  return request({
    url: '/manage/userinfo',
    method: 'put',
    data: data
  })
}

// 删除用户信息
export function delUserinfo(userId) {
  return request({
    url: '/manage/userinfo/' + userId,
    method: 'delete'
  })
}
