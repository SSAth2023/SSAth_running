<template>
  <div class="running-page">
    <div class="head">
      <div class="running-button2 d-flex" @click="back">
        <p>B a c k</p>
      </div>
    </div>
    <div class="running-container">
      <div class="running-card">
        <div class="running-background"></div>
        <div class="running-card-content">
          <div class="running-header">
            <img
              class="running-logo"
              @click="back"
              src="../../assets/image/S S A T H.png"
              alt="Running Logo"
            />
            <h2 class="running-title">회원 가입</h2>
          </div>
          <form @submit.prevent="regist" class="running-form">
            <div class="running-form-group">
              <label for="id" class="running-label">아이디</label>
              <input type="text" id="id" v-model="id" class="running-input" />
            </div>
            <div class="running-form-group">
              <label for="password" class="running-label">비밀번호</label>
              <input
                type="password"
                id="password"
                v-model="password"
                class="running-input"
              />
            </div>
            <div class="running-form-group">
              <label for="password2" class="running-label">비밀번호 확인</label>
              <input
                type="password"
                id="password2"
                v-model="password2"
                class="running-input"
              />
            </div>
            <div class="running-form-group">
              <label for="name" class="running-label">이름</label>
              <input
                type="text"
                id="name"
                v-model="name"
                class="running-input"
              />
            </div>
            <div class="running-form-group">
              <label for="age" class="running-label">나이</label>
              <input
                type="number"
                id="age"
                v-model="age"
                class="running-input"
              />
            </div>
            <button
              type="submit"
              class="running-button primary"
              :disabled="!isFormValid"
            >
              등록
            </button>
          </form>
        </div>
      </div>
    </div>
    <div class="foot"></div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const id = ref("");
const password = ref("");
const password2 = ref("");
const name = ref("");
const age = ref(null);
const users = computed(() => userStore.users);

onMounted(() => {
  userStore.setUsers();
});

const isFormValid = computed(() => {
  // 필요한 경우 다른 유효성 검사 로직을 추가하세요
  return (
    id.value !== "" &&
    password.value !== "" &&
    password2.value !== "" &&
    name.value !== "" &&
    age.value !== null
  );
});

const isPasswordValid = (pwd) => {
  return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

const regist = () => {
  if (
    id.value === "" ||
    password.value === "" ||
    name.value === "" ||
    age.value === ""
  ) {
    alert("모든 내용을 입력해주세요");
    return;
  }

  if (password.value !== password2.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  if (!isPasswordValid(password.value)) {
    alert("비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.");
    return;
  }

  if (Array.isArray(users.value) && users.value.length > 0) {
    const userExists = users.value.some((user) => user.userId === id.value);

    if (userExists) {
      alert("이미 존재하는 아이디입니다.");
      return;
    }
  }

  const user = {
    userId: id.value,
    password: password.value,
    name: name.value,
    age: age.value,
  };
  console.log(user);
  userStore.signup(user);
};

const back = () => {
  return new Promise((resolve, reject) => {
    if (
      confirm(
        "이전화면으로 돌아가시겠습니까?\n(작성한 정보는 저장되지 않습니다.)"
      )
    ) {
      resolve();
    } else {
      reject();
    }
  })
    .then(() => {
      router.go(-1);
    })
    .catch(() => {});
};
</script>

<style>
.running-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("../../assets/image/forest.jpg");
  background-size: cover;
  background-position: center;
  opacity: 0.5;
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
  max-width: 800px;
}

.running-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
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
  height: 35px;
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1em;
}

.running-button {
  height: 35px;
  width: 100%;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1em;
  font-weight: bold;
  transition: background-color 0.3s ease-in-out;
}

.running-button[disabled] {
  height: 35px;
  width: 100%;
  border: none;
  border-radius: 5px;
  opacity: 0.5; /* 비활성화된 상태에는 투명도를 적용하여 시각적으로 구분 */
  cursor: not-allowed;
  font-size: 1em;
  font-weight: bold;
  transition: background-color 0.3s ease-in-out;
}

.running-button.primary {
  background-color: #28bb65;
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

.running-button2:hover {
  color: #2ecc71; /* 바꿀 색상으로 수정하세요 */
  transition: background-color 0.3s ease-in-out;
}
</style>
