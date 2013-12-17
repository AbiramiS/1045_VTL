var status = false;

function GetXmlHttpObject() {
	var xmlhttp = false;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		try {
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				xmlhttp = false;
			}
		}
	}
	return xmlhttp;
}

function processRequestdate() {

	if (req.readyState == 4) {
		if (req.status == 200) {
			postProcessdate(req.responseXML);
		}
	}
}

function initRequest() {
	var url = "AutoCompletedate?";
	req = GetXmlHttpObject();
	req.onreadystatechange = processRequestdate;
	req.open("GET", url, true);
	req.send(null);
}

function postProcessdate(responseXML) {

	var marker = new Array();
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map_canvas"));
		//var point = new GLatLng(-33.9, 151.2);

		var point = new GLatLng(13.034841, 80.213918);
		map.setCenter(point, 15);
		map.setUIToDefault();
		map.setMapType(G_NORMAL_MAP);
		var i;

		var CHATING = responseXML.getElementsByTagName("CHATING")[0];
		var j = 0;
		for (i = 0; i < CHATING.childNodes.length; i++) {
			var CHAT = CHATING.childNodes[i]
			var ID = CHAT.getElementsByTagName("ID")[0];
			var MOBILENO = CHAT.getElementsByTagName("MOBILENO")[0];
			var EMAILID = CHAT.getElementsByTagName("EMAILID")[0];
			var LATITUDE = CHAT.getElementsByTagName("LATITUDE")[0];
			var LONGITUDE = CHAT.getElementsByTagName("LONGITUDE")[0];

			if (ID.childNodes[0].nodeValue != "null") {
				if (MOBILENO.childNodes[0].nodeValue == "NOTMOVING") {
					alert("Vehicle Not Moving:\nVehicle Id:"
							+ ID.childNodes[0].nodeValue);		
					getData(ID.childNodes[0].nodeValue);
					
					
					
					//document.location.href="BlockSession?searchtoken="+MOBILENO.childNodes[0].nodeValue.innerHTML;
				}
				point = new GLatLng(LATITUDE.childNodes[0].nodeValue,
						LONGITUDE.childNodes[0].nodeValue);
				marker[j] = new GMarker(point);
				GEvent.addListener(marker[j], "click", callback(j, point,
						ID.childNodes[0].nodeValue,
						MOBILENO.childNodes[0].nodeValue,
						EMAILID.childNodes[0].nodeValue));
				map.addOverlay(marker[j]);
				var bounds = map.getBounds();
				bounds.extend(point);
				j = j + 1;
			}
		}

		function getData(ID) {
			var client;
			var data;
			var url_action = "BlockUser?useridvalue="+ID;
			if (window.XMLHttpRequest) {
				client = new XMLHttpRequest();
			} else {
				client = new ActiveXObject("Microsoft.XMLHTTP");
			}
			client.onreadystatechange = function() {
				if (client.readyState == 4 && client.status == 200) {
					document.getElementById("response").innerHTML = client.responseText;
				}
			};
			/*data = "name=" + document.getElementById("name").value + "&file="
					+ document.getElementById("filname").value;*/
			client.open("POST", url_action, true);
			client.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			client.send("hai");
		}
	}

	function callback(i, point, ID, MOBILENO, EMAILID) {
		return function() {
			marker[i]
					.openInfoWindowHtml('<h3>'
							+ ID
							+ '</h3><br /><br />'
							+ MOBILENO
							+ '<br />'
							+ EMAILID
							+ '<br /><br /><a href="http://maps.google.com/maps?saddr=&daddr='
							+ point.toUrlValue()
							+ '" target ="_blank">Get Directions<\/a>');
		};
	}

	map.setCenter(bounds.getCenter(), 18);
	setTimeout("initRequest()", 10000);
}
	
	
	
