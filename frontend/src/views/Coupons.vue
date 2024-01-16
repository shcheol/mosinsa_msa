<template>
  <div class="container">
    <h2>쿠폰목록</h2>

    <div class="card mb-3" style="max-width: 540px;" v-for="(coupon) in coupons" :key="coupon">
      <table class="table">
        <tbody>
        <tr>
          <td>쿠폰번호</td>
          <td v-if="coupon!=null">{{ coupon.couponId }}</td>
          <button v-if="coupon!=null" @click="couponDetails(coupon.couponId)">쿠폰상세</button>
        </tr>
        <tr>
          <td>프로모션번호</td>
          <td v-if="coupon!=null">{{ coupon.promotionId }}</td>
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
    apiBoard.getCoupons(localStorage.getItem("customerId"))
        .then((response) => {
          console.log(response);
          this.coupons = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    couponDetails(id) {
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
