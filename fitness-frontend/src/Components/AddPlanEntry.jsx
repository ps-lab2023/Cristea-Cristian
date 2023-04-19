import React, { useState } from 'react'
import '../Styles/AddPlanEntry.css'
import downArrow from '../Icons/down.svg'
import upArrow from '../Icons/up.svg'
import saveIcon from '../Icons/save.svg'
import AddPlanEntryApi from '../Api/AddPlanEntryApi'
import trashIcon from '../Icons/icon-trash.svg'
import DeletePlanEntryApi from '../Api/DeletePlanEntryApi'
import EditPlanEntryApi from '../Api/EditPlanEntryApi'

const AddPlanEntry = ({ expanded, dayId, orderNumber, entryData, setPlanEntries, getPlanEntries}) => {
  const [formData, setFormData] = useState(
    () => {
      if(entryData === undefined) {
        return {
          nameOfExercise: '',
          hours: '',
          minutes: '',
          seconds: '',
          noOfReps: '',
          description: '',
          restBetween: '',
          noOfSets: ''
        }
      }
      else {
        return {
          nameOfExercise: entryData.nameOfExercise,
          hours: parseInt(entryData.duration.substring(0,2)) === 0 ? undefined : parseInt(entryData.duration.substring(0,2)),
          minutes: parseInt(entryData.duration.substring(3,6)) === 0 ? undefined :  parseInt(entryData.duration.substring(3,6)),
          seconds: parseInt(entryData.duration.substring(7,10)) === 0 ? undefined : parseInt(entryData.duration.substring(7,10)),
          noOfReps: entryData.noOfReps === 0 ? undefined : entryData.noOfReps,
          description: entryData.description,
          restBetween: entryData.restBetween === 0 ? undefined : entryData.restBetween,
          noOfSets: entryData.noOfSets
        }
      }
    }
  );
  const [expand, setExpand] = useState(expanded)
  const [emptyName, setEmptyName] = useState(false)
  const [emptyNumberOfSets, setEmptyNumberOfSets] = useState(false)

  const handleFormData = (event) => {
    let { name, value } = event.target
    if(name === 'nameOfExercise') setEmptyName(false);
    if(name === 'noOfSets') setEmptyNumberOfSets(false);
    if (value < 0) value = 0
    setFormData(prevFormData => {
        return {
            ...prevFormData,
            [name]: value
        }
    })
  }

  const validateFormData = () => {
    let validForm = true
    if (formData.nameOfExercise === '') {
      setEmptyName(true);
      validForm = false;
    } 
    if (formData.noOfSets === '') {
      setEmptyNumberOfSets(true);
      validForm = false;
    }
    return validForm;
  }

  const addPlanEntry = async () => {
    if(entryData === undefined) {
      if(validateFormData()) {
        setExpand(false);
        const response = await AddPlanEntryApi(dayId, 
          orderNumber,
          formData.nameOfExercise,
          formData.hours === '' ? 0: formData.hours,
          formData.minutes === '' ? 0 : formData.minutes,
          formData.seconds === '' ? 0 : formData.seconds,
          formData.noOfReps === '' ? 0 : formData.noOfReps,
          formData.description,
          formData.restBetween === '' ? 0: formData.restBetween,
          formData.noOfSets);
        setPlanEntries(oldArray => [...oldArray.slice(0, -1), response]);
      }
    } else {
      if(validateFormData()) {
        setExpand(false);
          await EditPlanEntryApi(entryData.id,
          formData.nameOfExercise,
          formData.hours === '' ? 0: formData.hours,
          formData.minutes === '' ? 0 : formData.minutes,
          formData.seconds === '' ? 0 : formData.seconds,
          formData.noOfReps === '' ? 0 : formData.noOfReps,
          formData.description,
          formData.restBetween === '' ? 0: formData.restBetween,
          formData.noOfSets);
        getPlanEntries();
      }
    }
  }

  const deletePlanEntry = async () => {
    if(entryData !== undefined) {
      await DeletePlanEntryApi(entryData.id);
      getPlanEntries();
    }
    else {
      setPlanEntries(oldArray => oldArray.slice(0, -1));
    }
  }

  const errorNameInputStyle = {
    borderBottom: emptyName ? "2px solid rgba(255, 0, 0, 0.5)" : "2px solid #D8DAD3"
  }

  const errorNoOfSetsInputStyle = {
    borderBottom: emptyNumberOfSets ? "2px solid rgba(255, 0, 0, 0.5)" : "2px solid #D8DAD3"
  }

  return (
    <div>
      {expand === true ? 
        <div id="add-plan-entry-container">
          <div id="name-of-exercise-container">
            <label htmlFor='name-entry-input' className='add-entry-label'>
              Name
            </label>
            <div id='name-icon-container'>
              <input id="name-entry-input"
                type='text'
                placeholder='Exercise name...'
                className='add-entry-input'
                name='nameOfExercise'
                value={formData.nameOfExercise}
                onChange={handleFormData}
                style={errorNameInputStyle}
              />
              { entryData !== undefined && <img src={upArrow} onClick={() => setExpand(false)}/> }
            </div>
          </div>
          <div id='no-of-sets'>
            <label htmlFor='sets-input' className='add-entry-label'>
              Number of sets
            </label>
            <input id='sets-input'
              type='number'
              className='add-entry-input'
              min={0}
              name='noOfSets'
              value={formData.noOfSets}
              onChange={handleFormData}
              style={errorNoOfSetsInputStyle}
            />
          </div>
          <div id='duration-container'>
            <label htmlFor='h-input' className='add-entry-label'>
              Duration of a set
            </label>
            <div id='duration-inputs-container'>
              <input id='hour-input'
                type='number'
                placeholder='Hours...'
                className='add-entry-input'
                min={0}
                name='hours'
                value={formData.hours}
                onChange={handleFormData}
              />
              <input id='minute-input'
                type='number'
                placeholder='Minutes...'
                className='add-entry-input'
                min={0}
                max={59}
                name='minutes'
                value={formData.minutes}
                onChange={handleFormData}
              />
              <input id='sec-input'
                type='number'
                placeholder='Seconds...'
                className='add-entry-input'
                min={0}
                max={59}
                name='seconds'
                value={formData.seconds}
                onChange={handleFormData}
              />
            </div>
          </div>
          <div id='no-of-reps-container'>
            <label htmlFor='no-of-reps-input' className='add-entry-label'>
              Repetitions per set
            </label>
            <input id='no-of-reps-input'
              type='number'
              className='add-entry-input'
              min={0}
              name='noOfReps'
              value={formData.noOfReps}
              onChange={handleFormData}
            />
          </div>
          <div id='rest-container'>
            <label htmlFor='rest-input' className='add-entry-label'>
              Rest between sets
            </label>
            <input id='rest-input'
              type='number'
              placeholder='Seconds...'
              className='add-entry-input'
              min={0}
              name='restBetween'
              value={formData.restBetween}
              onChange={handleFormData}
            />
          </div>
          <div id='description-entry-container'>
            <label htmlFor='description-input' className='add-entry-label'>
              Description
            </label>
            <input id='description-input'
              type='text'
              placeholder='Add description...'
              className='add-entry-input'
              name='description'
              value={formData.description}
              onChange={handleFormData}
            />
          </div>
          <div id='icons-div'>
            <img src={trashIcon} id='trash-icon' title='Delete exercise' onClick={() => deletePlanEntry()}/>
            <img src={saveIcon} id='save-icon' title='Save exercise' onClick={() => addPlanEntry()}/>
          </div>
        </div> 
        : 
        entryData !== undefined ?
        <div id='overview-entry-container'>
          <h1 className="plan-entry-info">{entryData.nameOfExercise}</h1>
          <img src={downArrow} className='add-plan-entry-icon' onClick={() => setExpand(true)}/>
        </div> :
        <div id='overview-entry-container'>
        <h1 className="plan-entry-info">{formData.nameOfExercise}</h1>
        <img src={downArrow} className='add-plan-entry-icon' onClick={() => setExpand(true)}/>
      </div>
      }
    </div>
  )
}

export default AddPlanEntry