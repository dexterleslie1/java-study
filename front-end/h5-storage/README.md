演示h5 sessionStorage、localStorage用法

区别：
(1) 在使用sessionStorage方法时，如果关闭了浏览器，这个数据就丢失了，下一次打开浏览器单击"读取数据"按钮时，读取不到任何数据。在使用localStorage方法时，即使浏览器关闭了，下次打开浏览器时仍然能够读取保存的数据。不过，数据保存是按不同的浏览器分别进行保存的，也就是说，打开别的浏览器是读取不到在这个浏览器中保存的数据的。
(2) 同一个浏览器中，sessionStorage在不同的TAB页面保存数据是分开互相不影响的，localStorage在不同TAB页面保存数据时共享的相互影响的

sessionStorage、localStorage使用
https://www.cnblogs.com/pengc/p/8714475.html
