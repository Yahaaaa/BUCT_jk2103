<script setup>
import { RouterView, useRoute, useRouter } from "vue-router";
import { inject, ref, onMounted, computed, provide } from "vue";
const router = useRouter();
const route = useRoute();
const showRelated = inject("showRelated");
console.log("NMSL");
console.log(showRelated.value);
console.log("NMSL");
const ArtifactId = route.params.id;
console.log(router.currentRoute.value.path);
const Artifact = inject("$Artifact");
console.log(Artifact.value[0].relatedArtifact[0]);
const artifacts = ref(Artifact.value[0].relatedArtifact);
</script>
<template>
  <div
    style="
      background-color: #f2e4c9;
      display: block;
      align-items: center;
      justify-content: center;
    "
  >
    <el-row>
      <el-col :span="6" v-for="artifact in artifacts" :key="artifact.id">
        <el-card :body-style="{ padding: '10px' }">
          <div class="image-container">
            <img :src="artifact.imageUrl" class="image" />
          </div>
          <div class="card-info-container">
            <h7 style="font-weight: bold">{{ artifact.artifactNameChinese }}</h7>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
              <RouterLink :to="'/artifact/' + artifact.id">
                <el-button @click="showRelated = false" type="text" class="button"
                  >查看详情</el-button
                >
              </RouterLink>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <div style="display: flex; align-items: center; justify-content: center">
      <button
        @click="
          router.push('/artifact/' + ArtifactId);
          showRelated = false;
        "
        class="collapse-button"
      >
        收起列表
      </button>
    </div>
  </div>
</template>
<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  color: #400601;
  float: right;
}
.image-container {
  height: 300px;
  width: 300px;
  overflow: hidden;
}
.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.card-info-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.collapse-button {
  padding: 10px 20px;
  background-color: #8b4513;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px; /* 调整按钮与列表的间距 */
}

.collapse-button:hover {
  background-color: #a0522d;
}
</style>
