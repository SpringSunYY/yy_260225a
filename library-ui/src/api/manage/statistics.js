import request from '@/utils/request'

export function appointmentStatistics(query){
  return request({
    url: '/manage/statistics/appointment',
    method: 'get',
    params: query
  })
}
