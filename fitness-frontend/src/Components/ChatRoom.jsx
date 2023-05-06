import React, { useEffect, useState } from 'react'
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import '../Styles/ChatRoom.css';
import ReactModal from 'react-modal';
import { useRef } from 'react';
import goBack from '../Icons/goBack.svg'

var stompClient = null;
const ChatRoom = ({ isOpen, setOpen }) => {
    const chatBoxRef = useRef(null);

    const [publicChats, setPublicChats] = useState([]);
    const [userData, setUserData] = useState({
        username: (JSON.parse(sessionStorage.getItem("user"))).username,
        receivername: '',
        connected: false,
        message: ''
    });

    useEffect(() => {
        connect()
    }, []);

    const scrollToBottom = () => {
        chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }

    const connect = () => {
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }

    const onConnected = () => {
        setUserData({ ...userData, "connected": true });
        stompClient.subscribe('/chatroom/public', onMessageReceived);

        userJoin();
    }

    const userJoin = () => {
        var chatMessage = {
            senderName: userData.username,
            status: "JOIN"
        };
        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }

    const onMessageReceived = (payload) => {
        var payloadData = JSON.parse(payload.body);
        if(payloadData.status == 'MESSAGE') {
            publicChats.push(payloadData);
            setPublicChats([...publicChats]);
        }
        setTimeout(() => {
            scrollToBottom();
        }, 10);
    }

    const onError = (err) => {
        console.log(err);
    }

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
    }
    const sendValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                message: userData.message,
                status: "MESSAGE"
            };
            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            setUserData({ ...userData, message: '' });
            setTimeout(() => {
                scrollToBottom();
            }, 10);
        }
    }

    const closeModal = () => {
        setOpen(false);
    }

    return (
        <ReactModal
            isOpen={isOpen}
            style=
            {{
                overlay: { backgroundColor: '#00000078' },
                content: {
                    borderRadius: '20px',
                    margin: 'auto',
                    height: '570px',
                    width: '680px',
                    padding: '20px',
                    paddingTop: '20px',
                    overflow: 'hidden'
                },
            }}
            ariaHideApp={false}
        >
            <img src={goBack} id='go-back-icon' onClick={() => closeModal()}></img>
            <div className="container">
                <div className="chat-content" ref={chatBoxRef}>
                    <ul className="chat-messages">
                        {publicChats.map((chat, index) => (
                            <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                                {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                <div className="message-data">{chat.message}</div>
                                {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="send-message">
                    <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
                    <button type="button" className="send-button" onClick={sendValue}>send</button>
                </div>
            </div>
        </ReactModal>
    )
}

export default ChatRoom
