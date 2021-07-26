<template>
    <div id="menu-nav">
        <div id="navigation-bar">
            <ul>
                <li @click="goToMain"><i class="fa fa-home"></i></li>
                <li @click="goToSearch"><i class="fa fa-search"></i></li>
                <li><i class="fas fa-gamepad"></i></li>
                <li><i class="fas fa-users"></i></li>
                <li @click="goToWriting"><i class="fas fa-edit"></i></li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    name:'Footer',
    methods: {
        goToMain: function() {
            this.$router.push('main')
        },
        goToSearch: function() {
            this.$router.push('search')
        },
        goToWriting: function() {
            this.$router.push('writing')
        },
    },
}
</script>

<style>
    @import "../../css/layout/footer.css";
</style>