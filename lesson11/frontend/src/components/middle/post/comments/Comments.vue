<template>
  <div>
    <Comment v-for="comment in comments" :user="comment.user.login" :comment="comment.text" :key="comment.id"/>
  </div>
</template>

<script>
import Comment from "@/components/middle/post/comments/comment/Comment";
export default {
  name: "Comments",
  components: {Comment},
  props: ["comments"]
}
</script>

<style scoped>

</style>