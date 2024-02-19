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
        <td>할인률</td>
        <td v-if="promotion!=null">{{ promotion.discountPolicy }}</td>
      </tr>
      <tr>
        <td>기간</td>
        <td v-if="promotion!=null">{{ promotion.period.startDate }} ~ {{ promotion.period.endDate }}</td>
      </tr>
      <tr>
        <td>수량</td>
        <td v-if="promotion!=null">{{ stock }}/{{ promotion.quantity }}</td>
      </tr>
      </tbody>
    </table>

    <button @click="joinPromotion(promotion.promotionId)">참여하기</button>
    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'

export default {
  data() {
    return {
      promotion: null,
      stock: null
    }
  },
  mounted() {
    apiBoard.getPromotionDetails(this.$route.params.id)
        .then((response) => {
          console.log(response);
          this.promotion = response.data.promotionDto;
          this.stock = response.data.stock;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    joinPromotion(promotionId) {
      if (localStorage.getItem("customerId") == null) {
        alert('login이 필요합니다.')
        this.$router.push({name: "login"})
      }
      apiBoard.joinPromotions(localStorage.getItem("customerId"), promotionId)
          .then((response) => {
            alert(response.data.result);
          }).catch(function (e) {
        console.log(e);
      });
    }
  }

}
</script>
