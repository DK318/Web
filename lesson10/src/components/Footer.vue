<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
        <div class="users-count">Total users: {{ usersCount }}</div>
        <div class="posts-count">Total posts: {{ postsCount }}</div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersCount", "postsCount"]
}
</script>

<style scoped>

</style>