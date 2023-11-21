import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_COMMENT_API = "http://localhost:8080/api/comment";

export const useCommentStore = defineStore("comment", () => {
  const commentList = ref([]);

  const getCommentList = (mapId) => {
    axios
      .get(`${REST_COMMENT_API}/list/${mapId}`)
      .then((res) => {
        commentList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const comment = ref({});

  const getComment = (commentId) => {
    axios.get(`${REST_COMMENT_API}/${commentId}`).then((res) => {
      comment.value = res.data;
    });
  };

  const createComment = (comment) => {
    axios({
      url: `${REST_COMMENT_API}/create`,
      method: "POST",
      header: {
        "Content-Type": "application/json",
      },
      data: comment,
    })
      .then(() => {
        router.push({ name: "comment" });
      })
      .catch(() => {
        alert("error!");
      });
  };

  const updateComment = (commentId) => {
    axios.put(`${REST_COMMENT_API}/update`, comment.value).then(() => {
      commentList.value = commentList.value.map((temp) => {
        if (temp.commentId === commentId) {
          temp.content = comment.value.content;
        }
        return temp;
      });
      console.log("ERROR!!!!");
      router.push({ name: "comment" });
    });
  };

  return {
    commentList,
    getCommentList,
    comment,
    getComment,
    createComment,
    updateComment,
  };
});
