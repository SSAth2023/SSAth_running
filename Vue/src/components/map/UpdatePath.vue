<template>
  <div>
    <div class="input-group mb-3 input-search"></div>
    <div id="map" style="height: 80vh"></div>
    <div>
      <label>제목</label><br />
      <input type="text" v-model="title" /><br />
      <label>내용</label><br />
      <textarea v-model="description"></textarea>
      <br />
      <label>경로</label>
    </div>
    <button @click="updateRunningPath">저장</button>
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
const infoWindow = ref(null);
const polylineOptions = ref({
  strokeColor: "#008000",
  strokeOpacity: 0.7,
  strokeWeight: 4,
});

const runningPathDetail = computed(() => runningPathStore.runningPath);

const title = ref(runningPathDetail.value.title);
const description = ref(runningPathDetail.value.description);

const updateRunningPath = () => {
  const path = {
    mapId: runningPathDetail.value.mapId,
    userId: runningPathDetail.value.userId,
    title: title.value,
    path: runningPathDetail.value.path,
    description: description.value,
    distance: runningPathDetail.value.distance,
  };
  runningPathStore.updateRunningPath(path);
};

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
  if (runningPathDetail.value) {
    const location = {
      lat: JSON.parse(runningPathDetail.value.path)[0]["lat"],
      lng: JSON.parse(runningPathDetail.value.path)[0]["lng"],
    };
    map.value.setCenter(location);
    if (map.value) {
      const polyline = new google.maps.Polyline({
        path: JSON.parse(runningPathDetail.value.path),
        strokeColor: polylineOptions.value.strokeColor,
        strokeOpacity: polylineOptions.value.strokeOpacity,
        strokeWeight: polylineOptions.value.strokeWeight,
        map: map.value,
      });
    }

    const markerStart = new google.maps.Marker({
      position: location,
      title: `${runningPathDetail.value.title} 출발점`,
      map: map.value,
      icon: {
        path: google.maps.SymbolPath.CIRCLE, // 마커 모양을 원으로 지정
        fillColor: "white", // 마커 색상
        fillOpacity: 1, // 마커 투명도
        strokeColor: "#56B37E",
        strokeWeight: 3, // 테두리 두께
        scale: 10, // 마커 크기
      },
      label: {
        text: "S",
        color: "Green",
        fontWeight: "bold",
      },
    });

    const markerEnd = new google.maps.Marker({
      position: new google.maps.LatLng(
        JSON.parse(runningPathDetail.value.path)[
        JSON.parse(runningPathDetail.value.path).length - 1
        ]["lat"],
        JSON.parse(runningPathDetail.value.path)[
        JSON.parse(runningPathDetail.value.path).length - 1
        ]["lng"]
      ),
      title: "도착점",
      map: map.value,
      icon: {
        path: google.maps.SymbolPath.CIRCLE, // 마커 모양을 원으로 지정
        fillColor: "white", // 마커 색상
        fillOpacity: 1, // 마커 투명도
        strokeColor: "#56B37E",
        strokeWeight: 3, // 테두리 두께
        scale: 10, // 마커 크기
      },
      label: {
        text: "E",
        color: "Green",
        fontWeight: "bold",
      },
    });

    const contentString = `<div style="border: none"> 코스 : ${runningPathDetail.value.title
      }<br> 거리 : ${(runningPathDetail.value.distance / 1000).toFixed(
        2
      )} km</div>`;
    infoWindow.value = new google.maps.InfoWindow();
    infoWindow.value.setContent(contentString);
    infoWindow.value.setPosition({
      lat: JSON.parse(runningPathDetail.value.path)[0]["lat"],
      lng: JSON.parse(runningPathDetail.value.path)[0]["lng"],
    });
    infoWindow.value.setOptions({
      pixelOffset: new google.maps.Size(0, -30),
      disableAutoPan: true,
    });
    infoWindow.value.open(map.value);
  }
});
</script>

<style scoped>
.current-location-button {
  position: absolute;
  background-color: white;
  border: 0cm;
  top: 10px;
  right: 60px;
  z-index: 1000;
}

.input-search {
  position: absolute;
  background-color: white;
  border: 0cm;
  top: 10px;
  left: 43%;
  width: 300px;
  z-index: 1000;
}
</style>
