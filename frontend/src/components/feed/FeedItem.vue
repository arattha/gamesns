<template>
  <div class="fbody">
    
    <div class="cardbox bg-white">
      <!-- 프로필 -->
        <div class="cardbox-heading">
          <!-- 내부 사진 크기 조절 필요 -->
            <div class="fimg">
                <img :style="{'background-image': 'url('+defaultProfile+')'}" alt="">
            </div>
            <div class="media-body">
                <p class="m-0 name">{{boardItem.uid}}</p>
                <p class="m-0 time">10 hours ago</p>
            </div><!--/ media -->
        </div><!--/ cardbox-heading -->

        <!-- 이미지나 내용 -->
        <div class="cardbox-item">
          <div class="image-slider" style="padding: 0;">
          <div class="slider" style="padding: 0;" v-if="img_src.length > 1">
            <button class="prev" @click="prev"><i class="fas fa-chevron-left"></i></button>
            <button class="next" @click="next"><i class="fas fa-chevron-right"></i></button>
          </div>
          <!-- 이미지 -->
          <div class="ffimg" style="padding: 0px;" v-for="number in [currentNumber]" v-bind:key="number" transition="fade">
            <img class="img-fluid" alt=""
              :src="img_src[Math.abs(currentNumber) % img_src.length]">
          </div>
          </div>
          <!-- 내용 -->
          <div>{{boardItem.contents}}</div>
        </div><!--/ cardbox-item -->
        
        <!-- 좋아요 등 -->
        <div class="cardbox-base">
          <div class="likebox">
            <div><i class="far fa-heart fa-lg"></i></div>
            <p>242</p>		   
            <div><i class="far fa-comment fa-lg"></i></div>
            <p>20</p>
          </div>
          <div class="sharebox">
            <Sharelink :boardItem="boardItem" />
          </div>
        </div><!--/ cardbox-base -->

        <div>
          <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        </div>
    </div>

  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import Sharelink from "./Sharelink";
export default {
  props:['boardItem'],
  components: {
    Sharelink
  },
  data: () => {
    return { 
      defaultImage,
      defaultProfile,
      img_src:[],
      currentNumber: 0
    };
  },created() {
    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });
  },
  methods: {
    next: function(e) {
      e.stopPropagation();
      this.currentNumber += 1
    },
    prev: function(e) {
      e.stopPropagation();
      this.currentNumber -= 1
    }
  },
  destroyed(){

  },
};
</script>
