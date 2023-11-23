<template>
  <div>
    <div id="map" style="height: 100vh"></div>

    <!--검색-->
    <div class="input-group mb-3 input-search">
      <input
        type="text"
        class="form-control"
        @keyup.enter="search"
        v-model="searchInput"
        placeholder="검색하고 싶은 장소가 있으신가요?"
      />
      <button
        class="btn btn-outline-secondary"
        style="border: none"
        @click="search"
      >
        검색
      </button>
    </div>
    <!--좋아요 누른 경로만 보기-->
    <RouterLink
      v-if="userStore.userName != ''"
      class="btn btn-outline-secondary running-path-bookmarked"
      :to="`/path/bookmark`"
    >
      <i class="bi bi-bookmarks-fill"></i>
    </RouterLink>
    <!--경로 추가-->
    <RouterLink
      v-if="userStore.userName != ''"
      class="btn btn-outline-secondary running-path-create"
      :to="`/path/create`"
      >경로 추가</RouterLink
    >
    <!--현재 위치-->
    <button
      class="btn btn-outline-secondary current-location-button"
      @click="moveToCurrentLocation"
    >
      현재 위치로 이동
    </button>
    <!-- 날씨 -->
    <div class="weather">
      <div class="weather-location">
        <i class="bi bi-compass"></i> {{ locationName }}
      </div>
      <div class="weather-temp">
        <i class="bi bi-thermometer-half"></i> {{ temp }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRunningPathStore } from "../../stores/runningPath";
import { useUserStore } from "../../stores/user";
import { mapStyle } from "../common/mapStyle";
import { useRouter } from "vue-router";

const router = useRouter();
const runningPathStore = useRunningPathStore();
const userStore = useUserStore();
const map = ref(null);
const searchInput = ref("");
const infoWindow = ref(null);
const curlocationToPost = ref({});
let locationName = ref(null);
let temp = ref(null);
console.log(locationName.value);
const polylineOptions = ref({
  strokeColor: "#008000",
  strokeOpacity: 0.7,
  strokeWeight: 4,
});
const polylineOptionsOver = ref({
  strokeColor: "#000080",
  strokeOpacity: 0.7,
  strokeWeight: 6,
});
const pathArrow = {
  path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
  scale: 3,
  strokeColor: "#FFFFHH",
  strokeWeight: 5,
};

const getRandomColor = () => {
  const letters = "0123456789ABCDEF";
  let color = "#";
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
};

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
          curlocationToPost.value = {
            mapId: "",
            path: JSON.stringify(location),
            userId: userStore.userName["userId"],
          };
          const str = JSON.stringify(location);
          console.log(JSON.stringify(location));
          console.log(str.slice(7, str.indexOf(",")));
          const lat = str.slice(7, str.indexOf(","));
          console.log(str.slice(str.indexOf("lng") + 5, str.length - 1));
          const lng = str.slice(str.indexOf("lng") + 5, str.length - 1);
          getWeather(lat, lng);
          console.log(curlocationToPost);
          map.value.setCenter(location);
        } else {
          console.error("장소를 찾을 수 없습니다.");
        }
      }
    );
    runningPathStore.getRunningPathList(curlocationToPost);
  }
};

const runningPathList = computed(() => runningPathStore.runningPathList);

const moveToCurrentLocation = () => {
  console.log(navigator.geolocation);
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const location = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        curlocationToPost.value = {
          mapId: "",
          path: `"lat":${position.coords.latitude},"lng":${position.coords.longitude}`,
          userId: userStore.userName["userId"],
        };
        console.log(curlocationToPost);
        map.value.setCenter(location);
        runningPathStore.getRunningPathList(curlocationToPost);
        getWeather(position.coords.latitude, position.coords.longitude);
      },
      (error) => {
        console.error("위치 수집 여부 거절");
      }
    );
  } else {
    console.error("Geolocation이 지원되지 않습니다.");
  }
};

const addEvent = (polyline, runningPath) => {
  let count = 0;
  let interval;

  google.maps.event.addListener(polyline, "mouseover", (event) => {
    const contentString = `<div style="border: none"> 코스 : ${
      runningPath.title
    }<br> 거리 : ${(runningPath.distance / 1000).toFixed(2)} km</div>`;
    if (!infoWindow.value) {
      infoWindow.value = new google.maps.InfoWindow();
    }
    infoWindow.value.setContent(contentString);
    infoWindow.value.setPosition({
      lat: JSON.parse(runningPath.path)[0]["lat"],
      lng: JSON.parse(runningPath.path)[0]["lng"],
    });
    infoWindow.value.setOptions({
      disableAutoPan: true,
    });
    infoWindow.value.open(map.value);
    interval = setInterval(() => {
      polyline.setOptions({
        strokeColor: polylineOptionsOver.value.strokeColor,
        strokeOpacity: polylineOptionsOver.value.strokeOpacity,
        strokeWeight: polylineOptionsOver.value.strokeWeight,
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
  });

  google.maps.event.addListener(polyline, "mouseout", () => {
    clearInterval(interval);
    polyline.setOptions({
      strokeColor: polylineOptions.value.strokeColor,
      strokeOpacity: polylineOptions.value.strokeOpacity,
      strokeWeight: polylineOptions.value.strokeWeight,
      icons: [],
    });
    if (infoWindow.value) {
      infoWindow.value.close();
    }
  });

  google.maps.event.addListener(polyline, "click", () => {
    router.push({
      name: "runningPathDetail",
      params: { mapId: runningPath.mapId },
    });
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

  const getLocation = async () => {
    return new Promise((resolve, reject) => {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const location = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          };
          curlocationToPost.value = {
            mapId: "",
            path: `"lat":${position.coords.latitude},"lng":${position.coords.longitude}`,
            userId: userStore.userName["userId"],
          };
          map.value.setCenter(location);
          resolve();
          await getWeather(position.coords.latitude, position.coords.longitude);
        },
        (error) => {
          console.error("위치 수집 여부 거절");
          reject(error);
        }
      );
    });
  };

  // 위치 수집 기다리기
  await getLocation();
  await runningPathStore.getRunningPathList(curlocationToPost);

  if (runningPathList.value.length > 0) {
    runningPathList.value.forEach((runningPath) => {
      if (map.value) {
        const polyline = new google.maps.Polyline({
          path: JSON.parse(runningPath.path),
          strokeColor: getRandomColor(),
          strokeOpacity: polylineOptions.value.strokeOpacity,
          strokeWeight: polylineOptions.value.strokeWeight,
          map: map.value,
        });

        addEvent(polyline, runningPath);
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
  top: 10px;
  right: 60px;
  z-index: 1000;
}

.custom-info-window {
  background-color: white;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.running-path-create {
  position: absolute;
  background-color: white;
  border: 0cm;
  bottom: 25px;
  right: 60px;
  z-index: 1000;
}

.running-path-bookmarked {
  position: absolute;
  background-color: white;
  border: 0cm;
  bottom: 67px;
  right: 60px;
  z-index: 1000;
}
.weather {
  width: 100px;
  height: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: absolute;
  top: 7%;
  left: 92%;
  border: 1;
  background-color: rgba(216, 216, 216, 0.603);
  z-index: 1000s;
}
.weather-location {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-left: 10px;
  border: none;
}
.weather-temp {
  margin-left: 10px;
  border: none;
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
