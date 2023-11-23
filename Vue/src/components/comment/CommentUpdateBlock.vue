<template>
  <div class="bar">
    <label>Modify comment</label>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="store.comment.content"
        @keyup.enter="update"
      />
      <button class="btn btn-outline-success" @click="update">완료</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useCommentStore } from "../../stores/comment";
import { useRoute, useRouter } from "vue-router";

const store = useCommentStore();
const route = useRoute();
const router = useRouter();

onMounted(() => {
  store.getCommentList(route.params.mapId);
});

const update = () => {
  return new Promise((resolve, reject) => {
    if (confirm("수정을 완료하시겠습니까?")) {
      store.updateComment(store.comment.commentId);
      resolve(); // 사용자가 확인을 선택한 경우 resolve 호출
    } else {
      reject(); // 사용자가 취소를 선택한 경우 reject 호출
    }
  })
    .then(() => {
      alert("댓글이 수정되었습니다.");
      router.push({
        name: "runningPathDetail",
        params: { mapId: route.params.mapId },
      });
    })
    .catch(() => {
      // 사용자가 취소를 선택하거나 업데이트 실패 시의 로직
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
