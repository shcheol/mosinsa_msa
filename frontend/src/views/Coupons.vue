<template>
  <div class="container">
    <h2>쿠폰목록</h2>

    <div v-if="coupons ==null || coupons.length<=0" >쿠폰이 없습니다.</div>
    <div v-else v-for="(coupon) in coupons" :key="coupon">
      <table class="table">
        <tbody>
        <tr>
          <td>쿠폰번호</td>
          <td v-if="coupon!=null">{{ coupon.couponId }}</td>
          <button v-if="coupon!=null" @click="couponCondition(coupon.couponId)">쿠폰상세</button>
        </tr>
        <tr>
          <td>할인률</td>
          <td v-if="coupon!=null">{{ coupon.details.discountPolicy }}</td>
        </tr>
        <tr>
          <td>상태</td>
          <td v-if="coupon!=null">{{ coupon.state }}</td>
        </tr>
        </tbody>
      </table>

    </div>

    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      coupons: null,
      promotionDetail: null
    }
  },
  mounted() {
    apiBoard.getCoupons()
        .then((response) => {
          console.log(response);
          this.coupons = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    couponCondition(id) {
      this.$router.push({
        name: 'couponDetails',
        params: {id: id}
      })
    },
    promotionDetails(id){
      apiBoard.getPromotionDetails(id)
          .then((response) => {
            console.log(response);
            this.coupons = response.data;
          })
          .catch(function (e) {
            console.log(e);
          });


    }

  }


}
</script>
