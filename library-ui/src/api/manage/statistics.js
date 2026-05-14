import request from '@/utils/request'

export function appointmentStatistics(query){
  return request({
    url: '/manage/statistics/appointment',
    method: 'get',
    params: query
  })
}

export function hourStatistics(query){
  return request({
    url: '/manage/statistics/hour',
    method: 'get',
    params: query
  })
}
