<template>
  <div class="fbody">
    <div class="cardbox bg-white">
      <!-- 프로필 -->
      <div class="cardbox-heading">
        <!-- 내부 사진 크기 조절 필요 -->
        <div class="fimg">
          <img :src="'http://localhost:8080/account/file/' + boardItem.uid" />
        </div>
        <div class="media-body">
          <p class="m-0 name">{{ boardItem.nickname }}</p>
          <p class="m-0 time">
            {{ boardItem.createDate | moment('from', 'now') }}
          </p>
        </div>
        <!--/ media -->
      </div>
      <!--/ cardbox-heading -->

      <!-- 이미지나 내용 -->
      <div class="cardbox-item">
        <div class="image-slider" style="padding: 0;" v-if="img_src.length > 0">
          <div class="slider" style="padding: 0;" v-if="img_src.length > 1">
            <button class="prev" @click="prev"><i class="black fas fa-chevron-left"></i></button>
            <button class="next" @click="next"><i class="black fas fa-chevron-right"></i></button>
          </div>
          <!-- 이미지 -->
          <div
            class="ffimg"
            style="padding: 0px;"
            v-for="number in [currentNumber]"
            v-bind:key="number"
            transition="fade"
          >
            <img
              class="img-fluid"
              alt=""
              :src="img_src[Math.abs(currentNumber) % img_src.length]"
            />
          </div>
        </div>
        <!-- 내용 -->
        <!-- <editor v-model="boardItem.contents" :isOK="false"/> -->
        <div style="padding: 0px 5px;">
          <editor-content :editor="editor" style="padding: 0px 5px;" />
          <div class="showContentDiv">
            <a @click="showContent" class="grayText">자세히보기</a>
          </div>
        </div>
        <!-- <div>{{boardItem.contents}}</div> -->
      </div>
      <!--/ cardbox-item -->

      <!-- 좋아요 등 -->
      <div class="cardbox-base">
        <div class="likebox">
          <Like :boardItem="boardItem" />
          <Reply :boardItem="boardItem" />
        </div>
        <div class="sharebox">
          <Sharelink :boardItem="boardItem" />
        </div>
      </div>
      <!--/ cardbox-base -->

      <div>
        <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet" />
        <link
          href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
          rel="stylesheet"
        />
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from '../../assets/images/img-placeholder.png';
import defaultProfile from '../../assets/images/profile_default.png';
import { Editor, EditorContent } from '@tiptap/vue-2';
import StarterKit from '@tiptap/starter-kit';
import Paragraph from '@tiptap/extension-paragraph';
import { mergeAttributes } from '@tiptap/core';
import Link from '@tiptap/extension-link';
import Sharelink from './Sharelink';
import Like from './Like';
import Reply from './Reply';

export default {
  props: ['boardItem'],
  components: {
    EditorContent,
    Sharelink,
    Like,
    Reply,
  },
  data: () => {
    return {
      contentPreview: '',
      editor: null,
      defaultImage,
      defaultProfile,
      img_src: [],
      currentNumber: 0,
    };
  },
  created() {
    this.boardItem.imgFiles.forEach((element) => {
      this.img_src.push('http://localhost:8080/board/file/' + element.file_name);
    });
    // console.log(this.boardItem.contents);

    let subContent = '';

    const extractDivPattern = /<(\/div|div)([^>]*)>/gi;
    let extractContent = this.boardItem.contents.replace(extractDivPattern, '');

    if (extractContent.length < 95) {
      subContent = extractContent;
    } else {
      let idx = extractContent.indexOf('<br>');

      if (idx > 95) {
        subContent = extractContent.substr(0, 95) + '...';
      } else if (idx < 0) {
        subContent =
          extractContent.length > 95 ? extractContent.substr(0, 95) + '...' : extractContent;
      } else {
        let secondIdx = extractContent.indexOf('<br>', idx + 4);

        if (secondIdx > 95) {
          subContent = extractContent.substr(0, 95) + '...';
        } else if (secondIdx < 0) {
          subContent =
            extractContent.length > 95 ? extractContent.substr(0, 95) + '...' : extractContent;
        } else {
          subContent = extractContent.substr(0, secondIdx) + '...';
        }
      }
    }

    this.contentPreview = subContent;
  },
  methods: {
    next: function(e) {
      e.stopPropagation();
      this.currentNumber += 1;
    },
    prev: function(e) {
      e.stopPropagation();
      this.currentNumber -= 1;
    },
    showContent: function() {
      this.$emit('showModal', this.boardItem);
    },
  },
  mounted() {
    this.editor = new Editor({
      editable: false,
      content: this.contentPreview,
      extensions: [
        StarterKit,
        Link,
        Paragraph.extend({
          parseHTML() {
            return [{ tag: 'div' }];
          },
          renderHTML({ HTMLAttributes }) {
            return [
              'div',
              mergeAttributes(this.options.HTMLAttributes, HTMLAttributes, HTMLAttributes, {
                class: 'boardContent',
              }),
              0,
            ];
          },
        }),
      ],
    });
  },
  beforeDestroyed() {
    this.editor.destroy();
  },
};
</script>

<style lang="scss">
.boardContent {
  word-break: normal;
}

.showContentDiv {
  padding: 10px 15px !important;
  a:hover {
    color: #8899fd;
  }
  a:visited {
    color: #a0a0a0;
  }
  a:active {
    color: #a0a0a0;
  }
}

.grayText {
  color: #a0a0a0;
  text-decoration: none;
}

.black {
  color: #808080;
}
</style>
