let stompClient = null;

function init() {
    stompClient = Stomp.over(new SockJS('/application-websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/users', (list) => showUsers(JSON.parse(list.body).users));
        stompClient.send("/app/users", {}, {});
    });
}

function addUser() {
    stompClient.send("/app/user", {}, JSON.stringify({name: $("#nameInput").val()}))
}

function showUsers(users) {
    $('tbody').empty()
    for (let user of users) {
        $('<tr><th scope="row">' + user.id + '</th><td>' + user.name + '</td></tr>').appendTo('tbody');
    }
}