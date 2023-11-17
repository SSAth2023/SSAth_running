<template>
  <div class="header">
    <button type="button" class="btn btn-outline-warning btn-lg">Login</button>
    <Searchbar />
  </div>
</template>

<script setup>
import Searchbar from "../search/Searchbar.vue";
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
