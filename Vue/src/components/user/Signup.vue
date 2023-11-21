<template>
  <div class="container2">
    <img class="logo2" @click="back" src="../../assets/image/S S A T H.png" />
    <div class="card bg-light border border-1 border-black">
      <div class="card-body">
        <h2 class="card-title text-start title">회원 가입</h2>
        <form @submit.prevent="regist">
          <div class="mb-3">
            <label for="id" class="form-label">아이디</label>
            <input type="text" id="id" v-model="id" class="form-control" />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input
              type="password"
              id="password"
              v-model="password"
              class="form-control"
            />
          </div>
          <div class="mb-3">
            <label for="password2" class="form-label">비밀번호 확인</label>
            <input
              type="password"
              id="password2"
              v-model="password2"
              class="form-control"
            />
          </div>
          <div class="mb-3">
            <label for="name" class="form-label">이름</label>
            <input type="text" id="name" v-model="name" class="form-control" />
          </div>
          <div class="mb-3">
            <label for="age" class="form-label">나이</label>
            <input type="number" id="age" v-model="age" class="form-control" />
          </div>
          <button type="submit" class="btn btn-primary">등록</button>
        </form>
      </div>
      <div class="card-footer text-muted text-end">
        <button class="btn btn-outline-secondary" @click="back">이전</button>
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
      router.push("/");
    })
    .catch(() => {});
};
</script>

<style>
form {
  margin-top: 5vh;
}

.container2 {
  margin: 10vh;
}

.logo2 {
  width: 15vw;
  height: 10vh;
  cursor: pointer;
  margin-bottom: 5vh;
  margin-top: 5vh;
}

.up {
  display: flex;
  justify-content: space-between;
}
</style>
