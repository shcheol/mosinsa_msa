<template>
  <div class="container">
    <h2>쿠폰 상세</h2>

    <table class="table">
      <tbody>
      <tr>
        <td>쿠폰번호</td>
        <td v-if="coupon!=null">{{ coupon.couponId }}</td>
      </tr>
      <tr>
        <td>프로모션 번호</td>
        <td v-if="coupon!=null">{{ coupon.promotionId }}</td>
      </tr>
      <tr>
        <td>발급일</td>
        <td v-if="coupon!=null">{{ coupon.issuedDate }}</td>
      </tr>
      <tr>
        <td>상태</td>
        <td v-if="coupon!=null">{{ coupon.state }}</td>
      </tr>
      <tr>
        <td>만료기간</td>
        <td v-if="coupon!=null">{{ coupon.details.duringDate }}</td>
      </tr>
      <tr>
        <td>할인률</td>
        <td v-if="coupon!=null">{{ coupon.details.discountPolicy }}</td>
      </tr>
      </tbody>
    </table>

    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      coupon: null,
    }
  },
  mounted() {
    apiBoard.getCouponDetails(this.$route.params.id)
        .then((response) => {
          console.log(response);
          this.coupon = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
  }

}
</script>
