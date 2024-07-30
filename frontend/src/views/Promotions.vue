<template>
  <div class="container">
    <h2>진행중인 프로모션</h2>

      <div class="main__container">
        <div class="item" v-for="(promotion) in promotions" :key="promotion" @click="promotionDetails(promotion)">
          <p v-if="promotion!=null">{{ promotion.title }}</p>
          <p v-if="promotion!=null">{{ promotion.context }}</p>
          <p v-if="promotion!=null">기간: {{ dateFormatting(promotion.period.startDate) }} ~ {{dateFormatting(promotion.period.endDate)}}</p>
        </div>
      </div>


    <div class="page-info">
      <div class="d-flex justify-content-center">
        <ul class="pagination">
          <li :class="first? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getPromotions(currentPage -1)">Prev</p>
          </li>
          <li v-for="(cur, idx) in pageArr" :key="cur" class='page-item'>
            <p :class="currentPage === idx? 'page-link active':'page-link'" @click="showProjects(idx)">{{ idx + 1 }}</p>
          </li>
          <li :class="last? 'page-item disabled' : 'page-item'">
            <p class='page-link' @click="getPromotions(currentPage + 1)">Next</p>
          </li>
        </ul>
      </div>
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
      currentPage: 0,
      pageArr: null,
      first: null,
      last: null,
    }
  },
  mounted() {
    this.getPromotions(this.currentPage);
  },
  methods: {

    getPromotions(page){
      this.currentPage = page;
      apiBoard.getPromotions(page)
          .then((response) => {
            console.log(response);
            this.promotions = response.data.content;
            this.pageArr = new Array(response.data.totalPages);
            this.first = response.data.first;
            this.last = response.data.last;
          })
          .catch(function (e) {
            console.log(e);
          });
    },

    promotionDetails(promotion) {
      if (!promotion.proceeding){
        alert("종료된 프로모션입니다.")
      }else {
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
