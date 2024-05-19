<template>
  <div class="bbk">
    <el-page-header @back="goBack">
      <template #content>
        <span class="text-large font-600 mr-3"> 文物详情 </span>
      </template>
    </el-page-header>
  </div>
  <div class="main" v-for="artifact in Artifact" :key="artifact.artifact.id">
    <div class="container">
      <div class="header">{{ artifact.artifact.artifactNameChinese }}</div>
      <div class="content">
        <div class="image-container">
          <!-- <img :src="artifact.artifact.imageUrl" alt="Artifact Image" /> -->
          <VueMagnifier class="img" :src="artifact.artifact.imageUrl" width="600" />
        </div>
        <div class="info-container">
          <p style="font-size: larger">
            <strong>藏馆:</strong> {{ artifact.artifact.libraryChinese }}
          </p>
          <p style="font-size: larger">
            <strong>材质:</strong> {{ artifact.artifact.materialChinese }}
          </p>
          <p style="font-size: larger">
            <strong>尺寸:</strong> {{ artifact.artifact.sizeChinese }}
          </p>
          <p style="font-size: larger">
            <strong>简介:</strong> {{ artifact.artifact.descriptionChinese }}
          </p>
          <div class="RelatedArtifact-container">
            <button @click="showRelatedArtifact()" class="dropdown-btn">
              点击此处查看相关文物 &nbsp; &nbsp; &nbsp; &nbsp;
            </button>
          </div>
        </div>
      </div>
      <RouterView />
      <div class="footer">
        <div class="comment-container">
          <input type="text" v-model="newComment" placeholder="发表评论..." />
          <button @click="addComment(artifact)">发送</button>
        </div>
      </div>
      <div class="aside">
        <div class="comment-list">
          <div
            class="comment"
            v-for="comment in artifact.artifact.comments"
            :key="comment.id"
          >
            <!-- {{ artifact.artifact.comments }} -->
            <div style="display: flex">
              <div class="ava-container">
                <img :src="comment.avatarUrl" :alt="comment.userName" />
                <!-- <RouterLink :to="'/user/' + comment.userId">
                  <img :src="user.avatarUrl" :alt="user.username" />
                </RouterLink> -->
              </div>
              <div style="display: flex; flex-direction: column">
                <p style="font-weight: bold">
                  <strong>{{ comment.userName }}: </strong>
                </p>
                <p style="font-size: large">{{ comment.content }}</p>
              </div>
            </div>
            <div style="width: 600px">
              <p>{{ comment.createTime }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { RouterView, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { useStore } from "vuex";
import { inject, ref, onMounted, computed, provide, watch } from "vue";
import { ElMessage, ElNotification } from "element-plus"; // 使用 Element Plus 的消息提示
import VueMagnifier from "@websitebeaver/vue-magnifier";
import "@websitebeaver/vue-magnifier/styles.css";

const goBack = () => {
  router.go(-1);
};

const showRelated = ref(false);
const Url = inject("$Url");
const router = useRouter();
const Artifact = ref([]);
const newComment = ref("");
const zoomRef = ref(null); //zoomRef
const store = useStore();
store.dispatch("getUser");
const user = computed(() => store.state.user);
const isLoggedIn = computed(() => !!store.state.user);
const UserId = ref("0");
const route = useRoute();
if (isLoggedIn.value) {
  UserId.value = user.value.id;
}
provide("$Artifact", Artifact);
console.log("nmsl");
console.log(UserId.value);
console.log("nmsl");
watch(
  () => route.params.id,
  async (newId) => {
    // 在这里重新获取文物数据
    const res = await axios.get(Url + `/artifact/${newId}`);
    Artifact.value.pop();
    Artifact.value.push(res.data.data);
  }
);
onMounted(async () => {
  const res = await axios.get(Url + router.currentRoute.value.path);
  console.log(res.data.data);
  console.log(1);
  Artifact.value.push(res.data.data);

  if (zoomRef.value) {
    const zoomInstance = zoomRef.value;
    zoomInstance.init();
  }
});
provide("showRelated", showRelated);
const showRelatedArtifact = () => {
  if (showRelated.value == true) {
    return;
  } else {
    console.log("nmsl");
    console.log("/artifact/" + route.params.id + "/related");
    console.log("nmsl");
    router.push("/artifact/" + route.params.id + "/related");
    showRelated.value = true;
  }
};
const addComment = async (item) => {
  if (UserId.value === "0") {
    router.push("/login");
    return;
  }
  console.log(item);
  if (newComment.value.trim() !== "") {
    const res = await axios.post(Url + "/artifact/" + item.artifact.id, {
      artifactId: item.artifact.id,
      userId: UserId.value,
      content: newComment.value,
      // createTime: "nmsl",
      avatarUrl: user.value.avatarUrl,
      userName: user.value.username,
    });
    console.log("nmsl");
    console.log(res.data);
    if (res.data.code === "503") {
      ElMessage.error(res.data.msg);

      return;
    }
    if (res.data.code === "501") {
      ElMessage.error(res.data.msg);

      return;
    }
    if (res.data.code === "-1") {
      ElMessage.error(res.data.msg);

      return;
    }
    // Assuming the response includes the updated artifact object
    const updatedArtifact = res.data.data;

    console.log("nmsl");
    console.log(2);
    console.log(updatedArtifact);
    Artifact.value.pop();
    Artifact.value.push(updatedArtifact);
    newComment.value = "";
  }
};
</script>
<style scoped>

el-header {
  height: 80px;
  background-color: #ececec;
}


.dropdown-btn {
  background-color: #1d2659; /* 按钮背景色 */
  color: white; /* 文字颜色 */
  padding: 12px 24px; /* 按钮内边距 */
  font-size: 16px; /* 文字大小 */
  border-radius: 20px; /* 去除边框 */
  cursor: pointer; /* 鼠标指针样式 */
  position: relative; /* 相对定位 */
}

/* 添加向下箭头 */
.dropdown-btn::after {
  content: "\25BC"; /* Unicode 编码，表示向下箭头 */
  position: absolute; /* 绝对定位 */
  top: 50%; /* 置于垂直方向的中间 */
  right: 20px; /* 离按钮右侧的距离 */
  transform: translateY(-50%); /* 上移自身高度的一半，使箭头垂直居中 */
}
/* 鼠标悬停在按钮上时改变背景色 */
.dropdown-btn:hover {
  background-color: #d5e5f2;
}
.RelatedArtifact-container {
  position: absolute;
  bottom: 0;
  right: 0;
}
.main {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  min-height: 1200px;
  /* height: inherit; */
  /* box-sizing: border-box; */
  background-color: #d5e5f2;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.container {
  display: flex;
  flex-direction: column;
  width: 100%; /* 修改为100% */
}

.header {
  background-color: #1d2659;
  color: white;
  font-size: 24px;
  padding: 20px;
  text-align: center;
  margin-bottom: 20px;
}

.content {
  display: flex;
  padding: 20px;
  background-color: #f5f5dc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  height: auto; /* 修改为auto */
}
.image-container {
  margin-right: 20px;
  width: 600px;
  height: 600px;
  display: flex;
  justify-content: center; /* 修改为stretch */
  align-items: stretch; /* 修改为stretch */
  border: 2px solid #8b4513;
  border-radius: 10px;
  overflow: hidden;
}

.image-container .img {
  max-width: 100%;
  height: 100%; /* 修改为100% */
  object-fit: contain;
  border-radius: 10px;
}

.info-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  position: relative;
}

.footer {
  background-color: #f5f5dc;
  padding: 8px;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 0px;
}

.comment-container {
  display: flex;
  align-items: center;
  border: 0;
}

.comment-container input[type="text"] {
  flex: 1;
  padding: 10px;
  border: 1px solid #8b4513;
  border-radius: 5px;
}

.comment-container button {
  padding: 10px 20px;
  background-color: #8b4513;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.comment-container button:hover {
  background-color: #a0522d;
}

.aside {
  background-color: #1d2659;
  padding: 20px;
  width: auto;
  /* max-height: 200px; */
  /* margin-right: auto; */
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
  height: calc(100% - 380px);
}

.comment {
  display: flex;
  flex-direction: column;
  padding: 10px;
  background-color: #f2e4c9;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}
.ava-container {
  /* flex: 0.15; */
  margin-right: 20px;
  width: 100px; /* 设置固定宽度 */
  height: 100px; /* 设置固定高度 */
  min-width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 2px solid #8b4513; /* 添加边框样式 */
  border-radius: 10px;
  overflow: hidden; /* 隐藏溢出内容 */
}
.ava-container img {
  max-width: 100%;
  max-height: 100%;
  width: auto;
  height: auto;
}
</style>
