import React, {useEffect, useState} from 'react'
import Navbar from '../Components/Navbar'
import WorkoutPlanOverview from '../Components/WorkoutPlanOverview'
import uuid from '../Utils/key'
import GetUserWorkoutPlansApi from '../Api/GetUserWorkoutPlansApi'

const MyPlansPage = () => {
    const [workoutPlans, setWorkoutPlans] = useState([])

    const getUserWorkoutPlans = async () => {
        const response = await GetUserWorkoutPlansApi((JSON.parse(sessionStorage.getItem("user"))).id);
        setWorkoutPlans(response)
    }

    const getPlanPairs = () => {
        const pairs = [];
        for (let i = 0; i < workoutPlans.length; i += 2) {
          pairs.push([workoutPlans[i], workoutPlans[i + 1]]);
        }
        return (
            <div id="row-container">
                {
                    pairs.map( p =>
                        <div id="plans-pair" key={uuid()}>
                            {p[0] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[0]} isTrainer={false} userText={"See progress"} followedPlan={true}/>}
                            {p[1] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[1]} isTrainer={false} userText={"See progress"} followedPlan={true}/>}
                        </div>
                    )
                }
            </div>
        )
    }

    useEffect(() => {
        getUserWorkoutPlans()
    }, [])

    return (
        <div className='trainer-plans-page-main-container'>
            <Navbar
            />
            {getPlanPairs()}
        </div>
    )
}

export default MyPlansPage