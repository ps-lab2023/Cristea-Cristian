import axios from "axios";
import { GET_WORKOUT_PLAN_DAYS_URL } from "../Utils/UrlConstants";

export default async (workoutPlanId) => {
    try {
        const response = await axios.get(`${GET_WORKOUT_PLAN_DAYS_URL}${workoutPlanId}`);
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}