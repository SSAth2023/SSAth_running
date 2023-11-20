<template>
  <div>
    <!-- 검색 입력과 버튼을 담는 부트스트랩의 입력 그룹 -->
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        @keyup.enter="search"
        v-model="searchInput"
        placeholder="Enter building name"
      />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>

    <!-- 지도를 표시하는 부분 -->
    <RouterLink :to="`/path/create`">경로 추가</RouterLink>
    <div id="map" style="height: 100vh"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";

const runningPathStore = useRunningPathStore();
const map = ref(null);
const searchInput = ref("");
const polylineOptions = ref({
  strokeColor: "#FF7E00",
  strokeOpacity: 1.0,
  strokeWeight: 3,
});

const search = () => {
  if (searchInput.value.trim() !== "") {
    const placesService = new google.maps.places.PlacesService(map.value);

    placesService.textSearch(
      {
        query: searchInput.value,
      },
      (results, status) => {
        if (
          status === "OK" &&
          results &&
          results[0] &&
          results[0].geometry &&
          results[0].geometry.location
        ) {
          const location = results[0].geometry.location;
          map.value.setCenter(location);
        } else {
          console.error("장소를 찾을 수 없습니다.");
        }
      }
    );
  }
};

const runningPathList = computed(() => runningPathStore.runningPathList);
console.log(runningPathStore.runningPathList);

onMounted(async () => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 15,
  });

  await runningPathStore.getRunningPathList();

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const location = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        map.value.setCenter(location);
      },
      (error) => {
        console.error("위치 수집 여부 거절");
      }
    );
  }

  // runningPathList에 저장된 초기 경로를 지도에 표시
  if (runningPathList.value.length > 0) {
    runningPathList.value.forEach((runningPathList) => {
      if (map.value) {
        new google.maps.Polyline({
          path: JSON.parse(runningPathList.path),
          strokeColor: polylineOptions.value.strokeColor,
          strokeOpacity: polylineOptions.value.strokeOpacity,
          strokeWeight: polylineOptions.value.strokeWeight,
          map: map.value,
        });
      }
    });
  }
});
</script>

<style scoped></style>
