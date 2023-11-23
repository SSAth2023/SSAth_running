<template>
  <div class="sidebar">
    <div class="path">
      <div>
        <div class="favor">
          <div class="bg-primary bg-gradient rounded-1">
            {{ store.runningPath.title }}
          </div>
          <div>
            <div class="favor">
              <i
                style="font-size: 1.5rem"
                class="bi bi-suit-heart-fill"
                :class="{
                  like: store.runningPath.bookmark,
                  unlike: !store.runningPath.bookmark,
                }"
                @click="toggleLike(store.runningPath)"
              ></i>
              <p class="text-center text-black fs-6 fw-normal m-0">
                {{ store.runningPath.likes }}
              </p>
            </div>
          </div>
        </div>
        <p class="text-black fs-4 fw-normal m-0 px-3 py-2">
          총 {{ (store.runningPath.distance / 1000).toFixed(2) }}km 코스
        </p>
        <p class="text-end text-black fs-5 fw-normal m-0 px-3 py-2">
          등록인: {{ store.runningPath.userId }}
        </p>
        <p class="text-end text-black fs-5 fw-normal m-0 px-3 py-2">
          등록 날짜: {{ store.runningPath.regDate }}
        </p>
        <hr />
        <p class="de text-start text-black fs-6 m-0 px-3 py-2">코스 설명</p>
        <div class="border border-warning-subtle border-4">
          <p class="text-black fs-6 fw-normal m-0 px-3 py-2">
            {{ store.runningPath.description }}
          </p>
        </div>
        <hr />
        <RouterLink
          :to="`/comment/${store.runningPath.mapId}`"
          class="text-black fs-6 fw-normal m-0 px-3 py-2"
          >댓글 보기
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRunningPathStore } from "../../stores/runningPath";
import { useUserStore } from "../../stores/user";
import { useBookmarkStore } from "../../stores/bookmark";
import { onMounted, reactive, watch } from "vue";
import { useRoute } from "vue-router";

const store = useRunningPathStore();
const userStore = useUserStore();
const bookmarkStore = useBookmarkStore();
const computedDistances = reactive({});
const route = useRoute();

const toggleLike = function (runningPath) {
  console.log(runningPath.mapId);
  bookmarkStore.getBookmark(runningPath.mapId, userStore.userName["userId"]);
  runningPath.bookmark = !runningPath.bookmark;
};

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");

  const curDataToPost = {
    mapId: route.params.mapId,
    userId: userStore.userName["userId"],
  };
  store.getRunningPath(curDataToPost);

  if (storedDistances) {
    computedDistances.value = reactive(JSON.parse(storedDistances));
    localStorage.setItem(
      "computedDistances",
      JSON.stringify(computedDistances.value)
    );
  } else {
    store.getRunningPathList();
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
.sidebar {
  background-color: #f4f4f4;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.path {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.favor {
  margin-top: 20px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.bg-gradient {
  color: #ffffff;
  padding: 10px;
  border-radius: 5px;
  font-family: "LINESeedKR-Bd";
  font-size: 1.5rem;
  text-align: center;
}

img {
  width: 50px;
  /* Adjust as needed */
  height: 50px;
  /* Adjust as needed */
  margin-left: 10px;
}

.text-info {
  color: #3498db;
}

.de {
  font-family: "LINESeedKR-Bd";
}

.border-warning-subtle {
  border: 1px solid #f39c12;
}

.text-black {
  color: #333;
}
.like {
  color: red;
}
.unlike {
  color: rgb(228, 227, 227);
}
</style>
