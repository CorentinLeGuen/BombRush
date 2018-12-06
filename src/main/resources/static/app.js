var stompClient = null;

var socket = new SockJS('/bombrush');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/room', function (rooms) {
        $("#rooms").text("");
        fillTable(JSON.parse(rooms.body));
    });
});

function newRoom() {
    stompClient.send("/app/info", {}, $("#title").val());
}

function fillTable(roomNames) {
    for (var i = 0; i < roomNames.length; i++) {
        $("#rooms").append("<tr><td><a href=\"/room/" + roomNames[i] + "\">" + roomNames[i] + "</a></td></tr>");
    }
}

function refreshTable() {
    fetch("http://localhost:8080/rooms")
//    fetch("https://bombrush.herokuapp.com/rooms")
        .then(data => { return data.json() })
        .then(res => { fillTable(res); })
}
refreshTable();

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() {
        newRoom();
        $("#send").remove();
    });
});
