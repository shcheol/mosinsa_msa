import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Login from '../views/Login.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/products/:id',
        name: 'productDetails',
        component: () => import('../views/ProductDetail.vue'),
        props: true
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('../views/About.vue')
    },
    {
        path: '/myPage',
        name: 'myPage',
        component: () => import('../views/MyPage.vue')
    },
    {
        path: '/myPage/likes',
        name: 'likes',
        component: () => import('../views/Likes.vue')
    },
    {
        path: '/myPage/orders',
        name: 'orders',
        component: () => import('../views/Orders.vue')
    },
    {
        path: '/myPage/orders/:id',
        name: 'orderDetails',
        component: () => import('../views/OrderDetail.vue'),
        props: true
    },
    {
        path: '/myPage/coupons',
        name: 'coupons',
        component: () => import('../views/Coupons.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router