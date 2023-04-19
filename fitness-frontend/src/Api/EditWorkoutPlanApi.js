import axios from "axios";
import { EDIT_WORKOUT_PLAN_URL } from "../Utils/UrlConstants";

export default async (id, name, goal, mainActivity, level, description) => {
    try {
        const response = await axios.put(EDIT_WORKOUT_PLAN_URL, {
            id: id,
            name: name,
            goal: goal,
            mainActivity: mainActivity, 
            level: level,
            description: description
        });
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}