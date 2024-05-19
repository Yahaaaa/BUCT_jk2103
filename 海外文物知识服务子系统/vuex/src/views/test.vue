<template>
       <div id="galleryID">
          <a
                v-for="image in imagesData"
                :href="image.largeURL"
                :data-pswp-width="image.width"
                :data-pswp-height="image.height"
                target="_blank"
                rel="noreferrer">
              <img :src="image.thumbnailURL" alt="" />
          </a >
      </div>
</template>

 <script setup>
import { ref, onMounted, onUnmounted } from "vue";
import PhotoSwipeLightbox from "photoswipe/lightbox";
import "photoswipe/style.css";
const imagesData = ref([]);
imagesData.value.push({
  largeURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
  width: 1000,
  height: 1000,
  thumbnailURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
});
imagesData.value.push({
  largeURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
  width: 1000,
  height: 1000,
  thumbnailURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
});
imagesData.value.push({
  largeURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
  width: 1000,
  height: 1000,
  thumbnailURL:
      "https://lh4.ggpht.com/XvMPh18c7EJ99hhodR98SamExy3y6VoPODqzeAq3-El-glu417phDZUrLMNygADveCEZ0WdJtxoSut7bfmFY84m85V6e=w1000",
});
let lightbox = null;

onMounted(() => {
  lightbox = new PhotoSwipeLightbox({
    gallery: "#" + "galleryID",
    children: "a",
    pswpModule: () => import("photoswipe"),
  });
  lightbox.init();
});

onUnmounted(() => {
  if (lightbox) {
    lightbox.destroy();
    lightbox = null;
  }
});
</script>
