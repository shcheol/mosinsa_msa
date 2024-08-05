import Vuex from 'vuex'

const store = new Vuex.Store({
    state: {
        cart: [],
        isLogin: localStorage.getItem("customer-info") !== null,
    },
    actions: {

        addCart({state, commit}, product) {
            if (product.stock < 0) {
                alert("남은수량 없음");
                return;
            }

            const cartItem = state.cart.find(item => item.productId === product.productId);
            if (!cartItem) {
                commit('pushProductToCart', product);
            } else {
                commit('increaseItemQuantity', cartItem);
            }
        },
        removeCart({state, commit}, product) {
            const cartItem = state.cart.find(item => item.productId === product.productId);
            if (cartItem.quantity > 0) {
                commit('decreaseItemQuantity', cartItem);
            } else {
                alert("장바구니에 담은 수량 없음");
            }
        }
    },
    mutations: {
        setLoginState(state){
            state.isLogin=true;
        },
        setLogoutState(state){
            state.isLogin=false;
        },
        pushProductToCart(state, product) {
            state.cart.push({
                productId: product.productId,
                name: product.name,
                price: product.price,
                quantity: 1,
            });
        },
        increaseItemQuantity(state, cartItem) {
            cartItem.quantity++;
        },
        decreaseItemQuantity(state, cartItem) {
            cartItem.quantity--;
        },
    },
    getters: {
        getCartProducts(state) {
            return state.cart.map(cartItem => {
                    const product = state.cart.find(product => product.productId === cartItem.productId);
                    if (product !== undefined) {
                        return {
                            productId: product.productId,
                            name: product.name,
                            price: product.price,
                            quantity: product.quantity,
                        };
                    }
                }
            );
        },
        cartTotal(state) {
            let total = 0;
            state.cart.forEach(cartItem => {
                total += cartItem.price * cartItem.quantity;
            });
            return total;
        },
    },
})

export default store;