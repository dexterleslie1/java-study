import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Mutations from "@/views/Mutations";
import Getters from "@/views/Getters";
import Actions from "@/views/Actions";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: "/vuex/mutations",
    name: "Mutations",
    component: Mutations
  },
  {
    path: "/vuex/getters",
    name: "Getters",
    component: Getters
  },
  {
    path: "/vuex/actions",
    name: "Actions",
    component: Actions
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: function () {
      return import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
