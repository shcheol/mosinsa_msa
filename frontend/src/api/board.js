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
        let item = localStorage.getItem("access-token");
        console.log(item)
        config.headers = {
            "Authorization": "Bearer "+localStorage.getItem("access-token"),
            "Refresh-Token": localStorage.getItem("refresh-token"),
            "CustomerId": localStorage.getItem("customerId")
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
    postProductReviews: function (writerId, writerName, productId, contents) {
        return instance.post(BASE_URL + 'product-service/reviews', {
                "writerId": writerId,
                "writerName": writerName,
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
    getReviewComments: function (reviewId) {
        return instance.get(BASE_URL + `product-service/reviews/${reviewId}/comments`)
    },
    postReviewComments: function (writerId, writerName, reviewId, contents) {
        return instance.post(BASE_URL + `product-service/reviews/${reviewId}/comments`, {
                "writerId": writerId,
                "writerName": writerName,
                "content": contents
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
    getLikesProducts: function (customerId) {
        return instance.get(BASE_URL + 'product-service/products/likes?customer=' + customerId)
    },
    getProductDetails: function (id) {
        return instance.get(BASE_URL + 'product-service/products/' + id)
    },
    postLikesProduct: function (customerId, productId) {
        return instance.post(BASE_URL + 'product-service/products/' + productId + '/likes', {
            "productId": productId,
            "memberId": customerId
        })
    },
    postOrderConfirm: function (customerId, myOrderProducts, couponId, shippingInfo) {
        return instance.post(BASE_URL + 'order-service/orders/orderConfirm', {
                "customerId": customerId,
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
    cancelOrders: function (customerId, orderId) {
        return instance.post(BASE_URL + 'order-service/orders/' + orderId + '/cancel', {
                "customerId": customerId
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getOrders: function (id) {
        return instance.get(BASE_URL + 'order-service/orders?customerId=' + id)
    },
    getOrderDetails: function (id) {
        return instance.get(BASE_URL + 'order-service/orders/' + id)
    },
    getCoupons: function (id) {
        return instance.get(BASE_URL + 'coupon-service/coupons/my/' + id)
    },
    getCouponDetails: function (id) {
        return instance.get(BASE_URL + 'coupon-service/coupons/' + id)
    },
    getPromotions: function () {
        return instance.get(BASE_URL + 'coupon-service/promotions')
    },
    joinPromotions: function (memberId, promotionId) {
        return instance.post(BASE_URL + 'coupon-service/promotions/' + promotionId + '/join',
            {
                "memberId": memberId
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