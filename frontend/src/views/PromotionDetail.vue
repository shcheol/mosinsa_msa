<template>
  <div class="container">
    <h2>프로모션 상세</h2>

    <table class="table">
      <tbody>
      <tr>
        <td>프로모션 번호</td>
        <td v-if="promotion!=null">{{ promotion.promotionId }}</td>
      </tr>
      <tr>
        <td>제목</td>
        <td v-if="promotion!=null">{{ promotion.title }}</td>
      </tr>
      <tr>
        <td>내용</td>
        <td v-if="promotion!=null">{{ promotion.context }}</td>
      </tr>
      <tr>
        <td>기간</td>
        <td v-if="promotion!=null">{{ promotion.period.startDate }} ~ {{ promotion.period.endDate }}</td>
      </tr>
      </tbody>
    </table>

    <button @click="joinPromotion(promotion)" class="btn btn-dark">참여하기</button>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      promotion: null,
    }
  },
  mounted() {
    apiBoard.getPromotionDetails(this.$route.params.id)
        .then((response) => {
          console.log(response);
          this.promotion = response.data;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    joinPromotion(promotion) {
      if (promotion.participated) {
        alert("이미 참여했습니다.");
      } else {
        apiBoard.joinPromotions(promotion.promotionId)
            .then(() => {
              alert("쿠폰 발급 성공. 쿠폰함을 확인하세요");
            }).catch(function (e) {
          alert("발급 실패, " + e.message);
        });
      }
    }
  }

}
</script>
