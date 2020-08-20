$(document).ready(function(){
	$("#buttonFadeIn").click(function() {
		$("#divFadeInOut").fadeIn(1000, "swing");
	});
	$("#buttonFadeOut").click(function() {
		$("#divFadeInOut").fadeOut(1000, "swing");
	});
	
	$("#buttonFadeToggle").click(function() {
		$("#divFadeToggle").fadeToggle(1000, "linear");
	});
	
	$("#buttonFadeToIn").click(function() {
		$("#divFadeTo").fadeTo(1000, 0.8, "linear");
	});
	$("#buttonFadeToOut").click(function() {
		$("#divFadeTo").fadeTo(1000, 0.2, "linear");
	});
	
	var left = $("#divAnimate").offset().left;
	$("#buttonAnimate").click(function() {
		$("#divAnimate").show();
		$("#divAnimate").animate({left:$(window).width()/2, opacity: 1}, 1000, "linear");
	});
	$("#buttonReset").click(function() {
		$("#divAnimate").hide();
		$("#divAnimate").css("left", left);
		$("#divAnimate").css("opacity", 0);
	});
});