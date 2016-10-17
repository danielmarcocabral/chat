
var webSocket=  new WebSocket("ws://192.168.1.9:8999/chat/chatServerEndpoint");
webSocket.onopen = function () { alert("WebSocket connection opened") };
webSocket.onmessage = function processMessage(message){
	
	var jsonData = JSON.parse(message.data);
	
	if(jsonData.message!=null){
		chatBox.value += jsonData.message + "\n";
		chatBox.scrollTop = chatBox.scrollHeight;
	}else{
		alert("message is null");
	}
	
	return false;
}
webSocket.onclose = function () { alert("WebSocket connection closed") };
function sendMessage(){
	if(inputBox.value.trim()!=""){
		webSocket.send(inputBox.value);
		inputBox.value="";
		inputBox.placeholder="Your message";
	}
	
}
function inputBoxKeyPress(event){
	 if (event.keyCode == 13) {
	     sendMessage();
	 }	
}
$(document).ready(function(){
	$("textarea").keydown(false);
});

