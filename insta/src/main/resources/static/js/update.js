function update(userId, event) {
	event.preventDefault();
	
	let data = $("#profileUpdate").serialize();
	
	
	$.ajax({
		type: "put",
		url : `/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res=>{ //성공시
		alert("회원정보 수정이 완료되었습니다");
		location.href = `/user/profile/${userId}`;
	}).fail(error=>{ //실패시
		console.log("회원정보 수정에 실패하였습니다")
		validCheck(error);
	});
}

function validCheck(error){
	
	valid_password.innerHTML = "";
	valid_name.innerHTML="";
	valid_email.innerHTML="";

	
	for(var key in error.responseJSON) {
		new Function('return ' + key)().innerHTML = error.responseJSON[key];
		}
	
}

