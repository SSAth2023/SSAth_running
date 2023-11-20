<template>
  <div>
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="searchInput"
        placeholder="Enter building name"
      />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>
    <label>제목</label><br />
    <input v-model="title" /><br />
    <label>내용</label><br />
    <textarea v-model="description"></textarea>
    <br />
    <label>경로</label>

    <div id="map" style="height: 500px"></div>
    <button @click="createRunningPath">추가</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";

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
/* 부트스트랩 클래스나 추가적인 스타일은 여기에 작성 */
</style>
