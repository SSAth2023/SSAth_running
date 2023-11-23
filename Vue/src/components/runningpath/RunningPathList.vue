<template>
  <div>
    <div class="recommend">R e c o m m e n d</div>

    <div
      v-for="(runningPath, index) in currentPageRunningPathList"
      :key="runningPath.mapId"
    >
      <div class="path-container">
        <div class="path">
          <div class="path-details">
            <RouterLink class="title" :to="`/path/${runningPath.mapId}`">
              {{
                index +
                1 +
                (currentPage - 1) * perPage +
                ". " +
                runningPath.title
              }}
            </RouterLink>
            <p class="distance">
              Start: {{ runningPath.calDist / 1000 }}km
              <span style="margin-left: 20px; margin-right: 20px"></span>Course:
              {{ (runningPath.distance / 1000).toFixed(2) }}km
            </p>
            <p class="description">{{ runningPath.description }}</p>
            <RouterLink
              :to="`/path/${runningPath.mapId}`"
              class="text-black fs-6 fw-normal m-0"
              >m o r e</RouterLink
            >
          </div>
          <div class="favor">
            <i
              style="font-size: 1.5rem"
              class="bi bi-suit-heart-fill"
              :class="{
                like: runningPath.bookmark,
                unlike: !runningPath.bookmark,
              }"
              @click="toggleLike(runningPath)"
            ></i>
            <p class="text-center text-black fs-6 fw-normal m-0">
              {{ runningPath.likes }}
            </p>
          </div>
        </div>
      </div>
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
import { useRouter } from "vue-router";

const store = useRunningPathStore();
const userStore = useUserStore();
const bookmarkStore = useBookmarkStore();
const computedDistances = reactive({});
const router = useRouter();

const toggleLike = function (runningPath) {
  if (userStore.userName == "") {
    router.push({ name: "login" });
  }
  bookmarkStore.getBookmark(runningPath.mapId, userStore.userName["userId"]);
  runningPath.bookmark = !runningPath.bookmark;
};

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");

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
  console.log(store.runningPathList);
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

function tempDist(paths, mapId) {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      const location = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      };
      // console.log(paths);
      const tmpPath = location;
      const selectedPath = JSON.parse(paths);

      const distance = getDistance(
        selectedPath[0].lat,
        selectedPath[0].lng,
        tmpPath.lat,
        tmpPath.lng
      );

      // console.log(distance);

      // computedDistances에 설정
      computedDistances[mapId] = Math.round(distance * 100) / 100;

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
.recommend {
  font-weight: 600;
  margin: 30px 0px 30px 20px;
}
.path-container {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 20px;
  margin-left: 1vw;
  margin-right: 1vw;
}

.path {
  display: flex;
  justify-content: space-between;
}

.path-details {
  flex-grow: 1;
  margin-right: 15px;
}
.text-black:hover {
  color: #28bb65; /* Change to the desired color when hovering */
}

.title {
  color: rgb(90, 90, 90);
  font-size: 18px;
  font-weight: 600;
  letter-spacing: 1.5px;
}

.distance {
  margin-top: 10px;
  color: #28bb65;
}

.description {
  margin-top: 10px;
  color: #333;
}

.favor {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

img {
  width: 3.7425vw;
  height: 6.5104vh;
}

.like {
  color: red;
}
.unlike {
  color: rgb(228, 227, 227);
}

.page-link {
  color: #000;
  background-color: #fff;
  border: 1px solid #ccc;
}
</style>
