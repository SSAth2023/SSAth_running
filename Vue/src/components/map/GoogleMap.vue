<template>
  <div>
    <!-- 검색 입력과 버튼을 담는 부트스트랩의 입력 그룹 -->
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        v-model="searchInput"
        placeholder="Enter building name dkmfeiwmsdklnfoiwenklsdnflwsn"
      />
      <button class="btn btn-outline-secondary" @click="search">검색</button>
    </div>

    <!-- 지도를 표시하는 부분 -->
    <p>안녕안녕 선생님</p>
    <div id="map" style="height: 500px"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchInput: "", // 검색어를 저장하는 데이터 속성
      map: null, // 구글 맵 객체를 저장하는 데이터 속성
      placesService: null, // PlacesService 객체를 저장하는 데이터 속성
      infowindow: null, // InfoWindow 객체를 저장하는 데이터 속성
      markers: [], // 마커들을 저장하는 데이터 속성
    };
  },
  mounted() {
    // 페이지 로드 시 초기화 함수 호출
    this.initMap();
  },

  methods: {
    initMap() {
      const map = new google.maps.Map(
        //google map 호출 및 중앙 좌표 & 줌 설정 / 기본 좌표는 사피섬
        document.getElementById("map"), { center: { lat: 6.009, lng: 116.008 }, zoom: 17, }
      )

      // 현재 위치를 얻어옵니다.
      //navigator : window 객체의 property
      //geolocation : 사용자의 지리적 위치 정보를 확인하는 API
      //navigator.geolocation을 동작시 브라우저에 정보 수집 팝업이 발생하며, 수락할 경우 하위 코드가 동작한다.
      if (navigator.geolocation) {
          //getCurrentPosition : 사용자의 현재 위치를 요청한다.
          navigator.geolocation.getCurrentPosition(
              //position에 들어있는 정보들 중 위도와 경도를 추출해 lat, lng에 저장
              (position) => {
                  const currentLocation = {
                      lat: position.coords.latitude,  
                      lng: position.coords.longitude,
                  };
                  //해당 위도 경로로 map을 위치
                  map.setCenter(currentLocation);
              },
              (error) => {
                  console.error("Error getting current location:", error);
            },
          );
      }

      var drawnPathCoordinates = [
      ];

      //선을 그리기 위한 google의 DrawingManager를 초기화
      const drawingManager = new google.maps.drawing.DrawingManager({
          //기본 drawingMode 지정, Control로 Options에 대해 변경 가능하게 설정
          drawingMode: google.maps.drawing.OverlayType.POLYLINE,
          drawingControl: true,
          drawingControlOptions: {
              position: google.maps.ControlPosition.TOP_CENTER,
              drawingModes: [
                  //   google.maps.drawing.OverlayType.MARKER,
                  //   google.maps.drawing.OverlayType.CIRCLE,
                  //   google.maps.drawing.OverlayType.POLYGON,
                  google.maps.drawing.OverlayType.POLYLINE,
                  //   google.maps.drawing.OverlayType.RECTANGLE,
              ],
          },
          polylineOptions: {
              editable: true,
          },
          //marker와 cicle에 대한 추가 옵션
          //   markerOptions: {
          //     icon: "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
          //   },
          //   circleOptions: {
          //     fillColor: "#ffff00",
          //     fillOpacity: 1,
          //     strokeWeight: 5,
          //     clickable: false,
          //     editable: true,
          //     zIndex: 1,
          //   },
      });
      //지도에 드로잉 매니저 등록
      drawingManager.setMap(map);

      //POLYLINE(선 그리기) 완료될 때 동작하는 이벤트 리스너 추가(이벤트명, 실행할 함수명, 옵션)
      google.maps.event.addListener(
          drawingManager,
          "polylinecomplete",
          function (polyline) {
              //좌표를 변수에 저장
              drawnPathCoordinates = polyline.getPath().getArray();

              console.log("Drawn path coordinates:", drawnPathCoordinates);
              console.log(drawnPathCoordinates[0]);
              console.log(JSON.stringify(drawnPathCoordinates[0]));

          }
      );

      // 저장된 좌표를 사용하여 지도에 선 그리기
      function drawPolylineOnMap(coordinates) {
          // Polyline을 그릴 때 사용할 스타일 설정
          var polylineOptions = {
              path: coordinates,
              geodesic: true,
              strokeColor: '#FF0000',
              strokeOpacity: 1.0,
              strokeWeight: 2
          };

          // Polyline 객체 생성
          var polyline = new google.maps.Polyline(polylineOptions);

          // Polyline을 지도에 추가
          polyline.setMap(map);
      }

      //저장된 좌표를 사용하여 지도에 선 그리기
      drawPolylineOnMap(drawnPathCoordinates);
    },
    search() {
      // 검색 로직은 여기에 작성
      // 예: this.searchNearbyPlaces(this.searchInput, currentLocation)
    },
    // 나머지 필요한 메서드들은 여기에 작성
  },
};
</script>

<style scoped>
/* 부트스트랩 클래스나 추가적인 스타일은 여기에 작성 */
</style>


