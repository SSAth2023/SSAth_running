<template>
  <div class="running-page">
    <div class="running-container">
      <div class="running-card">
        <div class="running-background"></div>
        <div class="running-card-content">
          <div class="running-header">
            <img class="running-logo" @click="back" src="../../assets/image/S S A T H.png" alt="Running Logo" />
            <h2 class="running-title">KEEP GOING</h2>
          </div>
          <form @submit.prevent="login" class="running-form">
            <div class="running-form-group">
              <label for="id" class="running-label">아이디</label>
              <input type="text" id="id" v-model="id" class="running-input" />
            </div>
            <div class="running-form-group">
              <label for="password" class="running-label">비밀번호</label>
              <input type="password" id="password" v-model="password" class="running-input" />
            </div>
            <button type="submit" class="running-button primary">로그인</button>
            <RouterLink to="/login/signup" class="running-link">회원가입</RouterLink>
          </form>
          <div class="running-footer">
            <button class="running-button secondary" @click="back">이전</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "../../stores/user";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const id = ref("");
const password = ref("");

const login = () => {
  const user = {
    userId: id.value,
    password: password.value,
  };

  userStore.loginUser(user);
};

const back = () => {
  if (confirm("이전화면으로 돌아가시겠습니까?\n(지워진 아이디와 비밀번호는 다시 적히지 않습니다.)")) {
    router.push("/");
  }
};
</script>

<style scoped>
.running-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('https://img.freepik.com/free-photo/handsome-young-sportsman-running-at-the-beach_171337-16733.jpg?size=626&ext=jpg&ga=GA1.1.496225047.1700671491&semt=sph');
  /* 이미지 경로를 수정하세요 */
  background-size: cover;
  background-position: center;
  opacity: 0.7;
  /* 배경의 투명도를 조절하세요 */
  z-index: 0;
}

.running-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.running-container {
  max-width: 1000px;
}

.running-card {
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  z-index: 2;
}

.running-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
}

.running-logo {
  width: 60px;
  height: auto;
  cursor: pointer;
}

.running-title {
  font-size: 1.5em;
  margin-left: 20px;
  color: #2c3e50;
  opacity: 0.7;
}

.running-form {
  padding: 20px;
}

.running-form-group {
  margin-bottom: 20px;
}

.running-label {
  display: block;
  font-size: 0.9em;
  margin-bottom: 5px;
  color: #555;
}

.running-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1em;
}

.running-button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1.1em;
  font-weight: bold;
  transition: background-color 0.3s ease-in-out;
}

.running-button.primary {
  background-color: #3498db;
  color: #fff;
}

.running-button.secondary {
  background-color: #95a5a6;
  color: #fff;
}

.running-link {
  display: block;
  text-align: center;
  color: #3498db;
  text-decoration: none;
  font-size: 0.9em;
  margin-top: 10px;
}

.running-footer {
  text-align: right;
  padding: 10px 20px;
}

.running-card-content {
  width: 500px;
  background-color: #ffffff;
  position: relative;
  opacity: 0.9;
  z-index: 1;
}
</style>
