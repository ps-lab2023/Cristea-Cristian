import React from 'react'
import '../Styles/Navbar.css'
import { useNavigate } from 'react-router-dom'
import LogOutApi from '../Api/LogOutApi'
import ChatRoom from './ChatRoom';
import { useState } from 'react';

const TrainerNavbar = ({
}) => {
    const navigate = useNavigate();
    const [isChatRoomOpen, setChatRoomOpen] = useState(false)

    const newPlanFunction = () => {
        navigate("/add-plan")
    }

    const logOutFunction = async () => {
        await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
        sessionStorage.clear()
        navigate("/")
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
                <li><button onClick={newPlanFunction}>Add a plan</button></li>
                <li><button href='/trainer-plans'>My plans</button></li>
                <li><button onClick={logOutFunction}>Log out</button></li>
            </ul>
        </nav>
    )
}

export default TrainerNavbar