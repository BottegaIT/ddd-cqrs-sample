$(document).ready(function() {
	$("#sendShipment").click(sendShipment);
	$("#deliverShipment").click(deliverShipment);
});

function sendShipment() {
	$.post("send", {
		shipmentId : $(this).attr("shipmentId")
	}, function(data) {
	});
}

function deliverShipment() {
	$.post("deliver", {
		shipmentId : $(this).attr("shipmentId")
	}, function(data) {
	});
}
