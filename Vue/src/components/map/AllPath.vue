<template>
  <div>
    <!-- 검색 입력과 버튼을 담는 부트스트랩의 입력 그룹 -->
    <div class="input-group mb-3">
      <input type="text" class="form-control" v-model="searchInput" placeholder="Enter building name" />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>

    <!-- 지도를 표시하는 부분 -->
    <RouterLink :to="`/path/create`">경로 추가</RouterLink>
    <div id="map" style="height: 500px"></div>

    <div v-for="(path, index) in drawnCourse" :key="index">
      <google-map-polyline :path="path" :options="polylineOptions"></google-map-polyline>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';

const drawnCourse = ref([
  [
    { lat: 6.009070421227313, lng: 116.00857077388191, totalDistance: 0 },
    { lat: 6.008344868584089, lng: 116.00835619716072, totalDistance: 84.18913402125744 },
    { lat: 6.007854053012362, lng: 116.00871024875069, totalDistance: 151.4319247155042 },
    { lat: 6.009070421227313, lng: 116.00857077388191, totalDistance: 287.7149778605559 }
  ],
  [
    { lat: 6.010070421227313, lng: 116.00857077388191, totalDistance: 0 },
    { lat: 6.010344868584089, lng: 116.00835619716072, totalDistance: 84.18913402125744 },
    { lat: 6.010854053012362, lng: 116.00871024875069, totalDistance: 151.4319247155042 },
    { lat: 6.010070421227313, lng: 116.00857077388191, totalDistance: 287.7149778605559 }
  ]]);
export default {
  setup() {
    const map = ref(null);
    const searchInput = ref('');
    const polylineOptions = ref({
      strokeColor: "#FF7E00",
      strokeOpacity: 1.0,
      strokeWeight: 3,
    });

    const search = () => {
      if (searchInput.value.trim() !== '') {
        const placesService = new google.maps.places.PlacesService(map.value);

        placesService.textSearch({
          query: searchInput.value,
        }, (results, status) => {
          if (status === 'OK' && results && results[0] && results[0].geometry && results[0].geometry.location) {
            const location = results[0].geometry.location;
            map.value.setCenter(location);
          } else {
            console.error('장소를 찾을 수 없습니다.');
          }
        });
      }
    };

    onMounted(() => {
      map.value = new google.maps.Map(
        document.getElementById("map"),
        { center: { lat: 6.009, lng: 116.008 }, zoom: 15 }
      );

      // if (navigator.geolocation) {
      //   navigator.geolocation.getCurrentPosition(
      //     (position) => {
      //       const location = {
      //         lat: position.coords.latitude,
      //         lng: position.coords.longitude,
      //       };
      //       map.value.setCenter(location);
      //     },
      //     (error) => {
      //       console.error("위치 수집 여부 거절");
      //     }
      //   );
      // }

      // const drawingManager = new google.maps.drawing.DrawingManager({
      //   drawingMode: google.maps.drawing.OverlayType.POLYLINE,
      //   drawingControl: true,
      //   drawingControlOptions: {
      //     position: google.maps.ControlPosition.TOP_CENTER,
      //     drawingModes: [google.maps.drawing.OverlayType.POLYLINE],
      //   },
      //   polylineOptions: {
      //     editable: true,
      //   },
      // });

      // drawingManager.setMap(map.value);

      // google.maps.event.addListener(
      //   drawingManager,
      //   "polylinecomplete",
      //   function (polyline) {
      //     const path = polyline.getPath();
      //     const coordinates = [];

      //     let totalDistance = 0;

      //     for (let i = 0; i < path.getLength(); i++) {
      //       const currentLatLng = path.getAt(i);
      //       const currentCoordinates = { lat: currentLatLng.lat(), lng: currentLatLng.lng() };

      //       if (i > 0) {
      //         const prevLatLng = path.getAt(i - 1);
      //         const prevCoordinates = { lat: prevLatLng.lat(), lng: prevLatLng.lng() };

      //         const distance = google.maps.geometry.spherical.computeDistanceBetween(
      //           new google.maps.LatLng(prevCoordinates.lat, prevCoordinates.lng),
      //           new google.maps.LatLng(currentCoordinates.lat, currentCoordinates.lng)
      //         );

      //         totalDistance += distance;
      //       }

      //       currentCoordinates.totalDistance = totalDistance;
      //       coordinates.push(currentCoordinates);
      //     }

      //     drawnCourse.value.push(coordinates);
      //     console.log("Drawn path coordinates:", drawnCourse.value, " ");
      //   }
      // );
      // drawnCourse에 저장된 초기 경로를 지도에 표시
      if (drawnCourse.value.length > 0) {
        drawnCourse.value.forEach((path) => {
          new google.maps.Polyline({
            path: path,
            strokeColor: polylineOptions.value.strokeColor,
            strokeOpacity: polylineOptions.value.strokeOpacity,
            strokeWeight: polylineOptions.value.strokeWeight,
            map: map.value,
          });
        });
      }
    });


    return {
      drawnCourse,
      polylineOptions,
      searchInput,
      search,
    };
  },
};
</script>

<style scoped>
/* 부트스트랩 클래스나 추가적인 스타일은 여기에 작성 */
</style>


