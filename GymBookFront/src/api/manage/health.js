import request from '@/utils/request'

// 查询健康管理列表
export function listHealth(query) {
  return request({
    url: '/manage/health/list',
    method: 'get',
    params: query
  })
}

// 查询健康管理详细
export function getHealth(id) {
  return request({
    url: '/manage/health/' + id,
    method: 'get'
  })
}

// 新增健康管理
export function addHealth(data) {
  return request({
    url: '/manage/health',
    method: 'post',
    data: data
  })
}

// 修改健康管理
export function updateHealth(data) {
  return request({
    url: '/manage/health',
    method: 'put',
    data: data
  })
}

// 删除健康管理
export function delHealth(id) {
  return request({
    url: '/manage/health/' + id,
    method: 'delete'
  })
}
