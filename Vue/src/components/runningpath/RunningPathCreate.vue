<template>
  <div>
    <div class="input-group mb-3" style="height: 5vh">
      <input
        type="text"
        class="form-control"
        @keyup.enter="search"
        v-model="searchInput"
        placeholder="Enter building name"
      />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>
    <div>
      <label>제목</label><br />
      <input v-model="title" /><br />
      <label>내용</label><br />
      <textarea v-model="description"></textarea>
      <br />
      <label>경로</label>
    </div>
    <div id="map" style="height: 80vh"></div>
    <button @click="createRunningPath" style="height: 3vh">추가</button>
    <button
      class="btn btn-outline-secondary current-location-button"
      @click="moveToCurrentLocation"
    >
      현재 위치로 이동
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { mapStyle } from "../common/mapStyle";

const runningPathStore = useRunningPathStore();
const drawnCourse = ref();
const map = ref(null);
const searchInput = ref("");
const title = ref("");
const description = ref("");

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
const createRunningPath = () => {
  console.log(drawnCourse.value[drawnCourse.value.length - 1]);
  const path = {
    // userId:localStorage.getItem("userId"),
    userId: 1234,
    title: title.value,
    path: JSON.stringify(drawnCourse.value),
    description: description.value,
    distance: drawnCourse.value[drawnCourse.value.length - 1].totalDistance,
  };
  runningPathStore.createRunningPath(path);
};

onMounted(() => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 15,
    options: {
      styles: mapStyle,
    },
  });

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

  const drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode: google.maps.drawing.OverlayType.POLYLINE,
    drawingControl: true,
    drawingControlOptions: {
      position: google.maps.ControlPosition.TOP_CENTER,
      drawingModes: [google.maps.drawing.OverlayType.POLYLINE],
    },
    polylineOptions: {
      strokeColor: "#FF7E00",
      strokeOpacity: 1.0,
      strokeWeight: 3,
    },
  });

  drawingManager.setMap(map.value);

  google.maps.event.addListener(
    drawingManager,
    "polylinecomplete",
    function (polyline) {
      const path = polyline.getPath();
      const coordinates = [];

      let totalDistance = 0;

      for (let i = 0; i < path.getLength(); i++) {
        const currentLatLng = path.getAt(i);
        const currentCoordinates = {
          lat: currentLatLng.lat(),
          lng: currentLatLng.lng(),
        };

        if (i > 0) {
          const prevLatLng = path.getAt(i - 1);
          const prevCoordinates = {
            lat: prevLatLng.lat(),
            lng: prevLatLng.lng(),
          };

          const distance =
            google.maps.geometry.spherical.computeDistanceBetween(
              new google.maps.LatLng(prevCoordinates.lat, prevCoordinates.lng),
              new google.maps.LatLng(
                currentCoordinates.lat,
                currentCoordinates.lng
              )
            );

          totalDistance += distance;
        }

        currentCoordinates.totalDistance = totalDistance;
        coordinates.push(currentCoordinates);
      }

      drawnCourse.value = coordinates;
      console.log("Drawn path coordinates:", drawnCourse.value, " ");
    }
  );
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
