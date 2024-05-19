<template>
  <div style="padding-left: 430px; padding-top: 120px" id="background">
    <div class="registry-wrapper">
      <div class="header">注册</div>
      <div class="form-wrapper">
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
        />
        <input
            class="input-item"
            type="password"
            name="password"
            placeholder="请确认密码"
            v-model="form.confirm"
        />

        <div style="display: grid; grid-template-columns: repeat(2, 1fr); grid-gap: 15px">
          <bl class="btn" @click="$router.push('/login')">登录</bl>
          <bl class="btn" @click="register">注册</bl>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import request from "@/utils/request";
import { ref } from "vue";
import { useRouter } from "vue-router";

import { inject } from "vue";
export default {
  name: "register",
  setup() {
    const form = ref({
      username: "",
      password: "",
      confirm: ""
    });
    const Url = inject("$Url");
    const router = useRouter(); // 获取路由实例

    const register = () => {
      if (form.value.password !== form.value.confirm) {
        ElMessage({
          type: "error",
          message: "两次密码输入不一致",
        });
        return;
      }
      request.post(Url + "/register", form.value).then((res) => {
        if (res.data.code == 0) {
          ElMessage({
            type: "success",
            message: "注册成功",
          });
          console.log("注册成功");
          router.push("/login");
        } else {
          ElMessage({
            type: "error",
            message: "用户名已被注册",
          });
        }
      });
    };
    return {
      form,
      register,
    };
  },
};
</script>

<style scoped>
html {
  height: 100%;
}
body {
  height: 100%;
}
.registry-wrapper {
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
  background: url("../assets/bg2.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
</style>