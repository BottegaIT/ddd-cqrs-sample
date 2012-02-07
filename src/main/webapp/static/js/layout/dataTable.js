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
	$("table.dataTable").each(initializeColumnOrder);
	var found = $(".pagination>li:not(.currentPage)");
	found.click(changePage);
	$("table.dataTable .sortable").click(sortByColumn);
});

/**
 * Checks the table for "sortedby" attribute for the column the table is sorted
 * by. Finds this column and add .ascending or .descending class depending on
 * the order.
 */
function initializeColumnOrder(index, table) {
	var currentSort = $(table).attr("sortedby");

	if (currentSort) {
		var ascending = "ascending";
		if ($(table).attr("ascending") === "false") {
			ascending = "descending";
		}
		var found = $(table).find("td[sortby=" + currentSort + "]");
		found.addClass(ascending);
	}
}

/**
 * Changes order to sort by this column. Page will be reloaded with new get
 * arguments to reflect this change.
 */
function sortByColumn() {
	var sortBy = $(this).attr("sortby");
	var ascending = !$(this).hasClass("ascending");

	var urlSearch = document.location.search;
	urlSearch = insertUrlSearchParam(urlSearch, "page", 1);
	urlSearch = insertUrlSearchParam(urlSearch, "sortBy", sortBy);
	urlSearch = insertUrlSearchParam(urlSearch, "ascending", ascending);
	document.location.search = urlSearch;
}

function changePage() {
	var newPage = $(this).html();
	document.location.search = insertUrlSearchParam(document.location.search,
			"page", newPage);
}

/**
 * Changes urlSearch parameter by key to have given value. If the parameter is
 * not present in the url it's added.
 * 
 * @author annakata
 * @see http://stackoverflow.com/questions/486896/adding-a-parameter-to-the-url-with-javascript
 */
function insertUrlSearchParam(urlSearch, key, value) {
	key = escape(key);
	value = escape(value);
	var kvp = [];
	if (urlSearch) {
		if (urlSearch.indexOf("?") == 0) {
			urlSearch = urlSearch.substr(1);
		}
		kvp = urlSearch.split('&');
	}

	var i = kvp.length;
	var x;
	while (i--) {
		x = kvp[i].split('=');

		if (x[0] == key) {
			x[1] = value;
			kvp[i] = x.join('=');
			break;
		}
	}

	if (i < 0) {
		kvp[kvp.length] = [ key, value ].join('=');
	}
	return kvp.join('&');
}