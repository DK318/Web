<template>
    <div>
        <Post v-for="post in posts" :key="post.id" :post="post" :users="users" :comments="comments" :showComments="false"/>
    </div>
</template>

<script>
import Post from "@/components/middle/post/Post";
export default {
    name: "Index",
  components: {Post},
  props: ["posts", "users", "comments"],
}
</script>

<style scoped>

</style>