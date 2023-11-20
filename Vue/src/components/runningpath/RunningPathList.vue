<template>
  <div>
    <div class="bg-info-subtle rounded-1">가까운 코스</div>
    <hr />
    <div
      v-for="runningPath in currentPageRunningPathList"
      :key="runningPath.mapId"
    >
      <div class="path">
        <div>
          <RouterLink
            class="text-center text-black fs-3 fw-normal m-0 px-3 py-2"
            :to="`/path/${runningPath.mapId}`"
            >{{ num++ + ". " + runningPath.title }}</RouterLink
          >
          <p class="text-black fs-4 fw-normal m-0 px-3 py-2">
            약 {{ computedDistances[runningPath.mapId] }}km 내,
            {{ (runningPath.distance / 1000).toFixed(2) }}km
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
          <img src="@/assets/image/Favorite.png" />
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
import { onMounted, computed, ref, reactive, watch } from "vue";

const store = useRunningPathStore();

let computedDistances = reactive({});

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");

  if (storedDistances) {
    computedDistances = reactive(JSON.parse(storedDistances));
  } else {
    store.runningPathList.forEach(async (runningPath) => {
      try {
        await tempDist(runningPath.path, runningPath.mapId);
      } catch (error) {
        console.error("거리 계산 중 오류 발생:", error);
      }
    });
  }

  store.getRunningPathList();
});

const num = 1;
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
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const location = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          };
          const tmpPath = location;

          path = "{" + path + "}";
          const selectedPath = JSON.parse(path);

          const distance = getDistance(
            selectedPath.lat,
            selectedPath.lng,
            tmpPath.lat,
            tmpPath.lng
          );

          // computedDistances에 설정
          computedDistances[mapId] = Math.round(distance);

          resolve(distance);

          // 스토리지에 데이터 저장
          localStorage.setItem(
            "computedDistances",
            JSON.stringify(computedDistances)
          );
        },
        (error) => {
          console.error("위치 수집 여부 거절");
          reject(error);
        }
      );
    }
  });
}

watch(computedDistances, (newVal) => {
  localStorage.setItem("computedDistances", JSON.stringify(newVal));
});
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
</style>
