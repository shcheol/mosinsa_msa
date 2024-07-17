<template>
  <div class="vl-parent" ref="loadingRef">
    <h3>상품 리뷰 ({{ reviewsNumberOfElements }})</h3>

    <div class="comment-list" >
      <ul>
        <li v-for="(review) in reviews" :key="review">
          <div>
            <span class="nickname">{{ review.writer }}</span>
            <button class="deleteBtn" @click="deleteReview(review.reviewId)"
                    v-if="customerInfo!==null && review.writerId === customerInfo.id">삭제
            </button>
            <br/>
            <span class="date">{{ dateFormatting(review.createdDate) }}</span>
            <br/>
            <p>{{ review.contents }}</p>
            <div class="reviewLikes" style="display: inline;" @click="reviewLikes(review.reviewId)"
                 v-if="reviewLikesReactionInfoMap.has(review.reviewId)">
              <img v-if="reviewLikesReactionInfoMap.get(review.reviewId).hasReacted" src="../assets/mythumbsup.png"
                   width="16" height="16"
                   style="display: inline; position: relative; left: 4px;" alt="likes"/>
              <img v-else src="../assets/thumbsup.png" width="16" height="16"
                   style="display: inline; position: relative; left: 4px;" alt="likes"/>

              <span style="display: inline; position: relative;left: 10px;color: red">{{
                  reviewLikesReactionInfoMap.get(review.reviewId).reactionCnt
                }}</span>
            </div>

            <div class="reviewDislikes" style="display: inline;" @click="reviewDislikes(review.reviewId)"
                 v-if="reviewDislikesReactionInfoMap.has(review.reviewId)">
              <img v-if="reviewDislikesReactionInfoMap.get(review.reviewId).hasReacted"
                   src="../assets/mythumbsdown.png"
                   width="16" height="16"
                   style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
              <img v-else src="../assets/thumbsdown.png" width="16" height="16"
                   style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
              <span style="display: inline; position: relative;left: 30px;color: blue">{{
                  reviewDislikesReactionInfoMap.get(review.reviewId).reactionCnt
                }}</span>
            </div>
            <br/>
            <button class="replyBtn" @click="showComments(review.reviewId, review)">답글 ({{
                review.commentsCount
              }})
            </button>

          </div>
        </li>
      </ul>
      <textarea v-model="reviewContent" placeholder="리뷰를 작성해주세요" class="reviewArea"></textarea>
      <button class="btn btn-dark" @click="postReview(productId)">등록</button>
    </div>
  </div>

</template>

<script>
import apiBoard from "@/api/board";
import dayjs from "dayjs";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import {ref} from 'vue'


