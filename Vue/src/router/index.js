import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import RunningPathList from "../components/runningpath/RunningPathList.vue";
import RunningPathCreate from "../components/runningpath/RunningPathCreate.vue";
import RunningPathDetail from "../components/runningpath/RunningPathDetail.vue";
import RunningPathUpdate from "../components/runningpath/RunningPathUpdate.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/path",
      name: "home",
      component: MainView,
      children: [
        {
          path: "",
          name: "runningPathList",
          component: RunningPathList,
        },
        {
          path: "create",
          name: "runningPathCreate",
          component: RunningPathCreate,
        },
        {
          path: ":mapId",
          name: "runningPathDetail",
          component: RunningPathDetail,
        },
        {
          path: "update",
          name: "runningPathUpdate",
          component: RunningPathUpdate,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupView,
    },
  ],
});

export default router;
