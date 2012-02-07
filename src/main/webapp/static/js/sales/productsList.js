/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
