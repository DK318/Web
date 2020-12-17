<template>
  <form class="comment-form" @submit.prevent="addComment()">
    Add comment:
    <br/>
    <label>
      <textarea v-model="text"></textarea>
    </label>
    <br/>
    <div class="error">{{ error }}</div>
    <input type="submit" value="Submit"/>
  </form>
</template>

<script>
export default {
  name: "AddComment",
  props: ["postId"],
  data: function () {
    return {
      text: "",
      error: ""
    };
  },
  methods: {
    addComment: function () {
      this.$root.$emit("onAddComment", this.text, this.postId);
    },
  },
  beforeCreate() {
    this.$root.$on("onAddCommentValidationError", error => {
      this.error = error;
    });
  }
}
</script>

<style scoped>
  .error {
    color: red;
    font-size: 0.8rem;
    padding: 0.2rem 0;
  }
</style>