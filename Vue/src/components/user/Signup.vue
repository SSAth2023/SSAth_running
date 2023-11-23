<template>
  <div class="running-page">
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
            <button type="submit" class="running-button primary">등록</button>
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
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const id = ref("");
const password = ref("");
const password2 = ref("");
const name = ref("");
const age = ref(0);
const users = computed(() => userStore.users);

onMounted(() => {
  userStore.setUsers();
});

const isPasswordValid = (pwd) => {
  return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

const onlyNumberAndEnglish = (str) => {
  return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
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

  if (!onlyNumberAndEnglish(id)) {
    alert("아이디는 숫자 혹은 영어로만 입력되어야 합니다.");
  }

  if (id)
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
      router.push("/");
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
  background-image: url("https://img.freepik.com/free-photo/sunset-on-the-aegean-sea-coast-ship-and-land-in-the-distance-water-greece_1268-16901.jpg?w=1380&t=st=1700672640~exp=1700673240~hmac=e9f88b9c5e62170f068b88bc803747758821ec036211ee225d5868e3d18405de");
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
