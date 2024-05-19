<template>
  <div class="input-wrapper">
    <div class="search-container">
      <input input type="text" placeholder="输入搜索内容..." v-model="searchInput">
      <button @click="search($event)">搜索</button>
    </div>
  </div>
</template>

<style scoped>
.input-wrapper .search-container{
background-color: #ffffff;
height: 155px;
max-width: 1200px; /* 设置最大宽度 */
min-width: 320px; /* 设置最小宽度 */

display: flex;
align-items: center;
}

.input-wrapper{
width: 100%;
text-align: center;
}

.search-container  input {
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
.input-wrapper .search-container button {
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
box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);}
</style>