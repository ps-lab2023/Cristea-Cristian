import React, {useEffect, useState} from 'react'
import '../Styles/TrainerPlansPage.css'
import TrainerNavbar from '../Components/TrainerNavbar'
import WorkoutPlanOverview from '../Components/WorkoutPlanOverview'
import GetTrainerWorkoutPlansApi from '../Api/GetTrainerWorkoutPlansApi'
import uuid from '../Utils/key'
import { useNavigate } from 'react-router-dom'
import LogOutApi from '../Api/LogOutApi'

const TrainerPlansPage = () => {
  const [workoutPlans, setWorkoutPlans] = useState([])

  const getWorkoutPlans = async () => {
    const trainerId = (JSON.parse(sessionStorage.getItem("user"))).id;
    const response = await GetTrainerWorkoutPlansApi(trainerId);
    setWorkoutPlans(response);
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
                        {p[0] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[0]} getWorkoutPlans={getWorkoutPlans} isTrainer={true}/>}
                        {p[1] !== undefined && <WorkoutPlanOverview workoutPlanInfo={p[1]} getWorkoutPlans={getWorkoutPlans} isTrainer={true}/>}
                    </div>
                )
            }
        </div>
    )
  }

  const navigate = useNavigate()

  const logOutFunction = async () => {
    await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
    sessionStorage.clear()
    navigate("/")
  }

  const newPlanFunction = () => {
    navigate("/add-plan")
  }

  useEffect(() => {
    getWorkoutPlans();
  }, [])

  return (
    <div className='trainer-plans-page-main-container'>
        <TrainerNavbar 
          logOutFunction={logOutFunction}
          newPlanFunction={newPlanFunction}
        />
        {getPlanPairs()}
    </div>
  )
}

export default TrainerPlansPage