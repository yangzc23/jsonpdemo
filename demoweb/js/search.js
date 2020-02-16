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
					url: "http://192.168.2.105:8080/hello/detail/" + kw, 
					type: "GET",
					dataType: "jsonp",
					success: function(data){
						try {
						      var input = eval('(' + JSON.stringify(data) + ')');
						    }
						    catch (error) {
						      return alert("Cannot eval JSON: " + error);
						    }
						    var options = {
						      collapsed: true,
						      rootCollapsable: true,
						      withQuotes: false,
						      withLinks: true
						    };
						    $('#result').jsonViewer(input, options);
						//alert(data.sname);
						//$("#result").text(JSON.stringify(data,null,2));
					},
				});
		}
	);
})

