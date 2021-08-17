<template>
	<div class="dropdown">
    <button class="dropbtn" @click="showDrop">
			<img style="width: 16px;" src="@/assets/images/ellipsis.png" alt="" />
		</button>
    <div class="dropdown-content" v-if="show">
			<a style="color: #1E90FF; border-bottom: 1px solid grey">수정</a>
			<a style="color: #CD5C5C">삭제</a>
    </div>
  </div>
</template>

<script>
export default {
	name: 'Dropdown',
	data: () => {
		return {
			show: false,
		}
	},
	methods: {
		showDrop() {
			this.show = !this.show;
		},
	}

}
</script>

<style>
	@import '../css/feed/dropdown.css';
</style>