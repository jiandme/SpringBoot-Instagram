
let page = 0;
function popularLoad() {
	$.ajax({
		url: `/api/image/popular?page=${page}`,
		dataType: "json"
	}).done(res => {
		res.forEach((image)=>{
			let popularItem = getPopularItem(image);
			$("#popularList").append(popularItem);
		});
	}).fail(error => {
		console.log("오류", error);
	});
}

popularLoad();

function getPopularItem(image) {
	let item = `<div class="p-img-box">
					<a href="/image/${image.imageId}"> <img src="/upload/${image.postImage}" />
					</a>
				</div>`;
	return item;
}


$(window).scroll(() => {
	
	let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
	//console.log(checkNum);
	
	if(checkNum < 10 && checkNum > -10){
		page++;
		popularLoad();
	}
});







