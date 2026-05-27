import request from '@/utils/request'

// 查询课程申请列表
export function listPersonalization(query) {
  return request({
    url: '/manage/personalization/list',
    method: 'get',
    params: query
  })
}
