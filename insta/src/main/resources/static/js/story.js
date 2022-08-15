
let principalId = $("#principalId").val();

let page = 0;
function storyLoad() {
	$.ajax({
		url: `/api/image/story?page=${page}`,
		dataType: "json"
	}).done(res => {
		res.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
	}).fail(error => {
		console.log("오류", error);
	});
}

storyLoad();

function getStoryItem(image) {
	let item = `<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.profileImage}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.name}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImage}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;
			     
			     if(image.likeState){
					item += `<i class="fas fa-heart active" id="LikeIcon-${image.imageId}" onclick="toggleLike(${image.imageId})"></i>`;
				}else{
					item += `<i class="far fa-heart" id="LikeIcon-${image.imageId}" onclick="toggleLike(${image.imageId})"></i>`;
				}
				
		
		item += `
			</button>
		</div>

		<span class="like"><b id="LikeCount-${image.imageId}">${image.likeCount} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.imageId}">`;

			image.commentDto.forEach((comment)=>{
				item +=`<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
				<p>
					<b>${comment.name} :</b> ${comment.content}
				</p>`;

				if(principalId == comment.userId){
					item += `	<button onclick="deleteComment(${comment.id},${comment.userId})">
										<i class="fas fa-times"></i>
									</button>`;
				}
				
			item += `	
			</div>`;
				
			});


		item += `
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.imageId}" />
			<button type="button" onClick="addComment(${image.imageId})">게시</button>
		</div>

	</div>
</div>`;
	return item;
}


$(window).scroll(() => {
	let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
	//console.log(checkNum);
	
	if(checkNum < 10 && checkNum > -10){
		page++;
		storyLoad();
	}
});







