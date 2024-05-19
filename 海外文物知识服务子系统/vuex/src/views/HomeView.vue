<template>
  <!--  <el-container class="home-container">-->
  <div id="background">
    <div style="">
      <div class="block text-center" >
        <el-carousel height="680px" motion-blur >
          <el-carousel-item v-for="item in items" :key="item">
            <div class="image-container">
              <img :src="item.image" alt="Logo" />
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
    <div class="video" style="display: flex;justify-content: center;">
      <iframe style="width: 1000px;height: 700px; margin-top: 10px" src="//player.bilibili.com/player.html?aid=401286817&bvid=BV12o4y1F7kG&cid=1133369322&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>
    </div>
    <div style="display: flex;">
      <div v-for="item in museum" :key="item">
        <div class="image-container" style="width: 300px;height: 300px; margin-left: 40px;">
          <img :src="item.image" style="object-fit: contain;" />
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import api from "../api/index";

export default {
  name: "home",
  data() {
    return {
      items: [
        { image: "https://www.shanximuseum.com/uploads/20211103/71f4df46f535ac731acc4ea45470045b.jpg", title: "2" },
        { image: " https://www.shanximuseum.com/sxo\/Uploads\/Picture\/2018\/12\/07\/s5c0a19ba25b12.jpg", title: "3" },
        { image: "https://www.shanximuseum.com/sxo\/Uploads\/Picture\/2018\/12\/10\/s5c0de59c93fcf.jpg", title: "4" },
        { image: "https://www.shanximuseum.com/sxo/Uploads/Picture/2018/12/07/s5c09e4ffa836c.jpg", title: "5" },
      ],
      museum:[
        {image:require('@/assets/img/aomusitedan1.jpg')},
        {image:require('@/assets/img/bolin1.jpg')},
        {image:require('@/assets/img/hubei2.jpg')},
        {image:require('@/assets/img/shanxi2.jpg')}
      ]
    };
  },
  mounted() {
    /*api.getChengpin().then(res => {
      console.log(res.data)
    })*/
  },
};
</script>

<style scoped>

.video{
  box-shadow: 10px 2px 6px rgba(0, 0, 0, 0.2);
  padding: 20px;
}

.demonstration {
  color: var(--el-text-color-secondary);
}
template {
  height: 100%;
}
img {
  height: 100%;
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.5;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.image-container {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.image-container img {
  object-fit: cover;
  height: 100vh;
  width: 100%;
}
#background {
  background-color: #5b2528
}
</style>
