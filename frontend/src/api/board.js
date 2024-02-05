import axios from "axios";

// const BASE_URL="http://152.67.205.195:8000/";
const BASE_URL="/";

export default{
    getCategories: function (){
        return axios.get(BASE_URL + 'product-service/category')
    },
    getProducts: function (){
        return axios.get(BASE_URL + 'product-service/products')
    },
    getProductsInCategory: function (categoryId){
        return axios.get(BASE_URL + 'product-service/products?categoryId='+categoryId)
    },
    getLikesProducts: function (customerId){
        return axios.get(BASE_URL + 'product-service/products/likes?customer='+customerId)
    },
    getProductDetails: function (id){
        return axios.get(BASE_URL + 'product-service/products/'+id)
    },
    patchLikesProduct: function (customerId, productId){
        return axios.patch(BASE_URL + 'product-service/products/'+productId+'/likes',{
            "productId": productId,
            "memberId": customerId
        })
    },
    postOrders: function (customerId, myOrderProducts, couponId){
        return axios.post(BASE_URL + 'order-service/orders',{
                "customerId" : customerId,
                "myOrderProducts":myOrderProducts,
                "couponId" : couponId
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    cancelOrders: function (customerId, orderId){
        return axios.post(BASE_URL + 'order-service/orders/cancel',{
                "customerId" : customerId,
                "orderId" : orderId
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getOrders: function (id){
        return axios.get(BASE_URL + 'order-service/orders?customerId=' + id)
    },
    getOrderDetails: function (id){
        return axios.get(BASE_URL + 'order-service/orders/' + id)
    },
    getCoupons: function (id){
        return axios.get(BASE_URL + 'coupon-service/coupons/my/' + id)
    },
    getCouponDetails: function (id){
        return axios.get(BASE_URL + 'coupon-service/coupons/' + id)
    },
    getPromotions: function (){
        return axios.get(BASE_URL + 'coupon-service/promotions')
    },
    patchJoinPromotions: function (memberId, promotionId){
        return axios.patch(BASE_URL + 'coupon-service/promotions', {
            "memberId":memberId,
            "promotionId":promotionId
        },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    },
    getPromotionDetails: function (id){
        return axios.get(BASE_URL + 'coupon-service/promotions/' + id)
    },
    postLogin: function (id, pwd){
        return axios.post(BASE_URL + 'customer-service/login', {
            "loginId" : id,
            "password" : pwd
        },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
    }
}