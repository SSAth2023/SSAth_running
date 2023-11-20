<template>
  <div class="header">
    <button type="button" class="btn btn-outline-warning btn-lg" @click="login">
      Login
    </button>
    <Searchbar />
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const login = () => {
  router.push("login");
};
</script>

<style scoped>
.header {
  background-color: white;
}

.btn {
  margin: 1.5vh;
  margin-left: 18vw;
  width: 10vw;
  height: 5vh;
}
</style>
