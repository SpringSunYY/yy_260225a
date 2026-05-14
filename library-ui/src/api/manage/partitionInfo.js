import request from '@/utils/request'

// 查询分区信息列表
export function listPartitionInfo(query) {
  return request({
    url: '/manage/partitionInfo/list',
    method: 'get',
    params: query
  })
}

// 查询分区信息详细
export function getPartitionInfo(id) {
  return request({
    url: '/manage/partitionInfo/' + id,
    method: 'get'
  })
}

// 新增分区信息
export function addPartitionInfo(data) {
  return request({
    url: '/manage/partitionInfo',
    method: 'post',
    data: data
  })
}

// 修改分区信息
export function updatePartitionInfo(data) {
  return request({
    url: '/manage/partitionInfo',
    method: 'put',
    data: data
  })
}

// 删除分区信息
export function delPartitionInfo(id) {
  return request({
    url: '/manage/partitionInfo/' + id,
    method: 'delete'
  })
}
