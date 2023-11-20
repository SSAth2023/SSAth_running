<template>
  <div>
      <!-- <div class="path">
        <div>
          <div class="bg-info-subtle rounded-1">{{ currentMap.title }}</div>
          <p class="text-black fs-4 fw-normal m-0 px-3 py-2">
            약 {{ computedDistances[currentMap.mapId] }}km 내,
            {{ (currentMap.distance / 1000).toFixed(2) }}km
          </p>
          <p class="text-black fs-6 fw-normal m-0 px-3 py-2">
            {{ currentMap.description }}
          </p>
          <RouterLink
            :to="`/path/${currentMap.mapId}`"
            class="text-black fs-6 fw-normal m-0 px-3 py-2"
            >댓글 보기</RouterLink
          >
        </div> -->
        <div class="favor">
          <img src="@/assets/image/Favorite.png" />
          <p class="text-center text-black fs-6 fw-normal m-0 px-3 py-2">13</p>
        </div>
      
      <hr />
   </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { useRunningPathStore } from "../../stores/runningPath";
import { onMounted, reactive, watch, ref } from "vue";

const store = useRunningPathStore();
const route = useRoute();

const currentMapId = ref(null);
const currentMap = ref(null);
let computedDistances = reactive({});

onMounted(() => {
  const storedDistances = localStorage.getItem("computedDistances");
  currentMapId.value = route.params.mapId;
  currentMap.value = store.getRunningPath(currentMapId);

  console.log(currentMap.value)
  

  if (storedDistances) {
    computedDistances = reactive(JSON.parse(storedDistances));
  } else {
    store.runningPathList.forEach(async (runningPath) => {
      try {
        tempDist(runningPath.path, runningPath.mapId);
      } catch (error) {
        console.error("거리 계산 중 오류 발생:", error);
      }
    });
  }

  store.getRunningPathList();
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
