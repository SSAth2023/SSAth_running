import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_COMMENT_API = "http://localhost:8080/api/bookmark";

export const useBookmarkStore = defineStore("bookmark", () => {
  const checkBookmark = (mapId, userId) => {
    console.log(mapId, userId);
    axios.get(`${REST_COMMENT_API}/check/${mapId}/${userId}`).then((res) => {
      console.log(res.data);
      return res.data;
    });
  };

  const bookmark = ref(0);
  const getBookmark = (mapId, userId) => {
    axios.get(`${REST_COMMENT_API}/${mapId}/${userId}`).then((res) => {
      bookmark.value = res.data;
      return res.data;
    });
  };

  const bookmarkCnt = ref(0);
  const getBookmarkCnt = (mapId) => {
    axios.get(`${REST_COMMENT_API}/list/${mapId}`).then((res) => {
      bookmarkCnt.value = res.data;
    });
  };

  return {
    checkBookmark,
    bookmark,
    getBookmark,
    bookmarkCnt,
    getBookmarkCnt,
  };
});
