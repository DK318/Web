<template>
  <article>
    <div class="title"><a href="#" @click.prevent="showPost">{{ post.title }}</a></div>
    <div class="information">By {{ users[post.userId].login }}</div>
    <div class="body">{{ post.text }}</div>
  <div class="footer">
    <div class="left">
      <img src="../../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
      <span class="positive-score">+173</span>
      <img src="../../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
    </div>
    <div class="right">
      <img src="../../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
      <a href="#">{{ commentsCount }}</a>
    </div>
  </div>
    <Comments v-if="showComments" :users="users" :comments="getComments"/>
  </article>
</template>

<script>
import Comments from "@/components/middle/post/comments/Comments";
export default {
  name: "Post",
  components: {Comments},
  props: ["post", "users", "comments", "showComments"],
  computed: {
    commentsCount: function () {
      return Object.values(this.comments).filter(comment => comment.postId === this.post.id).length;
    },
    getComments: function () {
      return Object.values(this.comments).filter(comment => comment.postId === this.post.id);
    }
  },
  methods: {
    showPost() {
      this.$root.$emit("showPost", this.post);
    }
  }
}
</script>

<style scoped>

</style>