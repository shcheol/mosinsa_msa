<template>
  <div class="container">
    <h5>진행중인 프로모션</h5>

    <div class="main_container">
      <div class="item" v-for="(promotion, idx) in promotions" :key="promotion" @click="promotionDetails(promotion)">
        <div v-if="promotion!=null && idx === currentPage">
          <p>{{ promotion.title }}</p>
          <p>{{ promotion.context }}</p>
          <p>기간: {{ dateFormatting(promotion.period.startDate) }} ~ {{ dateFormatting(promotion.period.endDate) }}</p>
        </div>
      </div>
    </div>

    <div class="page-info">
      <div class="d-flex justify-content-center">
        <ul class="pagination">
          <li :class="'page-item'">
            <p class='page-link' @click="showingPromotion(currentPage-1)">&lt;</p>
          </li>
          <li v-for="(cur) in pageArr" :key="cur" class='page-item'>
            <p class='page-link'> {{ currentPage + 1 }}/{{ totalElements }}</p>
          </li>
          <li :class="'page-item'">
            <p class='page-link' @click="showingPromotion(currentPage + 1)"> &gt;</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import apiBoard from '@/api/board'
import dayjs from "dayjs";

export default {
  data() {
    return {
      promotions: null,
      currentPage: 0,
      pageArr: null,
      totalElements: null,
    }
  },
  mounted() {
    this.getPromotions(this.currentPage);
  },
  methods: {
    showingPromotion(idx) {
      idx = (idx + this.totalElements) % this.totalElements;
      this.currentPage = idx;
    },
    getPromotions() {
      apiBoard.getPromotions()
          .then((response) => {
            console.log(response);
            this.promotions = response.data.content;
            this.pageArr = new Array(response.data.totalPages);
            this.totalElements = response.data.totalElements;
          });
    },
    promotionDetails(promotion) {
      if (!promotion.proceeding) {
        alert("종료된 프로모션입니다.")
      } else {
        this.$router.push({
          name: 'promotionDetails',
          params: {id: promotion.promotionId}
        })
      }
    },
    dateFormatting(date) {
      return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
    },
  }
}
</script>
