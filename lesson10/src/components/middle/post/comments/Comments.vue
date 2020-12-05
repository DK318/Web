<template>
  <div>
    <Comment v-for="comment in comments" :user="getUser(comment)" :comment="comment.text" :key="comment.id"/>
  </div>
</template>

<script>
import Comment from "@/components/middle/post/comments/comment/Comment";
export default {
  name: "Comments",
  components: {Comment},
  props: ["users", "comments"],
  methods: {
    getUser(comment) {
      return this.users[comment.userId].login;
    }
  }
}
</script>

<style scoped>

</style>