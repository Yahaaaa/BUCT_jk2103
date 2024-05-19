<template>
  <div class="user-homepage">
    <aside class="sidebar">
      <div class="profile">
        <img :src="user.avatarUrl" alt="User Avatar" class="avatar" />
        <h1 style="color: #ffffff">{{ user.username }}</h1>
        <div v-if="user.Isbanned" class="banned-container">
          <span class="banned-text">您已被禁止评论</span>
          <img src="@/assets/img/BannedIcon.png" alt="Banned Icon" class="banned-icon" />
        </div>
        <div class="btn">
          <button @click="EditProfile" class="btn">修改个人信息</button>
        </div> <div class="btn">
            <button @click="ReturnToHomePage" class="btn">返回主页</button>
        </div>
        <div class="btn">
          <router-link to="/home" tag="el-menu-item">
            <button @click="logout">退出登录</button>
          </router-link>
        </div>
      </div>
      <!-- 其他个人信息展示 -->
    </aside>

    <main class="comments-main" >
      <h1 >用户评论</h1>

      <div v-for="comment in displayedComments" :key="comment.id" class="comment">
        <div class="comment-artifact" >
          <img
            :src="comment.artifact_image"
            alt="Artifact Image"
            class="artifact-image"
            @click="openArtifactPage(comment.artifact_id)"
          />
          <div style="margin-left: 20px">
            <div style="margin-bottom: 20px" class="artifact-info">
              <h2>{{ comment.artifact_name }}</h2>
            </div>
            <div class="comment-text" style="width: 600px;" >{{ comment.content }}</div>
          </div>
        </div>
        <p class="comment-time" style="margin-top: 20px">创建时间: {{ comment.create_time }}</p>
      </div>
      <p v-if="comments.length === 0">该用户还没有发表任何评论。</p>
      <el-pagination
        v-if="comments.length > 0"
        layout="prev, pager, next"
        :total="comments.length"
        :page-size="pageSize"
        @current-change="handlePageChange"
        :style="{ color: 'red' }"
      />
    </main>
  </div>
</template>

<script>
import base from "@/api/path";
import axiosInstance from "@/utils/request";
import dayjs from "dayjs";
import { computed, inject } from "vue";
import { useStore } from "vuex";

export default {
  name: "UserHomepage",
  data() {
    return {
      user: {},
      comments: [],
      currentPage: 1,
      pageSize: 4,
    };
  },
  created() {
    this.store.dispatch("getUser");
    const user = computed(() => this.store.state.user);

    if (!user.value || !user.value.id) {
      this.$message.error("用户未登录");
      this.$router.push("/login");
    } else {
      this.fetchComments();
    }
  },
  computed: {
    displayedComments() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.comments.slice(startIndex, endIndex);
    },
  },
  methods: {
    EditProfile() {
      this.$router.push("/user_info_change");
    },
    ReturnToHomePage() {
      this.$router.go(-1);
    },
    fetchComments() {
      const user = computed(() => this.store.state.user);
      const userId = user.value.id;
      const Url = inject("$Url");
      axiosInstance
        .get(Url + `/user/space/${userId}`)
        .then((response) => {
          const userData = response.data.data;
          // 获取用户信息
          this.user = {
            id: userData.id,
            username: userData.username,
            avatarUrl: userData.avatarUrl,
            Isbanned: userData.isBanned,
            // 其他用户信息属性
          };

          // 处理评论信息
          this.comments = userData.comments.map((comment) => ({
            id: comment.id,
            user_id: comment.userId,
            artifact_id: comment.artifactId,
            content: comment.content,
            create_time: comment.createTime,
            artifact_name: "", // 先设置为空
            artifact_image: "", // 先设置为空
          }));

          // 获取评论对应的 artifact 信息
          this.comments.forEach((comment) => {
            axiosInstance
              .get(Url + "/artifact/" + comment.artifact_id)
              .then((response) => {
                const artifactData = response.data.data.artifact;
                // 更新评论中的 artifact_name 和 artifact_image
                comment.artifact_name = artifactData.artifactNameChinese;
                comment.artifact_image = artifactData.imageUrl;
              })
              .catch((error) => {
                console.error("Error fetching artifact details:", error);
              });
          });
        })
        .catch((error) => {
          console.error("Error fetching comments:", error);
        });
    },
    handlePageChange(newPage) {
      this.currentPage = newPage;
    },
    logout() {
      this.store.commit("setUser", null); // 清空 store.state.user
      this.$router.push("/home"); // 跳转到 /home 路由
    },
    openArtifactPage(artifactId) {
      this.$router.push(`/artifact/${artifactId}`);
  }
  },
  setup() {
    const store = useStore();

    return {
      store,
    };
  },
};
</script>
<style scoped>
.user-homepage {
  display: flex;
}

.sidebar {
  min-width: 230px;
  max-width: 230px;
  padding: 20px;
  background-color:#1d2659;
}

.profile {
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border-radius: 50%;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.comments-main {
  flex-grow: 1;
  padding: 30px;
  min-width: 1300px;
  max-width: 1300px;
  padding-left: 150px;
  padding-right: 130px;
  width: 90%;
}

.comment {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  word-wrap: break-word;
  width: 90%;
}

.comment-artifact {
  display: flex;
  align-items: start;
}

.artifact-image {
  width: 150px;
  height: 150px;
  margin-right: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out; /* 添加过渡效果 */
}
.artifact-image:hover {
  transform: scale(1.05); /* 放大图片 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 增加阴影效果 */
}
.artifact-info h2 {
  margin: 0;
  color: #333;
}

.comment-text {
  font-size: 16px;
  white-space: pre-wrap;
  line-height: 1.5;
  color: #555;
}

.comment-time {
  font-size: 12px;
  color: #888;
}
@keyframes shake {
  0% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  75% {
    transform: translateX(5px);
  }
  100% {
    transform: translateX(0);
  }
}
.banned-container {
  display: flex;
  align-items: center;
  background-color: #ff0000;
  color: #ffffff;
  padding: 5px 10px;
  border-radius: 4px;
  animation: shake 0.5s infinite;
}
#background {
  background-color: #d5e5f2;
  width: 100%;
  height: 100%;
  background-size: 100% 100%;
  min-height: 800px;
}
.banned-text {
  color: white;
  margin-right: 5px;
}

.banned-icon {
  width: 50px;
  height: 50px;
}
/*body {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  font-family: 'Roboto', sans-serif;
  color: #333;
}*/
button {
  padding: 8px 16px;
  background-color: #c3cfe2;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0077b6;
}
</style>
