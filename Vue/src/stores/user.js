import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

export const useUserStore = defineStore("user", () => {
  const REST_USER_API = "http://localhost:8080/api/user";
  const router = useRouter();

  //특정 유저 정보 출력
  const user = ref({});
  const setUser = (id) => {
    axios.get(`${REST_USER_API}/${id}`).then((res) => {
      user.value = res.data;
    });
  };

  //모든 유저 정보 출력
  const users = ref([]);
  const setUsers = function () {
    axios.get(`${REST_USER_API}/list`).then((res) => {
      users.value = res.data;
    });
  };

  //회원 가입
  const signup = function (user) {
    axios({
      url: REST_USER_API,
      method: "POST",
      header: {
        "Content-Type": "application/json",
      },
      data: user,
    })
      .then(() => {
        router.push({ name: "login" });
      })
      .catch(() => {
        alert("수정 필요");
      });
  };

  //회원 탈퇴
  const deleteUser = function (id) {
    axios
      .delete(`${REST_USER_API}/delete/${id}`)
      .then(() => {
        router.push({ name: "login" });
      })
      .catch(() => {
        alert("수정필요");
      });
  };

  //로그인
  const loginUser = function (user) {
    axios({
      url: REST_USER_API,
      method: "POST",
      header: {
        "Content-Type": "application/json",
      },
      data: user,
    })
      .then(() => {
        router.push({ name: "path" });
      })
      .catch(() => {
        alert("수정필요");
      });
  };

  //로그아웃
  const logoutUser = function () {
    axios
      .get(`${REST_USER_API}/logout`)
      .then(() => {
        router.push({ name: "path" });
      })
      .catch(() => {
        alert("수정필요");
      });
  };

  return {
    user,
    setUser,
    users,
    setUsers,
    signup,
    deleteUser,
    loginUser,
    logoutUser,
  };
});
