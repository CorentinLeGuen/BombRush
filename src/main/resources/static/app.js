var stompClient = null;

var socket = new SockJS('/bombrush');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/room', function (greeting) {
        showGreeting(JSON.parse(greeting.body).title);
    });
});

function newRoom() {
    stompClient.send("/app/info", {}, $("#title").val());
}

function showGreeting(message) {
    $("#rooms").append("<tr><td><a href=\"/room/" + message + "\">" + message + "</a></td></tr>");
}

function fillTable(roomNames) {
    for (var i = 0; i < roomNames.length; i++) {
        $("#rooms").append("<tr><td><a href=\"/room/" + roomNames[i].title + "\">" + roomNames[i].title + "</a></td></tr>");
    }
}

function refreshTable() {
//    fetch("http://localhost:8080/rooms")
    fetch("https://bombrush.herokuapp.com/rooms")
        .then(data => { return data.json() })
        .then(res => { fillTable(res); })
}
refreshTable();

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { newRoom(); });
});
