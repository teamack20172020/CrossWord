$(function(){
	var height = 5;
	var width = 5;
	var h;

	var result = $("#test01");

	var ul = $("<ul>").attr("class", "table").appendTo(result);
	var item = $("#template").val().split(',');
	for(var i=0;i < height;i++){
		h='<li><div id="row"><ul>'
			for(var j=0;j < width;j++){
			h += '<li><input class="crossword" type="text" '
			if(item[i*height+j]=="■"){
				h+= 'maxlength="0" id="enp" ></input></li>'
			}else{
				h+= 'maxlength="1" id="noenp" ></input></li>'
			}
		}
		h +='</ul></div></li>';
		ul.append(h);
		}
});