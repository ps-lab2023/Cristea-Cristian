import React, { useState } from 'react'
import '../Styles/WorkoutPlanOverview.css'
import runningIcon from '../Icons/running-icon.png'
import walkingIcon from '../Icons/walking-icon.png'
import swimmingIcon from '../Icons/swimmer.png'
import cyclingIcon from '../Icons/bicycle.png'
import gymIcon from '../Icons/gym-icon.png'
import { activityColors, activityTypes } from '../Utils/Constants'
import trashIcon from '../Icons/trash.svg'
import editIcon from '../Icons/edit-icon.svg'
import { useNavigate } from 'react-router-dom'
import ConfirmationMessageModal from './ConfirmationMessageModal'
import DeleteWorkoutPlanApi from '../Api/DeleteWorkoutPlanApi'
import downloadIcon from '../Icons/download-icon.svg'
import GetWorkoutPlanXMLApi from '../Api/GetWorkoutPlanXMLApi'
import beautify from 'xml-beautifier';

const WorkoutPlanOverview = ({ workoutPlanInfo, getWorkoutPlans, isTrainer, userText, followedPlan }) => {

    const [isConfirmationMessageModalOpen, setConfimationMessageModalOpen] = useState(false)

    const openConfirmationMessageModal = () => {
        setConfimationMessageModalOpen(true)
    }

    const deleteWorkoutPlan = async () => {
        await DeleteWorkoutPlanApi(workoutPlanInfo.id);
        getWorkoutPlans()
    }

    const getIcon = () => {
        switch (workoutPlanInfo.mainActivity) {
            case "RUNNING":
                return runningIcon;
            case "WALKING":
                return walkingIcon;
            case "SWIMMING":
                return swimmingIcon;
            case "CYCLING":
                return cyclingIcon;
            case "GYM":
                return gymIcon;
        }
    }

    const getColor = () => {
        const index = activityTypes.indexOf(workoutPlanInfo.mainActivity);
        return activityColors[index];
    }

    const style = {
        backgroundColor: `${getColor()}`,
        backgroundImage: `url(${getIcon()})`
    }

    const navigate = useNavigate();

    const editPlanFunction = () => {
        navigate(`/add-plan/${workoutPlanInfo.id}`)
    }

    const seeMoreButtonFunction = () => {
        navigate(`/plan/${workoutPlanInfo.id}/${followedPlan}`)
    }


    const downloadPlan = async () => {
        const response = await GetWorkoutPlanXMLApi(workoutPlanInfo.id);
        console.log(response)
        const xmlData = beautify(response);
        const blob = new Blob([xmlData], { type: "text/xml" });
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.download = `workout-plan-${workoutPlanInfo.id}.xml`;
        link.href = url;
        link.click();
    }

    return (
        <div id='workout-plan-overview-main-container' style={style}>
            <ConfirmationMessageModal
                text='Are you sure you want to delete this workout plan?'
                isOpen={isConfirmationMessageModalOpen}
                setOpen={setConfimationMessageModalOpen}
                workoutId={workoutPlanInfo.id}
                buttonText='Delete'
                buttonFunction={deleteWorkoutPlan}
            />
            <div id="plan-header">
                <h2 id='workout-plan-name-header'>{workoutPlanInfo.name}
                    {!isTrainer && <span id="trainer-span">&nbsp;&nbsp;by {workoutPlanInfo.trainerName}</span>}
                </h2>
                {followedPlan && <img src={downloadIcon} id="download-icon" onClick={() => downloadPlan()}/>}
            </div>
            <h2 className='workout-plan-info' id='goal-info'>Goal: {workoutPlanInfo.goal}</h2>
            <h2 className='workout-plan-info'>{workoutPlanInfo.noOfDays} days</h2>
            <h2 className='workout-plan-info'>Main activity: {workoutPlanInfo.mainActivity}</h2>
            <h2 className='workout-plan-info'>Level: {workoutPlanInfo.level}</h2>
            <h2 className='workout-plan-info'>{workoutPlanInfo.description}&nbsp;</h2>
            {isTrainer ?
                <div id='icons-container'>
                    <img src={trashIcon} className='plan-overview-icons' title='Delete plan' onClick={() => openConfirmationMessageModal()} />
                    <img src={editIcon} className='plan-overview-icons' title='Edit plan' onClick={() => editPlanFunction()} />
                </div>
                :
                <div id='icons-container'>
                    <button id="see-more-button" onClick={seeMoreButtonFunction}>{userText}
                        <span id='icon-see-more'>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </span>
                    </button>
                </div>
            }
        </div>
    )
}

export default WorkoutPlanOverview