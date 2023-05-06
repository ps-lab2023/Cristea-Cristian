import React, {useEffect, useState} from 'react'
import GetNewWorkoutPlansApi from '../Api/GetNewWorkoutPlansApi'
import Navbar from '../Components/Navbar'
import WorkoutPlanOverview from '../Components/WorkoutPlanOverview'
import uuid from '../Utils/key'

const FindPlansPage = () => {
    const [workoutPlans, setWorkoutPlans] = useState([])

    const getWorkoutPlans = async () => {
        const response = await GetNewWorkoutPlansApi((JSON.parse(sessionStorage.getItem("user"))).id);
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
                            {p[0] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[0]} getWorkoutPlans={getWorkoutPlans} isTrainer={false} userText={"See plan"} followedPlan={false}/>}
                            {p[1] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[1]} getWorkoutPlans={getWorkoutPlans} isTrainer={false} userText={"See plan"} followedPlan={false}/>}
                        </div>
                    )
                }
            </div>
        )
    }
    
    useEffect(() => {
        getWorkoutPlans()
    }, [])


    return (
        <div className='trainer-plans-page-main-container'>
            <Navbar
            />
            {getPlanPairs()}
        </div>
      )
}

export default FindPlansPage