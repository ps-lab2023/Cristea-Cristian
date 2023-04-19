import React from 'react'
import '../Styles/Navbar.css'
import { useNavigate } from 'react-router-dom'

const TrainerNavbar = ({
    logOutFunction,
    newPlanFunction
}) => {
    return (
        <nav className='navbar-container'>
            <h1 className='app-name'>FitnessTracker</h1>
            <ul className='user-container'>
                <li><a onClick={newPlanFunction}>Add a plan</a></li>
                <li><a href='/trainer-plans'>My plans</a></li>
                <li><a onClick={logOutFunction}>Log out</a></li>
            </ul>
        </nav>
    )
}

export default TrainerNavbar