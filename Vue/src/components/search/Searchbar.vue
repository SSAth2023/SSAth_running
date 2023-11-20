<template>
  <div class="bar">
    <label>러닝 경로 검색</label>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="searchInput"
        placeholder="검색하고 싶은 곳은?"
      />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.bar {
  background-color: white;
  margin: 1vh;
  margin-top: 2vh;
}

.btn {
  background-color: lemonchiffon;
}

label {
  font-family: "LINESeedKR-Bd";
  font-size: small;
}
</style>
