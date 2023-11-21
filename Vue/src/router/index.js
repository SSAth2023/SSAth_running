import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import CommentView from "../views/CommentView.vue";
import RunningPathCreate from "../components/runningpath/RunningPathCreate.vue";
import RunningPathList from "../components/runningpath/RunningPathList.vue";
import RunningPathDetail from "../components/runningpath/RunningPathDetail.vue";
import UserSignup from "../components/user/Signup.vue";
import Login from "../components/user/Login.vue";
import AllPath from "@/components/map/AllPath.vue";
import DetailPath from "@/components/map/DetailPath.vue";
import UpdatePath from "@/components/map/UpdatePath.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: MainView,
    },
    {
      path: "/path",
      name: "path",
      children: [
        {
          path: "",
          name: "AllPath",
          components: {
            default: AllPath,
            sidebar: RunningPathList,
          },
        },
        {
          path: "create",
          name: "runningPathCreate",
          component: RunningPathCreate,
        },
        {
          path: ":mapId",
          name: "runningPathDetail",
          components: {
            default: DetailPath,
            sidebar: RunningPathDetail,
          },
        },
        {
          path: "update/:mapId",
          name: "runningPathUpdate",
          component: UpdatePath,
        },
      ],
    },
    {
      path: "/login",
      children: [
        {
          path: "",
          name: "login",
          component: Login,
        },
        {
          path: "signup",
          name: "signup",
          component: UserSignup,
        },
      ],
    },
    {
      path: "/comment/:mapId",
      name: "comment",
      components: {
        default: AllPath,
        comment: CommentView,
      },
    },
  ],
});

export default router;
