var currentPath = window.location.href;
var regex = new RegExp(".*/room/(.*)$");
var title = regex.exec(currentPath)[1];

document.getElementById("title").innerHTML = "Bienvenue dans le salon " + title + "!";

var stompClient = null;

var socket = new SockJS('/bombrush');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/users', function (users) {
//        $.each(JSON.parse(users.body), function(k,v) {
//            $("#users").text("<tr><td>" + v +"<em> " + k + "</em></td></tr>");
//        });
        var table = $('#conversation');
        $("#users").text("");
        var elements = JSON.parse(users.body);
        console.log(elements.length);
        for (var i = 0; i < elements.length; i++) {
            $("#users").append("<tr><td>" + elements[i] + "</em></td></tr>");
        }
    });
});

function newRoom() {
    stompClient.send("/app/user", {}, $("#name").val());
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { newRoom(); });
});