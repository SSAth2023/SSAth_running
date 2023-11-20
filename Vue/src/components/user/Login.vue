<template>
  <div class="container">
    <h2>로그인</h2>
    <fieldset class="text-center">
      <label for="id">아이디</label>
      <input type="text" id="id" v-model="id" /><br />
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="password" class="view" /><br />
      <button class="btn" @click="login">로그인</button>
      <RouterLink to="/login/signup">회원가입</RouterLink>
    </fieldset>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "../../stores/user";

const userStore = useUserStore();

const id = ref("");
const password = ref("");

const login = () => {
  const user = {
    userId: id.value,
    password: password.value,
  };

  userStore.loginUser(user);
};
</script>

<style></style>
