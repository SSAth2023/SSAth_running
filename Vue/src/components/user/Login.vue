<template>
  <div class="running-page">
    <div class="head">
      <div class="running-button2 d-flex" @click="back">
        <p>B a c k</p>
      </div>
    </div>
    <div class="running-footer"></div>
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
              <label for="id" class="running-label">ID</label>
              <input type="text" id="id" v-model="id" class="running-input" />
            </div>
            <div class="running-form-group">
              <label for="password" class="running-label">Password</label>
              <input type="password" id="password" v-model="password" class="running-input" />
            </div>
            <button type="submit" class="running-button primary" :disabled="!isFormValid">
              Login
            </button>
            <RouterLink to="/login/signup" class="running-link">Sign Up</RouterLink>
          </form>
        </div>
      </div>
    </div>
    <div class="foot"></div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useUserStore } from "../../stores/user";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const id = ref("");
const password = ref("");

const isFormValid = computed(() => {
  return id.value !== "" && password.value !== "";
});

const login = () => {
  const user = {
    userId: id.value,
    password: password.value,
  };

  userStore.loginUser(user);
};

const back = () => {
  if (
    confirm(
      "이전화면으로 돌아가시겠습니까?\n(지워진 아이디와 비밀번호는 다시 적히지 않습니다.)"
    )
  ) {
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
  background-image: url("../../assets/image/avenue.jpg");
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
  width: 80px;
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
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1.1em;
  font-weight: bold;
  background-color: black;
  transition: background-color 0.3s ease-in-out;
}

.running-button[disabled] {
  width: 100%;
  border: none;
  border-radius: 5px;
  opacity: 0.5;
  cursor: not-allowed;
  font-size: 1.1em;
  font-weight: bold;
  background-color: black;
  transition: background-color 0.3s ease-in-out;
}

.running-button2 {
  width: 10vw;
  height: 5vh;
  padding: 10px;
  margin-top: 2.5vh;
  margin-left: 2vw;
  cursor: pointer;
  font-size: 1.1em;
  font-weight: bold;
  background-color: black;
  z-index: 100;
  color: #fff;
}

.running-button.primary {
  background-color: #28bb65;
  color: #fff;
}

.running-link {
  display: block;
  text-align: center;
  color: #28bb65;
  text-decoration: none;
  font-size: 0.9em;
  margin-top: 10px;
}

.running-footer {
  text-align: right;
  padding: 5px 10px;
  position: fixed;
  z-index: 10;
  width: 10vw;
}

.running-card-content {
  width: 500px;
  background-color: #ffffff;
  position: relative;
  opacity: 0.9;
  z-index: 1;
}

.head {
  background-color: black;
  width: 100vw;
  height: 8vh;
  position: fixed;
  top: 0;
  z-index: 10;
  opacity: 0.8;
}

.foot {
  background-color: black;
  width: 100vw;
  height: 8vh;
  position: fixed;
  bottom: 0;
  z-index: 10;
  opacity: 0.8;
}

.running-button2:hover {
  color: #2ecc71;
  transition: background-color 0.3s ease-in-out;
}
</style>
