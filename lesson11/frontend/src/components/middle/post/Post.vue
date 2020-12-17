<template>
  <article>
    <div class="title"><a href="#" @click.prevent="changePost()">{{ post.title }}</a></div>
    <div class="information">By {{ post.user.login }}</div>
    <div class="body">{{ post.text }}</div>
  <div class="footer">
    <div class="left">
      <img src="../../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
      <span class="positive-score">+173</span>
      <img src="../../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
    </div>
    <div class="right">
      <img src="../../../assets/img/date_16x16.png" title="Date" alt="Date"/>
      {{ date }}
      <img src="../../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
      <a href="#">{{ commentsCount }}</a>
    </div>
  </div>
  <div class="comment-section" v-if="showComments">
    <AddComment v-if="user" :postId="post.id"/>
    <Comments :comments="post.comments"/>
  </div>
  </article>
</template>

<script>
import Comments from "@/components/middle/post/comments/Comments";
import AddComment from "@/components/middle/post/comments/AddComment";
export default {
  name: "Post",
  components: {AddComment, Comments},
  props: ["post", "showComments", "user"],
  computed: {
    date: function () {
      const postDate = new Date(this.post.creationTime);

      let dd = postDate.getDate();
      if (dd < 10) {
        dd = '0' + dd;
      }

      let mm = postDate.getMonth() + 1;
      if (mm < 10) {
        mm = '0' + mm;
      }

      let yy = postDate.getFullYear() % 100;
      if (yy < 10) {
        yy = '0' + yy;
      }

      return dd + '.' + mm + '.' + yy;
    },
    commentsCount: function () {
      return this.post.comments.length;
    }
  },
  methods: {
      changePost: function () {
          this.$root.$emit("onChangePost", this.post);
      }
  }
}
</script>

<style scoped>
</style>