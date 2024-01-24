<template>
  <div class="container">
    <h2>프로모션 목록</h2>

    <div v-for="(promotion) in promotions" :key="promotion">
      <table>
        <tbody>
        <tr>
          <td>프로모션 번호</td>
          <td v-if="promotion!=null">{{ promotion.promotionId }}</td>
          <button v-if="promotion!=null" @click="promotionDetails(promotion.promotionId)">프로모션상세</button>
        </tr>
        <tr>
          <td>제목</td>
          <td v-if="promotion!=null">{{ promotion.title }}</td>
        </tr>
        <tr>
          <td>기간</td>
          <td v-if="promotion!=null">{{ promotion.period.startDate }} ~ {{promotion.period.endDate}}</td>
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
      promotions: null,
    }
  },
  mounted() {
    apiBoard.getPromotions()
        .then((response) => {
          console.log(response);
          this.promotions = response.data.content;
        })
        .catch(function (e) {
          console.log(e);
        });
  },
  methods: {
    promotionDetails(id) {
      this.$router.push({
        name: 'promotionDetails',
        params: {id: id}
      })
    },
  }
}
</script>
