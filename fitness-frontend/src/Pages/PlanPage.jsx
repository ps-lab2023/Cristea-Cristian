import React, {useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import PlanDay from "../Components/PlanDay";
import uuid from "../Utils/key";
import GetWorkoutPlanDaysApi from "../Api/GetWorkoutPlanDaysApi";
import '../Styles/PlanPage.css'
import Button from "../Components/Button";
import { useNavigate } from "react-router-dom";
import AddUserPlanApi from "../Api/AddUserPlanApi";
import DeleteUserPlanApi from "../Api/DeleteUserPlanApi";
import GetUserPlanApi from "../Api/GetUserPlanApi";
import UpdateCurrentDayApi from "../Api/UpdateCurrentDayApi";

const PlanPage = () => {
    const [planDays, setPlanDays] = useState([]);
    const [currentDay, setCurrentDay] = useState(0);

    const {planId, followedPlan} = useParams();

    const navigate = useNavigate();

    const getPlanDays = async () => {
        const response = await GetWorkoutPlanDaysApi(planId);
        return response;
    }


    const getPlanDaysFunction = async () => {
        const planDays = await getPlanDays();
        setPlanDays(planDays);
    }

    const cancelFunction = () => {
        if(followedPlan === 'true')
            navigate('/my-plans')
        else
            navigate('/find-plans')
    }

    const followPlanFunction = async () => {
        await AddUserPlanApi((JSON.parse(sessionStorage.getItem("user"))).id, planId);
        navigate('/my-plans')
    }

    const unfollowPlanFunction = async () => {
        await DeleteUserPlanApi((JSON.parse(sessionStorage.getItem("user"))).id, planId);
        navigate('/my-plans')
    }

    const getCurrentDay = async () => {
        const response = await GetUserPlanApi((JSON.parse(sessionStorage.getItem("user"))).id, planId);
        setCurrentDay(response.currentDay)
    }

    const updateCurrentDay = async () => {
        await UpdateCurrentDayApi((JSON.parse(sessionStorage.getItem("user"))).id, planId);
        getCurrentDay();
    }

    const getDone = (i) => {
        if(i < currentDay - 1) return true;
        return false;
    }

    useEffect(() => {
        getPlanDaysFunction();
        if(followedPlan === 'true') {
            getCurrentDay();
        }
    }, [])

    return (
        <div id='plan-page-container'>
            <div id='days-container'>
                {
                    planDays.map((e, i) =>
                        i === currentDay-1 ?
                            <PlanDay key={uuid()} dayNumber={i+1} dayId={e.id} noOfEntries={e.noOfEntries} showMarkIcon={followedPlan} markAsDoneFunction={updateCurrentDay}/>
                        :
                            <PlanDay key={uuid()} dayNumber={i+1} dayId={e.id} noOfEntries={e.noOfEntries} showMarkIcon={false} done={getDone(i)}/>     
                    )
                }
            </div>
            <div id='plan-page-button-container'>
                <Button
                    text='Back'
                    onClickFunction={cancelFunction}
                />
                {followedPlan === 'false' ?
                    <Button
                        text='Follow plan'
                        onClickFunction={followPlanFunction}
                    />
                    :
                    <Button
                        text='Unfollow plan'
                        onClickFunction={unfollowPlanFunction}
                    />
                }             
            </div>
        </div>
    )
}

export default PlanPage