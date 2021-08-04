function handleScroll() {

  let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
  let windowHeight = window.innerHeight; // 스크린 창
  let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
  console.log(document.documentElement.scrollTop);
  if( parseInt(scrollLocation + windowHeight ) == parseInt( fullHeight ) && scrollLocation != 0){
      this.getBoardItems();
  }
}

export { handleScroll };
