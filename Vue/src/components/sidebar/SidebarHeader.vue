<template>
  <div class="header">
    <button
      v-if="userName === ''"
      type="button"
      class="btn btn-outline-warning btn-lg"
      @click="login"
    >
      Login
    </button>
    <button
      v-if="userName != ''"
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
// import Searchbar from "../search/Searchbar.vue";
import { computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../../stores/user";

const router = useRouter();
const userStore = useUserStore();
const userName = computed(() => userStore.userName);

const login = () => {
  router.push("login");
};

const logout = () => {
  sessionStorage.clear();
  userStore.userName = "";
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
</style>
