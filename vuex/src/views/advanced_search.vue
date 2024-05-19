<script setup>
import { start } from "@popperjs/core";
import axios from "axios";
import Mock from "mockjs";

import { computed, inject, provide, ref,watch } from "vue";
import {ArrowDown} from "@element-plus/icons-vue";

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
  console.log(11111111111)
  console.log(temp.value)
  if(relic != ""&&relic != '年代'){
    console.log("!!!!!!!!!"+relic)
    console.log("relic")
    temp.value = temp.value.filter((artifact) => {
      return artifact.relicTime === relic;
    });
  }
  console.log(relic)
  console.log(2222222222222222)
  console.log(temp.value)

  if(museum != ""&& museum!='博物馆'){
    temp.value = temp.value.filter((artifact) => {
      console.log("!!!!!!!!!"+museum)
      console.log("museum")

      return artifact.libraryChinese === museum;
    });
  }
  console.log(333333333333)
  console.log(temp.value)
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






const res_artifacts = ref([]);
const time_status = ref(0);
const radio = ref(["包含"]);
const activeIndex = ref(-1); //用于指定放大
const Url = inject("$Url");
console.log("Url");
console.log(Url);
const currentPage = ref(1);
const pageSize = ref(5);
const background = ref(false);
const disabled = ref(false);
const startIndex = ref(1);
const isHovered = ref(false);
const total = ref(0);
async function changeTime() {
  time_status.value = time_status.value ^ 1;
  nowbody.value = {
    condition: condition.value,
    order: time_status.value,
  };
  const res = await axios.post(Url + "/advanced_search", nowbody.value);
  res_artifacts.value = res.data.data;
  let temp=ref([]);
  temp.value=res_artifacts.value
  console.log(se_material.value)
  if (se_material.value != ""&& se_material.value!="材质") {
    temp.value = temp.value.filter((artifact) => {
      return artifact.se_materialChinese === se_material.value
    });
  }
  if(se_relic.value != ""&&se_relic.value != '年代') {
    temp.value = temp.value.filter((artifact) => {
      return artifact.se_relicTime === se_relic.value;
    });
  }
  if(se_museum.value != ""&& se_museum.value!='博物馆'){
    temp.value = temp.value.filter((artifact) => {

      return artifact.libraryChinese === se_museum.value;
    });
  }
  total.value=temp.value.length
  artifacts.value=temp.value;
}
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
  return artifacts.value.values();
});
let startCondition = 0;
const option = ref([]);
option.value.push([
  { value: "description", label: "简介" },
  { value: " library_Chinese", label: "博物馆" },
  { value: "relicTime", label: "年代" },
  { value: "artifactName_Chinese", label: "标题" },
  { value: " material", label: "材质" },
]);
const searchText = ref([]);
searchText.value.push("");

const operator = ref([]);
operator.value.push([
  { value: "or", label: "or" },
  { value: "and", label: "and" },
  { value: "not", label: "not" },
]);

const nowbody = ref([
  {
    condition: "",
    order: "",
  },
]);

const value = ref([]);
value.value.push("");

const operatorValue = ref([]);
operatorValue.value.push("");
const cnts = ref(1);
const add = () => {
  radio.value.push("包含");
  option.value.push([
    { value: "description", label: "简介" },
    { value: " library_Chinese", label: "博物馆" },
    { value: "relicTime", label: "年代" },
    { value: "artifactName_Chinese", label: "标题" },
    { value: " material", label: "材质" },
  ]);
  searchText.value.push("");
  operatorValue.value.push("");
  operator.value.push([
    { value: "or", label: "or" },
    { value: "and", label: "and" },
    { value: "not", label: "not" },
  ]);
  value.value.push("");
  cnts.value++;
};
const sub = () => {
  if (cnts.value == 1) return;
  radio.value.pop();
  option.value.pop();
  operatorValue.value.pop();

  value.value.pop();
  operator.value.pop();
  searchText.value.pop();
  cnts.value--;
};

function transCondition(q, x, y, z) {
  console.log(startCondition);
  if (startCondition === 0) {
    if (x === "" || y === "" || z === "") return "";
    startCondition = 1;
    if (q === "not") {
      if (z === "精确") {
        return x + " != '" + y + "' ";
      } else {
        return x + " NOT LIKE" + " '%" + y + "%' ";
      }
    }
    if (z === "精确") {
      return x + " = '" + y + "' ";
    } else {
      return x + " LIKE " + " '%" + y + "%' ";
    }
  }
  if (q === "" || x === "" || y === "" || z === "") return "";
  console.log(q);
  console.log(x);
  console.log(y);
  console.log("zzzz" + z);
  if (q != "not") {
    if (z === "精确") {
      return q + " " + x + " = '" + y + "' ";
    } else {
      return q + " " + x + " LIKE " + " '%" + y + "%' ";
    }
  } else {
    if (z === "精确") {
      return "AND" + " " + x + " != '" + y + "' ";
    } else {
      return "AND" + " " + x + " NOT LIKE" + " '%" + y + "%' ";
    }
  }
}

