$(document).ready(function(){
	var element = $("#divContentWrittenByJavascript");
	element.html("This is html anchor demo");

	var height = $(document).height();
	var elementDivAnchors = $(".anchor > div");
	for(var i=0; i<elementDivAnchors.length; i++) {
		var elementTemporary = $(elementDivAnchors[i]);
		elementTemporary.height(height);
	}
});