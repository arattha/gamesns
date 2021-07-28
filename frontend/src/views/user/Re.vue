<template>
<div class="fbody">
    <div class="col-lg-6 offset-lg-3">
    <div class="cardbox bg-white">
        <!-- 프로필 -->
        <div class="cardbox-heading">
            <div class="media m-0">
                <img class="img-fluid rounded-circle" src="http://www.themashabrand.com/templates/bootsnipp/post/assets/img/users/4.jpg" alt="User">
            </div>
            <div class="media-body">
                <p class="m-0 name">Benjamin Robinson</p>
				<p class="m-0 time">10 hours ago</p>
			</div><!--/ media -->
        </div><!--/ cardbox-heading -->
			
        <!-- 이미지나 내용 -->
		<div class="cardbox-item">
			<img class="img-fluid" src="http://www.themashabrand.com/templates/bootsnipp/post/assets/img/1.jpg" alt="Image">
            <div>안녕하세요</div>
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
                <div><i class="fas fa-share-alt fa-lg"></i></div>
            </div>
		</div><!--/ cardbox-base -->

            <div>
                <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
                <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
            </div>
    </div>
    </div>
</div>
</template>

<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
export default {

}
</script>

<style>
    @import "../../components/css/user/re.css";

</style>