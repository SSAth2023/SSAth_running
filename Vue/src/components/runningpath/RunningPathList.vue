<template>
  <div>
    <h2>path</h2>

    <RouterLink :to="`/path/${mapId}`">상세 눌러라</RouterLink>
    <RouterLink :to="{ name: 'runningPathUpdate' }">수정 눌러라</RouterLink>

    <div
      v-for="runningPath in currentPageRunningPathList"
      :key="runningPath.mapId"
    >
      <h5>hi</h5>
    </div>

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
  </div>
</template>

<script setup>
import { useRunningPathStore } from "../../stores/runningPath";
import { onMounted, computed, ref } from "vue";

const store = useRunningPathStore();

onMounted(() => {
  store.getRunningPathList();
});

const perPage = 5;

const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.runningPathList.length / perPage);
});
const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageRunningPathList = computed(() => {
  return store.runningPathList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});
</script>

<style scoped></style>
