$(function(){
	$("#search").click(
		function(){
			var kw = $("#keyword").val();
			if(kw == "" || kw == null || kw == undefined){
				console.log("keyword为空");
				return;
			}
			$.ajax(
				{ 
					url: "http://localhost:8080/hello/detail/" + kw, 
					type: "GET",
					dataType: "jsonp",
					success: function(data){
						//alert(data.sname);
						$("#result").text(JSON.stringify(data));
					},
				});
		}
	);
})

