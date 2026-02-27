import request from '@/utils/request'

// 查询座位信息列表
export function listSeatInfo(query) {
  return request({
    url: '/manage/seatInfo/list',
    method: 'get',
    params: query
  })
}

// 查询座位信息详细
export function getSeatInfo(id) {
  return request({
    url: '/manage/seatInfo/' + id,
    method: 'get'
  })
}

// 新增座位信息
export function addSeatInfo(data) {
  return request({
    url: '/manage/seatInfo',
    method: 'post',
    data: data
  })
}

// 修改座位信息
export function updateSeatInfo(data) {
  return request({
    url: '/manage/seatInfo',
    method: 'put',
    data: data
  })
}

// 删除座位信息
export function delSeatInfo(id) {
  return request({
    url: '/manage/seatInfo/' + id,
    method: 'delete'
  })
}
