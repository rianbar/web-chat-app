'user strict';

var getMessageArea = document.querySelector("#message-area");
var connectBtn = document.querySelector("#btn-connect");
var connectButtonField = document.querySelector("#connect-container");
var connectingIcon = document.querySelector("#connecting-icon");
var errorMessage = document.querySelector("#error-message");
var connectionStatus = document.getElementById("connection-status")
var statusName = document.querySelector("#status");


var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = localStorage.getItem("username");

    if (username) {

        var socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);

        connectButtonField.classList.add("hidden");
        connectingIcon.classList.remove("hidden");
    }
}

function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);

    stompClient.send("/app/chat.AddUser",
        {},
        JSON.stringify({sender: username, type: "JOIN"})
    )

    connectingIcon.classList.add("hidden");
    connectionStatus.style.color = 'green';
    statusName.innerHTML = "online";
}

function onError(error) {
    connectingIcon.classList.add("hidden");
    errorMessage.classList.remove("hidden");
    connectionStatus.style.color = 'red';
    statusName.innerHTML = "offline";
}


function onMessageReceived(payload) {

}




connectBtn.addEventListener("click", connect, true);