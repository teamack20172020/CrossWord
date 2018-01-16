$(function(){

	//タイマー処理
	function Starttimer(){
		var wait = 1000;

		//setInterval(繰り返す処理,繰り返す秒の間隔(msec));
		timer=setInterval(time_write,wait);
	}

	//カウンター処理
	function time_write(){
		sec++;
		if(sec==10){
			sec=0;
			sec2++;
		}
		if(sec2==6){
			sec2=0;
			min++;
		}
		if(min==10){
			min=0;
			min2++;
		}
		$('span').text(min2+""+min+":"+sec2+""+sec);
	}
});