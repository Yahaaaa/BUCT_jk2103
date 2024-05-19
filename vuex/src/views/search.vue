<template>
  <div class="app">
    <el-container class="home-container">
      <el-header
        ><!-- 头部区 -->
        <div class="bbk">
          <el-page-header @back="goBack">
            <template #content>
              <span class="text-large font-600 mr-3"> 文物检索 </span>
            </template>
          </el-page-header>
        </div>
      </el-header>
      <div >
        <div class="search-nav">
          <span
            :class="{ active: searchType === 'artifact' }"
            @click="setSearchType('artifact')"
            >文物名称</span
          >
          <span>|</span>
          <span
            :class="{ active: searchType === 'museum' }"
            @click="setSearchType('museum')"
            >博物馆</span
          >
          <span>|</span>
          <span
            :class="{ active: searchType === 'relicTime' }"
            @click="setSearchType('relicTime')"
            >文物年代</span
          >
        </div>
        <div class="input-wrapper">
          <div class="search-container">
            <input input type="text" placeholder="输入搜索内容..." v-model="searchInput" />
            <button class="search1" @click="search">搜索</button>
            <el-button
              size="large"
              style="margin-left: 10px"
              class="search2"
              :icon="Search"
              round
              @click="$router.push('/advanced_search')"
              >Advanced Search</el-button
            >
          </div>
        </div>

        <div>
          <div class="dropdown" style="display: flex;gap:50px">
            <el-dropdown class="el-dropdown-menu">
              <el-button style="width: 200px">
                {{ se_material || '材质' }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-for="material in materials" :key="material" v-model="se_material" @click="material_click(material)">
                    {{ material }}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-dropdown class="el-dropdown-menu">
              <el-button style="width: 200px">
                {{ se_relic || '年代' }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-for="relic in relics" :key="relic" @click="relic_click(relic)">
                    {{ relic }}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-dropdown class="el-dropdown-menu">
              <el-button style="width: 200px">
                {{ se_museum || '博物馆' }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-for="museum in museums" :key="museums" @click="museum_click(museum)">
                    {{ museum }}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
      <div class="changeTime_container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          style="margin-left: 260px"
        />
        <div class="changeTime" style="width: 120px" @click="changeTime">
          时间顺序
          <div class="box-icon">
            <div class="up" :class="{ 'opacity-5': time_status === 1 }"></div>
            <div class="down" :class="{ 'opacity-1': time_status === 1 }"></div>
          </div>
        </div>
      </div>
      <main class="main-content" style="font-family: 宋体, SimSun, sans-serif">
        <div class="item" v-for="item in items" :key="item.id">
          <el-card class="card" style="width: 1088px; flex: 1">
            <div style="display: flex">
              <div style="flex: 1; padding-right: 5px">
                <RouterLink :to="'/artifact/' + item.id">
                  <img
                    :src="item.imageUrl"
                    @mouseover="activeIndex = item.id"
                    @mouseleave="activeIndex = -1"
                    :class="{ active: activeIndex === item.id }"
                  />
                </RouterLink>
              </div>
              <div
                style="flex: 2; display: flex; flex-direction: column; margin-left: 40px"
              >
                <div style="text-align: left; font-size: 30px; font-weight: 700">
                  <RouterLink :to="'/artifact/' + item.id" style="text-decoration: none">
                    <p style="color: black">{{ item.artifactNameChinese }}</p>
                  </RouterLink>
                </div>
                <div class="info-container">
                  <div class="info-item">
                    <span class="title">藏品时代:</span>

                    <span class="content">{{ item.relicTime }}</span>
                  </div>
                  <div class="info-item">
                    <span class="title">材质:</span>
                    <span class="content">{{ item.materialChinese }}</span>
                  </div>
                  <div class="info-item">
                    <span class="title">规格:</span>
                    <span class="content">{{ item.sizeChinese }}</span>
                  </div>

                  <div class="text-container">
                    <span class="title">简介:</span>
                    <span class="text" :style="{ '-webkit-line-clamp': clampLines }">
                      {{item.descriptionChinese }}</span>
                  </div>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </main>
      <el-backtop :right="100" :bottom="100" style="color: #8c8c45" />
    </el-container>
  </div>
</template>
<script setup>
import {ArrowDown, Search} from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { ref, onMounted, computed,watch  } from "vue";
import Mock from "mockjs";
import axios from "axios";
import { inject } from "vue";
const materials = ref([
  '不限材质','铜','纸','玉','陶','石','玻璃','瓷','木','银','玉器','书画','工艺美术精品','金、银器','青铜器','象牙','纺织品','骨','羊毛','石器','黄铜'
]);
const artifacts=ref([]);
const relics = ref([
  '不限年代','新石器时代', '夏', '商', '西周','东周','春秋','战国','战国中晚期', '秦' ,'汉', '西晋', '北魏', '北齐','隋', '唐' ,'宋','金' ,'元' ,'明' ,'清'
]);
const museums = ref([
  '不限博物馆','山西博物馆','湖北博物馆','民族学博物馆','亚洲艺术博物馆','伊斯兰艺术博物馆','阿姆斯特丹国立博物馆'
]);
const se_material = ref('');
const se_relic = ref('');
const se_museum=ref('');
watch([se_material, se_relic, se_museum], ([material, relic, museum]) => {
  let temp=ref([]);
  temp.value=res_artifacts.value
  console.log(material)
  if (material != ""&& material!="材质") {
    temp.value = temp.value.filter((artifact) => {
      return artifact.materialChinese === material
    });
  }
  if(relic != ""&&relic != '年代'){
    console.log("!!!!!!!!!"+relic)
    console.log("relic")
    temp.value = temp.value.filter((artifact) => {
      return artifact.relicTime === relic;
    });
  }

  if(museum != ""&& museum!='博物馆'){
    temp.value = temp.value.filter((artifact) => {
      console.log("!!!!!!!!!"+museum)
      console.log("museum")

      return artifact.libraryChinese === museum;
    });
  }
  total.value=temp.value.length
  artifacts.value=temp.value;
});

const material_click = (option) => {
  se_material.value=option
  if(se_material.value=="不限材质") se_material.value="材质"
};

const relic_click = (option) => {
  se_relic.value=option
  if(se_relic.value=="不限年代") se_relic.value="年代"
};
const museum_click=(option)=>{
  se_museum.value=option
  if(se_museum.value=="不限博物馆") se_museum.value="博物馆"
}

const value = ref('')

const Url = inject("$Url");
const clampLines = 4;
const total = ref(0);
const router = useRouter();

const searchInput = ref("");
const searchType = ref("artifact");
const activeIndex = ref(-1);
const nowUrl = ref("");

/*默认0为升序 1为降序*/
const time_status = ref(0);

const goBack = () => {
  router.go(-1);
};

onMounted(searchAll);

const res_artifacts = ref([]);

const currentPage = ref(1);
const pageSize = ref(5);
const background = ref(false);
const disabled = ref(false);
const startIndex = ref(1);
const handleSizeChange = (val) => {
  pageSize.value = val;
};
const handleCurrentChange = (val) => {
  currentPage.value = val;
};
const items = computed(() => {
  startIndex.value = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex.value + pageSize.value;
  return artifacts.value.slice(startIndex.value, endIndex);
});

async function changeTime() {
  time_status.value = time_status.value ^ 1;
  const res = await axios.get(nowUrl.value + time_status.value);
  res_artifacts.value = res.data.data;
  artifacts.value = res.data.data;
  total.value = res.data.total;
}

async function searchAll() {
  nowUrl.value = Url + "/searchAll?order=";
  const res = await axios.get(nowUrl.value + time_status.value);
  res_artifacts.value = res.data.data;
  artifacts.value = res.data.data;
  total.value = res.data.total;
}

const setSearchType = (type) => {
  searchType.value = type;
};

const search = async () => {
  let url = "";
  console.log(searchType.value);
  switch (searchType.value) {
    case "artifact":
      url = Url + "/artifact?artifactName=";
      break;
    case "museum":
      url = Url + "/search_museum?museumName=";
      break;
    case "relicTime":
      url = Url + "/search_relicTime?relicTime=";
      break;
  }
  nowUrl.value = url + searchInput.value + "&" + "order=";
  url = nowUrl.value + time_status.value;
  console.log(url);
  const res = await axios.get(url);
  res_artifacts.value = res.data.data;
  artifacts.value = res.data.data;
  total.value = res.data.total;
};
</script>

<style scoped>

.dropdown{
  background-color: #ffffff ;
  width: 100%; /* 设置最大宽度 */

  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}

.el-dropdown-menu {
  max-height: 130px; /* 设置菜单高度为200px */
  overflow-y: auto; /* 设置滚动条 */
}

.el-dropdown-menu::-webkit-scrollbar {
  width: 6px; /* 滚动条宽度 */
}

.el-dropdown-menu::-webkit-scrollbar-thumb {
  background-color: #c1c1c1; /* 滚动条颜色 */
  border-radius: 5px; /* 滚动条边角弧度 */
}

.active {
  transform: scale(1.1);
}
img {
  transition: transform 0.3s ease-in-out;
}

.changeTime_container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.changeTime {
  padding: 0 20px;
  height: 32px;
  color: #505363;
  font-weight: bold;
  font-size: 14px;
  margin-left: auto;
  margin-right: 25px;
  display: flex;
  line-height: 32px;
  cursor: pointer;
}
.right:hover {
  color: #217aff;
  border: 1px solid #217aff;
}
.opacity-5 {
  opacity: 0.5;
}
.opacity-1 {
  opacity: 1 !important;
}
.box-icon {
  height: 30px;
  margin-top: 7px;
}
.box-icon .up {
  width: 0;
  height: 0;
  border-bottom: 6px solid #a3a5b3;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  margin-bottom: 4px;
}
.box-icon .down {
  width: 0;
  height: 0;
  opacity: 0.5;
  border-top: 6px solid #a3a5b3;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
}

.text-container {
  display: flex;
  font-size: 20px;
}

.text {
  width: 500px;
  text-align: left;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis; /*省略号设置*/
  -webkit-line-clamp: 4; /* 这里的数字 3 表示最多显示三行，可以根据需要进行调整 */
  line-height: 1.5;
  max-height: 6em;
  /* 这里的数字 4.5em 表示最多显示三行，每行的高度为 1.5em，可以根据需要进行调整 */
}

.info-container {
  display: flex;
  flex-direction: column;
}

.info-item {
  font-size: 20px;
  display: flex;
}

.title {
  font-weight: bold;
  width: 100px;
  text-align: left;
}

.search2:hover {
  background-color: #f2f2f2; /* 设置鼠标悬停时的背景颜色为浅红色 */
  color: #888;
}

.main-content {
  display: flex;
  flex-direction: column;
}
.main-content > div {
  flex: 1;
}

.search-nav {
  display: flex;
  justify-content: center;
  background-color: #ffffff;
}

.search-nav span {
  color: rgb(128, 127, 128); /* 默认颜色为灰色 */
  cursor: pointer;
  padding: 0.5rem; /* 增加点击区域 */
}

.search-nav span.active {
  color: #cccc5c; /* 激活状态的颜色 */
}
el-page-header_header {
  margin-top: 10px;
}
el-header {
  height: 80px;
  background-color: #ececec;
}

.card {
  flex: 1;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
  margin-top: 15px;
  margin-left: 56px;
  margin-right: 56px;
}
/*:deep(.el-card__body){
  padding:0;
}*/

.text-large {
  font-size: 15px;
}

.app {
  background-color: #5b2528;
  display: flex;
}
.home-container {
  height: 100%;
  min-height: 1500px;
  max-width: 1200px; /* 设置最大宽度 */
  min-width: 320px; /* 设置最小宽度 */
  background-color: #ececec; /* 底色 */
  margin: auto;
  width: 50%;
  flex-direction: column; /* 设置为垂直方向布局 */
}

.bbk {
  margin-top: 20px;
}
img {
  margin-top: 20px;
  margin-bottom: 20px;
  width: 350px;
  height: 250px;
  object-fit: contain;
}

.search-container {
  background-color: #ffffff;
  height: 100px;
  width: 100%; /* 设置最大宽度 */
  //margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.search-container input {
  height: 50px;
  box-sizing: border-box;
  outline: none;
  border: none;
  width: 500px;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  padding: 0 20px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.item {
  flex-basis: calc(50% - 10px); /* 动态计算宽度减去间隔 */
}
.search-container .search1 {
  cursor: pointer;
  height: 50px;
  padding: 0 30px;
  border: none;
  box-sizing: border-box;
  background-color: #5b2528;
  font-size: 24px;
  color: #fff;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>
