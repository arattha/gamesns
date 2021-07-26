<template>
  <div>
      <Header/>
      <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
    name:'Mainfeed',
    components: {
        Header,
        Footer,

    }
}
</script>

<style>

</style>