<template>
    <div>
      <el-radio-group v-model="radio1">
        <el-radio-button label="上海"></el-radio-button>
        <el-radio-button label="北京"></el-radio-button>
        <el-radio-button label="广州"></el-radio-button>
        <el-radio-button label="深圳"></el-radio-button>
      </el-radio-group>
    </div>
  </template>
  
  <script>
    export default {
      data () {
        return {
          radio1: '包含',
          radio2: '不含',
          radio3: '精准',
        };
      }
    }
  </script>