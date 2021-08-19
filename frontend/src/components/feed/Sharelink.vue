<template>
  <div>
        <a class="kakao" @click="openkakaoshare">
            <i class="fas fa-share-alt fa-lg" style="width:18px;height:18px"></i>
        </a>
        <head>
            <script type="text/JavaScript" src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        </head>
  </div>

</template>

<script>
import http from '@/util/http-common.js';
export default {
    name: 'Sharelink',
    props: ['boardItem'],
      data: () => {
    return {
        reply_num : 0,
        likelist: [],
        likelist_num: 0,
        };
    },
    methods: {
        openkakaoshare: function () {
            try {
                if (window.Kakao) {
                    window.Kakao.init('95193d8232dc43a8e06b95a27d2bd1ce');
                }
            }
            catch(e) {console.log()}
            console.log(this.reply_num)
            window.Kakao.Link.sendDefault({
                objectType: 'feed',
                content: {
                    title: this.boardItem.contents,
                    description: this.boardItem.uid + '유저아이디',
                    // '~~~님의 글'
                    imageUrl:
                        'https://ifh.cc/g/7jY7L7.png',
                        // 이미지 url을 어떻게 얻지?
                        // this.boardItem.imgFiles ~ 로 하는 것인지?
                        // 가능하면 작성글 이미지, 아니면 기본 함고 이미지.
                    link: {
                        mobileWebUrl: 'http://localhost:8081',
                        webUrl: 'http://localhost:8081',
                    },
                },
                social: {
                    likeCount: this.likelist_num,
                    // 좋아요 수
                    commentCount: this.reply_num,
                    //  댓글 수
                },
                buttons: [
                {
                    title: '웹으로 보기',
                    link: {
                        mobileWebUrl: 'http://localhost:8081',
                        webUrl: 'http://localhost:8081',
                    },
                },
            ],
            })
        }
    },
    created() {
        let data = {
      bid : this.boardItem.bid
    }
      http
      .get('/reply/cnt', {params:data})
      .then(({data}) => {
        this.reply_num = data;
      })
      .catch((err) => {
        console.log('reply num 에러입니다')
        console.log(err)
      })

        let data2;
      data2 = {
        bid: this.boardItem.bid,
      };

      http
        .get(`/board/like`, { params: data2 })
        .then(({ data }) => {
          if (data == null) {
            this.likelist = [];
            this.likelist_num = 0;
          } else {
            this.likelist = data;
            this.likelist_num = this.likelist.length;
          }
        })
        .catch(() => {
          console.log('좋아요 리스트 에러');
        });
    },
}
</script>

<style>
    .kakao{
        color: #ff8f00;
    }
</style>