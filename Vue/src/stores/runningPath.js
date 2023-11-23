import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_PATH_API = "http://localhost:8080/api/path";
export const useRunningPathStore = defineStore("runningPath", () => {
  const runningPathList = ref([]);

  const getRunningPathList = async (location) => {
    console.log(location);
    console.log(location.value);
    try {
      const res = await axios.post(REST_PATH_API, location.value);
      runningPathList.value = res.data;
    } catch (err) {
      console.error(err);
    }
  };

  const runningPath = ref({});

  const getRunningPath = async (path) => {
    try {
      const res = await axios.post(`${REST_PATH_API}/${path.mapId}`, path);
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

  const updateRunningPath = (runningPath) => {
    console.log(runningPath);
    axios.put(`${REST_PATH_API}/update`, runningPath).then(() => {
      router.push({ name: "home" });
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

  const bookmarkedRunningPath = ref([]);
  const getBookmakredRunningPath = async (userId) => {
    console.log(userId.value);
    try {
      const res = await axios.post(`${REST_PATH_API}/bookmark`, userId.value);
      runningPath.value = res.data;
      bookmarkedRunningPath.value = res.data
      console.log(runningPath.value);
    } catch (err) {
      console.error(err);
    }
  };

  return {
    runningPathList,
    getRunningPathList,
    runningPath,
    getRunningPath,
    createRunningPath,
    updateRunningPath,
    searchRunningPathList,
    bookmarkedRunningPath,
    getBookmakredRunningPath,
  };
});
