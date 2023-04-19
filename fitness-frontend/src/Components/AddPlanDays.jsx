import React, {useState, useEffect} from 'react'
import AddPlanDay from './AddPlanDay'
import plusIcon from '../Icons/plus.svg'
import uuid from '../Utils/key'
import '../Styles/AddPlanDays.css'
import AddPlanDayApi from '../Api/AddPlanDayApi'
import GetWorkoutPlanDaysApi from '../Api/GetWorkoutPlanDaysApi'

const AddPlanDays = ({planId, editMode}) => {
    const [planDays, setPlanDays] = useState([]);

    const addPlanDay = async () => {
        const response = await AddPlanDayApi(planId, planDays.length+1);
        return response;
    }

    const addPlanDayFunction = async () => {
        const planDay = await addPlanDay()
        setPlanDays(oldArray => [...oldArray, {
            id: planDay.id,
            dayNumber: planDay.dayNumber,
            noOfEntries: 0 
        }]);
    }

    const getPlanDays = async () => {
        const response = await GetWorkoutPlanDaysApi(planId);
        return response;
    }

    const getPlanDaysFunction = async () => {
        const planDays = await getPlanDays();
        setPlanDays(planDays);
    }
    
    useEffect(() => {
        if(planId !== 0 && editMode === false)
        {
            addPlanDayFunction();
        }
        else if(editMode === true)
        {
            getPlanDaysFunction();
        }
    }, [planId])


    return (
        <div id='add-days-container'>
            {
                planDays.map((e, i) =>
                    <AddPlanDay key={uuid()} expanded={false} dayNumber={i+1} dayId={e.id} noOfEntries={e.noOfEntries} getPlanDays={getPlanDaysFunction}/>
                )
            }
            <img src={plusIcon} id='plus-icon-plan-days' 
                onClick={() => {getPlanDaysFunction();addPlanDayFunction()}}
                title='Add a new day'/>
        </div>
    )
}

export default AddPlanDays