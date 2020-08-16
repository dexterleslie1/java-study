$(document).ready(function(){
	// Generate random content to expand window and make sure it is supporting mouse wheel
	var elementDivContent = $("#divContent");
	for(var i=0; i<100; i++) {
		var elementTemporary = $("<div>div" + i + "</div>");
		elementDivContent.append(elementTemporary);
		elementTemporary.height(50);
	}
	
	var elementDivFloatingMenu = $("#divFloatingMenu");
	var floatingMenuOffset = elementDivFloatingMenu.offset();
	
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop();
		var floatingMenuTop = floatingMenuOffset.top + scrollTop;
		elementDivFloatingMenu.offset({"top":floatingMenuTop});
	});
});