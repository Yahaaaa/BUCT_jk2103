<template>
  <div style="padding-left: 430px; padding-top: 120px" id="background">
    <div class="login-wrapper">
      <div class="header">登录</div>
      <div class="form-wrapper">
        <!-- 使用 v-model 来绑定输入框的值 -->
        <input
          type="text"
          name="username"
          placeholder="用户名"
          class="input-item"
          v-model="form.username"
        />
        <input
          type="password"
          name="password"
          placeholder="密码"
          class="input-item"
          v-model="form.password"
        /><!--本来输入框中内容没有绑定-->
        <div style="display: grid; grid-template-columns: repeat(2, 1fr); grid-gap: 15px">
          <bl class="btn" @click="login()">登录</bl>
          <bl class="btn" @click="$router.push('/register')">注册</bl>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import request from "@/utils/request";
import { ElMessage, ElNotification } from "element-plus"; // 使用 Element Plus 的消息提示
import { inject } from "vue";
import { computed } from "vue";
const router = useRouter();
const form = ref({
  username: "",
  password: "",
});
const Url = inject("$Url");
// const UserId = inject("$UserId");
const store = useStore();
// const isLoggedIn = inject("$isLoggedIn");
function login() {
  request
    .post(Url + "/login", form.value)
    .then((res) => {
      if (res.data.code === "0" && res.data.data) {
        // 登录成功
        console.log(res.data.data);
        console.log(store.state.user);
        store.commit("setUser", res.data.data);

        router.push("/user");
        ElMessage.success("登录成功");
      } else {
        // 登录失败
        const errorMessage = res.data.msg || "登录失败，请检查用户名和密码";
        ElMessage.error(errorMessage);
      }
    })
    .catch((error) => {
      // 网络请求失败
      ElNotification({
        title: "错误",
        message: "请求服务失败，请稍后再试",
        type: "error",
      });
      console.error("登录请求失败:", error);
    });
}
</script>
<style scoped>
html {
  height: 100%;
}
body {
  height: 100%;
}
.login-wrapper {
  background-color: rgba(255, 255, 255, 0.9);
  width: 358px;
  height: 550px;
  border-radius: 15px;
  padding: 0 50px;
  position: absolute;
  left: 50%;
  top: 45%;
  transform: translate(-50%, -50%);
}
.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 200px;
}
.input-item {
  display: block;
  width: 100%;
  margin-bottom: 20px;
  border: 0;
  padding: 10px;
  border-bottom: 1px solid rgb(128, 125, 125);
  font-size: 15px;
  outline: none;
}
.input-item::placeholder {
  text-transform: uppercase;
}
.btn {
  text-align: center;
  padding: 10px;
  margin: 0 auto;
  width: 100%;
  margin-top: 40px;
  background-image: linear-gradient(to right, rgba(10, 2, 5, 0.8), rgb(0, 4, 15, 0.8));
  color: #fff;
}
.btn:hover {
  color: yellow;
}
a {
  text-decoration-line: none;
  color: #abc1ee;
}

#background {
  background: url("../assets/img/background1.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
</style>
