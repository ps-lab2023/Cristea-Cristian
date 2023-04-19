import React from 'react'
import uuid from '../Utils/key'
import '../Styles/AddPlanDetails.css'
import { activityTypes, levels } from '../Utils/Constants'

const AddPlanDetails = ({handleFormData, emptyName, formData}) => {
    const errorInputStyle = {
        boxShadow: emptyName ? "0px 4px 4px rgba(255, 0, 0, 0.5)" : "0px 4px 4px rgba(0, 0, 0, 0.25)"
    }

    const errorMessage = 'The plan must have a name.'

    return (
        <div id='form-container'>
            <div id='plan-name-container'>
                <label htmlFor='name-input' className='plan-label'>
                    Plan name
                </label>
                <input id='name-input'
                    className='plan-input'
                    type='text'
                    name='name'
                    placeholder='Plan name...'
                    onChange={handleFormData}
                    value={formData.name}
                    style={errorInputStyle}
                >
                </input>
            </div>
            <div id='main-activity-container'>
                <label htmlFor='main-activity-drop-down' className='plan-label'>
                    Main activity
                </label>
                <select
                    id = 'main-activity-drop-down'
                    className='select-container'
                    name='mainActivity'
                    onChange={handleFormData}
                    value={formData.mainActivity}
                >
                    {activityTypes.map(a => (
                        <option value={a} key={uuid()}>{a.charAt(0) + a.slice(1).toLowerCase()}</option>
                    ))}
                </select>
            </div>
            <div id='level-container'>
                <label htmlFor='level-drop-down' className='plan-label'>
                    Level
                </label>
                <select
                    id = 'level-drop-down'
                    className='select-container'
                    name='level'
                    onChange={handleFormData}
                    value={formData.level}
                >
                    {levels.map(l => (
                        <option value={l} key={uuid()}>{l.charAt(0) + l.slice(1).toLowerCase()}</option>
                    ))}
                </select>
            </div>
            <div id='goal-container'>
                <label htmlFor='goal-input' className='plan-label'>
                    Goal
                </label>
                <input id='goal-input'
                    className='plan-input'
                    type='text'
                    name='goal'
                    placeholder='Goal...'
                    onChange={handleFormData}
                    value={formData.goal}
                >
                </input>
            </div>
            <div id='description-container'>
                <label htmlFor='description-area' className='plan-label'>
                    Description
                </label>
                <textarea id='description-area'
                    rows='4'
                    name='description'
                    cols='40'   
                    placeholder='Description...' 
                    onChange={handleFormData}
                    value={formData.description}
                />
            </div>
            {emptyName ? <p id='plan-name-error-message'>{errorMessage}</p> : <div id='no-plane-name-error-message'> </div>}
        </div>
    )
}

export default AddPlanDetails