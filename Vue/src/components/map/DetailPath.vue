<template>
  <div>
    <div id="map" style="height: 100vh"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { mapStyle } from "../common/mapStyle";
import { useRoute } from "vue-router";

const route = useRoute();
const runningPathStore = useRunningPathStore();
const map = ref(null);
const polylineOptions = ref({
  strokeColor: "#56B37E",
  strokeOpacity: 0.5,
  strokeWeight: 4,
});

let markerStart = null;
let markerEnd = null;
const onPolylineClick = (event, runningPath, pathTitle) => {
  if (markerStart != null) {
    markerStart.setMap(null);
  }
  if (markerEnd != null) {
    markerEnd.setMap(null);
  }
  // 클릭된 경로의 첫 번째 좌표 가져오기
  const firstCoordinate = JSON.parse(runningPath.path)[0];
  const lastCoordinate = JSON.parse(runningPath.path)[
    JSON.parse(runningPath.path).length - 1
  ];

  // 경로의 첫 번째 좌표로 지도 이동
  if (map.value && firstCoordinate) {
    const center = new google.maps.LatLng(
      firstCoordinate.lat,
      firstCoordinate.lng
    );
    map.value.setCenter(center);

    markerStart = new google.maps.Marker({
      position: center,
      title: `${pathTitle} 출발점`,
      map: map.value,
    });

    markerEnd = new google.maps.Marker({
      position: new google.maps.LatLng(lastCoordinate.lat, lastCoordinate.lng),
      title: "도착점",
      map: map.value,
    });

    const infowindow = new google.maps.InfoWindow({
      content: `${pathTitle}`,
    });

    infowindow.open(map.value, markerStart);
  }
};

const runningPathDetail = computed(() => runningPathStore.runningPath);

onMounted(async () => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 15,
    options: {
      styles: mapStyle,
    },
  });

  await runningPathStore.getRunningPath(route.params.mapId);

  // runningPathList에 저장된 초기 경로를 지도에 표시
  if (runningPathDetail.value.length > 0) {
    runningPathDetail.value.forEach((runningPath) => {
      if (map.value) {
        const polyline = new google.maps.Polyline({
          path: JSON.parse(runningPath.path),
          strokeColor: polylineOptions.value.strokeColor,
          strokeOpacity: polylineOptions.value.strokeOpacity,
          strokeWeight: polylineOptions.value.strokeWeight,
          map: map.value,
        });

        google.maps.event.addListener(polyline, "click", (event) => {
          onPolylineClick(event, runningPath, runningPath.title);
        });
      }
    });
  }
});
</script>

<style scoped></style>
