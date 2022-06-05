import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/dashboard/routes',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/dashboard/roles',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/dashboard/role',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/dashboard/role/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/dashboard/role/${id}`,
    method: 'delete'
  })
}
