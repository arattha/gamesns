<template>
  <div style="display : flex" class="modal">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
        </div>
    </div>
    <div class="modal-card">
      <div class="image">
        <img v-for="(img,index) in img_src" :key="index" :src="img" alt="">
        <!--이미지 -->
      </div>
    </div>
    <div class="content">
      <span>{{boardItem.contents}}</span>
    </div>
  </div>
</template>
<script>
export default {
  props:["boardItem"],
  data: () => {
    return {
      img_src:[],
    };
  },
  created() {
    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });
  },
}
</script>
<style>
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: black;
}
.modal-card {
  position: relative;
  max-width: 80%;
  margin: auto;
  margin-top: 30px;
  padding: 20px;
  background-color: white;
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}
</style>