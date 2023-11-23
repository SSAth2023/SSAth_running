<template>
  <div>
    <div id="map" style="height: 100vh"></div>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">
              러닝 경로 수정
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
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateRunningPath">
              수정
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--수정-->
    <button v-if="userStore.userName['userId'] === runningPathDetail.userId" data-bs-toggle="modal"
      data-bs-target="#exampleModal" class="btn btn-outline-warning running-path-update">
      수정
    </button>
    <!--삭제-->
    <button v-if="userStore.userName['userId'] === runningPathDetail.userId"
      class="btn btn-outline-danger running-path-delete" @click="runningPathDelete">
      삭제
    </button>
    <!-- 날씨 -->
    <div class="weather">
      <div class="weather-location">
        <img class="running-logo" @click="back" src="../../assets/image/S S A T H.png" alt="Running Logo" /><br>
        <img :src="`${weatherIconAdrs}`" style="height: 30px;">
        {{ locationName }}
      </div>
      <div class="weather-temp">
        <i class="bi bi-thermometer-half"></i> {{ temp }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
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
let locationName = ref(null);
let temp = ref(null);
let weatherIconAdrs = ref(null);
let count = 0;
let interval;

const polylineOptions = ref({
  strokeColor: "#FF1330",
  strokeOpacity: 0.7,
  strokeWeight: 4,
});
const polylineOptionsOver = ref({
  strokeColor: "#28bb65",
  strokeOpacity: 0.7,
  strokeWeight: 6,
});
const pathArrow = {
  path: google.maps.SymbolPath.CIRCLE,
  scale: 5,
  fillColor: "#000",
  fillOpacity: 1,
  strokeWeight: 0,
};

const runningPathDetail = computed(() => runningPathStore.runningPath);
const title = ref(runningPathDetail.value.title);
const description = ref(runningPathDetail.value.description);

watch(runningPathDetail, (newVal) => {
  title.value = newVal.title;
  description.value = newVal.description;
});

const updateRunningPath = () => {
  return new Promise((resolve, reject) => {
    if (confirm("수정을 완료하시겠습니까?")) {
      const path = {
        mapId: runningPathDetail.value.mapId,
        userId: runningPathDetail.value.userId,
        title: title.value,
        path: runningPathDetail.value.path,
        description: description.value,
        distance: runningPathDetail.value.distance,
      };
      runningPathStore.updateRunningPath(path);

      resolve(); // 사용자가 확인을 선택한 경우 resolve 호출
    } else {
      reject(); // 사용자가 취소를 선택한 경우 reject 호출
    }
  })
    .then(() => {
      alert("경로 정보가 수정되었습니다.");
      router.push({
        name: "runningPathDetail",
        params: { mapId: route.params.mapId },
      });
    })
    .catch(() => {
      // 사용자가 취소를 선택하거나 업데이트 실패 시의 로직
    });
};

const runningPathDelete = () => {
  return new Promise((resolve, reject) => {
    if (confirm("정말 삭제하시겠습니까?")) {
      axios.delete(
        `http://localhost:8080/api/path/delete/${route.params.mapId}`
      );
      resolve(); // 사용자가 확인을 선택한 경우 resolve 호출
    } else {
      reject(); // 사용자가 취소를 선택한 경우 reject 호출
    }
  })
    .then(() => {
      alert("경로가 삭제되었습니다.");
      router.push("/");
    })
    .catch(() => {
      // 사용자가 취소를 선택하거나 업데이트 실패 시의 로직
    });
};

const getWeather = async function (lat, lon) {
  fetch(
    `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=a1a2fe0dd5325059336133b7a25936e3&units=metric`
  )
    .then(function (response) {
      return response.json();
    })
    .then(function (json) {
      console.log(json);
      temp.value = json.main.temp;
      locationName.value = json.name;
      const weatherIcon = json.weather[0].icon;
      weatherIconAdrs = `https://openweathermap.org/img/wn/${weatherIcon}@2x.png`;
      console.log(weatherIconAdrs)
    });
};

onMounted(async () => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 16,
    options: {
      styles: mapStyle,
    },
  });

  const curDataToPost = {
    mapId: route.params.mapId,
    userId: userStore.userName["userId"],
  };

  await runningPathStore.getRunningPath(curDataToPost);
  getWeather(JSON.parse(runningPathDetail.value.path)[0]["lat"], JSON.parse(runningPathDetail.value.path)[0]["lng"]);
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
        strokeColor: polylineOptionsOver.value.strokeColor,
        strokeOpacity: polylineOptionsOver.value.strokeOpacity,
        strokeWeight: polylineOptionsOver.value.strokeWeight,
        map: map.value,
      });
      interval = setInterval(() => {
        polyline.setOptions({
          icons: [
            {
              icon: pathArrow,
              offset: count + "%",
            },
          ],
        });
        count += 0.05;
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

.weather {
  width: 170px;
  height: 85px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: absolute;
  top: 8%;
  right: 1%;
  border: 1;
  background-color: rgba(142, 141, 141, 0.36);
  z-index: 1000s;
}

.weather-location {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-left: 5px;
  border: none;
}

.weather-temp {
  margin-top: 1%;
  margin-left: 10px;
  border: none;
}
</style>
