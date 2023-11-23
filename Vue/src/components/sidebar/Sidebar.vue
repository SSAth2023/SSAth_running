<template>
  <div id="app">
    <div class="slide-bar" :style="{ marginLeft: slideBarWidth }">
      <div id="wrapper">
        <SidebarHeader />
        <hr />
        <RouterView name="sidebar" />
        <RouterView name="comment" />
        <hr />
      </div>
      <SidebarFooter style="width: 100%" />
    </div>
    <button class="toggle-button" @click="toggleSlideBar" :style="{ marginLeft: buttonMargin }">
      <i v-if="slideBarWidth !== '1px'" class="bi bi-filter-left"></i>
      <!-- <img v-if="slideBarWidth !== '1px'" src="../../assets/image/free-icon-right-arrow-271228.png"
        alt="Open Slide Bar" /> -->
      <i v-if="slideBarWidth === '1px'" class="bi bi-list"></i>
    </button>
  </div>
</template>

<script>
const RunningPathList = () => import("../runningpath/RunningPathList.vue");
import SidebarFooter from "./SidebarFooter.vue";
import SidebarHeader from "./SidebarHeader.vue";

export default {
  data() {
    return {
      slideBarWidth: "-21vw",
      buttonMargin: "0.95vw",
    };
  },
  methods: {
    toggleSlideBar() {
      this.slideBarWidth = this.slideBarWidth === "1px" ? "-21vw" : "1px";
      this.buttonMargin = this.buttonMargin === "0.95vw" ? "21.95vw" : "0.95vw";
    },
  },
  components: { SidebarHeader, RunningPathList, SidebarFooter },
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
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  width: 22vw;
  margin: 1% 0 1% 0;
  height: 95%;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: white;
  border: 1px double #818181;
  overflow-x: hidden;
  transition: margin-left 0.5s;
}

.slide-bar::-webkit-scrollbar {
  display: none;
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
  background-color: white;
  padding-top: 5px;
  position: fixed;
  top: 50%;
  margin-top: 1vw;
  transform: translateY(-50%);
  cursor: pointer;
  border-top: 1px solid;
  border-right: 1px solid;
  border-bottom: 1px solid;
  border-left: 1px solid white;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
  outline: none;
  display: flex;
  align-items: center;
  z-index: 3;
  transition: margin-left 0.5s;
  width: 1.5vw;
  height: 6vh;
}

#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: 15vh;
}

.toggle-button img {
  width: 2vw;
  height: 2vw;
  margin-right: 1vw;
}
</style>
