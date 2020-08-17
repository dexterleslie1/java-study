$(document).ready(function(){
	// 私有变量、函数
	function ObjPrivate() {
		var a = 0;
		var fn = function() {
			
		}
	};
	
	var objPrivate = new ObjPrivate();
	console.log("以下演示私有变量、函数：");
	console.log("实例方式访问，objPrivate.a=" + objPrivate.a);
	console.log("实例方式访问，objPrivate.fn=" + objPrivate.fn);
	
	// 静态变量、函数
	function ObjStatic() {
	};
	ObjStatic.a = 0;
	ObjStatic.fn = function() {
		
	}
	console.log("以下演示静态变量、函数访问方式：");
	console.log("静态方式访问，ObjStatic.a=" + ObjStatic.a);
	console.log("静态方式访问，ObjStatic.fn=" + ObjStatic.fn);
	var objStaticInstance = new ObjStatic();
	console.log("实例方式访问，objStaticInstance.a=" + objStaticInstance.a);
	console.log("实例方式访问，objStaticInstance.fn=" + objStaticInstance.fn);
	
	// 实例变量、函数
	function ObjInstance() {
		this.a = [];
		this.fn = function() {
			
		};
	};
	console.log("以下演示实例变量、函数访问方式：");
	console.log("静态方式访问，ObjInstance.a=" + ObjInstance.a);
	console.log("静态方式访问，ObjInstance.fn=" + ObjInstance.fn);
	var objInstance = new ObjInstance();
	console.log("实例方式访问，objInstance.a=" + objInstance.a);
	console.log("实例方式访问，objInstance.fn=" + objInstance.fn);
	
	// prototype用法
	function ObjPrototype(name) {
		this.name = name;
	}
	ObjPrototype.prototype.getName = function() {
		return this.name;
	}
	
	console.log("以下演示prototype用法：");
	var objPrototype1 = new ObjPrototype("Dexter1");
	console.log("调用getName函数，name=" + objPrototype1.getName());
	
	ObjPrototype.prototype.hello = function() {
		return "Hello " + this.name;
	}
	console.log("调用hello函数，hello=" + objPrototype1.hello());
});