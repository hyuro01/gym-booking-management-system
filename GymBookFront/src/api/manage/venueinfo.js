import request from '@/utils/request'

// 查询场馆信息列表
export function listVenueinfo(query) {
  return request({
    url: '/manage/venueinfo/list',
    method: 'get',
    params: query
  })
}

// 查询场馆信息详细
export function getVenueinfo(id) {
  return request({
    url: '/manage/venueinfo/' + id,
    method: 'get'
  })
}

// 新增场馆信息
export function addVenueinfo(data) {
  return request({
    url: '/manage/venueinfo',
    method: 'post',
    data: data
  })
}

// 修改场馆信息
export function updateVenueinfo(data) {
  return request({
    url: '/manage/venueinfo',
    method: 'put',
    data: data
  })
}

// 删除场馆信息
export function delVenueinfo(id) {
  return request({
    url: '/manage/venueinfo/' + id,
    method: 'delete'
  })
}
