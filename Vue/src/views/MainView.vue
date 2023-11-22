<template>
  <div>
    <Sidebar v-if="!isLoginView && !isSignupView" />
    <RouterView />
  </div>
</template>

<script setup>
import Sidebar from "../components/sidebar/Sidebar.vue";
import { ref, onMounted, watchEffect } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const isLoginView = ref(false);
const isSignupView = ref(false);

onMounted(() => {
  router.push("/path");

  // 초기 화면 설정
  checkLoginView();

  // 화면이 변경될 때마다 체크
  watchEffect(() => {
    checkLoginView();
  });
});

const checkLoginView = () => {
  // 현재 라우트의 이름을 확인하여 로그인 화면 여부 체크
  isLoginView.value = router.currentRoute.value.name === "login";
  isSignupView.value = router.currentRoute.value.name === "signup";
};
</script>

<style scoped></style>
