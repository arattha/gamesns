<template>
  <div>
        <a class="kakao" @click="openkakaoshare">
            <i class="fas fa-share-alt fa-lg"></i>
        </a>
        <head>
            <script type="text/JavaScript" src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        </head>
  </div>

</template>

<script>
export default {
    name: 'Sharelink',
    props: ['boardItem'],
    methods: {
        openkakaoshare: function () {
            window.Kakao.init('95193d8232dc43a8e06b95a27d2bd1ce')
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
                    likeCount: 286,
                    // 좋아요 수
                    commentCount: 45,
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
}
</script>

<style>
    .kakao{
        color: #ff8f00;
    }
</style>