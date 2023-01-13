import React, {useEffect, useState} from 'react'
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
 import './css/Chat.css'
import {useAuth} from "./auth/AuthProvider";
import {tab} from "@testing-library/user-event/dist/tab";

let stompClient = null;
const ChatRoom = () => {

    const {auth, claims} = useAuth();

    const [chats, setChats] = useState([]);
    const [userData, setUserData] = useState({
        username: claims.sub,
        receiver: '',
        connected: false,
        content: ''
    });




    const joinChat = () => {
        let Sock = new SockJS("http://localhost:8080/ws");
        stompClient = over(Sock);
        stompClient.connect({}, onConnect, onError)
    }

    const onConnect = () => {
        setUserData(prevState => ({...prevState, connected: true}));
        stompClient.subscribe('/chatroom/public', messageReceived);
        userJoin()
    }

    function userJoin(){
        let chatMessage = {
            sender: userData.username,
            status: "CONNECT"
        }
        stompClient.send('/app/message', {}, JSON.stringify(chatMessage));
    }

    const messageReceived = (data) => {
        let parsedData = JSON.parse(data.body)
        console.log("BIATCHHHHH")
        console.log(parsedData)

        if (parsedData.status === "CHAT" && parsedData.sender !== claims.sub) {
             chats.push(parsedData);
            // setChats([...parsedData])
            console.log(chats)
        }
    }


    const onError = (err) => {
        console.log(err)
    }

    const   sendMessage = () => {
        console.log(stompClient)
        if (stompClient){
            console.log("VANACSMMMMMMM")
            let chatMessage = {
                sender: userData.username,
                content: userData.content,
                status: "CHAT",
                receiver: "CHATROOM"
            }
            console.log("Sent message: ")
            console.log(chatMessage)
            chats.push(chatMessage)
            stompClient.send('/app/message', {}, JSON.stringify(chatMessage));
            setUserData(prevState => ({...prevState, content: ""}))

        }
    }



    if (userData.connected) {
        console.log("chats maderfak")
        console.log(chats)
        return (
            <div className="container">
                <div className="chat-box">
                    <div className="chat-content">
                        {chats.map((chat, index) => (
                            <li className={`message ${chat.sender === userData.username && "self"}`} key={index}>
                                {chat.sender !== userData.username && <div className="avatar">{chat.sender}</div>}
                                <div className="message-data">{chat.content}</div>
                                {chat.sender === userData.username && <div className="avatar self">{chat.sender}</div>}
                            </li>
                        ))}
                        <div className="send-message">
                            <input type="text" className="input" placeholder="Enter message" value={userData.content}
                                   onChange={(e) => setUserData(prevState => ({
                                       ...prevState,
                                       content: e.target.value
                                   }))}/>
                            <button type="button" className="send-button" onClick={sendMessage}>Send message</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    } else {
        return (
            <div className="container">
                <div className="register">
                    <button type="button" onClick={joinChat}>Join chat</button>
                </div>
            </div>
        )
    }
}

export default ChatRoom