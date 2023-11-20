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

    <div id="map" style="height: 500px"></div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";

const drawnCourse = ref([]);
export default {
  setup() {
    const map = ref(null);
    const searchInput = ref("");

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
          editable: true,
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
                  new google.maps.LatLng(
                    prevCoordinates.lat,
                    prevCoordinates.lng
                  ),
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

          drawnCourse.value.push(coordinates);
          console.log("Drawn path coordinates:", drawnCourse.value, " ");
        }
      );
    });

    return {
      drawnCourse,
      searchInput,
      search,
    };
  },
};
</script>

<style scoped>
/* 부트스트랩 클래스나 추가적인 스타일은 여기에 작성 */
</style>
