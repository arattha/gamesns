<template>
    <div>
        <Header/>
        <div class="form">
            <h5 class="title">알림 - {{}}개</h5>
            <div class="list">
                <li>ddd</li>
            </div>
        </div>
        <Footer/>
    </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
    name: 'Alarm',
    components: {
        Header,
        Footer,
    },
}
</script>

<style>
    @import "../../components/css/alarm/alarm.css";
</style>
