<template>
  <div class="container">
    <div v-if="review!==null">
      <div>
      <img @click="back()" src="../assets/back.png"
           width="20" height="20"
           style="display: inline; position: relative; left: 4px;" alt="뒤로가기"/>
      <h3 style="display: inline; position: relative;left: 10px;">답글 ({{ review.commentsCount }})</h3>
      </div>
      <div class="comment-list">
        <ul>
          <li>
            <div>
              <span class="nickname">{{ review.writer }}</span>
              <br/>
              <span class="date">{{ dateFormatting(review.createdDate) }}</span>
              <br/>
              <p>{{ review.contents }}</p>
              <div class="reviewLikes" style="display: inline;" @click="reviewLikes(review.reviewId)"
                   v-if="reviewLikesInfo!==null">
                <img v-if="reviewLikesInfo.hasReacted" src="../assets/mythumbsup.png"
                     width="16" height="16"
                     style="display: inline; position: relative; left: 4px;" alt="likes"/>
                <img v-else src="../assets/thumbsup.png" width="16" height="16"
                     style="display: inline; position: relative; left: 4px;" alt="likes"/>

                <span style="display: inline; position: relative;left: 10px;color: red">{{
                    reviewLikesInfo.reactionCnt
                  }}</span>
              </div>

              <div class="reviewDislikes" style="display: inline;" @click="reviewDislikes(review.reviewId)"
                   v-if="reviewDislikesInfo!==null">
                <img v-if="reviewDislikesInfo.hasReacted" src="../assets/mythumbsdown.png"
                     width="16" height="16"
                     style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
                <img v-else src="../assets/thumbsdown.png" width="16" height="16"
                     style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
                <span style="display: inline; position: relative;left: 30px;color: blue">{{
                    reviewDislikesInfo.reactionCnt
                  }}</span>
              </div>
              <br/>

              <div>
                <ul>
                  <li v-for="(comment) in comments" :key="comment">
                    <div>
                      <span class="nickname">{{ comment.writer }}</span>
                      <button class="deleteBtn"
                              @click="deleteComment(review.reviewId, comment.commentId)"
                              v-if="customerInfo!==null && (comment.writerId === customerInfo.id) && comment.contents !== '삭제된 댓글입니다.'">
                        삭제
                      </button>
                      <br/>
                      <span class="date">{{ dateFormatting(comment.createdDate) }}</span>
                      <p>{{ comment.contents }}</p>

                      <div class="commentLikes" style="display: inline;"
                           @click="commentLikes(review.reviewId, comment.commentId)"
                           v-if="commentLikesReactionInfoMap.has(comment.commentId)">
                        <img v-if="commentLikesReactionInfoMap.get(comment.commentId).hasReacted"
                             src="../assets/mythumbsup.png" width="16" height="16"
                             style="display: inline; position: relative; left: 4px;" alt="likes"/>
                        <img v-else src="../assets/thumbsup.png" width="16" height="16"
                             style="display: inline; position: relative; left: 4px;" alt="likes"/>
                        <span
                            style="display: inline; position: relative;left: 10px;color: red">{{
                            commentLikesReactionInfoMap.get(comment.commentId).reactionCnt
                          }}</span>
                      </div>

                      <div class="commentDislikes" style="display: inline;"
                           @click="commentDislikes(review.reviewId, comment.commentId)"
                           v-if="commentDislikesReactionInfoMap.has(comment.commentId)">
                        <img
                            v-if="commentDislikesReactionInfoMap.get(comment.commentId).hasReacted"
                            src="../assets/mythumbsdown.png" width="16" height="16"
                            style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
                        <img v-else src="../assets/thumbsdown.png" width="16" height="16"
                             style="display: inline; position: relative; left: 24px;" alt="dislikes"/>
                        <span style="display: inline; position: relative;left: 30px;color: blue">{{
                            commentDislikesReactionInfoMap.get(comment.commentId).reactionCnt
                          }}</span>
                      </div>
                    </div>
                  </li>
                </ul>
                <div>

                </div>
              </div>
            </div>
          </li>
          <textarea v-model="commentContent" placeholder="답글을 작성해주세요" class="commentArea"></textarea>
          <button class="btn btn-dark" @click="postComment(review.reviewId)">등록</button>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import apiBoard from "@/api/board";
