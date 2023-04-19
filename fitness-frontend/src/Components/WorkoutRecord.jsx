import React from 'react'
import '../Styles/WorkoutRecord.css'
import runningIcon from '../Icons/running.png'
import walkingIcon from '../Icons/walking.png'
import cyclingIcon from '../Icons/cycling.png'
import swimmingIcon from '../Icons/swimming.png'
import otherIcon from '../Icons/others.png'
import trashIcon from '../Icons/trash.svg'
import editIcon from '../Icons/edit-icon.svg'
import { months } from '../Utils/Constants'
import { useState } from 'react'
import ConfirmationMessageModal from './ConfirmationMessageModal'
import AddEditWorkoutRecordModal from './AddEditWorkoutRecordModal'
import DeleteWorkoutRecord from '../Api/DeleteWorkoutRecord'

const WorkoutRecord = ({
    workoutInfo,
    refreshPage
}) => {
    const activityTypes = ["Running", "Walking", "Cycling", "Swimming", "Gym"]
    let icon

    const [isConfirmationMessageModalOpen, setConfimationMessageModalOpen] = useState(false)

    const [isEditWorkoutModalOpen, setEditWorkoutModalOpen] = useState(false)

    const [activityType, setActivityType] = useState("Running")

    const getDate = () => {
        const strs = workoutInfo.date.split('-')
        const month = months[parseInt(strs[1]) - 1]
        const strs1 = strs[2].split('T')
        const day = strs1[0]
        const strs2 = strs1[1].split(':')
        const hour = strs2[0]
        const minutes = strs2[1]
        return `${month} ${day} ${hour}:${minutes}`
    }

    const openConfirmationMessageModal = () => {
        setConfimationMessageModalOpen(true)
    }

    const openEditWorkoutModal = () => {
        setActivityType(workoutInfo.activityType)
        setEditWorkoutModalOpen(true)
    }

     const deleteWorkoutRecord = async () => {
        await DeleteWorkoutRecord(workoutInfo.id);
        refreshPage();
        setConfimationMessageModalOpen(false);
    }

    switch (workoutInfo.activityType) {
        case 'RUNNING':
            icon = runningIcon
            break
        case 'WALKING':
            icon = walkingIcon
            break
        case 'CYCLING':
            icon = cyclingIcon
            break
        case 'SWIMMING':
            icon = swimmingIcon
            break
        case 'GYM':
            icon = otherIcon
            break
    }
    return (
        <div className="workout-record-main-container">
            <ConfirmationMessageModal
                text='Are you sure you want to delete this workout record?'
                isOpen={isConfirmationMessageModalOpen}
                setOpen={setConfimationMessageModalOpen}
                refreshPage={refreshPage}
                workoutId={workoutInfo.id}
                buttonText='Delete'
                buttonFunction={deleteWorkoutRecord}
            />
            <AddEditWorkoutRecordModal
                isOpen={isEditWorkoutModalOpen}
                setOpen={setEditWorkoutModalOpen}
                refreshPage={refreshPage}
                type={1}
                activityType={activityType}
                workoutInfo={workoutInfo}
            />
            <img src={icon} className='workout-icon' />
            <div className='info-container'>
                <div className='category-time-container'>
                    <h2 className='category-time-header'>
                        {activityTypes[workoutInfo.activityType]}
                    </h2>
                </div>
                <div className='workout-info-container'>
                    <h1 className='workout-info-header'>
                        {workoutInfo.duration}
                    </h1>
                    <div className='mini-info-container'>
                        <h1 className='workout-info-header'>
                            {workoutInfo.distance}
                        </h1>
                        <h3 className='um-header'>km</h3>
                    </div>
                    <div className='mini-info-container'>
                        <h1 className='workout-info-header'>
                            {workoutInfo.calories}
                        </h1>
                        <h3 className='um-header'>kcal</h3>
                    </div>
                </div>
                <div className='date-icons-container'>
                    <h2 className='category-time-header'>
                            {getDate()}
                    </h2>
                    <div className='icons-container'>
                        <img src={editIcon} className='edit-icon' title='Edit record' onClick={openEditWorkoutModal}/>
                        <img src={trashIcon} className='trash-icon' title='Delete record' onClick={openConfirmationMessageModal}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default WorkoutRecord