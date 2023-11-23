<template>
  <div>
    <div id="map" style="height: 100vh"></div>

    <!--검색-->
    <div
      class="input-group border border-1 rounded-start-3 rounded-5 border-success mb-3 input-search"
    >
      <input
        type="text"
        class="form-control"
        @keyup.enter="search"
        v-model="searchInput"
        placeholder="Search"
      />
      <button
        class="btn btn-outline-success"
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

    <img
      class="running-logo"
      @click="back"
      src="../../assets/image/S S A T H.png"
      alt="Running Logo"
    />
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

onMounted(async () => {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 6.009, lng: 116.008 },
    zoom: 15,
    options: {
      styles: mapStyle,
    },
  });

  // 위치 수집 Promise 생성
  const getLocation = () => {
    return new Promise((resolve, reject) => {
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
          map.value.setCenter(location);
          resolve();
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

.running-logo {
  position: absolute;
  width: 10vw;
  border: 0cm;
  bottom: 25px;
  right: 160px;
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
