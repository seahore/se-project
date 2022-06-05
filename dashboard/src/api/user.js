import request from '@/utils/request'

export function login(data) {
  return request({
    url: 'http://localhost:8080/login',
    method: 'get',
    params: data
  })
}

export function getInfo(token) {
  return request({
    url: '/dashboard/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/dashboard/user/logout',
    method: 'post'
  })
}
