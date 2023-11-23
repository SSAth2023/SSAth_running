<template>
  <footer class="footer mt-auto py-3 bg-body-secondary">
    <div class="container flex-center">
      <div class="col align-items-center">
        <div class="text-center">
          <p class="p2 text-muted">박영규 | 이민형</p>
          <p class="copy text-muted small m-0 mt-2" style="font-size: 13px; opacity: 0.5">
            &copy; 2023 Your Company. All rights reserved.
          </p>
        </div>
      </div>
      <div class="box">
        <a href="https://github.com/SSAth2023/SSAth_running" target="_blank" class="boxitem" title="SSAth GitHub">
          <img class="img-fluid" src="@/assets/image/github.png" />
          <!-- <p class="m-0">GitHub</p> -->
        </a>
        <a href="https://www.instagram.com/" target="_blank" class="boxitem" title="SSAth Instagram">
          <img class="img-fluid" src="@/assets/image/instagram.png" />
          <!-- <p class="m-0">Instagram</p> -->
        </a>
        <a href="https://zircon-sage-fe0.notion.site/SSAth-Project-8633c2fa6ef94ed0bb7542534a48fc72" target="_blank"
          class="boxitem" title="SSAth teamspace">
          <img class="img-fluid" src="@/assets/image/notion.png" />
          <!-- <p class="m-0">Notion</p> -->
        </a>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.p2 {
  margin: 0;
  text-align: center;
  font-size: 14px;
}

.box {
  display: flex;
  align-items: center;
  margin-bottom: 1vh;
  margin-top: 1vh;
}

.footer {
  position: fixed;
  bottom: 0;
  height: 20vh;
  padding: 0;
  position: relative;
}

.boxitem {
  width: 33%;
}

a {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 3px;
}

img {
  width: 25px;
}

.flex-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
