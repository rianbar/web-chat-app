'user strict';

var getMessageArea = document.querySelector("#message-area");



var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = localStorage.getItem("username");
    console.log("username");

    if (username) {

        var socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);

    stompClient.send("/app/chat.addUser",
                    {},
                    JSON.stringify({sender: username, type: "JOIN"}));

    

}

function onError() {

}