import request from '@/utils/request'

// 查询违规信息列表
export function listViolationInfo(query) {
  return request({
    url: '/manage/violationInfo/list',
    method: 'get',
    params: query
  })
}

// 查询违规信息详细
export function getViolationInfo(id) {
  return request({
    url: '/manage/violationInfo/' + id,
    method: 'get'
  })
}

// 新增违规信息
export function addViolationInfo(data) {
  return request({
    url: '/manage/violationInfo',
    method: 'post',
    data: data
  })
}

// 修改违规信息
export function updateViolationInfo(data) {
  return request({
    url: '/manage/violationInfo',
    method: 'put',
    data: data
  })
}

// 删除违规信息
export function delViolationInfo(id) {
  return request({
    url: '/manage/violationInfo/' + id,
    method: 'delete'
  })
}
