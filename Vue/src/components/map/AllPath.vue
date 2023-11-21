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
    <button
      class="btn btn-outline-secondary current-location-button"
      @click="moveToCurrentLocation"
    >
      현재 위치로 이동
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { mapStyle } from "../common/mapStyle";
import { useRouter } from "vue-router";

const router = useRouter();
const runningPathStore = useRunningPathStore();
const map = ref(null);
const searchInput = ref("");
const polylineOptions = ref({
  strokeColor: "#56B37E",
  strokeOpacity: 0.5,
  strokeWeight: 4,
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

const moveToCurrentLocation = () => {
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
  } else {
    console.error("Geolocation이 지원되지 않습니다.");
  }
};
// let markerStart = null;
// let markerEnd = null;
// const onPolylineClick = (event, runningPath, pathTitle) => {
//   if (markerStart != null) {
//     markerStart.setMap(null);
//   }
//   if (markerEnd != null) {
//     markerEnd.setMap(null);
//   }
//   // 클릭된 경로의 첫 번째 좌표 가져오기
//   const firstCoordinate = JSON.parse(runningPath.path)[0];
//   const lastCoordinate = JSON.parse(runningPath.path)[
//     JSON.parse(runningPath.path).length - 1
//   ];

//   // 경로의 첫 번째 좌표로 지도 이동
//   if (map.value && firstCoordinate) {
//     const center = new google.maps.LatLng(
//       firstCoordinate.lat,
//       firstCoordinate.lng
//     );
//     map.value.setCenter(center);

//     markerStart = new google.maps.Marker({
//       position: center,
//       title: `${pathTitle} 출발점`,
//       map: map.value,
//     });

//     markerEnd = new google.maps.Marker({
//       position: new google.maps.LatLng(lastCoordinate.lat, lastCoordinate.lng),
//       title: "도착점",
//       map: map.value,
//     });

//     const infowindow = new google.maps.InfoWindow({
//       content: `${pathTitle}`,
//     });

//     infowindow.open(map.value, markerStart);
//   }

//   // console.log("Clicked Running Path:", runningPath);
//   // console.log("First Coordinate:", firstCoordinate);
// };

onMounted(async () => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 15,
    options: {
      styles: mapStyle,
    },
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
    runningPathList.value.forEach((runningPath) => {
      if (map.value) {
        const polyline = new google.maps.Polyline({
          path: JSON.parse(runningPath.path),
          strokeColor: polylineOptions.value.strokeColor,
          strokeOpacity: polylineOptions.value.strokeOpacity,
          strokeWeight: polylineOptions.value.strokeWeight,
          map: map.value,
        });

        google.maps.event.addListener(polyline, "click", (event) => {
          router.push({
            name: "runningPathDetail",
            params: { mapId: runningPath.mapId },
          });
        });
      }
    });
  }
});
</script>

<style scoped>
.current-location-button {
  position: absolute;
  background-color: white;
  border: 0cm;
  top: 87px; /* 조정 가능한 값 */
  right: 60px; /* 조정 가능한 값 */
  z-index: 1000; /* 다른 요소 위로 표시하기 위한 z-index 설정 */
}
</style>
