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
            "Authorization": "Bearer " + localStorage.getItem("access-token"),
            "Refresh-Token": localStorage.getItem("refresh-token"),
            "Customer-Info": JSON.stringify(localStorage.getItem("customer-info")),
            'Content-Type': 'application/json; charset=utf-8',
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)


export default {
    getCategories: function (id) {
        if (!id) {
            return instance.get(BASE_URL + 'product-service/category')
        }
        return instance.get(BASE_URL + `product-service/category/${id}`)
    },
    getProducts: function (page,categoryId) {
        if (!categoryId){
            return instance.get(BASE_URL + `product-service/products?page=${page}`)
        }
        return instance.get(BASE_URL + `product-service/products?page=${page}&categoryId=${categoryId}`)
    },
    getSaleProducts: function (page,sales) {
        return instance.get(BASE_URL + `product-service/products?page=${page}&sales=${sales}`)
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
    getLikesProducts: function () {
        return instance.get(BASE_URL + `product-service/products/likes`)
    },
    getProductDetails: function (id) {
        return instance.get(BASE_URL + 'product-service/products/' + id)
    },
    getReactionCount: function (target, id, reactionType) {
        return instance.get(BASE_URL + `product-service/reactions/total?target=${target}&targetId=${id}&reactionType=${reactionType}`)
    },
    postReaction: function (target, id, reactionType) {
        return instance.post(BASE_URL + 'product-service/reactions', {
            "target": target,
            "targetId": id,
            "reactionType": reactionType,
        })
    },
    postReactionCancel: function (target, id, reactionType) {
        return instance.post(BASE_URL + 'product-service/reactions/cancel', {
            "target": target,
            "targetId": id,
            "reactionType": reactionType,
        })
    },
    postOrderConfirm: function (myOrderProducts, shippingInfo) {
        return instance.post(BASE_URL + 'order-service/orders/orderConfirm', {
                "myOrderProducts": myOrderProducts,
                "shippingInfo": shippingInfo,
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    postOrders: function (myOrderProducts, shippingInfo) {
        return instance.post(BASE_URL + 'order-service/orders/order', {
                "myOrderProducts": myOrderProducts,
                "shippingInfo": shippingInfo,
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
        return instance.get(BASE_URL + `coupon-service/coupons`)
    },
    getCouponDetails: function (id) {
        return instance.get(BASE_URL + 'coupon-service/coupons/' + id)
    },
    getPromotions: function () {
        return instance.get(BASE_URL + `coupon-service/promotions`)
    },
    joinPromotions: function (promotionId, quests) {
        return instance.post(BASE_URL + `coupon-service/promotions/${promotionId}`,
            {
                "quests": quests
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