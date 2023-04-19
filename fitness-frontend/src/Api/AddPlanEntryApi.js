import axios from "axios";
import { ADD_PLAN_ENTRY_URL } from "../Utils/UrlConstants";

export default async (planDayId, orderNumber, nameOfExercise, hours, minutes, seconds, noOfReps, description, restBetween, noOfSets) => {
    try {
        const response = await axios.post(ADD_PLAN_ENTRY_URL, {
            planDayId: planDayId, 
            orderNumber: orderNumber,
            nameOfExercise: nameOfExercise,
            duration: {
                hours: hours,
                minutes: minutes,
                seconds: seconds
            },
            noOfReps: noOfReps,
            description: description,
            restBetween: restBetween,
            noOfSets: noOfSets
        })
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}