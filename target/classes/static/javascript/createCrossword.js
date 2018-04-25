
//秒数カウント用変数
var PassSec;
var PassageID;

$(function(){

	//背景画像をランダムに決定
	var url=[];
	url[0] = '01.jpg';
	url[1] = '02.jpg';
	url[2] = '03.jpg';
	url[3] = '04.jpg';
	url[4] = '05.jpg';
	var n = Math.floor(Math.random() * url.length);
	var elm = document.getElementById('body');
	elm.style.backgroundImage = 'url(/photo/CrossWord/background_' + url[n] + ')';

	//クロスワード出力部生成処理
	var h;
	var width = $("#width").val();
	var height = $("#height").val();

	var result = $("#table");
	var item = $("#template").val().split(',');

	var ul = $("<ul>").attr("class", "table").appendTo(result);
	for(var i=0;i < height;i++){
		h='<li><div id="row"><ul>'
			for(var j=0;j < width;j++){
				var index = (i * width) +j;
				h += '<li id="dummy"><input class="crossword" ';
				if(item[i*height+j]=="■"){
					h+= 'type="hidden" maxlength="0" id="enp" value="';
					h+= item[i*height+j];
				}else{
					h+= 'type="text" maxlength="1" id="noenp" value="';
					if(item[i*height+j]!="□"){
						h+= item[i*height+j];
					}
				}
				h+= '" name="crossWord_res[';
				h+= index;
				h+= ']" ondrop="f_drop(event)"></input></li>';
			}
		h +='</ul></div></li>';
		ul.append(h);
	}

	// タイマーの初期化と実行(1000ms間隔)
	PassSec = Number($("#playTime").val());
	PassageID = setInterval('showPassage()',1000);
});

//クリアボタンの処理
function clearWord(){
	var h;
	var width = $("#width").val();
	var height = $("#height").val();

	var result = $("#table");
	var item = $("#template").val().split(',');

	var ul = $("<ul>").attr("class", "table").appendTo(result);

	//クロスワードの表示の削除
	var elem = document.getElementById("table");
	elem.innerHTML = "";

	//クロスワードの再表示
	var ul = $("<ul>").attr("class", "table").appendTo(result);
	for(var i=0;i < height;i++){
		h='<li><div id="row"><ul>'
			for(var j=0;j < width;j++){
				var index = (i * width) +j;
				h += '<li id="dummy"><input class="crossword" ';
				if(item[i*height+j]=="■"){
					h+= 'type="hidden" maxlength="0" id="enp" value="';
					h+= item[i*height+j];
				}else{
					item[i]="";
					h+= 'type="text" maxlength="1" id="noenp" value="';
				}
				h+= '" name="crossWord_res[';
				h+= index;
				h+= ']" ondrop="f_drop(event)"></input></li>';
			}
		h +='</ul></div></li>';
		ul.append(h);
	}
}

//タイマーの更新
function showPassage() {
	//カウントアップ
	PassSec++;
	var second=PassSec%60;
	var minutes=(PassSec-second)/60;
	// 表示文作成
	var msg = "経過時間："+minutes +"分"+ second + "秒";
	// 表示更新
	document.getElementById("PassageArea").innerHTML = msg;
}

//タイマーの停止
function stopShowing() {
	// タイマーのクリア
	clearInterval( PassageID );
	$("#playTime").val( PassSec )
}