import dayjs from "dayjs";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  data() {
    return {
      review: null,
      commentContent: null,
      comments: null,
      reviewLikesInfo: null,
      reviewDislikesInfo: null,
      customerInfo: JSON.parse(localStorage.getItem("customer-info")),
      websocketClient: null,
      commentLikesReactionInfoMap: new Map(),
      commentDislikesReactionInfoMap: new Map(),
    }
  },
  mounted() {
    this.connect();
    this.review = JSON.parse(history.state.review);
    this.reviewLikesInfo = JSON.parse(history.state.reviewLikesInfo);
    this.reviewDislikesInfo = JSON.parse(history.state.reviewDislikesInfo);
    this.getComments(this.review.reviewId);
  },
  beforeUnmount() {
    this.disconnect();
  },
  methods: {
    connect() {
      const serverURL = "/websocket-service/register"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
          {},
          frame => {
            console.log('소켓 연결 성공', frame);
            this.stompClient.subscribe(`/topic/${this.review.reviewId}`, response => {
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
      if (message.type === "COMMENT") {
        this.comments
            .filter(comment => comment.commentId === message.commentId)
            .map(findComment => {
              if (message.likesType === "LIKES") {
                if (message.canceled) {
                  this.commentLikesReactionInfoMap.get(findComment.commentId).reactionCnt -= 1;
                } else {
                  this.commentLikesReactionInfoMap.get(findComment.commentId).reactionCnt += 1;
                }
              } else {
                if (message.canceled) {
                  this.commentDislikesReactionInfoMap.get(findComment.commentId).reactionCnt -= 1;
                } else {
                  this.commentDislikesReactionInfoMap.get(findComment.commentId).reactionCnt += 1;
                }
              }
            });
      }
    },
    disconnect() {
      this.stompClient.disconnect();
    },
    commentLikes(reviewId, commentId) {
      if (this.commentLikesReactionInfoMap.has(commentId)) {
        if (!this.commentLikesReactionInfoMap.get(commentId).hasReacted) {
          apiBoard.postReaction('COMMENT', commentId, 'LIKES').then(() => this.commentLikesReactionInfoMap.get(commentId).hasReacted = true);
        } else {
          apiBoard.postReactionCancel('COMMENT', commentId, 'LIKES').then(() => this.commentLikesReactionInfoMap.get(commentId).hasReacted = false);
        }
      }
    },
    commentDislikes(reviewId, commentId) {
      if (this.commentDislikesReactionInfoMap.has(commentId)) {
        if (!this.commentDislikesReactionInfoMap.get(commentId).hasReacted) {
          apiBoard.postReaction('COMMENT', commentId, 'DISLIKES').then(() => this.commentDislikesReactionInfoMap.get(commentId).hasReacted = true);
        } else {
          apiBoard.postReactionCancel('COMMENT', commentId, 'DISLIKES').then(() => this.commentDislikesReactionInfoMap.get(commentId).hasReacted = false);
        }
      }
    },
    totalCommentReaction(commentId) {
      apiBoard.getReactionCount('COMMENT', commentId, 'LIKES')
          .then((response) => {
            this.commentLikesReactionInfoMap.set(commentId, response.data);
          });
      apiBoard.getReactionCount('COMMENT', commentId, 'DISLIKES')
          .then((response) => {
            this.commentDislikesReactionInfoMap.set(commentId, response.data);
          });
    },
    reviewLikes(reviewId) {
      if (!this.reviewLikesInfo.hasReacted) {
        apiBoard.postReaction('REVIEW', reviewId, 'LIKES').then(() => this.reviewLikesInfo.hasReacted = true);
      } else {
        apiBoard.postReactionCancel('REVIEW', reviewId, 'LIKES').then(() => this.reviewLikesInfo.hasReacted = false);
      }
    },
    reviewDislikes(reviewId) {
      if (!this.reviewDislikesInfo.hasReacted) {
        apiBoard.postReaction('REVIEW', reviewId, 'DISLIKES').then(() => this.reviewDislikesInfo.hasReacted = true);
      } else {
        apiBoard.postReactionCancel('REVIEW', reviewId, 'DISLIKES').then(() => this.reviewDislikesInfo.hasReacted = false);
      }
    },
    getComments(reviewId) {
      apiBoard.getReviewComments(reviewId)
          .then((response) => {
            console.log(response);
            this.comments = response.data.content;
            this.comments.forEach((c) => {
              this.totalCommentReaction(c.commentId)})
          })
    },
    deleteComment(reviewId, commentId) {
      apiBoard.deleteReviewComments(reviewId, commentId)
          .then(() => {
            apiBoard.getReviewComments(reviewId)
                .then((response) => {
                  this.comments= response.data.content;
                });
          });
    },
    dateFormatting(createdAt) {
      return dayjs(createdAt).format('YYYY-MM-DD HH:mm:ss');
    },
    postComment(reviewId) {
      apiBoard.postReviewComments(reviewId, this.commentContent)
          .then(() => {
            this.commentContent = "";
            this.getComments(reviewId)
          });
    },
    back(){
      this.$router.go(-1);
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

.commentArea {
  width: 100%;
}
</style>