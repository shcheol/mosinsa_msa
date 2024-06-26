import axios from "axios";
import router from '../router/index.js'

const UNAUTHORIZED = 401;

const onUnauthroized = () => {
    alert("로그인이 필요한 서비스입니다.")
    router.push('/login')
}

// const BASE_URL="http://152.67.205.195:8000/";
const BASE_URL = "/";

const instance = axios.create({
    baseURL: BASE_URL
})
instance.interceptors.response.use(
    (config) => {
        return config;
    },
    (error) => {
        if (error.response.status === UNAUTHORIZED) {
            onUnauthroized()
        }
        return Promise.reject(error);
    }
)
instance.interceptors.request.use(
    (config) => {
        config.headers = {
            "Authorization": "Bearer "+localStorage.getItem("access-token"),
            "Refresh-Token": localStorage.getItem("refresh-token"),
            "Customer-Info": JSON.stringify(localStorage.getItem("customer-info"))
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)


export default {
    getCategories: function () {
        return instance.get(BASE_URL + 'product-service/category')
    },
    getProducts: function () {
        return instance.get(BASE_URL + 'product-service/products')
    },
    getProductReviews: function (productId) {
        return instance.get(BASE_URL + 'product-service/reviews?productId=' + productId)
    },
    postProductReviews: function (productId, contents) {
        return instance.post(BASE_URL + 'product-service/reviews', {
                "writerId": JSON.parse(localStorage.getItem("customer-info")).id,
                "writerName": JSON.parse(localStorage.getItem("customer-info")).name,
                "productId": productId,
                "content": contents
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    deleteProductReviews: function (reviewId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/delete`, {
                "writerId": JSON.parse(localStorage.getItem("customer-info")).id
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postReviewLikes: function (reviewId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/likes`, {
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postReviewDislikes: function (reviewId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/dislikes`, {
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getReviewComments: function (reviewId) {
        return instance.get(BASE_URL + `product-service/reviews/${reviewId}/comments`)
    },
    postReviewComments: function (reviewId, contents) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/comments`, {
                "writerId": JSON.parse(localStorage.getItem("customer-info")).id,
                "writerName": JSON.parse(localStorage.getItem("customer-info")).name,
                "content": contents
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    deleteReviewComments: function (reviewId, commentId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/comments/${commentId}/delete`, {
                "writerId": JSON.parse(localStorage.getItem("customer-info")).id
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postCommentLikes: function (reviewId, commentId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/comments/${commentId}/likes`, {
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postCommentDislikes: function (reviewId, commentId) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/comments/${commentId}/dislikes`, {
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getProductsInCategory: function (categoryId) {
        return instance.get(BASE_URL + 'product-service/products?categoryId=' + categoryId)
    },
    getLikesProducts: function () {
        return instance.get(BASE_URL + `product-service/products/likes?customer=${JSON.parse(localStorage.getItem("customer-info")).id}`)
    },
    getProductDetails: function (id) {
        return instance.get(BASE_URL + 'product-service/products/' + id)
    },
    postLikesProduct: function (productId) {
        return instance.post(BASE_URL + 'product-service/products/' + productId + '/likes', {
            "productId": productId,
            "memberId": JSON.parse(localStorage.getItem("customer-info")).id
        })
    },
    postOrderConfirm: function (myOrderProducts, couponId, shippingInfo) {
        return instance.post(BASE_URL + 'order-service/orders/orderConfirm', {
                "myOrderProducts":
                myOrderProducts
                ,
                "couponId": couponId,
                "shippingInfo": shippingInfo,
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postOrders: function (orderConfirm) {
        return instance.post(BASE_URL + 'order-service/orders/order', {
                "orderConfirm": orderConfirm
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    cancelOrders: function (orderId) {
        return instance.post(BASE_URL + 'order-service/orders/' + orderId + '/cancel', {
                "customerId": JSON.parse(localStorage.getItem("customer-info")).id
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getOrders: function () {
        return instance.get(BASE_URL + 'order-service/orders?customerId=' + JSON.parse(localStorage.getItem("customer-info")).id)
    },
    getOrderDetails: function (id) {
        return instance.get(BASE_URL + 'order-service/orders/' + id)
    },
    getCoupons: function () {
        return instance.get(BASE_URL + `coupon-service/coupons/my/${JSON.parse(localStorage.getItem("customer-info")).id}`)
    },
    getCouponDetails: function (id) {
        return instance.get(BASE_URL + 'coupon-service/coupons/' + id)
    },
    getPromotions: function () {
        return instance.get(BASE_URL + 'coupon-service/promotions')
    },
    joinPromotions: function (promotionId) {
        return instance.post(BASE_URL + 'coupon-service/promotions/' + promotionId + '/join',
            {
                "memberId": JSON.parse(localStorage.getItem("customer-info")).id
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getPromotionDetails: function (id) {
        return instance.get(BASE_URL + 'coupon-service/promotions/' + id)
    },
    postLogin: function (id, pwd) {
        return axios.post(BASE_URL + 'customer-service/login', {
                "loginId": id,
                "password": pwd
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    }
}