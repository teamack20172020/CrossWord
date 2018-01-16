function Dshow(strShow,strHidden){
	var obj='';
	obj=document.all && document.all(strShow)
	|| document.getElementById && document.getElementById(strShow);
	obj.style.display = "block";


	var Hi='';
	Hi=document.all && document.all(strHidden) || document.getElementById && document.getElementById(strHidden);
	Hi.style.display = "block";

}


function Dnone(strHidden,strHi){
	var obj='';
	obj=document.all && document.all(strHidden) || document.getElementById && document.getElementById(strHidden);
	obj.style.display = "none";


	var Hi='';
	Hi=document.all && document.all(strHi) || document.getElementById && document.getElementById(strHi);
	Hi.style.display = "none";

}