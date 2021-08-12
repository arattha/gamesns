<template>
  <div>
    {{ editor.getCharacterCount() }}/{{ limit }}
    <div class="editor-div">
      <editor-content class="editorContent" :editor="editor" />
    </div>
    <div id="metaDataDiv" class="metadata-div" v-show="metaLoading" @click="goLink"></div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import { mergeAttributes } from '@tiptap/core';
import { Editor, EditorContent } from '@tiptap/vue-2';
import StarterKit from '@tiptap/starter-kit';
import Image from '@tiptap/extension-image';
import CharacterCount from '@tiptap/extension-character-count';
import HardBreak from '@tiptap/extension-hard-break';
import Paragraph from '@tiptap/extension-paragraph';
import http from '@/util/http-common.js';

export default {
  components: {
    EditorContent,
  },
  data() {
    return {
      url: '',
      metaLoading: false,
      editor: null,
      limit: 1000,
      regex: /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)/,
    };
  },
  watch: {
    url: async function(val, oldval) {
      if (val === oldval || !val) {
        this.metaLoading = false;
        return;
      }

      this.metaLoading = false;

      const element = document.querySelector('#metaDataDiv');
      while (element.hasChildNodes()) {
        element.removeChild(element.firstChild);
      }

      await http
        .get('/common/getMeta', { params: { url: val } })
        .then((res) => {
          if (res.data.data == 'success') {
            const imgE = document.createElement('img'); // + addeventlistener
            imgE.src = res.data.object.img;

            const contentDiv = document.createElement('div');
            contentDiv.setAttribute('class', 'metaContent');

            const title = document.createElement('div');
            title.appendChild(document.createTextNode(res.data.object.title));
            title.setAttribute('class', 'metaTitle');
            const descE = document.createElement('div');
            descE.appendChild(document.createTextNode(res.data.object.desc));
            descE.setAttribute('class', 'metaDesc');

            contentDiv.appendChild(title);
            contentDiv.appendChild(descE);

            element.appendChild(imgE);
            element.appendChild(contentDiv);
          }
        })
        .catch((err) => {
          console.err(err);
        });

      this.metaLoading = true;
    },
  },
  mounted() {
    this.editor = new Editor({
      autofocus: 'end',
      extensions: [
        StarterKit,
        Image,
        CharacterCount.configure({
          limit: this.limit,
        }),
        HardBreak.extend({
          addKeyboardShortcuts() {
            return {
              Enter: () => this.editor.commands.setHardBreak(),
            };
          },
        }),
        Paragraph.extend({
          parseHTML() {
            return [{ tag: 'div' }];
          },
          renderHTML({ HTMLAttributes }) {
            return ['div', mergeAttributes(this.options.HTMLAttributes), 0];
          },
        }),
      ],
      content: '',

      // editable: this.isOK,
      onUpdate: () => {
        this.setBoardContent(this.editor.getHTML());
        let url = this.regex.exec(this.editor.getHTML());

        if (url) {
          this.url = url[0];
        } else {
          this.metaLoading = false;
          this.url = '';
        }
      },
    });
  },
  methods: {
    ...mapActions(['setBoardContent']),
    goLink() {
      window.open(this.url);
    },
  },
  beforeDestroy() {
    this.editor.destroy();
  },
};
</script>

<style lang="scss">
$colorWhite: #fff;
$colorBlack: #000;

/* Basic editor styles */
.ProseMirror:focus {
  outline: none;
}
.ProseMirror {
  height: 100%;
  > * + * {
    margin-top: 0.75em;
  }
  p {
    margin: 0px;
  }
  ul li {
    color: #000;
    list-style: disc;
  }
  ol li {
    color: #000;
    list-style: decimal;
  }
  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0d0d0d;
    color: #fff;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}

.editor-div {
  border: 2px solid rgba(255, 175, 10, 1);
  height: unquote('max(280px, 40vh)');
  max-height: unquote('max(280px, 40vh)');
}

.editorContent {
  overflow: auto;
  padding: 1.25rem 1rem;
  -webkit-overflow-scrolling: touch;
  height: 100%;
}

/* 스크롤바 설정*/
.editorContent::-webkit-scrollbar {
  width: 6px;
}

/* 스크롤바 막대 설정*/
.editorContent::-webkit-scrollbar-thumb {
  height: 17%;
  background-color: rgba(255, 175, 10, 1);
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
}

/* 스크롤바 뒷 배경 설정*/
.editorContent::-webkit-scrollbar-track {
  background-color: rgba(255, 175, 10, 0.33);
}

.metadata-div {
  border: 1px solid #d0d0d0;
  height: 100px;
  margin-top: 1em;

  img {
    position: relative;
    float: left;
    box-sizing: border-box;
    border-right: 0.5px solid #d0d0d0;
    padding: 3px;
    height: 100%;
    width: 100px;
    margin-right: 15px;
  }

  .metaTitle {
    font-size: 0.8em;
    font-weight: bold;
    margin-bottom: 0.4em;

    // 글자수제한
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2; /* 표시하고자 하는 라인 수 */
    -webkit-box-orient: vertical;
  }

  .metaDesc {
    font-size: 0.5em;

    // 글자수제한
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3; /* 표시하고자 하는 라인 수 */
    -webkit-box-orient: vertical;
  }

  .metaContent {
    overflow: hidden;
    position: relative;
    top: 50%;
    transform: translateY(-50%);
  }
}
</style>
