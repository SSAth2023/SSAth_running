<template>
  <div class="header">
    <div v-if="userName !== ''" class="welcome-message bg-info text-white">
      <p class="mb-0">환영합니다, {{ userName.userId }}님!</p>
    </div>

    <button
      v-if="userName === ''"
      type="button"
      class="btn btn-outline-warning btn-lg"
      @click="login"
    >
      Login
    </button>
    <button
      v-if="userName !== ''"
      type="button"
      class="btn btn-outline-warning btn-lg"
      @click="logout"
    >
      Logout
    </button>
    <Searchbar />
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
    .catch(() => {
    });
  
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
    .catch(() => {
    });
};

onMounted(() => {
  if (sessionStorage.getItem("access-token")) {
    const token = sessionStorage.getItem("access-token").split(".");
    userStore.userName = JSON.parse(atob(token[1]));
  }
});
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

.welcome-message {
  padding: 1rem;
  border-radius: 0.25rem;
}
</style>
