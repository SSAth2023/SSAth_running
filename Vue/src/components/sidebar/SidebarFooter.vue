<template>
    <footer class="footer mt-auto py-3 bg-body-secondary">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-4 text-center">
            <img class="logo2 img-fluid img-thumbnail border border-dark-subtle" src="@/assets/image/S S A T H.png" />
          </div>
          <div class="col-md-4 box">
            <a href="https://github.com/SSAth2023/SSAth_running" target="_blank">
              <img class="img-fluid" src="@/assets/image/github.png" />
            </a>
            <p class="m-0">GitHub</p>
            <a href="https://www.instagram.com/" target="_blank">
              <img class="img-fluid" src="@/assets/image/instagram.png" />
            </a>
            <p class="m-0">Instagram</p>
            <a href="https://zircon-sage-fe0.notion.site/SSAth-Project-8633c2fa6ef94ed0bb7542534a48fc72" target="_blank">
                <img class="img-fluid" src="@/assets/image/notion.png" />
              </a>
              <p class="m-0">Notion</p>
          </div>
          <div class="col-md-4 text-center">
            <p class="p2 text-muted fw-bolder border-top border-bottom bg-light-subtle">
              박영규 | 이민형
            </p>
            <p class="text-muted small m-0 mt-2">
              &copy; 2023 Your Company. All rights reserved.
            </p>
          </div>
        </div>
      </div>
    </footer>
</template>

<style scoped>
    .p2 {
        margin: 0;
        text-align: center;
    }

    .box {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 1vh;
    }

    .footer {
        padding: 0;
    }

    .logo2 {
        width: 10vw;
        height: auto;
    }
</style>
