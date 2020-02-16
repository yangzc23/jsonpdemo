$(function(){
	$("#login_btn").click(
		function(){
			var username = $("#username").val();
			var password = $("#password").val();
			if(username == "" || username == null || username == undefined){
				console.log("用户名为空");
				return;
			}
			if(password == "" || password == null || password == undefined){
				console.log("密码为空");
				return;
			}
			$.ajax(
				{ 
					url: "http://192.168.2.105:8080/hello/login/", 
					type: "POST",
					data: $("#login").serialize(),
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
						    $('#login_result').jsonViewer(input, options);
						//alert(data.sname);
						//$("#result").text(JSON.stringify(data,null,2));
					},
				});
		}
	);
})

