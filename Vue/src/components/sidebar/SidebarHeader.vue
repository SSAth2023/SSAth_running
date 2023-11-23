<template>
  <div class="header">
    <div
      v-if="userName !== ''"
      class="welcome-message bg-success-subtle text-black"
    >
      <p class="mb-0">환영합니다, {{ userName.userId }}님!</p>
    </div>
    <div class="button-head">
      <button class="btn" @click="back">b a c k</button>
      <button v-if="userName === ''" type="button" class="btn" @click="login">
        L o g i n
      </button>
      <button v-if="userName !== ''" type="button" class="btn" @click="logout">
        L o g o u t
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../../stores/user";

const router = useRouter();
const userStore = useUserStore();
const userName = computed(() => userStore.userName);

const login = () => {
  return new Promise((resolve, reject) => {
    if (confirm("로그인 화면으로 이동하시겠습니까?")) {
      resolve();
    } else {
      reject();
    }
  })
    .then(() => {
      router.push("/login");
    })
    .catch(() => {});
};

const logout = () => {
  return new Promise((resolve, reject) => {
    if (confirm("로그아웃 하시겠습니까?")) {
      resolve();
    } else {
      reject();
    }
  })
    .then(() => {
      sessionStorage.clear();
      userStore.userName = "";
    })
    .catch(() => {});
};

onMounted(() => {
  if (sessionStorage.getItem("access-token")) {
    const token = sessionStorage.getItem("access-token").split(".");
    userStore.userName = JSON.parse(atob(token[1]));
  }
});

const back = () => {
  router.go(-1);
};
</script>

<style scoped>
.button-head {
  display: flex;
  justify-content: space-between;
  text-align: center;
  margin: 0px 20px 0px 20px;
}
.header {
  background-color: white;
}

.welcome-message {
  padding: 1rem;
  border-radius: 0.25rem;
}

.btn {
  font-size: 18px;
  font-weight: 600;
  color: #818181;
  border: none;
}
.btn:hover {
  color: #28bb65;
}
</style>
