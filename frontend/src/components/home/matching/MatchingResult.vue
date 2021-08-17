<template>
  <div>
    도착
    {{ discordUrl }}
  </div>
</template>
<script>
export default {
  data() {
    return {
      discordUrl: '',
    };
  },
  created() {
    this.discordUrl = this.$route.params.discordUrl;
  },
};
</script>
<style></style>
