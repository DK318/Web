<template>
    <div>
        <Post v-for="post in posts" :key="post.id" :post="post" :users="users" :showComments="false" :commentsCount="getCommentCountForCurrentPost(post.id)"/>
    </div>
</template>

<script>
import Post from "@/components/middle/post/Post";
export default {
    name: "Index",
  components: {Post},
  props: ["posts", "users", "commentCount"],
  methods: {
      getCommentCountForCurrentPost: function (id) {
        return this.commentCount.filter(count => count.postId === id);
      }
  }
}
</script>

<style scoped>

</style>