<template>
  <div>
    <div id="map" style="height: 100vh"></div>
    <!--수정-->
    <router-link v-if="userStore.userName['userId'] === runningPathDetail.userId"
      :to="`/path/update/${runningPathDetail.mapId}`" :path="runningPathDetail"
      class="btn btn-outline-secondary running-path-update">
      수정
    </router-link>
    <!--삭제-->
    <button v-if="userStore.userName['userId'] === runningPathDetail.userId"
      class="btn btn-outline-secondary running-path-delete" @click="runnigPathDelete">
      삭제
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { useUserStore } from "../../stores/user";
import { mapStyle } from "../common/mapStyle";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const runningPathStore = useRunningPathStore();
const userStore = useUserStore();
const map = ref(null);
const infoWindow = ref(null);
let count = 0;
let interval;
const polylineOptions = ref({
  strokeColor: "#000080",
  strokeOpacity: 0.7,
  strokeWeight: 6,
});
const pathArrow = {
  path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
  scale: 3,
  strokeColor: "#FFFFHH",
  strokeWeight: 5,
}

const runningPathDetail = computed(() => runningPathStore.runningPath);

const runnigPathDelete = () => {
  axios
    .delete(`http://localhost:8080/api/path/delete/${route.params.mapId}`)
    .then(() => {
      router.push({ name: "home" });
    });
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
      interval = setInterval(() => {
        polyline.setOptions({
          icons: [
            {
              icon: pathArrow,
              offset: count + "%",
            },
            {
              icon: pathArrow,
              offset: count + 1 + "%",
            },
            {
              icon: pathArrow,
              offset: count + 2 + "%",
            },
          ],
        });
        count += 0.02;
        if (count > 100) count = 0;
      }, 1);
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
      disableAutoPan: true,
    });
    infoWindow.value.open(map.value);
  }
});
</script>

<style scoped>
.running-path-delete {
  position: absolute;
  background-color: white;
  border: 0cm;
  bottom: 25px;
  right: 60px;
  z-index: 1000;
}

.running-path-update {
  position: absolute;
  background-color: white;
  border: 0cm;
  bottom: 25px;
  right: 117px;
  z-index: 1000;
}
</style>
