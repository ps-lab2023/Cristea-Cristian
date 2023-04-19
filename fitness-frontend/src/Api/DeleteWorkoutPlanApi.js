import axios from "axios";
import { DELETE_WORKOUT_PLAN_URL } from "../Utils/UrlConstants";

export default async (workoutPlanId) => {
    try {
        await axios.delete(`${DELETE_WORKOUT_PLAN_URL}${workoutPlanId}`);
    } catch (error) {
        return {
            isError: true
        }
    }
}