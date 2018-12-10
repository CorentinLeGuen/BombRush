var currentPath = window.location.href;
var regex = new RegExp(".*/room/(.*)$");
var title = regex.exec(currentPath)[1];

document.getElementById("title").innerHTML = "Bienvenue dans le salon " + decodeURI(title) + "!";
document.getElementsByName("title").innerHTML = "Salon " + decodeURI(title);

// Client Socket
var stompClient = null;

var socket = new SockJS('/bombrush');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/users', function (users) {
        $("#users").text("");
        var elements = JSON.parse(users.body);
        fillTable(elements);
    });
});

function fillTable(players) {
    for (var i = 0; i < players.length; i++) {
        $("#users").append("<li class='list-group-item'>" + players[i] + "</li>");
    }
}

function refreshTable() {
    fetch("http://localhost:8080/players?roomName="+title)
//    fetch("https://bombrush.herokuapp.com/players")
        .then(data => { return data.json() })
        .then(res => { fillTable(res); })
}
refreshTable();

function newRoom() {
    var v = {"user" : $("#name").val(), "roomName" : decodeURI(title)};
    stompClient.send("/app/user", {}, JSON.stringify(v));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() {
        newRoom();
        $("form").remove();
    });
});