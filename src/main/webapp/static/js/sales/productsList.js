$(document).ready(function() {
	$(":input[name=addProductButton]").click(addProductToBasket);
	$.get("basketItems", function(data) {
		handleBasketItems(data);
	});
	$("#clearFiltersButton").click(function() {
		window.location.href = window.location.pathname;
	});
});

function addProductToBasket() {
	$.post("addProduct", {
		productId : $(this).attr("productId")
	}, function(data) {
		handleBasketItems(data);
	});
}

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
