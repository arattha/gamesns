<template>
    <b-container fluid class="main-container">
                <nav>
                    <ul class="ul-box">
                        <img src="@/assets/images/logo.png" id="logo-box" alt="" style="width:156px">
                        <ul>
                            <li><a href="#1">Home</a></li>
                            <li><a href="#2">Searching</a></li>
                            <li><a href="#3">Matching</a></li>
                            <li><a href="#4">Community</a></li>
                            <li><a href="#5">Mypage</a></li>
                        </ul>
                    </ul>
                </nav>
    </b-container>
</template>

<script>
export default {
    name:'Header'
}
</script>

<style scoped>
    @import url("https://fonts.googleapis.com/css?family=Bitter");
    * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: bitter;
    }
    html,
    body {
    height: 100%;
    min-height: 100%;
    }
    a {
    text-decoration: none;
    color: #fff;
    }
    a.active {
    border-bottom: 2px solid #85c2ff;
    }
    .header {
    height: auto;
    }
    #header-scroll {
    position: fixed;
    height: 100%;
    background: rgba(0,0,0,0.4);
    left: 0;
    top: 0;
    float: left;
    width: 100%;
    -ms-transition: all 0.3s ease-out;
    -moz-transition: all 0.3s ease-out;
    -webkit-transition: all 0.3s ease-out;
    -o-transition: all 0.3s ease-out;
    transition: all 0.3s ease-out;
    }
    #header-scroll h1 {
    padding: 200px 0;
    height: 100%;
    font-size: 4em;
    text-align: center;
    line-height: 40px;
    -ms-transition: all 0.3s ease-out;
    -moz-transition: all 0.3s ease-out;
    -webkit-transition: all 0.3s ease-out;
    -o-transition: all 0.3s ease-out;
    transition: all 0.3s ease-out;
    }
    #header-scroll.small {
    height: auto;
    display: inline-block;
    /* float: left; */
    }
    #header-scroll.small h1 {
    padding: 10px 0;
    height: auto;
    font-size: 1rem;
    text-decoration: none;
    font-weight: bold;
    display: inline-block;
    }
    body {
    font-family: 'Open Sans', sans-serif;
    }
    nav {
    width: 70%;
    height: 60px;
    position: fixed;
    top: 0;
    }
    nav ul {
    padding: 20px;
    list-style: none;
    }
    nav ul li {
    display: inline-block;
    margin: 0 10px;
    }
    nav ul li a {
    padding: 10px 0;
    color: black;
    font-size: 1rem;
    text-decoration: none;
    font-weight: bold;
    transition: all 0.2s ease;
    }
    nav ul li a:hover {
    color: #85c2ff;
    }

    .main-container {
        display: flex;
        justify-content: center;
    }

    #logo-box {
        display: flex;
        justify-content: left;
        align-items: center;
        margin-bottom: 0px;
        margin-left: 0px;
    }

    .ul-box {
        display: flex;
        align-items: center;
        justify-content:space-between;
        background: bisque;
        padding: 5px 20px;
    }
</style>