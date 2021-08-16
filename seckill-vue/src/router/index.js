import Vue from 'vue'
import Router from 'vue-router'


import HelloWorld from '@/components/HelloWorld'
import register from '@/components/register'
import error from '@/components/error'
import getotp from '@/components/getotp'
import login from '@/components/login'
import create from '@/components/create'
import listitem from '@/components/listitem'
import getitem from '@/components/getitem'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/getitem',
      name: 'getitem',
      component: getitem
    },
    {
      path: '/listitem',
      name: 'listitem',
      component: listitem
    },
    {
      path:'/create',
      name:'create',
      component:create
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/error',
      name: 'error',
      component: error
    },
    { 
      path: '/getotp',
      name: 'getotp',
      component: getotp
    }

  ]
})
