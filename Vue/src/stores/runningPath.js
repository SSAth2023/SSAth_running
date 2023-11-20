import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_PATH_API = "http://localhost:8080/api/path";
export const useRunningPathStore = defineStore("runningPath", () => {
  const runningPathList = ref([]);

  const getRunningPathList = async () => {
    try {
      const res = await axios.get(REST_PATH_API);
      runningPathList.value = res.data;
    } catch (err) {
      console.error(err);
    }
  };

  const runningPath = ref({});

  const getRunningPath = async (mapId) => {
    try{
      const res = await axios.get(`${REST_PATH_API}/${mapId}`);
      runningPath.value = res.data;
    } catch (err) {
      console.error(err);
    }
  };

  const createRunningPath = (runningPath) => {
    axios({
      url: `${REST_PATH_API}/create`,
      method: "POST",
      header: {
        "Content-Type": "application/json",
      },
      data: runningPath,
    })
      .then(() => {
        router.push({ name: "home" });
      })
      .catch(() => {
        alert("수정 필요");
      });
  };

  const updateRunningPath = () => {
    axios.put(REST_PATH_API, runningPath.value).then(() => {
      router.push({ name: "runningPathList" });
    });
  };

  const searchRunningPathList = (searchCondition) => {
    axios
      .get(REST_PATH_API, {
        params: searchCondition,
      })
      .then((res) => {
        runningPathList.value = res.data;
      });
  };

  return {
    runningPathList,
    getRunningPathList,
    runningPath,
    getRunningPath,
    createRunningPath,
    updateRunningPath,
    searchRunningPathList,
  };
});
