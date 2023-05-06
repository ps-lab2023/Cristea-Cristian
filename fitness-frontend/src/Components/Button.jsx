import React from 'react'
import '../Styles/Button.css'

const Button = ({
    text,
    onClickFunction,
}) => {
    const buttonStyle = {
        width: '150px',
        height: '40px',
        background: (text === 'Add' || text === 'Follow plan') ? '#99C2A2' : '#FF5C5C',
        boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
        borderRadius: '10px',
        fontFamily: 'Oxygen',
        fontStyle: 'normal',
        fontWeight: '400',
        fontSize: '25px',
        color: '#FFFFFF',
        border: 'none',
        hover: '#253237'
      };
    return (
        <div>
            {(text !== 'Add' && text !== 'Delete' && text !== 'Follow plan' && text !== 'Unfollow plan') ? <button className='btn' onClick={onClickFunction}>
                {text}
            </button> : <button style={buttonStyle} onClick={onClickFunction}>
                {text}
            </button>
            }
        </div>
    )
}

export default Button