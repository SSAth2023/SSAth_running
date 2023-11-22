<template>
  <div>
    <div class="bg-info-subtle rounded-1">가까운 코스</div>
    <hr />
    <div
      v-for="(runningPath, index) in currentPageRunningPathList"
      :key="runningPath.mapId"
    >
      >
      <div class="path">
        <div>
          <RouterLink
            class="text-center text-black fs-3 fw-normal m-0 px-3 py-2"
            :to="`/path/${runningPath.mapId}`"
            >{{
              index + 1 + (currentPage - 1) * perPage + ". " + runningPath.title
            }}</RouterLink
          >
          <p class="text-black fs-4 fw-normal m-0 px-3 py-2">
            약 {{ computedDistances[runningPath.mapId] }}km 내,
            {{ (runningPath.distance / 1000).toFixed(2) }}km 코스
          </p>
          <p class="text-black fs-6 fw-normal m-0 px-3 py-2">
            {{ runningPath.description }}
          </p>
          <RouterLink
            :to="`/path/${runningPath.mapId}`"
            class="text-black fs-6 fw-normal m-0 px-3 py-2"
            >댓글 보기</RouterLink
          >
        </div>
        <div class="favor">
          <i
            style="font-size: 27px"
            class="bi bi-suit-heart"
            @click="like(runningPath.mapId, userStore.userName['userId'])"
          ></i>
          <p class="text-center text-black fs-6 fw-normal m-0 px-3 py-2">13</p>
        </div>
      </div>
      <hr />
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
import { useUserStore } from "../../stores/user";
import { useBookmarkStore } from "../../stores/bookmark";
import { onMounted, computed, ref, reactive, watch } from "vue";

const store = useRunningPathStore();
const userStore = useUserStore();
const bookmarkStore = useBookmarkStore();
const computedDistances = reactive({});

const like = function (mapId, userId) {
  console.log(mapId, userId);
  bookmarkStore.getBookmark(mapId, userId);
};

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");

  // store.getRunningPathList();

  if (storedDistances) {
    computedDistances.value = reactive(JSON.parse(storedDistances));
    localStorage.setItem(
      "computedDistances",
      JSON.stringify(computedDistances.value)
    );
  } else {
    // store.getRunningPathList();
  }
});
console.log(store.runningPathList);
watch(
  () => store.runningPathList,
  (newRunningPathList) => {
    newRunningPathList.forEach((runningPath) => {
      tempDist(runningPath.path, runningPath.mapId);
    });
  }
);

watch(computedDistances, (newVal) => {
  localStorage.setItem("computedDistances", JSON.stringify(newVal));
});

const perPage = 3;

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

function getDistance(lat1, lon1, lat2, lon2) {
  const R = 6371;

  // 라디안 변환
  const dLat = toRadians(lat2 - lat1);
  const dLon = toRadians(lon2 - lon1);
  const lat1Rad = toRadians(lat1);
  const lat2Rad = toRadians(lat2);

  // 히어법 공식 적용
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.sin(dLon / 2) *
      Math.sin(dLon / 2) *
      Math.cos(lat1Rad) *
      Math.cos(lat2Rad);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  const distance = R * c;

  return distance;
}

function toRadians(degrees) {
  return degrees * (Math.PI / 180);
}

function tempDist(path, mapId) {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      const location = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      };

      const tmpPath = location;
      const selectedPath = JSON.parse(path);

      const distance = getDistance(
        selectedPath[0].lat,
        selectedPath[0].lng,
        tmpPath.lat,
        tmpPath.lng
      );

      // computedDistances에 설정
      computedDistances[mapId] = Math.round(distance);

      // 스토리지에 데이터 저장
      localStorage.setItem(
        "computedDistances",
        JSON.stringify(computedDistances)
      );
    });
  }
}
</script>

<style scoped>
.bg-info-subtle {
  width: 7vw;
  margin: 2vh;
  font-family: "LINESeedKR-Bd";
  font-size: larger;
  text-align: center;
}

.path {
  display: flex;
  margin-bottom: 2vh;
}
.favor {
  display: flex;
}

img {
  width: 3.7425vw;
  height: 6.5104vh;
}

.like {
  color: darkred;
}
</style>
