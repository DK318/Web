<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="postsReverseOrder" :users="users" :commentCount="getCommentCount"/>
            <Enter v-if="page === 'Enter'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :post="post" :users="users" :comments="getComments()" :showComments="true" :commentsCount="countPostComments()"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/users/Users";
import Post from "@/components/middle/post/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
      Post,
      Users,
      Register,
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost
    },
    props: ["users", "posts"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        postsReverseOrder: function () {
          return Object.values(this.posts).sort((a, b) => b.id - a.id);
        },
        getCommentCount: function () {
          return this.$root.$data.getCommentCount();
        }
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("showPost", (post) => {
          this.post = post;
          this.page = "Post";
        })
    },
    methods: {
      countPostComments: function () {
        return this.$root.$data.getCommentCount().filter(count => this.post.id === count.postId);
      },
      getComments: function () {
        return this.$root.$data.comments;
      }
    }
}
</script>

<style scoped>

</style>