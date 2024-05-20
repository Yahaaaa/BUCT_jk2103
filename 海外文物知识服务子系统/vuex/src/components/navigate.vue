<template>
  <div class="h-6" />
  <el-menu
    style="border: 0px"
    :default-active="activeIndex2"
    class="el-menu-demo"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <div class="left-items">
      <router-link to="/home" tag="el-menu-item" style="text-decoration: none">
        <el-menu-item index="1">海外文物服务知识子系统</el-menu-item>
      </router-link>
      <router-link to="/home" tag="el-menu-item" style="text-decoration: none">
        <el-menu-item index="4">首页</el-menu-item>
      </router-link>
      <el-menu-item index="4" @click="Tozstp" style="text-decoration: none">
        文物知识图谱
      </el-menu-item>


      <router-link to="/time_line" tag="el-menu-item" style="text-decoration: none">
        <el-menu-item index="4">文物时间轴</el-menu-item>
      </router-link>
      <router-link to="/search" tag="el-menu-item" style="text-decoration: none">
        <el-menu-item index="4">文物查询</el-menu-item>
      </router-link>

      <el-menu-item index="4" @click="Toquest" style="text-decoration: none">
        问答系统
      </el-menu-item>

      <el-menu-item index="4" @click="Toback" style="text-decoration: none">
        后台管理
      </el-menu-item>
    </div>
    <div class="right-items">
      <router-link
        v-if="!isLoggedIn"
        to="/login"
        tag="el-menu-item"
        index="4"
        style="flex: 0 0 auto; text-align: right;text-decoration: none"
      >
        <el-menu-item index="4" style="text-align: right;">登录</el-menu-item>
      </router-link>
      <router-link
        v-else
        to="/user"
        tag="el-menu-item"
        index="4"
        style="flex: 0 0 auto; text-align: right"
      >
        <el-menu-item index="4" style="text-align: right">个人中心</el-menu-item>
      </router-link>
    </div>
  </el-menu>
</template>

<script setup>
import { computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useStore();
const isLoggedIn = computed(() => !!store.state.user);
const Toquest=()=>{
  window.open('http://10.16.2.168:3101/', '_self')
};
const Tozstp = () => {
  window.open('http://10.16.2.168:7474/browser/', '_self')
};
const Toback=()=>{
  window.open('http://10.16.2.168:3100', '_self')
}
</script>

<style scoped>
router-link{
  text-decoration: none
}
.el-menu-demo {
  display: flex;
  justify-content: space-between;
}
.left-items {
  display: flex;
}
.right-items {
  display: flex;
  margin-right: 70px;
}
</style>