export default {
  name: "Reviews",
  props: {
    propsValue: String
  },
  setup() {
    const loadingRef = ref(null)
    return {
      loadingRef,
    }
  },
  data() {
    return {
      productId: null,
      reviewsNumberOfElements: 0,
      reviews: null,
      reviewContent: null,
      reviewLikesReactionInfoMap: new Map(),
      reviewDislikesReactionInfoMap: new Map(),
      customerInfo: JSON.parse(localStorage.getItem("customer-info")),
      websocketClient: null,
      commentLoader: null,
    }
  },
  watch: {
    propsValue() {
      this.productId = this.propsValue;
      this.getReview(this.productId);
    }
  },
  mounted() {
    this.connect();
    window.addEventListener("beforeunload", function (event) {
      console.log(event);
      this.disconnect()
    });
    console.log("mounted " + this.loadingRef);

    this.showLoadingOverlay();

  },
  beforeUnmount() {
    this.disconnect();
  },
  methods: {
    showLoadingOverlay() {
      console.log(this.$refs.loadingRef);
      let loadingInterval = setInterval(function () {
        if (this.$refs.loadingRef) {
          this.commentLoader = this.$loading.show({
            container: this.$refs.loadingRef,
            width: 64,
            height: 64,
            loader: "spinner",
            canCancel: true,
            lockScroll: true,
          }, {});
          clearInterval(loadingInterval);
        }
      }.bind(this), 1000);

    },
    connect() {
      const serverURL = "/websocket-service/register"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`try connect socket. url: ${serverURL}`)

      this.stompClient.connect(
          {},
          frame => {
            console.log('connect success', frame);
            try {

              let connectInterval = setInterval(function () {
                console.log("timeout")
                if (this.stompClient.connected) {
                  this.stompClient.subscribe(`/topic/${this.productId}`, response => {
                    console.log('subscribe message: ', response.body);
                    const message = JSON.parse(response.body);
                    console.log(message)
                    this.processSubscribedMessage(message);
                  });
                  clearInterval(connectInterval);
                }
              }.bind(this), 1000);
            } catch (e) {
              console.log(e);
            } finally {
              this.commentLoader.hide();
            }
          },
          error => {
            console.log('socket connect fail', error);
          }
      );

    },
    processSubscribedMessage(message) {
      if (message.type === "REVIEW") {
        if (message.likesType === "LIKES") {
          if (message.canceled) {
            this.reviewLikesReactionInfoMap.get(message.reviewId).reactionCnt -= 1;
          } else {
            this.reviewLikesReactionInfoMap.get(message.reviewId).reactionCnt += 1;
          }
        } else {
          if (message.canceled) {
            this.reviewDislikesReactionInfoMap.get(message.reviewId).reactionCnt -= 1;
          } else {
            this.reviewDislikesReactionInfoMap.get(message.reviewId).reactionCnt += 1;
          }
        }
      }

    },
    disconnect() {
      if (this.stompClient.connected) {
        this.stompClient.disconnect();
      }
    },
    totalReviewReaction(reviewId) {
      apiBoard.getReactionCount('REVIEW', reviewId, 'LIKES')
          .then((response) => {
            this.reviewLikesReactionInfoMap.set(reviewId, response.data);
          });
      apiBoard.getReactionCount('REVIEW', reviewId, 'DISLIKES')
          .then((response) => {
            this.reviewDislikesReactionInfoMap.set(reviewId, response.data);
          });
    },
    reviewLikes(reviewId) {
      if (this.reviewLikesReactionInfoMap.has(reviewId)) {
        if (!this.reviewLikesReactionInfoMap.get(reviewId).hasReacted) {
          apiBoard.postReaction('REVIEW', reviewId, 'LIKES').then(() => this.reviewLikesReactionInfoMap.get(reviewId).hasReacted = true);
        } else {
          apiBoard.postReactionCancel('REVIEW', reviewId, 'LIKES').then(() => this.reviewLikesReactionInfoMap.get(reviewId).hasReacted = false);
        }
      }
    },
    reviewDislikes(reviewId) {
      if (this.reviewDislikesReactionInfoMap.has(reviewId)) {
        if (!this.reviewDislikesReactionInfoMap.get(reviewId).hasReacted) {
          apiBoard.postReaction('REVIEW', reviewId, 'DISLIKES').then(() => this.reviewDislikesReactionInfoMap.get(reviewId).hasReacted = true);
        } else {
          apiBoard.postReactionCancel('REVIEW', reviewId, 'DISLIKES').then(() => this.reviewDislikesReactionInfoMap.get(reviewId).hasReacted = false);
        }
      }
    },
    getReview(productId) {
      apiBoard.getProductReviews(productId)
          .then((response) => {
            console.log(response)
            this.reviews = response.data.content;
            this.reviews.forEach((r) => this.totalReviewReaction(r.reviewId));
            this.reviewsNumberOfElements = response.data.numberOfElements;
          });
    },
    deleteReview(reviewId) {
      apiBoard.deleteProductReviews(reviewId)
          .then(() => {
            this.getReview(this.productId);
          });
    },
    dateFormatting(createdAt) {
      return dayjs(createdAt).format('YYYY-MM-DD HH:mm:ss');
    },
    postReview(productId) {
      apiBoard.postProductReviews(productId, this.reviewContent)
          .then(() => {
            this.reviewContent = "";
            this.getReview(productId);
          });
    },
    showComments(reviewId, review) {
      this.$router.push({
        name: 'reviewDetails',
        params: {id: reviewId},
        state: {
          review: JSON.stringify(review),
          reviewLikesInfo: JSON.stringify(this.reviewLikesReactionInfoMap.get(reviewId)),
          reviewDislikesInfo: JSON.stringify(this.reviewDislikesReactionInfoMap.get(reviewId)),
        },
      })
    }
  }
}
</script>

<style scoped>

.comment-list {
  margin-bottom: 60px;
  border-top: 1px solid #eee;
}

.replyBtn {
  border: none;
  background: none;
  font-size: 15px;
  color: blue;
}

.deleteBtn {
  border: none;
  background: none;
  font-size: 15px;
  color: blue;
}

.nickname {
  border: none;
  background: none;
  font-size: 18px;
  font-weight: bold;
}

.date {
  border: none;
  background: none;
  font-size: 12px;
  color: gray;
}

.reviewArea {
  width: 100%;
}
</style>