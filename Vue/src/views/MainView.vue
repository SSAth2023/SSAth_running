<template>
  <div>
    <!-- <Sidebar /> -->
    <RouterView />
  </div>
</template>

<script setup>
import Sidebar from "../components/sidebar/Sidebar.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

onMounted(() => {
  router.push("/path");
});
</script>

<style scoped></style>
