import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/category/:id',
        name: 'categoryProducts',
        component: () => import('../views/CategoryProduct.vue'),
        props: true,
        meta: {
            reload: true,
        }
    },
    {
        path: '/products/:id',
        name: 'productDetails',
        component: () => import('../views/ProductDetail.vue'),
        props: true
    },
    {
        path: '/orderConfirm',
        name: 'orderConfirm',
        component: () => import('../views/OrderConfirmPage.vue'),
    },
    {
        path: '/orderPage',
        name: 'orderPage',
        component: () => import('../views/OrderPage.vue'),
    },
    {
        path: '/reviews/:id',
        name: 'reviewDetails',
        component: () => import('../views/ReviewDetails.vue'),
        props: true
    },
    {
        path: '/promotions',
        name: 'promotions',
        component: () => import('../views/Promotions.vue')
    },
    {
        path: '/promotions/:id',
        name: 'promotionDetails',
        component: () => import('../views/PromotionDetail.vue')
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
        path: '/myPage/cart',
        name: 'cart',
        component: () => import('../views/Cart.vue')
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
        path: '/myPage/coupons/:id',
        name: 'couponDetails',
        component: () => import('../views/CouponDetail.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/logout',
        name: 'logout',
        component: Logout
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router