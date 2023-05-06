import React, { useState, useEffect } from 'react'
import '../Styles/AddPlanPage.css'
import { activityTypes, levels } from '../Utils/Constants'
import AddWorkoutPlanApi from '../Api/AddWorkoutPlanApi'
import AddPlanDetails from '../Components/AddPlanDetails'
import AddPlanDays from '../Components/AddPlanDays'
import EditWorkoutPlanApi from '../Api/EditWorkoutPlanApi'
import { useNavigate, useParams } from 'react-router-dom'
import GetWorkoutPlanApi from '../Api/GetWorkoutPlanApi'

const AddPlanPage = () => {

    const getDetailesFormData = async () => {
        const response = await GetWorkoutPlanApi(planIdParam);
        setDetailsFormData({
            name: response.name,
            mainActivity: response.mainActivity,
            level: response.level,
            goal: response.goal,
            description: response.description
        })
    }

    const {planIdParam} = useParams();

    const [detailsFormData, setDetailsFormData] = useState({
        name: '',
        mainActivity: 'RUNNING',
        level: 'BEGINNER',
        goal: '',
        description: ''
    })

    useEffect(() => {
        if(planIdParam !== undefined)
            getDetailesFormData()
    }, [])

    const navigate = useNavigate();

    const getEditMode = () => {
        if(planIdParam === undefined) 
            return false;
        return true;
    }
    const [editMode, setEditMode] = useState(getEditMode());

    const [page, setPage] = useState(1);

    const [detailsCompleted, setDetailsCompleted] = useState(false)

    const [emptyName, setEmptyName] = useState(false)

    const getPlanId = () => {
        if(planIdParam === undefined) 
            return 0;
        return planIdParam;
    }

    const [planId, setPlanId] = useState(getPlanId());

    const validateFormData = () => {
        if(!detailsFormData.name) {
            setEmptyName(true);
            return false;
        }
        return true;
    }

    const handleDetailsFormData = (event) => {
        let { name, value } = event.target
        if(name == 'name') setEmptyName(false);
        if (value < 0) value = 0
        setDetailsFormData(prevFormData => {
            return {
                ...prevFormData,
                [name]: value
            }
        })
    }
    
    const addWorkoutPlanRecord = async() => {
        if(editMode === false) {
            if(validateFormData()) 
            {
                const response = await AddWorkoutPlanApi(
                    (JSON.parse(sessionStorage.getItem("user"))).id,
                    detailsFormData.name,
                    activityTypes.indexOf(detailsFormData.mainActivity),
                    levels.indexOf(detailsFormData.level),
                    detailsFormData.goal,
                    detailsFormData.description
                )
                setPlanId(response.id);
            }
        } else {
            if(validateFormData()) 
            {
                const response = await EditWorkoutPlanApi(
                    planId,
                    detailsFormData.name,
                    detailsFormData.goal,
                    activityTypes.indexOf(detailsFormData.mainActivity),
                    levels.indexOf(detailsFormData.level),
                    detailsFormData.description
                )
                setPlanId(response.id);
            }
        }
    }
    
    const goToNextForm = () => {
        if(page === 1 && validateFormData()) 
        {
            setDetailsCompleted(true); 
            setPage(2);
            addWorkoutPlanRecord();
        }
        else if(page === 2) {
            navigate('/trainer-plans');
            setPage(1);
        }
    }

    const goBack = () => {
        if(page === 2 && detailsCompleted)
        {
            setEditMode(true);
            setPage(1);
            setDetailsCompleted(false);
        }
        else if(page === 1)
        {
            navigate('/trainer-plans');
        }
    }

    return (
        <div className='add-plan-details-page-main-container'>
            {detailsCompleted === false? 
                <AddPlanDetails
                    handleFormData={handleDetailsFormData}
                    emptyName={emptyName}
                    formData={detailsFormData}
                />
                :
                <AddPlanDays
                    planId={planId}
                    editMode={editMode}
                />
            }
            <div id='back-btn' onClick={() => goBack()}></div>
            <div id='next-btn' onClick={() => goToNextForm()}></div>
        </div>
    )
}

export default AddPlanPage