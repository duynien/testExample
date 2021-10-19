var stompClient = null;
var username = null;
$(function () {
    connect();
})

function connect(event) {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected);
}

function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);
}

// function onError(error) {
//     connectingElement.textContent =
//         "Không thể kết nối với WebSocket!";
//     connectingElement.style.color = "red";
// }
function handleReceive(message,text){
    var sender = message.sender
    var avt_sender = sender.charAt(0);
    var htmlRs = `<li class="nav ${text} fw-bold h5 mb-2">
                            <span style="padding: 2px 8px;
                                                        background: #f3ecec;
                                                        border-radius: 50%;" 
                                                        class="avt-sender">${avt_sender}
                                                        </span>
                             <span class="d-flex align-items-center">
                                    ${sender} ${message.content}</span>
                       </li>`;
    $('.notice_content_name').prepend(htmlRs);
}
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message.type === "JOIN") {
        handleReceive(message,'text-success');
    } else if (message.type === "LEAVE") {
        handleReceive(message,'text-danger');
    }
    else if (message.type === "DOEXAM") {
        handleReceive(message,'text-success');
    }
}