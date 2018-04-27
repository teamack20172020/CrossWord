
//秒数カウント用変数
var PassSec;
var PassageID;

$(function(){

	//背景画像を設定
	var urlno = $("#backno").val();
	var elm = document.getElementById('body');
	elm.style.backgroundImage = 'url(/photo/CrossWord/backimg/background_' + ( '0' + urlno ).slice( -2 ) + '.jpg)';

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