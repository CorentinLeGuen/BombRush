var currentPath = window.location.href;
var regex = new RegExp(".*/room/(.*)$");
var title = regex.exec(currentPath)[1];

document.getElementById("title").innerHTML = "Bienvenue dans le salon '" + title + "'!";


// Client Socket
var stompClient = null;

var socket = new SockJS('/bombrush');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/users', function (users) {
        console.log(users);
        var table = $('#conversation');
        $("#users").text("");
        var elements = JSON.parse(users.body);
        for (var i = 0; i < elements.length; i++) {
            $("#users").append("<tr><td>" + elements[i] + "</em></td></tr>");
        }
    });
});

function newRoom() {
    var v = {"user" : $("#name").val(), "roomName" : title};
    stompClient.send("/app/user", {}, JSON.stringify(v));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
        $("form").remove();
    });
    $( "#send" ).click(function() { newRoom(); });
});