/**user画面生成時の処理**/
$(function(){
	var obj;
	var id;
	var nextid;
	$('.registation').click(function(){
		nextid=$('#Registration');
		if(id!=null&&id!=nextid){
			out(id,obj)
		}
		obj = $('input.reg');
		id=nextid;
		rect(id,obj);
	});

	$('.login').click(function(){
		nextid=$('#UserConfig');
		if(id!=null&&id!=nextid){
			out(id,obj)
		}
		obj = $('input.log');
		id=nextid;
		rect(id,obj)
	});

	$('.back').click(function(){
		out(id,obj);
		id=null;
	});
});

function rect(id,obj) {
	obj.attr('disabled', false);
	$(id).fadeIn(300);
	$(id).animate({
		marginTop: '+=30px'})
}
function out(id,obj){
	obj.attr('disabled', true);
	$(id).fadeOut(300);
	$(id).animate({
		marginTop: '-=30px'})
}