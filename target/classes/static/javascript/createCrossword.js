
//秒数カウント用変数
var PassSec;
var PassageID;

$(function(){
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
				h += '<li><input class="crossword" type="text" ';
				if(item[i*height+j]=="■"){
					h+= 'maxlength="0" id="enp" value="';
					h+= item[i*height+j];
				}else{
					h+= 'maxlength="1" id="noenp" value="';
					if(item[i*height+j]!="□"){
						h+= item[i*height+j];
					}
				}
				h+= '" name="crossWord_res[';
				h+= index;
				h+= ']" ></input></li>';
			}
		h +='</ul></div></li>';
		ul.append(h);
	}

	//ヒント出力部生成処理



	// タイマーの初期化と実行(1000ms間隔)
	PassSec = Number($("#time").val());
	PassageID = setInterval('showPassage()',1000);
});

//タイマーの更新
function showPassage() {
	//カウントアップ
	PassSec++;
	// 表示文作成
	var msg = "経過時間：" + PassSec + "秒";
	// 表示更新
	document.getElementById("PassageArea").innerHTML = msg;
}

//タイマーの停止
function stopShowing() {
	// タイマーのクリア
	clearInterval( PassageID );
	$("#time").val( PassSec )
}