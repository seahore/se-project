import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: 'home',
    component: Layout,
    children: [
      {
        path: '/',
        meta: { title: '主页', icon: 's-home' }
      }
    ]
  },
  {
    path: '/dashboard',
    component: Layout,
    children: [
      {
        path: 'overview',
        component: () => import('@/views/dashboard/index'),
        name: '总览',
        meta: { title: '总览', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/',
    component: () => import('@/pages/index')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/pages/index')
  },
  {
    path: '/trend',
    name: 'trend',
    component: () => import('@/pages/trend')
  },
  {
    path: '/following',
    component: () => import('@/pages/following')
  },
  {
    path: '/article/:id(\\d+)',
    component: () => import('@/pages/article')
  },
  {
    path: '/search/:word',
    component: () => import('@/pages/search')
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: '用户资料',
        meta: { title: '用户资料', icon: 'user', noCache: true }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/dashboard/posts',
    component: Layout,
    redirect: '/dashboard/posts/list',
    name: 'Example',
    meta: {
      title: '管理文章',
      icon: 'el-icon-s-help'
    },
    children: [
      {
        path: 'create',
        component: () => import('@/views/posts/create'),
        name: 'CreateArticle',
        meta: { title: '撰写文章', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/posts/edit'),
        name: 'EditArticle',
        meta: { title: '修改文章', noCache: true, activeMenu: '/dashboard/posts/list' },
        hidden: true
      },
      {
        path: 'passage-list',
        component: () => import('@/views/posts/list'),
        name: 'ArticleList',
        meta: { title: '文章列表', icon: 'list' }
      },
      {
        path: 'comments-list/:id(\\d+)',
        component: () => import('@/views/posts/comments-list'),
        name: 'CommentsList',
        meta: { title: '评论列表', icon: 'nested' },
        hidden: true
      }
    ]
  },
  {
    path: '/dashboard/users',
    component: Layout,
    meta: {
      roles: ['admin']
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/users/list'),
        name: 'Icons',
        meta: { title: '用户', icon: 'icon', noCache: true }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
