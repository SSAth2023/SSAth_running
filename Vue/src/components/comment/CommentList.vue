<template>
  <div>
    <table class="table table-success table-striped table-bordered table-hover text-center">
      <thead>
        <tr>
          <th scope="col"></th>
          <th scope="col">Writer</th>
          <th scope="col">Content</th>
          <th scope="col">Time</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-for="(comment, index) in currentPageCommentList" :key="comment.commentId">
          <th>{{ index + 1 + (currentPage - 1) * perPage }}</th>
          <th>
            {{ comment.userId }}
          </th>
          <th>
            {{ comment.content
            }}<button type="button" class="btn btn-warning" style="
                    margin: 1px;
                    --bs-btn-padding-y: 0.25rem;
                    --bs-btn-padding-x: 0.5rem;
                    --bs-btn-font-size: 0.75rem;
                  " @click="modeChange(comment.commentId)"
              v-if="comment.userId === userStore.userName['userId'] && !mode">
              수정
            </button>
            <button type="button" class="btn btn-warning" style="
                    margin: 1px;
                    --bs-btn-padding-y: 0.25rem;
                    --bs-btn-padding-x: 0.5rem;
                    --bs-btn-font-size: 0.75rem;
                  " v-if="comment.userId === userStore.userName['userId'] && mode" @click="mode = false">
              취소
            </button>
            <button type="button" class="btn btn-danger" style="
                    margin: 1px;
                    --bs-btn-padding-y: 0.25rem;
                    --bs-btn-padding-x: 0.5rem;
                    --bs-btn-font-size: 0.75rem;
                  " v-if="comment.userId === userStore.userName['userId']" @click="deleteComment(comment.commentId)">
              삭제
            </button>
          </th>
          <th>{{ formatDateTime(comment.comRegDate) }}</th>
        </tr>
      </tbody>
    </table>
    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item">
          <a class="page-link" :class="{ disabled: currentPage <= 1 }" href="#" @click.prevent="currentPage--">&lt;</a>
        </li>
        <li :class="{ cur: currentPage === page }" v-for="page in pageCount" :key="page">
          <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
            page
          }}</a>
        </li>
        <li class="page-item">
          <a class="page-link" :class="{ disabled: currentPage >= pageCount }" href="#"
            @click.prevent="currentPage++">&gt;</a>
        </li>
      </ul>
    </nav>
    <hr />
    <CommentInsertBlock v-if="!mode" />
    <CommentUpdateBlock v-if="mode" />
  </div>
</template>

<script setup>
import { useCommentStore } from "@/stores/comment";
import { onMounted, computed, ref } from "vue";
import CommentInsertBlock from "./CommentInsertBlock.vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "../../stores/user";
import CommentUpdateBlock from "./CommentUpdateBlock.vue";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const store = useCommentStore();
const userStore = useUserStore();
const mode = ref(false);

onMounted(() => {
  store.getCommentList(route.params.mapId);
});

const perPage = 5;

const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.commentList.length / perPage);
});
const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageCommentList = computed(() => {
  return store.commentList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});

const formatDateTime = function (datetime) {
  const date = new Date(datetime);

  if (isNaN(date.getTime())) {
    return "Invalid Date";
  }

  const formattedDateTime = `${date.getFullYear()}-${padZero(
    date.getMonth() + 1
  )}-${padZero(date.getDate())} ${padZero(date.getHours())}:${padZero(
    date.getMinutes()
  )}:${padZero(date.getSeconds())}`;
  return formattedDateTime;
};

const padZero = function (value) {
  return value < 10 ? `0${value}` : value;
};

const modeChange = (commentId) => {
  if (!mode.value) mode.value = true;

  store.comment = store.getComment(commentId);
};

const deleteComment = (commentId) => {
  return new Promise((resolve, reject) => {
    if (confirm("삭제하시겠습니까?")) {
      axios
        .delete(`http://localhost:8080/api/comment/delete/${commentId}`)
        .then(() => {
          resolve(); // 확인일 경우 resolve 호출
        })
        .catch((error) => {
          reject(error); // 삭제 요청 실패 시 reject 호출
        });
    } else {
      reject(); // 취소일 경우 reject 호출
    }
  })
    .then(() => {
      // 삭제 성공 시의 로직
      router.push({
        name: "runningPathDetail",
        params: { mapId: route.params.mapId },
      });
    })
    .catch(() => {
      // 취소 또는 삭제 실패 시의 로직
    });
};
</script>

<style scoped>
.a {
  color: #818181;
}

.a:hover {
  color: #28bb65;
}

.page-link {
  color: #000;
  background-color: #fff;
  border: 1px solid #ccc;
}

.page-link:hover {
  color: #000;
}

.cur a {
  background-color: #9f9f9f40;
}
</style>
