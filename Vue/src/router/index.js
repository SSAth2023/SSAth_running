import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import AllPath from "@/components/map/AllPath.vue"
import createRunningPath from "@/components/map/CreateMap.vue";

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
          name: "AllPath",
          component: AllPath
        },
        {
          path: "create",
          name: "CreatePath",
          component: createRunningPath
        }
      ]
    },
  ],
});

export default router;
