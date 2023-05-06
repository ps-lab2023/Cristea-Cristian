import React from 'react'
import '../Styles/Navbar.css'
import { useNavigate } from 'react-router-dom'
import LogOutApi from '../Api/LogOutApi';
import ChatRoom from './ChatRoom';
import { useState } from 'react';

const Navbar = () => {
    const navigate = useNavigate();

    const [isChatRoomOpen, setChatRoomOpen] = useState(false)
    const logOutFunction = async () => {
        await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
        sessionStorage.clear()
        navigate("/")
    }

    const findPlansFunction = () => {
        navigate("/find-plans")
    }

    const myRecordsFunction = () => {
        navigate("/home")
    }

    const myPlansFunction = () => {
        navigate("/my-plans")
    }

    return (
        <nav className='navbar-container'>
            <ChatRoom
                isOpen={isChatRoomOpen}
                setOpen={setChatRoomOpen}
            />
            <h1 className='app-name'>FitnessTracker</h1>
            <ul className='user-container'>
                <li><button onClick={() => setChatRoomOpen(true)}>Chat room</button></li>
                <li><button onClick={myPlansFunction}>My plans</button></li>
                <li><button onClick={findPlansFunction}>Find plans</button></li>
                <li><button onClick={myRecordsFunction}>My records</button></li>
                <li><button onClick={logOutFunction}>Log out</button></li>
            </ul>
        </nav>
    )
}

export default Navbar