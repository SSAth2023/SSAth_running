import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_COMMENT_API = "http://localhost:8080/api/bookmark";

export const useBookmarkStore = defineStore("bookmark", () => {
  
  const bookmark = ref(0);
  const getBookmark = (mapId, userId) => {
    axios.get(`${REST_COMMENT_API}/${mapId}/${userId}`).then((res) => {
      bookmark.value = res.data;
    });
  };

  const bookmarkCnt = ref(0);
  const getBookmarkCnt = (mapId) => {
    axios.get(`${REST_COMMENT_API}/list/${mapId}`).then((res) => {
      bookmarkCnt.value = res.data;
    });
  };

  return {
    bookmark,
    getBookmark,
    bookmarkCnt,
    getBookmarkCnt,
  };
});
