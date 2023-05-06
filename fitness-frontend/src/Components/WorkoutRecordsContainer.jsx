import React from 'react'
import WorkoutRecord from './WorkoutRecord'
import '../Styles/WorkoutRecordsContainer.css'
import uuid from '../Utils/key'
import raport from '../Icons/raport.svg'
import ChartModal from './ChartModal'
import { useState } from 'react'

const WorkoutRecordsContainer = ({
    month,
    year,
    monthOverview,
    monthWorkoutRecords,
    refreshPage
}) => {
    const [isChartOpen, setChartOpen] = useState(false);

    const openChart = () => { 
        setChartOpen(true)
    }   

    const monthRecord = monthWorkoutRecords.map(
        (monthRecord) => (
            <div key={uuid()}>
                <hr className='separator'></hr>
                <WorkoutRecord
                    workoutInfo={monthRecord}
                    refreshPage={refreshPage}
                />
            </div>
        )
    )
    return (
        
        <div className='workout-records-container'>
            <ChartModal
                isOpen={isChartOpen}
                month={month}
                year={year}
                setOpen={setChartOpen}
            />
            <div className='month-year-overview-container'>
                <div>
                    <h1 className='month-year-header'>
                        {month} {year}
                    </h1>
                    <img src={raport} title='Show month raports' className='raport-icon' onClick={() => openChart()}/>
                </div>
                <h2 className='month-overview-header'>
                    {monthOverview}
                </h2>
                
            </div>
            {monthRecord}
        </div>
    )
}

export default WorkoutRecordsContainer