import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import CommentView from "../views/CommentView.vue";
import RunningPathCreate from "../components/runningpath/RunningPathCreate.vue";
import RunningPathList from "../components/runningpath/RunningPathList.vue";
import RunningPathDetail from "../components/runningpath/RunningPathDetail.vue";
import RunningPathUpdate from "../components/runningpath/RunningPathUpdate.vue";
import CommentList from "../components/comment/CommentList.vue";
import commentUpdate from "../components/comment/CommentUpdate.vue";
import commentInsertBlock from "../components/comment/CommentInsertBlock.vue";
import AllPath from "@/components/map/AllPath.vue";

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
            default: AllPath,
            sidebar: RunningPathDetail,
          },
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
    {
      path: "/comment",
      name: "comment",
      component: CommentView,
      children: [
        {
          path: "list/:mapid",
          name: "commentList",
          component: CommentList
        },
        {
          path: "create",
          name: "commentCreate",
          component: commentInsertBlock
        },
        {

          path: "update",
          name: "commentUpdate",
          component: commentUpdate
        },
      ]
    },
  ],
});

export default router;
