import React from 'react'
import personIcon from '../Icons/person-circle.png'
import '../Styles/Navbar.css'

const Navbar = ({
    onClickFunction
}) => {
    return (
        <nav className='navbar-container'>
            <h1 className='app-name'>FitnessTracker</h1>
            <ul className='user-container'>
                <li><a >My plans</a></li>
                <li><a >Find plans</a></li>
                <li><a >Edit profile</a></li>
                <li><a onClick={onClickFunction}>Log out</a></li>
            </ul>
        </nav>
    )
}

export default Navbar