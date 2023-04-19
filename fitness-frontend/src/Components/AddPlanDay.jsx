import React, { useState } from 'react'
import AddPlanEntry from './AddPlanEntry'
import '../Styles/AddPlanDay.css'
import plusIcon from '../Icons/plus.svg'
import uuid from '../Utils/key'
import downArrow from '../Icons/down.svg'
import upArrow from '../Icons/up.svg'
import GetPlanDayEntriesApi from '../Api/GetPlanDayEntriesApi'
import trashIcon from '../Icons/icon-trash.svg'
import DeletePlanDayApi from '../Api/DeletePlanDayApi'

const AddPlanDay = ({ expanded, dayNumber, dayId, noOfEntries, getPlanDays}) => {
  const [planEntries, setPlanEntries] = useState([])
  const [expand, setExpand] = useState(expanded)

  const AddPlanEntryFunction = () => {
    setPlanEntries(oldArray => [...oldArray, undefined]);
  }

  const getPlanEntries = async () => {
    const response = await GetPlanDayEntriesApi(dayId);
    setPlanEntries(response);
    setExpand(true)
  }

  const iconsDisabled = () => {
    return planEntries.some(e => e === undefined);
  }

  const deleteDay = async () => {
    await DeletePlanDayApi(dayId);
    getPlanDays();
  }

  return (
    <div>
      {expand === true ? 
        <div id='add-plan-day-container'>
          <div id='day-header-container'>
            <h1 id='day-header'>Day {dayNumber}</h1>
            {planEntries.length === 0 && <h2 id='is-rest-day-header'>rest day</h2>}
            <img src={upArrow} className='arrow-icon' onClick={() => setExpand(false)}/>
          </div>
          <div id='day-entries-container'>
            {planEntries.length === 1 && planEntries[0] === undefined ? <AddPlanEntry orderNumber={1} key={uuid()} dayId={dayId} expanded={true} entryData={undefined} setPlanEntries={setPlanEntries}/> :
            planEntries.map((e, i) => 
              <div key={uuid()} id='entry-container'>
                <label key={uuid()} id='day-number-label'>{i+1}</label>
                { 
                    e === undefined ?
                      <AddPlanEntry key={uuid()} orderNumber={i+1} dayId={dayId} expanded={true} entryData={e} setPlanEntries={setPlanEntries} getPlanEntries={getPlanEntries}/>
                    :
                      <AddPlanEntry key={uuid()} orderNumber={i+1} dayId={dayId} expanded={false} entryData={e} setPlanEntries={setPlanEntries} getPlanEntries={getPlanEntries}/>
                }
              </div>
            )}
          </div>
          <div id='icon-container'>
            {!iconsDisabled() && <img src={trashIcon} id='delete-day-icon' title='Delete day' onClick={() => deleteDay()}/>}
            {!iconsDisabled() && <img src={plusIcon} id='plus-icon' title='Add a new exercise' onClick={() => AddPlanEntryFunction()}/>}
          </div>
        </div> :
        <div>
          <div id='add-plan-day-container'>
            <div id='day-header-container'>
              <h1 id='day-header'>Day {dayNumber}</h1>
              {(noOfEntries === 0 && planEntries.length === 0)? 
                <h2 id='is-rest-day-header'>rest day</h2> :
                <h2 id='is-rest-day-header'>{noOfEntries === 0 ? planEntries.length : noOfEntries}
                  {(noOfEntries === 0 ? planEntries.length : noOfEntries) === 1 ? " exercise" : " exercises"}</h2>
              }
              <img src={downArrow} className='arrow-icon' onClick={() => getPlanEntries()}/>
            </div>
          </div>
        </div>
      }
    </div>
  )
}

export default AddPlanDay