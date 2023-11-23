<template>
  <div>
    <!--검색-->
    <div class="input-group border border-1 rounded-start-3 border-success mb-3 input-search">
      <input type="text" class="form-control" @keyup.enter="search" v-model="searchInput" placeholder="Search" />
      <button class="btn btn-outline-success" style="border: none " @click="search">
        검색
      </button>
    </div>
    <div id="map" style="height: 100vh"></div>
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">
              러닝 경로 등록
            </h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <div>
                <label class="form-label">경로 이름</label><br />
                <input class="form-control" v-model="title" /><br />
              </div>
              <div>
                <label class="form-label">경로 설명</label><br />
                <textarea class="form-control" v-model="description"></textarea>
              </div>
              <br />
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
              취소
            </button>
            <button type="button" class="btn btn-success" @click="createRunningPath" data-bs-toggle="modal"
              data-bs-target="#exampleModal">
              등록
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- Button trigger modal -->
    <button v-if="isDrawingComplete" type="button" class="btn btn-success btn-lg create-button" data-bs-toggle="modal"
      data-bs-target="#exampleModal" style="font-size: 15px;">
      경로 지정 완료
    </button>
    <button v-else class="btn btn-secondary btn-lg create-button" disabled style="font-size: 15px;">
      더블 클릭 시 그리기 종료
    </button>
    <button class="btn btn-outline-secondary current-location-button" @click="moveToCurrentLocation">
      현재 위치로 이동
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { mapStyle } from "../common/mapStyle";
import { useUserStore } from "../../stores/user";

const runningPathStore = useRunningPathStore();
const drawnCourse = ref();
const map = ref(null);
const searchInput = ref("");
const title = ref("");
const description = ref("");
const userStore = useUserStore();
const isDrawingComplete = ref(false);

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
  return new Promise((resolve, reject) => {
    if (confirm("경로를 등록하시겠습니까?")) {
      console.log(drawnCourse.value[drawnCourse.value.length - 1]);
      const path = {
        userId: userStore.userName["userId"],
        title: title.value,
        path: JSON.stringify(drawnCourse.value),
        description: description.value,
        distance: drawnCourse.value[drawnCourse.value.length - 1].totalDistance,
      };
      runningPathStore.createRunningPath(path);
      resolve(); // 사용자가 확인을 선택한 경우 resolve 호출
    } else {
      reject(); // 사용자가 취소를 선택한 경우 reject 호출
    }
  })
    .then(() => {
      alert("경로가 등록되었습니다.");
      router.push("/");
    })
    .catch(() => {
      // 사용자가 취소를 선택한 경우 또는 댓글 등록 실패 시의 로직
    });
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
      isDrawingComplete.value = true;
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
  top: 10px;
  right: 60px;
  z-index: 1000;
}

.input-search {
  border-top-right-radius: 7px;
  border-bottom-right-radius: 7px;
  position: absolute;
  background-color: white;
  border: 0cm;
  top: 10px;
  left: 38%;
  width: 300px;
  z-index: 1000;
}

.create-button {
  position: absolute;
  bottom: 20px;
  right: 60px;
  z-index: 1000;
}
</style>
