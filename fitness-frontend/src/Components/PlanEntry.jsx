import React from 'react';
import '../Styles/PlanEntry.css'

const PlanEntry = ({entryData}) => {
    return (
        <div id='plan-entry-container'>
            <div id='plan-entry-title-container'>
                <h1 className="plan-entry-info-header">{entryData.nameOfExercise}</h1>
                <h1 className="plan-entry-info-header">{entryData.noOfSets} sets X&nbsp;
                {   
                    entryData.noOfReps === 0 ? 
                        entryData.duration
                    :
                        `${entryData.noOfReps} reps`
                }</h1>
            </div>
            <h1 className='plan-entry-info'>You should rest <span>{entryData.restBetween}</span> seconds between sets.</h1>
            <h1 className='plan-entry-info'>{entryData.description}</h1>
        </div>
    )
}

export default PlanEntry