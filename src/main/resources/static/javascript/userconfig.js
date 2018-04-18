/**user画面生成時の処理**/
$(function(){
	var obj;
	var id;
	var nextid;
	var width = $("#warning").val();
	if(width != null){
		alert(width);
	}
	/*表示非表示処理*/
	$('.registation').click(function(){
		nextid=$('#Registration');
		if(id!=null&&id!=nextid){
			out2(id,obj)
		}
		obj = $('input.reg');
		id=nextid;
		rect2(id,obj);
	});

	$('.login').click(function(){
		nextid=$('#UserConfig');
		if(id!=null&&id!=nextid){
			out2(id,obj)
		}
		obj = $('input.log');
		id=nextid;
		rect2(id,obj)
	});

	$('.back').click(function(){
		out(id,obj);
		id=null;
	});
/**user画面の処理ここまで**/

/**過去問と過去にしたクロスワードのランキング表示ここから↓**/
//緑ボタンの処理(過去問の表示)
	$('#oldcross').click(function(){
		outblue('#newcross','#oldcross','#tutorial');
		oldrect(1500);
		rectALL('.update','#Cross',1600);
	});

	$('#clearcross').click(function(){
		outblue('#start','#clearcross','#tutorial');
		oldrect(1500);
		rectALL('.update','#Cross',1600);
	});
//更新処理（過去問の切り替え）
	$('#update').click(function(){
		$('form li').hide();
		setTimeout("oldrect(300)", "5");
		setTimeout("$('#Cross').fadeIn(300)", "1500");
	});

/*青ボタンの処理(指定されたページに遷移）*/
	$('#newcross').click(function(){
		outblue('#oldcross','#newcross','#tutorial');
		setTimeout(function(){
			window.location.href = "/crossword/play";
		}, 2000);
	});
	$('#start').click(function(){
		outblue('#clearcross','#start','#tutorial');
		setTimeout(function(){
			window.location.href = "/crossword/Chose";
		}, 2000);

	});
});
/**過去問と過去にしたクロスワードのランキング表示ここまで↑**/

/*レイアウトを表示*/
function rect(id){
	$(id).fadeIn(300);
	$(id).animate({
		marginTop: '+=30px'})
}
/*レイアウトを消す*/
function out(id){
	$(id).fadeOut(300);
	$(id).animate({
		marginTop: '-=30px'})
}

/*表示処理＋inut="submit"を使用可能に*/
function rect2(id,obj) {
	obj.attr('disabled', false);
	rect(id);
}
/*削除処理＋inut="submit"を使用不可能に*/
function out2(id,obj){
	obj.attr('disabled', true);
	out(id);
}

function rectALL(id1,id2,time){
	setTimeout(function(){
		$(id1).fadeIn(300)
	}, time);
	setTimeout(function(){
		$(id2).fadeIn(300)
	}, time);
}

function outblue(id1,id2,id3){
	out(id1);
	var time=1000;
	setTimeout(function(){
		out(id2)
	}, time);
	setTimeout(function(){
		out(id3)
	}, time);
}
/*過去問表示処理*/
function oldrect(int){
	var size=$('form li').length;
	var c = new Array( 9 );
	for(var i=0; c[8]==undefined&&i<size;i++){
		while(c[i]==undefined){
			var a = Math.floor( Math.random() * size);
			c[i]=a;
			c = c.filter(
					function( value, index, self )
					{
						// 配列selfの要素valueの最初のインデックスと、indexが等しいならばtrueを返す
						return self.indexOf( value ) == index;
					});
		}
	}
	for(var i=0;i<c.length;i++){
		(function(local){ // とじこめる
			setTimeout(function(){ $("li#"+c[local]).fadeIn(300); },int);
			setTimeout(function(){ $("li#"+c[local]).css('display', 'inline');},int);
		}(i)); // とじこめる！
	}
}