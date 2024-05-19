<script setup>
import router from "@/router";

const goBack = () => {
  router.go(-1);
};
</script>
<template>
  <div class="error-container">
    <div class="error-panel server-error error-404">
      <img src="//static.hdslb.com/error/very_sorry.png" />
      <div style="text-align: center; padding: 0 0 40px 0">
        <a
          @click="goBack"
          class="rollback-btn"
          style="
            padding: 0 20px;
            float: none;
            background-color: #007bff;
            color: #fff;
            border: 1px solid #007bff;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            line-height: 36px;
            cursor: pointer;
          "
          >返回上一页</a
        >
      </div>
    </div>
    <div class="error-split" id="up"></div>
    <div class="error-manga">
      <img :src=item.img />
    </div>
  </div>
</template>

<script>
  const item={img:require('@/assets/img/ErrorManga.png')}
</script>

<style scopeed>
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #5b2528;
}
</style>
