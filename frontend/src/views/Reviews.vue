<template>
  <div>
    <h3>상품 리뷰 ({{ reviewsNumberOfElements }})</h3>

    <div class="comment-list">
      <ul>
        <li v-for="(review) in reviews" :key="review">
          <div>
            <span class="nickname">{{ review.writer }}</span>
            <button class="deleteBtn" @click="deleteReview(review.reviewId)" v-if="review.writerId === customerInfo.id">
              삭제
            </button>
            <br/>
            <span class="date">{{ dateFormatting(review.createdDate) }}</span>
            <br/>
            <p>{{ review.contents }}</p>
            <div class="reviewLikes" style="display: inline;" @click="reviewLikes(review.reviewId)">
              <img src="../assets/thumbsup.png" width="16" height="16"
                   style="display: inline; position: relative; left: 4px;" alt="likes"/>
              <span style="display: inline; position: relative;left: 10px;color: red">{{ review.likesCount }}</span>
            </div>

            <div class="reviewDislikes" style="display: inline;" @click="reviewDislikes(review.reviewId)">
              <img src="../assets/thumbsdown.png" width="16" height="16"
                   style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
              <span style="display: inline; position: relative;left: 30px;color: blue">{{ review.dislikesCount }}</span>
            </div>
            <br/>
            <button class="replyBtn" @click="showComments(review.reviewId)">답글 ({{ review.commentsCount }})</button>

            <div v-if="commentStateMap.get(review.reviewId)">
              <ul>
                <li v-for="(comment) in commentsMap.get(review.reviewId)" :key="comment">
                  <div>
                    <span class="nickname">{{ comment.writer }}</span>
                    <button class="deleteBtn"
                            @click="deleteComment(review.reviewId, comment.commentId)"
                            v-if="(comment.writerId === customerInfo.id) && comment.contents !== '삭제된 댓글입니다.'">삭제
                    </button>
                    <br/>
                    <span class="date">{{ dateFormatting(comment.createdDate) }}</span>
                    <p>{{ comment.contents }}</p>

                    <div class="commentLikes" style="display: inline;"
                         @click="commentLikes(review.reviewId, comment.commentId)">
                      <img src="../assets/thumbsup.png" width="16" height="16"
                           style="display: inline; position: relative; left: 4px;" alt="likes"/>
                      <span
                          style="display: inline; position: relative;left: 10px;color: red">{{
                          comment.likesCount
                        }}</span>
                    </div>

                    <div class="commentDislikes" style="display: inline;"
                         @click="commentDislikes(review.reviewId, comment.commentId)">
                      <img src="../assets/thumbsdown.png" width="16" height="16"
                           style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
                      <span
                          style="display: inline; position: relative;left: 30px;color: blue">{{
                          comment.dislikesCount
                        }}</span>
                    </div>
                  </div>
                </li>
              </ul>
              <div>
                <textarea v-model="commentContent" placeholder="댓글을 작성해주세요" class="commentArea"></textarea>
                <button class="btn btn-dark" @click="postComment(review.reviewId)">등록</button>
              </div>
            </div>
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

