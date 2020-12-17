<template>
  <div>{{ user }} : {{ comment }}</div>
</template>

<script>
export default {
  name: "Comment",
  props: ["user", "comment"]
}
</script>

<style scoped>

</style>