<template>
    <div id="menu-nav">
        <div id="navigation-bar">
            <ul>
                <li><a href="#"><i class="fa fa-home"></i><span></span></a></li>
                <li><a href="#"><i class="fa fa-search"></i><span></span></a></li>
                <li><a href="#"><i class="fas fa-gamepad"></i><span></span></a></li>
                <li><a href="#"><i class="fas fa-users"></i><span></span></a></li>
                <li><a href="#" onclick="hideandshow('#search-bar')"><i class="fas fa-edit"></i><span></span></a></li>
            </ul>
        </div>
        
        <div id="search-bar">
            <form>
            <input type="text" placeholder="Enter your query and hit Enter" />
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name:'Footer',
}
</script>

<style>
    @import "../../css/layout/footer.css";
</style>