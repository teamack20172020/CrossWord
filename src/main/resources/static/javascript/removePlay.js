/***** ドラッグ開始時の処理 *****/
$(function(){
	//配列に格納されている入力値を文字列に変換
	$('#form_id').submit(function(){
		var str = "";
		var width = $("#width").val();
		var height = $("#height").val();
		var size = width * height;

		var name = "input[name=crossWord_res[0]]";
		str += $(name).val();
		for(var i=1;i < size;i++){
			name = "input[name=crossWord_res["+i+"]]";
			str += ",";
			str += $(name).val();
		}
		$("#template").val(str);
		return true;
	});
});
