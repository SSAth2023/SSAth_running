<template>
  <div class="header">
    <button v-if="userName === ''" type="button" class="btn btn-outline-warning btn-lg" @click="login">
      Login
    </button>
    <p v-if="userName != ''">{{ userName["name"] }}님 안녕하세요.</p>
    <Searchbar />
  </div>
</template>

<script setup>
// import Searchbar from "../search/Searchbar.vue";
import { computed } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../../stores/user";

const userStore = useUserStore();
const router = useRouter();
const userName = computed(() => userStore.userName);

const login = () => {
  router.push("login");
};


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
