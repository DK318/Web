<template>
  <div class="enter form-box">
    <div class="header">Enter</div>
    <div class="body">
      <form @submit.prevent="onRegister">
        <div class="field">
          <div class="name">
            <label for="login">Login</label>
          </div>
          <div class="value">
            <input autofocus id="login" name="login" v-model="login"/>
          </div>
        </div>
        <div class="field">
          <div class="name">
            <label for="user-name">Name</label>
          </div>
          <div class="value">
            <input id="user-name" name="user-name" v-model="name"/>
          </div>
        </div>
        <div class="field error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Enter">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data: function () {
    return {
      login: "",
      name: "",
      error: ""
    }
  },
  methods: {
    onRegister: function () {
      this.$root.$emit("onRegister", this.login, this.name);
    }
  },
  beforeCreate() {
    this.$root.$on("onRegisterValidationError", (error) => this.error = error);
  }
}
</script>

<style scoped>

</style>