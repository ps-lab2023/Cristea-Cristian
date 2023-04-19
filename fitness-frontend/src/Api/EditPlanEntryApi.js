import axios from "axios";
import { EDIT_PLAN_ENTRY_URL } from "../Utils/UrlConstants";

export default async (id, nameOfExercise, hours, minutes, seconds, noOfReps, description, restBetween, noOfSets) => {
    try {
        const response = await axios.put(EDIT_PLAN_ENTRY_URL, {
            id: id,
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
        });
    } catch (error) {
        return {
            isError: true
        }
    }
}