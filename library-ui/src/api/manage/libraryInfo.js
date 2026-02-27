import request from '@/utils/request'

// 查询图书馆信息列表
export function listLibraryInfo(query) {
  return request({
    url: '/manage/libraryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询图书馆信息详细
export function getLibraryInfo(id) {
  return request({
    url: '/manage/libraryInfo/' + id,
    method: 'get'
  })
}

// 新增图书馆信息
export function addLibraryInfo(data) {
  return request({
    url: '/manage/libraryInfo',
    method: 'post',
    data: data
  })
}

// 修改图书馆信息
export function updateLibraryInfo(data) {
  return request({
    url: '/manage/libraryInfo',
    method: 'put',
    data: data
  })
}

// 删除图书馆信息
export function delLibraryInfo(id) {
  return request({
    url: '/manage/libraryInfo/' + id,
    method: 'delete'
  })
}
