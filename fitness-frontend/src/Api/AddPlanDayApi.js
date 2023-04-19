import axios from "axios";
import { ADD_PLAN_DAY_URL } from "../Utils/UrlConstants";

export default async (planId, dayNumber) => {
    try {
        const response = await axios.post(ADD_PLAN_DAY_URL, {
            planId: planId,
            dayNumber: dayNumber
        })
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}