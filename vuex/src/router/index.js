import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/home',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/time_line',
    name: 'time_line',
    component: () => import('../views/time_line.vue'),
  },
  {
    path: '/search',
    name: 'search',
    component: () => import('../views/search.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/register.vue'),
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('../views/test.vue'),
  },
  {
    path: '/',
    redirect:'/home'
  },  
  {
    path: '/user',
    name: 'user',
    component: () => import('../views/user.vue'),
  },
  {
    path: '/user_info_change',
    name: 'user_info_change',
    component: () => import('../views/user_info_change.vue'),
  },
  {
    path: '/advanced_search',
    name: 'advanced_search',
    component: () => import('../views/advanced_search.vue'),
  },
  {
    path: '/artifact/:id',
    name: 'artifact',
    component: () => import('../views/ArtifactView.vue'),
    children:[
      {
          path:"related",
          component: () => import('../views/RelatedArtifactView.vue'),
      }
  ]
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('../views/test.vue'),
  },
  {
    path:"/:catchall(.*)*",
    name:"Not Found",
    component: () => import('../views/NotFoundView.vue'),
  }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})
export default router
