$(document).ready(function() {
	$(":input[name=addProductButton]").click(function() {
		$.post("addProduct", {
			productId : $(this).attr("productId")
		}, function(data) {
			handleBasketItems(data);
		});
	});
	$.get("basketItems", function(data) {
		handleBasketItems(data);
	});
});

function clearBasket() {
	$.post("clearBasket", function() {
		$("#basketItemsList").empty();
		showOrHideBasketList();
	});
}

function handleBasketItems(basketItemsHtml) {
	$("#basketItemsList").html(basketItemsHtml);
	showOrHideBasketList();
}

function showOrHideBasketList() {
	if ($("#basketItemsList > li").size() > 0) {
		$(".basketContent").show();
		$(".basketNoContent").hide();
	} else {
		$(".basketContent").hide();
		$(".basketNoContent").show();
	}
}