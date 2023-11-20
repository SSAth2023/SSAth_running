<template>
  <div class="bar">
    <label>댓글 달기</label>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="searchInput"
        placeholder="댓글을 입력해주세요."
      />
      <button class="btn btn-outline-secondary" @click="search">작성</button>
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
