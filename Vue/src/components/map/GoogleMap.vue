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
      // Google Maps API와의 통합 초기화 로직은 여기에 작성
      // 예: this.map = new google.maps.Map(...)
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
