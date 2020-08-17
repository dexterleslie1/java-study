$(document).ready(function(){
	// $.extend 用于扩展定义jQuery静态成员
	$.extend({
		static_member1:0,
		static_function1:function(){
			return "function1 is called!";
		}
	});
	
	// 调用$.extend 扩展静态方法
	console.log("$.extend 扩展静态成员 $.member1=" + $.static_member1);
	console.log("$.extend 扩展静态函数 $.function1=" + $.static_function1());
	
	// $.fn.extend 用于扩展定义jQuery相关实例成员
	$.fn.extend({
		instance_member1:1,
		instance_function1:function() {
			return "instance_function is called!";
		},
		instance_onclick:function() {
			$(this).bind("click", function(){
				console.log("You have clicked me!!");
			});
		}
	});
	
	var elementDiv = $("<div>Click me!</div>");
	console.log("$.fn.extend 扩展实例成员 objInstance.instance_member1=" + elementDiv.instance_member1);
	console.log("$.fn.extend 扩展实例成员 objInstance.instance_function1=" + elementDiv.instance_function1());
	console.log("$.fn.extend 扩展实例成员 objInstance.static_member1=" + elementDiv.static_member1);
	elementDiv.instance_onclick();
	$(document.body).append(elementDiv);
});