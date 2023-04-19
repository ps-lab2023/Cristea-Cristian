import axios from "axios";
import { ADD_WORKOUT_PLAN_URL } from "../Utils/UrlConstants";

export default async (userId,planName, mainActivity, level, goal, description) => {
    try {
        const response = await axios.post(ADD_WORKOUT_PLAN_URL, {
            trainerId: userId,
            name: planName,
            mainActivity: mainActivity,
            level: level,
            goal: goal,
            description: description
        })
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}