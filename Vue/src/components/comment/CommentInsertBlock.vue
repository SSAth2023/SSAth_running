<template>
  <div class="bar">
    <label>댓글 달기</label>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="comment.content"
        placeholder="댓글을 입력해주세요."
      />
      <button class="btn btn-outline-secondary" @click="createComment()">
        작성
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useCommentStore } from "../../stores/comment";
import { useRoute } from "vue-router";
import { useUserStore } from "../../stores/user";

const store = useCommentStore();
const userStore = useUserStore();
const route = useRoute();

onMounted(() => {
  store.getCommentList(route.params.mapId);
});

const comment = ref({
  comment_id: store.commentList.length + 1,
  map_id: route.params.mapId,
  user_id: userStore.loginUser,
  content: "",
});

const createComment = () => {
  store.createComment(comment.value);
};
</script>

<style scoped>
.bar {
  background-color: white;
  margin: 1vh;
  margin-top: 2vh;
}

.btn {
  background-color: lemonchiffon;
}

label {
  font-family: "LINESeedKR-Bd";
  font-size: small;
}
</style>
