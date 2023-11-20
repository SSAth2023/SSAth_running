<template>
  <div class="container">
    <h2>회원 가입</h2>
    <fieldset class="text-center">
      <label for="id">아이디</label>
      <input type="text" id="id" v-model="id" class="view" /><br />
      <label for="password">비밀번호</label>
      <input type="password" id="password" v-model="password" class="view" /><br />
      <label for="password">비밀번호 확인</label>
      <input type="password" id="password2" v-model="password2" class="view" /><br />
      <label for="name">이름</label>
      <input type="text" id="name" v-model="name" class="view" /><br />
      <label for="age">나이</label>
      <input type="number" id="age" v-model="age" class="view" /><br />
      <button class="btn" @click="regist">등록</button>
    </fieldset>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

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
    console.log(userExists)
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
</script>
