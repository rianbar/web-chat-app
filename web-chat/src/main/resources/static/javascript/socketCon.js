'user strict';

var getMessageArea = document.querySelector("#message-area");
var connectBtn = document.querySelector("#btn-connect");
var connectButtonField = document.querySelector("#connect-container");
var connectingIcon = document.querySelector("#connecting-icon");
var errorMessage = document.querySelector("#error-message");
var connectionStatus = document.getElementById("connection-status")
var statusName = document.querySelector("#status");
var messageForm = document.getElementById("messageForm")
var usernameField = document.querySelector("#username-field");
var userImage = document.getElementById("user-image");
var messageInput = document.querySelector("#message");


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

    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);

    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: "JOIN"})
    )

    connectingIcon.classList.add("hidden");
    connectionStatus.style.color = 'green';
    statusName.innerHTML = "online";
}

function onError() {
    connectingIcon.classList.add("hidden");
    errorMessage.classList.remove("hidden");
    connectionStatus.style.color = 'red';
    statusName.innerHTML = "offline";
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var usernameLocal = localStorage.getItem("username");

    var messageElement = document.createElement("li");

    if(message.type === "JOIN") {
        messageElement.classList.add("event-message");
        message.content = message.sender + " joined!";
        usernameField.innerHTML = usernameLocal;
        userImage.innerHTML = usernameLocal[0];
        } else if (message.type === "LEAVE") {
        messageElement.classList.add("event-message");
        message.content = message.sender + " left!";
    } else {
        messageElement.classList.add("message-field");
        
        //avatar
        messageElement.appendChild(createAvatar(message));

        //username
        var usernameElement = document.createElement("span");
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    //message
    var textElement = document.createElement("p");
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);

    //insert message
    getMessageArea.appendChild(messageElement);
    getMessageArea.scrollTop = getMessageArea.scrollHeight;
}

function createAvatar(message) {
    var avatarElement = document.createElement("i");
    var avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style["background-color"] = getAvatarColor(message.sender);

    return avatarElement;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}


connectBtn.addEventListener("click", connect, true);

messageForm.addEventListener("submit", function(event) {
    event.preventDefault();
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: "CHAT"
        }

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}, true);

