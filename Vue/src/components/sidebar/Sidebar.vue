<template>
  <div id="app">
    <div class="slide-bar" :style="{ marginLeft: slideBarWidth }">
      <!-- 슬라이드바 컨텐츠 -->
    </div>
    <button
      class="btn toggle-button"
      @click="toggleSlideBar"
      :style="{ marginLeft: buttonMargin }"
    >
      <img
        src="../../assets/image/free-icon-right-arrow-271228.png"
        alt="Open Slide Bar"
      />
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slideBarWidth: "-30vw",
      buttonMargin: "1vw",
    };
  },
  methods: {
    toggleSlideBar() {
      this.slideBarWidth = this.slideBarWidth === "0px" ? "-30vw" : "0px";
      this.buttonMargin = this.buttonMargin === "1vw" ? "30vw" : "1vw";
    },
  },
};
</script>

<style scoped>
body {
  margin: 0;
  font-family: "Arial", sans-serif;
  overflow-x: hidden;
}

#app {
  position: relative;
}

.slide-bar {
  height: 100vh;
  width: 30vw;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #111;
  overflow-x: hidden;
  padding-top: 20px;
  transition: margin-left 0.5s;
}

.slide-bar a {
  padding: 8px 8px 8px 2vw;
  text-decoration: none;
  font-size: 2vw;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.slide-bar a:hover {
  color: #f1f1f1;
}

.main-content {
  padding: 16px;
}

.toggle-button {
  position: fixed;
  top: 50%;
  left: 1vw;
  margin-top: 1vw;
  transform: translateY(-50%);
  cursor: pointer;
  background-color: #818181;
  opacity: 0.4;
  padding: 1vw;
  border: none;
  outline: none;
  display: flex;
  align-items: center;
  z-index: 2;
  transition: margin-left 0.5s;
  margin-left: 1vw;
  width: 3vw;
  height: 6vh;
}

.toggle-button img {
  width: 2vw;
  height: 2vw;
  margin-right: 1vw;
}
</style>
