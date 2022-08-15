//좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#LikeIcon-${imageId}`);
	
	if (likeIcon.hasClass("far")) { // 좋아요
		
		$.ajax({
			type: "post",
			url: `/api/image/${imageId}/likes`,
			dataType: "text"
		}).done(res=>{
			console.log(res);
			let likeCountStr = $(`#LikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			$(`#LikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error);
			alert(error.responseText);
		});
		
		

	} else { // 좋아요취소
		
		$.ajax({
			type: "delete",
			url: `/api/image/${imageId}/likes`,
			dataType: "text"
		}).done(res=>{
			console.log(res);
			let likeCountStr = $(`#LikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#LikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});
		

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);
	
	
	let data = {
		imageId: imageId,
		content: commentInput.val()
	}


	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	
	$.ajax({
		type: "post",
		url: "/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(res=>{
		
		let comment = res;
		console.log(comment.userId);
		let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b>${comment.name} :</b>
		      ${comment.content}
		    </p>
		    <button onclick="deleteComment(${comment.id},${comment.userId})"><i class="fas fa-times"></i></button>
		  </div>
		`;
		
		commentList.prepend(content);
	}).fail(error=>{
		console.log("오류", error.responseJSON);
		alert(error.responseJSON.data);
	});

	commentInput.val(""); // 인풋 필드를 깨끗하게 비워준다.
}


// (5) 댓글 삭제
function deleteComment(commentId,userId) {
	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		data: `userId=${userId}`,
		dataType: "text"
	}).done(res=>{
		console.log(res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		alert(error.responseText);
	});
}







