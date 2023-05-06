import React, { useState } from "react";
import upArrow from '../Icons/up.svg'
import PlanEntry from "./PlanEntry";
import downArrow from '../Icons/down.svg'
import GetPlanDayEntriesApi from "../Api/GetPlanDayEntriesApi";
import uuid from "../Utils/key";
import '../Styles/PlanDay.css'
import markAsDoneIcon from '../Icons/done.png'

const PlanDay = ({dayNumber, dayId, noOfEntries, showMarkIcon, markAsDoneFunction, done}) => {
    const [planEntries, setPlanEntries] = useState([])
    const [expand, setExpand] = useState(false)

    const getPlanEntries = async () => {
        const response = await GetPlanDayEntriesApi(dayId);
        setPlanEntries(response);
        setExpand(true)
    }

    const divStyle = {
        background: done == true ? '#99C2A2' : '#D8DAD3'
    } 

    return (
        <div>
            {expand === true ?
                <div id='plan-day-container' style={divStyle}>
                    <div id='day-header-container'>
                        <h1 id='day-header'>Day {dayNumber}</h1>
                        {noOfEntries === 0 && <h2 id='is-rest-day-header'>rest day</h2>}
                        <img src={upArrow} className='arrow-icon' onClick={() => setExpand(false)}/>
                    </div>
                    <div id='day-entries-container'>
                        {
                            planEntries.map((e, i) => 
                            <div key={uuid()} id='entry-container'>
                            <label key={uuid()} id='day-number-label'>{i+1}</label>
                            { 
                                e === undefined ?
                                    <PlanEntry key={uuid()} entryData={e} />
                                :
                                    <PlanEntry key={uuid()} entryData={e} />
                            }
                            </div>
                        )}
                    </div>
                    <div id='icon-container'>
                        {showMarkIcon === 'true' && <img src={markAsDoneIcon} id='delete-day-icon' title='Mark as done' onClick={() => markAsDoneFunction()}/>}
                    </div>
                </div> :
                <div id='plan-day-container' style={divStyle}>
                    <div id='day-header-container'>
                        <h1 id='day-header'>Day {dayNumber}</h1>
                        {(noOfEntries === 0) ? 
                            <h2 id='is-rest-day-header'>rest day</h2>
                            :
                            <h2 id='is-rest-day-header'>{noOfEntries} 
                                {noOfEntries === 1 ? " exercise" : " exercises"}</h2>
                        }
                        <img src={downArrow} className='arrow-icon' onClick={() => getPlanEntries()}/>
                    </div>
                </div>
            }
        </div>
    )
}

export default PlanDay