import axios from "axios";

// const BASE_URL="http://152.67.205.195:8000/";
const BASE_URL="/";

export default{
    getProducts: function (){
        return axios.get(BASE_URL + 'product-service/products')
    },
    getProductDetails: function (id){
        return axios.get(BASE_URL + 'product-service/products/'+id)
    },
    getOrders: function (id){
        return axios.get(BASE_URL + 'order-service/orders?customerId=' + id)
    },
    getOrderDetails: function (id){
        return axios.get(BASE_URL + 'order-service/orders/' + id)
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