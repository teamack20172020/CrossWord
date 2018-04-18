/**ランキング画面生成時の処理**/
$(function(){
//MYランキングに移動
	var mytr=$('.rank1#rankuser').parents('tr');
	var mypos=$(mytr).position();
	//$('tbody.scroll').scrollTop(firstpos.top);
	if(mypos!=null){
	$('tbody.scroll').animate({scrollTop: mypos.top},"slow", "swing");
	}

/**selectバー変更時の処理**/
$('select[name="rankrange"]').change(function () {
	//selectバーの値を取得
	 var selectVal = $('select[name="rankrange"]').val();
	 var look;
	 if(selectVal!="MYランク"){
		 look=$("tr#"+selectVal);
	 }else{
		 look=$('.rank1#rankuser').parents('tr');
	 }
	 var lookfor=$(look).position();
	 var sh = $(".scroll").scrollTop();
	 var nextpos=lookfor.top+sh;
	 $('tbody.scroll').animate({scrollTop: nextpos},"slow", "swing");
	});

$('#next').click(function(){
	$('.configdiv').fadeIn(300);
	});
});