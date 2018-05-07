/***** ドラッグ開始時の処理 *****/
function f_dragstart(event){
	//ドラッグするデータのid名をDataTransferオブジェクトにセット
	event.dataTransfer.setData("text", event.target.id);
}

/***** ドラッグ要素がドロップ要素に重なっている間の処理 *****/
function f_dragover(event){
	event.preventDefault();
}

/***** ドロップ時の処理 *****/
function f_drop(event){
	//ドラッグされたデータのid名をDataTransferオブジェクトから取得
	var drag_id = event.dataTransfer.getData("text");
	var drag_name = drag_id.substr(3,3);
	var drag_elm =document.getElementById(drag_id);

	var str = drag_elm.textContent.split("");

	//ドロップされたデータのid名をDataTransferオブジェクトから取得
	var drop_name  = event.currentTarget.name;
	var drop_index =drop_name.replace(/[^0-9]/g,'');
	var input_name = drop_name.replace("[","").replace("]","").replace(drop_index,"");

	//次回配置位置を設定
	var next = 1;
	var check = 0;
	var check_wk = 1;
	var index = parseInt(drop_index);

	if(drag_name=="row"){
		next = parseInt($("#width").val());
		check_wk =  parseInt($("#width").val());
		check = (index) / check_wk;
	}else{
		check_wk =  parseInt($("#height").val());
		check = (index) % check_wk;
	}

	//配置可能かチェック

	var res = 0;
	for(var i = 0; i < str.length; i++) {
		if(check >= check_wk){
			res = -1;
			break;
		}
		var name = 'input[name="';
		name += input_name + '[' +index + ']';
		name += '"]';
		if($(name).val()=="■"){
			res = -1;
			break;
		}
		index += next;
		check ++;
	}

	index = parseInt(drop_index);
	if(res == 0){
		for(var i = 0; i < str.length; i++) {
			var name = 'input[name="';
			name += input_name + '[' +index + ']';
			name += '"]';
			$(name).val(str[i]);
			index += next;
		}
	}
	//エラー回避のため、ドロップ処理の最後にdropイベントをキャンセルしておく
	event.preventDefault();
}