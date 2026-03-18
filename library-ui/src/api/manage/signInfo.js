import request from '@/utils/request'

// 查询签到信息列表
export function listSignInfo(query) {
  return request({
    url: '/manage/signInfo/list',
    method: 'get',
    params: query
  })
}

// 查询签到信息详细
export function getSignInfo(id) {
  return request({
    url: '/manage/signInfo/' + id,
    method: 'get'
  })
}

// 新增签到信息
export function addSignInfo(data) {
  return request({
    url: '/manage/signInfo',
    method: 'post',
    data: data
  })
}

// 修改签到信息
export function updateSignInfo(data) {
  return request({
    url: '/manage/signInfo',
    method: 'put',
    data: data
  })
}

// 删除签到信息
export function delSignInfo(id) {
  return request({
    url: '/manage/signInfo/' + id,
    method: 'delete'
  })
}