const condition = ref("");
async function handleSubmit(event) {
  startCondition = 0;
  event.preventDefault();
  condition.value = "";
  console.log("Advanced");
  for (let i = 0; i < cnts.value; i++) {
    condition.value =
      condition.value +
      transCondition(
        operatorValue.value[i],
        value.value[i],
        searchText.value[i],
        radio.value[i]
      );
  }
  console.log(22222222222222);
  console.log(condition.value);
  nowbody.value = {
    condition: condition.value,
    order: time_status.value,
  };
  console.log(nowbody.value);
  const res = await axios.post(Url + "/advanced_search", nowbody.value);
  res_artifacts.value = res.data.data;
  let temp=ref([]);
  temp.value=res_artifacts.value
  console.log(se_material.value)
  if (se_material.value != ""&& se_material.value!="材质") {
    temp.value = temp.value.filter((artifact) => {
      return artifact.se_materialChinese === se_material.value
    });
  }
  console.log("-------------------")
  console.log(temp.value)
  console.log(se_relic.value)
  if(se_relic.value != ""&& se_relic.value != '年代') {
    temp.value = temp.value.filter((artifact) => {
      return artifact.relicTime === se_relic.value;
    });
  }
  console.log(temp.value)
  if(se_museum.value != ""&& se_museum.value!='博物馆'){
    temp.value = temp.value.filter((artifact) => {
      return artifact.libraryChinese === se_museum.value;
    });
  }
  console.log(temp.value)
  total.value=temp.value.length
  artifacts.value=temp.value;
  console.log(artifacts.value)
}
</script>
<template>
  <main>
    <div class="container">
      <h1 style="font-weight: bold">高级搜索</h1>
      <div style="display: flex; justify-content: space-between">
        <el-button round @click="add()">增加条件</el-button>
        <el-button round @click="sub()">删除条件</el-button>
      </div>
      <form @submit="handleSubmit">
        <div
          v-for="cnt in cnts"
          class="input-group"
          style="border-bottom: 2px solid #5b2528"
        >
          <el-select
            v-if="cnt > 1"
            style="width: 70px; font-weight: bold"
            v-model="operatorValue[cnt - 1]"
            placeholder=""
          >
            <el-option
              v-for="item in operator[cnt - 1]"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              style="font-weight: bold"
            >
            </el-option>
          </el-select>
          <el-select
            v-else
            style="width: 70px; visibility: hidden"
            v-model="operatorValue[cnt - 1]"
            placeholder=""
          >
            <el-option
              v-for="item in option[cnt - 1]"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-select style="width: 140px" v-model="value[cnt - 1]" placeholder="请选择">
            <el-option
              v-for="item in option[cnt - 1]"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <input
            v-model="searchText[cnt - 1]"
            type="text"
            :id="value"
            :name="value"
            placeholder="请输入..."
          />
          <div>
            <el-radio-group v-model="radio[cnt - 1]" fill="#5b2528">
              <el-radio-button label="包含"></el-radio-button>
              <el-radio-button label="精确"></el-radio-button>
            </el-radio-group>
          </div>
        </div>

        <div class="input-group">
          <input type="submit" value="搜索" />
        </div>
      </form>
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



    </div>
    <div class="item-container" style="font-family: 宋体, SimSun, sans-serif">
      <div class="item" v-for="item in items" :key="item.id">
        <el-card class="card" style="width: 1200px; flex: 1">
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
                  <span class="text" :style="{ '-webkit-line-clamp': clampLines }">{{
                    item.descriptionChinese
                  }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
      <el-backtop :right="100" :bottom="100" style="color: #8c8c45" />
    </div>
  </main>
</template>

<style scoped>

.dropdown{
  //background-color: ;
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
.changeTime_container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.item-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
/* status */
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
  color: rgb(128, 128, 128); /* 默认颜色为灰色 */
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
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 23px;
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
.el-button {
  border: none;
  width: 120px;
  height: 50px;
  cursor: pointer;
  background-color: #5b2528;
  border-radius: 5px;
  color: white;
  /* font-size: 10px; */
  margin-bottom: 20px;
  margin-left: 0px;
}
main {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  height: inherit;
  box-sizing: border-box;
  background-color: #5b2528;
}
.container {
  max-width: 1200px;
  margin: 20px auto;
  margin-top: 0px;
  display: flex;
  flex-direction: column;

  border: 0px;
  padding: 20px;
  background-color: rgb(247, 243, 240);
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.container h1 {
  font-size: 24px;
  margin-bottom: 20px;
}
.el-radio-button :hover {
  color: #cc8d8f;
  /* background-color: rgba(238, 185, 185, 0.8); */
}
.input-group {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 20px;
}

.input-group .param {
  flex: 0 0 120px;
  text-align: start;
}

.input-group input {
  flex: 1;
  padding: 4px;
  border-radius: 15%;
  border: 1px solid #ccc;
  margin-left: 5px;
}

.input-group input[type="submit"] {
  flex: 1 0 100%;
  background-color: #5b2528;
  color: #fff;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 3px;
}

.input-group input[type="submit"]:hover {
  background-color: #77221b;
}

@media (max-width: 600px) {
  .input-group input {
    flex: 1 0 100%;
  }
}
</style>
