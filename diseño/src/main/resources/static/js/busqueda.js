var htmlTextboxControl

$(document).ready(function () {
    $("#btnAgregar").click(function () {
        $("#campos-busqueda").append(htmlTextboxControl)
        $(".btn-cerrar").click(function () {
            $(this).parents(".row").eq(0).remove()
        })
    })
    $.get("html/textbox-control.html", function (data) {
        htmlTextboxControl = data
        $("#btnAgregar").trigger("click")
    })
    
    $("#btnBuscar").click(function () {
    	var queryString = "";
    	$(".form-control").each(function() {
    		if(queryString == "")
    			queryString += $(this).val();
    		else
    			queryString += "," + $(this).val();
    		});
    	window.location = "/busqueda?nombre=" + queryString;
    })    
})


