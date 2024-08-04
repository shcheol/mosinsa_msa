<template>
  <div class="container">
    <h2>진행중인 프로모션</h2>

    <div v-for="(promotion) in promotions" :key="promotion">
      <table class="table">
        <tbody v-if="promotion!=null" @click="promotionDetails(promotion.promotionId)">
        <tr>
          <td>제목</td>
          <td v-if="promotion!=null">{{ promotion.title }}</td>
        </tr>
        <tr>
          <td>기간</td>
          <td v-if="promotion!=null">{{ dateFormatting(promotion.period.startDate) }} ~ {{dateFormatting(promotion.period.endDate)}}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
  </div>
</template>

<script>
import apiBoard from '@/api/board'
import dayjs from "dayjs";

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
    dateFormatting(date) {
      return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
    },
  }
}
</script>
