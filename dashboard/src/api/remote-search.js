import request from '@/utils/request'

export function searchUser(name) {
  return request({
    url: '/dashboard/search/user',
    method: 'get',
    params: { name }
  })
}

export function transactionList(query) {
  return request({
    url: '/dashboard/transaction/list',
    method: 'get',
    params: query
  })
}
