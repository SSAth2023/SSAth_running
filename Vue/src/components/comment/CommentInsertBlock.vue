<template>
  <div class="bar">
    <label>Comment</label>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="comment.content"
        @keyup.enter="submit"
        placeholder="Insert your comment..."
      />
      <button class="btn btn-outline-success" @click="submit">submit</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useCommentStore } from "../../stores/comment";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "../../stores/user";

const store = useCommentStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

onMounted(() => {
  store.getCommentList(route.params.mapId);
});

const comment = ref({
  mapId: route.params.mapId,
  userId: userStore.userName["userId"],
  content: "",
});

const submit = () => {
  return new Promise((resolve, reject) => {
    if (confirm("댓글을 등록하시겠습니까?")) {
      store.createComment(comment.value);
      resolve(); // 사용자가 확인을 선택한 경우 resolve 호출
    } else {
      reject(); // 사용자가 취소를 선택한 경우 reject 호출
    }
  })
    .then(() => {
      alert("댓글이 등록되었습니다.");
      router.push({
        name: "runningPathDetail",
        params: { mapId: route.params.mapId },
      });
    })
    .catch(() => {
      // 사용자가 취소를 선택한 경우 또는 댓글 등록 실패 시의 로직
    });
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
