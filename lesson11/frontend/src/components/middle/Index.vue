<template>
    <div>
        <Post v-for="post in posts" :key="post.id" :post="post" :showComments="false"/>
    </div>
</template>

<script>
import Post from "@/components/middle/post/Post";
export default {
    name: "Index",
  components: {Post},
  props: ["posts"],
}
</script>

<style scoped>

</style>