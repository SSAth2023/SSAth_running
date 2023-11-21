<template>
  <div>
    <table
      class="table table-warning table-striped table-bordered table-hover text-center"
    >
      <thead>
        <tr>
          <th scope="col"></th>
          <th scope="col">작성자</th>
          <th scope="col">내용</th>
          <th scope="col">작성 시각</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-for="comment in currentPageCommentList" :key="comment.comment_id">
          <th>{{ num++ }}</th>
          <th>
            {{ comment.userId }}
          </th>
          <th>{{ comment.content }}</th>
          <th>{{ formatDateTime(comment.comRegDate) }}</th>
        </tr>
      </tbody>
    </table>
    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item">
          <a
            class="page-link"
            :class="{ disabled: currentPage <= 1 }"
            href="#"
            @click.prevent="currentPage--"
            >&lt;</a
          >
        </li>
        <li
          :class="{ active: currentPage === page }"
          v-for="page in pageCount"
          :key="page"
        >
          <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
            page
          }}</a>
        </li>
        <li class="page-item">
          <a
            class="page-link"
            :class="{ disabled: currentPage >= pageCount }"
            href="#"
            @click.prevent="currentPage++"
            >&gt;</a
          >
        </li>
      </ul>
    </nav>
    <hr />
    <CommentInsertBlock />
  </div>
</template>

<script setup>
import { useCommentStore } from "@/stores/comment";
import { onMounted, computed, ref } from "vue";
import CommentInsertBlock from "./CommentInsertBlock.vue";
import { useRoute } from "vue-router";

const route = useRoute();
const store = useCommentStore();
const num = 1;

onMounted(() => {
  store.getCommentList(route.params.mapId);
  console.log(store.commentList);
  store.commentList.forEach((comment) => {
    console.log(comment);
  });
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
</script>

<style scoped></style>
