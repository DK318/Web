<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'"/>
            <WritePost v-if="page === 'WritePost'" :user="user"/>
            <Post v-if="page === 'Post'" :post="post" :user="user" :showComments="true"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/users/Users";
import WritePost from "@/components/middle/WritePost";
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
      WritePost,
      Users,
        Register,
        Enter,
        Index,
        Sidebar
    },
    props: ["user", "posts"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);

        this.$root.$on("onChangePost", (post) => {
          this.post = post;
          this.$root.$emit("onChangePage", "Post");
        });
    }
}
</script>

<style scoped>

</style>