$(document).ready(function() {
	var logueado = 0
	var redirect = function(url, method) {
		var form = document.createElement('form');
		form.method = method;
		form.action = url;
		form.submit();
	};

})