export default {
  name: "Reviews",
  props: {
    propsValue: String
  },
  data() {
    return {
      productId: null,
      reviewsNumberOfElements: 0,
      reviews: null,
      reviewContent: null,
      commentContent: null,
      commentsMap: new Map(),
      commentStateMap: new Map(),
      customerInfo: JSON.parse(localStorage.getItem("customer-info")),
      websocketClient: null,
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
  },
  beforeUnmount() {
    this.disconnect();
  },
  methods: {
    connect() {
      const serverURL = "/product-service/register"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
          {},
          frame => {
            console.log('소켓 연결 성공', frame);
            this.stompClient.subscribe(`/topic/product/${this.productId}`, response => {
              console.log('구독으로 받은 메시지 입니다.', response.body);
              const message = JSON.parse(response.body);
              console.log(message)
              this.processSubscribedMessage(message);
            });
          },
          error => {
            console.log('소켓 연결 실패', error);
          }
      );
    },
    processSubscribedMessage(message) {
      if (message.type === "REVIEW") {
        this.reviews.filter(review => review.reviewId === message.reviewId)
            .map(findReview => {
                  if (message.likesType === "LIKES") {
                    if (message.canceled) {
                      findReview.likesCount -= 1;
                    } else {
                      findReview.likesCount += 1;
                    }
                  } else {
                    if (message.canceled) {
                      findReview.dislikesCount -= 1;
                    } else {
                      findReview.dislikesCount += 1;
                    }
                  }
                }
            );
      } else{
        this.commentsMap.get(message.reviewId)
            .filter(comment => comment.commentId === message.commentId)
            .map(findComment => {
              if (message.likesType === "LIKES") {
                if (message.canceled) {
                  findComment.likesCount -= 1;
                } else {
                  findComment.likesCount += 1;
                }
              } else {
                if (message.canceled) {
                  findComment.dislikesCount -= 1;
                } else {
                  findComment.dislikesCount += 1;
                }
              }
            });
      }
    },
    disconnect() {
      this.stompClient.disconnect();
    },
    commentLikes(reviewId, commentId) {
      apiBoard.postCommentLikes(reviewId, commentId)
          // .then(() => {
          //   apiBoard.getReviewComments(reviewId)
          //       .then((response) => {
          //         this.commentsMap.set(reviewId, response.data.content);
          //       });
          // });
    },
    commentDislikes(reviewId, commentId) {
      apiBoard.postCommentDislikes(reviewId, commentId)
          // .then(() => {
          //   apiBoard.getReviewComments(reviewId)
          //       .then((response) => {
          //         this.commentsMap.set(reviewId, response.data.content);
          //       });
          // });
    },
    reviewLikes(reviewId) {
      apiBoard.postReviewLikes(reviewId)
          // .then(() => {
          //   this.getReview(this.productId);
          // });
    },
    reviewDislikes(reviewId) {
      apiBoard.postReviewDislikes(reviewId)
          // .then(() => {
          //   this.getReview(this.productId);
          // });
    },
    getReview(productId) {
      apiBoard.getProductReviews(productId)
          .then((response) => {
            console.log(response)
            this.reviews = response.data.content;
            this.reviewsNumberOfElements = response.data.numberOfElements;
          });
    },
    deleteReview(reviewId) {
      apiBoard.deleteProductReviews(reviewId)
          .then(() => {
            this.getReview(this.productId);
          });
    },
    deleteComment(reviewId, commentId) {
      apiBoard.deleteReviewComments(reviewId, commentId)
          .then(() => {
            apiBoard.getReviewComments(reviewId)
                .then((response) => {
                  this.commentsMap.set(reviewId, response.data.content);
                });
          });
    },
    dateFormatting(createdAt) {
      return dayjs(createdAt).format('YYYY-MM-DD HH:mm:ss');
    }
    ,
    postReview(productId) {
      apiBoard.postProductReviews(productId, this.reviewContent)
          .then(() => {
            this.reviewContent = "";
            this.getReview(productId);
          });
    },
    postComment(reviewId) {
      apiBoard.postReviewComments(reviewId, this.commentContent)
          .then(() => {
            this.commentContent = "";
            apiBoard.getReviewComments(reviewId)
                .then((response) => {
                  this.commentsMap.set(reviewId, response.data.content);
                });
          });
    },
    showComments(reviewId) {
      if (this.commentStateMap.get(reviewId) === null) {
        this.commentStateMap.set(reviewId, true);
      } else {
        this.commentStateMap.set(reviewId, !this.commentStateMap.get(reviewId));
      }
      if (this.commentsMap.get(reviewId) == null) {
        apiBoard.getReviewComments(reviewId)
            .then((response) => {
              this.commentsMap.set(reviewId, response.data.content);
            });
      }
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

.commentArea {
  width: 100%;
}
</style>