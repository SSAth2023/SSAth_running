import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useRouter } from "vue-router";
import axios from "axios"

export const useRunningPathStore = defineStore("runningPath", () => {

  const REST_RUNNING_API = 'http://localhost:8080/api/path'

  const runningPath = ref([])

  const createRunningPath = ((runningPath) => {
    axios({
      url: `${REST_RUNNING_API}/create`,
      method: "POST",
      header: {
        "Content-Type": "application/json",
      },
      data: runningPath
    })
      .then(() => {
        routerKey.push({ name: "home" })
      })
      .catch(() => {
        alert("수정 필요");
      });
  })

  return { runningPath, createRunningPath };
});
