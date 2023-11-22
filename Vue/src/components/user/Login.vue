<template>
  <div class="container">
    <div class="card bg-light border border-1 border-black">
      <div class="card-body">
        <div class="up">
          <h2 class="card-title text-start">로그인</h2>
          <img
            class="logo img-fluid"
            @click="back"
            src="../../assets/image/S S A T H.png"
          />
        </div>
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="id" class="form-label">ID</label>
            <input type="text" id="id" v-model="id" class="form-control" />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input
              type="password"
              id="password"
              v-model="password"
              class="form-control"
            />
          </div>
          <button type="submit" class="btn btn-primary">로그인</button>
          <RouterLink to="/login/signup" class="btn btn-link"
            >회원가입</RouterLink
          >
        </form>
      </div>
      <div class="card-footer text-muted text-end">
        <button class="btn btn-outline-secondary" @click="back">이전</button>
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
  return new Promise((resolve, reject) => {
    if (
      confirm(
        "이전화면으로 돌아가시겠습니까?\n(지워진 아이디와 비밀번호는 다시 적히지 않습니다.)"
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
.container {
  margin-top: 20vh;
}

.logo {
  width: 20vw;
  height: 15vh;
  cursor: pointer;
}

.up {
  display: flex;
  justify-content: space-between;
}
</style>
