import request from '@/utils/request'

// 查询预约信息列表
export function listAppointmentInfo(query) {
  return request({
    url: '/manage/appointmentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询预约信息详细
export function getAppointmentInfo(id) {
  return request({
    url: '/manage/appointmentInfo/' + id,
    method: 'get'
  })
}

// 新增预约信息
export function addAppointmentInfo(data) {
  return request({
    url: '/manage/appointmentInfo',
    method: 'post',
    data: data
  })
}

// 修改预约信息
export function updateAppointmentInfo(data) {
  return request({
    url: '/manage/appointmentInfo',
    method: 'put',
    data: data
  })
}

// 删除预约信息
export function delAppointmentInfo(id) {
  return request({
    url: '/manage/appointmentInfo/' + id,
    method: 'delete'
  })
}
