$(document).ready(function(){
	// Generate random content to expand window and make sure it is supporting mouse wheel
	var elementDivContent = $("#divContent");
	for(var i=0; i<100; i++) {
		var elementTemporary = $("<div>div" + i + "</div>");
		elementDivContent.append(elementTemporary);
		elementTemporary.height(50);
		if(i%2==0) {
			elementTemporary.css("background-color", "#9ff5d0");
		} else {
			elementTemporary.css("background-color", "#7acdd6");
		}
	}
	
	var elementDivFloatingMenu = $("#divFloatingMenu");
	var floatingMenuOffset = elementDivFloatingMenu.offset();
	
	$(window).scroll(function() {
		var scrollTop = $(this).scrollTop();
		var top = floatingMenuOffset.top + scrollTop;
		elementDivFloatingMenu.offset({"top":top});
	});
	
	var elementDivFloatingMenuTop = $("#divFloatingMenuTop");
	var floatingMenuTopOffset = elementDivFloatingMenuTop.offset();
	
	$(window).scroll(function() {
		var scrollTop = $(this).scrollTop();
		var top = floatingMenuTopOffset.top + scrollTop;
		elementDivFloatingMenuTop.offset({"top":top});
	});
});