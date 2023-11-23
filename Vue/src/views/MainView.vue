<template>
  <div>
    <div v-show="!isLoading">
      <Sidebar v-if="!isLoginView && !isSignupView" />
      <RouterView />
    </div>
    <div class="loading" v-if="isLoading">
      <div class="character img-fluid"></div>
      <p class="fs-1 fw-bolder">Loading...</p>
      <div
        class="progress bar2"
        role="progressbar"
        aria-label="Animated striped example"
        aria-valuenow="20"
        aria-valuemin="0"
        aria-valuemax="100"
      >
        <div
          class="progress-bar progress-bar-striped progress-bar-animated bg-warning text-dark te"
          :style="{ width: progressBarWidth + '%' }"
        >
          신나게 달릴 준비 되셨나요?
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Sidebar from "../components/sidebar/Sidebar.vue";
import { ref, onMounted, watchEffect } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const isLoginView = ref(false);
const isSignupView = ref(false);
const isLoading = ref(true);
const progressBarWidth = ref(0);

onMounted(() => {
  const duration = 1000;
  const startTime = Date.now();

  const updateProgressBar = () => {
    const currentTime = Date.now();
    const elapsed = currentTime - startTime;
    progressBarWidth.value = (elapsed / duration) * 100;

    if (progressBarWidth.value < 120) {
      requestAnimationFrame(updateProgressBar);
    } else {
      isLoading.value = false;
    }
  };

  updateProgressBar();

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

<style scoped>
.loading {
  display: flex;
  margin: 10vh;
  flex-direction: column;
  align-items: center;
}

.character {
  width: 30vw;
  height: 50vh;
  background: url("../assets/image/boy.png") center center no-repeat;
  background-size: cover;
  animation: jump 1s infinite alternate;
  margin-top: 5vh;
}

.bar2 {
  position: fixed;
  z-index: 100;
  left: 25vw;
  bottom: 20vh;
  width: 50vw;
  height: 5vh;
}

.te {
  text-align: center;
  font-size: large;
}

@keyframes jump {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-20px);
  }
}
</style>
