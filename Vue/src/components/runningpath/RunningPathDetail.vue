<template>
  <div>
    <div class="path">
      <div>
        <div class="favor">
          <div class="bg-info-subtle rounded-1">
            {{ store.runningPath.title }}
          </div>
          <img class="img-fluid" src="@/assets/image/Favorite.png" />
          <p class="text-center text-black fs-6 fw-normal m-0 px-3 py-2">13</p>
        </div>
        <p class="text-black fs-4 fw-normal m-0 px-3 py-2">
          약 {{ computedDistances[store.runningPath.mapId] }}km 내,
          {{ (store.runningPath.distance / 1000).toFixed(2) }}km 코스
        </p>
        <p class="text-end text-black fs-5 fw-normal m-0 px-3 py-2">
          등록인: {{ store.runningPath.userId }}
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
          >댓글 보기</RouterLink
        >
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRunningPathStore } from "../../stores/runningPath";
import { onMounted, reactive, watch } from "vue";
import { useRoute } from "vue-router";

const store = useRunningPathStore();
const computedDistances = reactive({});
const route = useRoute();

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");
  store.getRunningPathList();
  store.getRunningPath(route.params.mapId);

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
.bg-info-subtle {
  width: 15vw;
  height: 5vh;
  margin: 2vh;
  font-family: "LINESeedKR-Bd";
  font-size: xx-large;
  text-align: center;
}

.path {
  display: flex;
  justify-content: center;
  margin-bottom: 2vh;
}
.favor {
  display: flex;
}

img {
  width: 3.7425vw;
  height: 6.5104vh;
}

.de {
  font-family: "LINESeedKR-Bd";
}
</style>
