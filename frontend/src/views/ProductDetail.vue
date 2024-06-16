<template>
  <div class="container">

    <div class="black-bg" v-if="modalState">
      <div class="white-bg">
        <h4>수량</h4>
        <input type="number" class="form-control" id="quantity" name="quantity" v-model="quantity"/>
        <button @click="orders(product.productId, product.price, quantity)">주문하기</button>
      </div>
    </div>

    <h2>상품 상세</h2>
    <table class="table">
      <tbody>
      <tr>
        <td>상품번호</td>
        <td v-if="product!=null">{{ product.productId }}</td>
      </tr>
      <tr>
        <td>이름</td>
        <td v-if="product!=null">{{ product.name }}</td>
      </tr>
      <tr>
        <td>수량</td>
        <td v-if="product!=null">{{ product.stock }}</td>
      </tr>
      <tr>
        <td>가격</td>
        <td v-if="product!=null">{{ product.price }}</td>
      </tr>
      <tr>
        <td>좋아요</td>
        <td v-if="product!=null">{{ product.likes }}</td>
        <button @click="likes(product.productId)">좋아요</button>
      </tr>
      </tbody>
    </table>
    <button @click="addCart(product)">담기</button>
    <button @click="modalState=!modalState">주문하기</button>
    <div>
      <a class="btn btn-dark" href="/" role="button">첫 화면으로 이동하기</a>
    </div>

    <br/>

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
            <span class="date">{{ dateFormatting(review.createdAt) }}</span>
            <p>{{ review.contents }}</p>
            <div class="reviewLikes" style="display: inline;">
              <img src="../assets/thumbsup.png" width="16" height="16" style="display: inline; position: relative; left: 4px;">
              <span style="display: inline; position: relative;left: 10px;color: red">0</span>
            </div>

            <div class="reviewDislikes" style="display: inline;">
              <img src="../assets/thumbsdown.png" width="16" height="16" style="display: inline; position: relative; left: 24px;">
              <span style="display: inline; position: relative;left: 30px;color: blue">0</span>
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
                    <span class="date">{{ dateFormatting(comment.createdAt) }}</span>
                    <p>{{ comment.contents }}</p>

                    <div class="commentLikes" style="display: inline;" @click="commentLikes(review.reviewId, comment.commentId)">
                      <img src="../assets/thumbsup.png" width="16" height="16" style="display: inline; position: relative; left: 4px;">
                           <span style="display: inline; position: relative;left: 10px;color: red">{{comment.likesCount}}</span>
                    </div>

                    <div class="commentDislikes" style="display: inline;" @click="commentDislikes(review.reviewId, comment.commentId)">
                      <img src="../assets/thumbsdown.png" width="16" height="16" style="display: inline; position: relative; left: 24px;">
                      <span style="display: inline; position: relative;left: 30px;color: blue">{{comment.dislikesCount}}</span>
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
      <button class="btn btn-dark" @click="postReview(product.productId)">등록</button>

    </div>


  </div>
</template>

<script>
import apiBoard from '@/api/board'
import dayjs from "dayjs";

export default {
  data() {
    return {
      productId: null,
      product: null,
      modalState: false,
      quantity: null,
      reviewsNumberOfElements: 0,
      reviews: null,
      reviewContent: null,
      commentContent: null,
      commentsMap: new Map(),
      commentStateMap: new Map(),
      customerInfo: JSON.parse(localStorage.getItem("customer-info"))
    }
  },
  mounted() {
    this.productId = this.$route.params.id;
    apiBoard.getProductDetails(this.productId)
        .then((response) => {
          console.log(response);
          this.product = response.data.response;
        })
        .catch(function (e) {
          console.log(e);
        });
    this.getReview(this.productId);
  },
  methods: {
    commentLikes(reviewId, commentId){
      apiBoard.postCommentLikes(reviewId, commentId)
          .then((response) => {
            console.log(response);
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    commentDislikes(reviewId, commentId){
      apiBoard.postCommentDislikes(reviewId, commentId)
          .then((response) => {
            console.log(response);
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    getReview(productId) {
      apiBoard.getProductReviews(productId)
          .then((response) => {
            console.log(response);
            this.reviews = response.data.content;
            this.reviewsNumberOfElements = response.data.numberOfElements;
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    deleteReview(reviewId) {
      apiBoard.deleteProductReviews(reviewId)
          .then((response) => {
            console.log(response)
            this.getReview(this.productId);
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    deleteComment(reviewId, commentId) {
      apiBoard.deleteReviewComments(reviewId, commentId)
          .then((response) => {
            console.log(response)
            apiBoard.getReviewComments(reviewId)
                .then((response) => {
                  console.log(response);
                  this.commentsMap.set(reviewId, response.data.content);
                })
                .catch(function (e) {
                  console.log(e);
                });
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    dateFormatting(date) {
      return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
    }
    ,
    orders(productId, price, quantity) {
      this.modalState = false;

      this.$router.push({
        name: 'orderConfirm',
        state: {orderProduct: [{productId: productId, price: price, quantity: quantity}]}
      })
    },
    addCart(product) {
      this.$store.dispatch("addCart", product);
    },
    likes(productId) {
      apiBoard.postLikesProduct(productId)
          .then((response) => {
            console.log(response);
            apiBoard.getProductDetails(productId)
                .then((response) => {
                  console.log(response);
                  this.product = response.data.response;
                })
                .catch(function (e) {
                  console.log(e);
                });
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    postReview(productId) {
      apiBoard.postProductReviews(productId, this.reviewContent)
          .then((response) => {
            console.log(response);
            this.reviewContent = "";
            this.getReview(productId);
          })
          .catch(function (e) {
            console.log(e);
          });
    },
    postComment(reviewId) {
      apiBoard.postReviewComments(reviewId, this.commentContent)
          .then((response) => {
            console.log(response);
            this.commentContent = "";
            apiBoard.getReviewComments(reviewId)
                .then((response) => {
                  console.log(response);
                  this.commentsMap.set(reviewId, response.data.content);
                })
                .catch(function (e) {
                  console.log(e);
                });
          })
          .catch(function (e) {
            console.log(e);
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
              console.log(response);
              this.commentsMap.set(reviewId, response.data.content);
            })
            .catch(function (e) {
              console.log(e);
            });
      }
    }
  }

}
</script>

<style>
.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: relative;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

.comment-list {
  margin-bottom: 60px;
  border-top: 1px solid #eee;
}

ul li {
  list-style: none;
}

li {
  text-align: -webkit-match-parent;
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