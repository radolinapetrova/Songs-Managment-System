let stompClient
let username

const connect = (e) => {
    e.preventDefault()

    const socket = new SockJS('/chat-example')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, onConnected, onError)
}


const onConnected = () => {
    stompClient.subscribe('/topic/public', onMessageReceived)
    stompClient.send("/app/chat.newUser",
        {},
        JSON.stringify({sender: username, type: 'CONNECT'})
    )
}
