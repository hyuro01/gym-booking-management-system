import request from '@/utils/request'

// 查询查看评价列表
export function listFeedbackinfo(query) {
  return request({
    url: '/manage/feedbackinfo/list',
    method: 'get',
    params: query
  })
}

// 查询已完成预约
export function endbook(query) {
  return request({
    url: '/manage/feedbackinfo/endbook',
    method: 'get',
    params: query
  })
}


// 查询查看评价详细
export function getFeedbackinfo(id) {
  return request({
    url: '/manage/feedbackinfo/' + id,
    method: 'get'
  })
}

// 新增查看评价
export function addFeedbackinfo(data) {
  return request({
    url: '/manage/feedbackinfo',
    method: 'post',
    data: data
  })
}

// 修改查看评价
export function updateFeedbackinfo(data) {
  return request({
    url: '/manage/feedbackinfo',
    method: 'put',
    data: data
  })
}

// 删除查看评价
export function delFeedbackinfo(id) {
  return request({
    url: '/manage/feedbackinfo/' + id,
    method: 'delete'
  })
}
