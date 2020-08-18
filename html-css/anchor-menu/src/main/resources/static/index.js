$(document).ready(function(){
	// 浮动菜单
	$.fn.floatingMenu = function(options) {
		var elementDivFloatingMenu = $(this);
		// 设定样式
		elementDivFloatingMenu.css("margin", "5px");
		elementDivFloatingMenu.css("border-style", "bold");
		elementDivFloatingMenu.css("border-width", "2px");
		elementDivFloatingMenu.css("border-color", "gray");
		elementDivFloatingMenu.css("padding", "10px");
		elementDivFloatingMenu.css("width", "200px");
		elementDivFloatingMenu.css("position", "absolute");
		elementDivFloatingMenu.css("top", "0px");
		elementDivFloatingMenu.css("right", "0px");
		if(options.marginTop) {
			elementDivFloatingMenu.css("margin-top", options.marginTop);
		}
		
		var floatingMenuOffset = elementDivFloatingMenu.offset();
		$(window).scroll(function() {
			var scrollTop = $(this).scrollTop();
			var top = floatingMenuOffset.top + scrollTop;
			elementDivFloatingMenu.offset({"top":top});
		});
	};
	
	// 锚点菜单
	$.fn.anchorMenu = function() {
		var elementMenuAnchor = $(this);
		var attrExt1 = elementMenuAnchor.attr("attrExt1");
		var attrExt2 = elementMenuAnchor.attr("attrExt2");
		var menuAnchorContents = $("div[attrExt1='" + attrExt2 + "'] > div");
		var menuAnchorContentPrev;
		$(window).scroll(function(){
			var scrollTop = $(this).scrollTop();
			// 找出最接近scrollTop anchor元素
			var offsetPos = -1;
			var menuAnchorContentCurrent;
			menuAnchorContents.each(function(index, element) {
				// 恢复menu原来样式
				var varAttrExt1 = $(element).attr("attrExt1");
				var elementTemporary = elementMenuAnchor.find("a[attrExt1='" + varAttrExt1 + "']");
				var htmlTemporary = elementTemporary.html();
				elementTemporary.html(htmlTemporary.replace(" [active]", ""));
				elementTemporary.css("font-weight", "normal");
				
				var offset = $(element).offset();
				var top = offset.top;
				var offsetToScrollTop = top - scrollTop;
				
				if(offsetPos<0 || offsetToScrollTop<offsetPos) {
					offsetPos = offsetToScrollTop;
					menuAnchorContentCurrent = $(element).attr("attrExt1");
				}
			});
			
			if(menuAnchorContentCurrent && menuAnchorContentPrev !== menuAnchorContentCurrent) {
				menuAnchorContentPrev = menuAnchorContentCurrent;
			}
			var elementTemporary = elementMenuAnchor.find("a[attrExt1='" + menuAnchorContentPrev + "']");
			elementTemporary.html(elementTemporary.html() + " [active]");
			elementTemporary.css("font-weight", "bold");
		});
		
		$(window).scroll();
	};
	
	var height = $(document).height();
	var elementDivAnchors = $(".anchor > div");
	for(var i=0; i<elementDivAnchors.length; i++) {
		var elementTemporary = $(elementDivAnchors[i]);
		elementTemporary.height(height);
	}
	
	$("#divFloatingMenu").floatingMenu({marginTop:"230px"});
	$("#divFloatingMenu").anchorMenu();
});