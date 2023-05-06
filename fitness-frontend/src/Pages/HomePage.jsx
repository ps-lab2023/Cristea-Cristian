import React, { useEffect } from 'react'
import CategoriesContainer from '../Components/CategoriesContainer'
import Overview from '../Components/Overview'
import '../Styles/HomePage.css'
import Navbar from '../Components/Navbar'
import AddRecordButton from '../Components/AddRecordButton'
import GetWorkoutRecordsApi from '../Api/GetWorkoutRecordsApi'
import MonthsRecords from '../Components/MonthsRecords'
import { useState } from 'react'
import { months } from '../Utils/Constants'
import FilterByActivityType from '../Api/FilterByActivityType'
import { useNavigate } from 'react-router-dom'
import AddWorkoutRecordModal from '../Components/AddEditWorkoutRecordModal'
import { activityTypes } from '../Utils/Constants'
import LogOutApi from '../Api/LogOutApi'

const HomePage = () => {
    const [overview, setOverview] = useState({
        duration: 0.0,
        numberOfSessions: 0,
        calories: 0
    })

    const [monthSummaries, setMonthSummaries] = useState([])

    const [buttonArray, setButtonArray] = useState([false, false, false, false, false, true])

    const [isAddWorkoutModalOpen, setAddWorkoutModalOpen] = useState(false)

    const overviewFormat = (km, min, times, kcal) => {
        return `${times} times ${min} min ${km} km ${kcal} kcal`
    }

    const navigate = useNavigate()

    const getWorkoutRecords = async (activityType) => {
        let response
        if (activityType === 5)
            response = await GetWorkoutRecordsApi((JSON.parse(sessionStorage.getItem("user"))).id)
        else
        {
            response = await FilterByActivityType((JSON.parse(sessionStorage.getItem("user"))).id, activityTypes[activityType])
        }    
        setOverview({
            duration: response.duration.toFixed(2),
            numberOfSessions: response.numberOfSessions,
            calories: response.calories,
        })
        setMonthSummaries([])
        response.monthSummaries.forEach(element => {
            setMonthSummaries(old =>
                [...old,
                {
                    month: months[element.month - 1],
                    year: element.year,
                    overview: overviewFormat(
                        element.totalDistance.toFixed(2),
                        element.totalTime.toFixed(2),
                        element.numberOfTimes,
                        element.calories,
                    ),
                    workoutRecords: element.workoutRecords
                }])
        });
    }

    const refreshPage = () => {
        let newBtn = Array(6).fill(false);
        newBtn[5] = true;
        setButtonArray(newBtn)
        getWorkoutRecords(5);
    }

    const filterByActivityType = event => {
        let newBtn = Array(6).fill(false)
        const activityType = parseInt(event.currentTarget.id)
        newBtn[activityType] = true;
        setButtonArray(newBtn)
        return getWorkoutRecords(activityType);
    }

    const logOutFunction = async () => {
        await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
        sessionStorage.clear()
        navigate("/")
    }

    const openAddWorkoutRecordModal = () => {
        setAddWorkoutModalOpen(true)
    }

    useEffect(() => {
        getWorkoutRecords(5)
    }, [])

    return (
        <div className='home-page-main-container'>
            <Navbar
                onClickFunction={logOutFunction}
            />
            <AddWorkoutRecordModal
                isOpen={isAddWorkoutModalOpen}
                setOpen={setAddWorkoutModalOpen}
                refreshPage={refreshPage}
                type={0}
            />
            <div className='main-page-center-div'>
                <div className='overview-categories-container'>
                    <Overview
                        duration={overview.duration}
                        numberOfSessions={overview.numberOfSessions}
                        numberOfCalories={overview.calories}
                    />
                    <AddRecordButton
                        onClickFunction={openAddWorkoutRecordModal}
                    />
                    <h1 className='filter-title'>Filter by activity type:</h1>
                    <CategoriesContainer
                        buttonArray={buttonArray}
                        onClickFunction={filterByActivityType}
                    />
                </div>
                <div className='workout-records-home'>
                    <MonthsRecords
                        monthsRecordsInfo={monthSummaries}
                        refreshPage={refreshPage}
                    />
                </div>
            </div>
        </div>
    )
}

export default HomePage