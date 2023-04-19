import axios from "axios";
import { DELETE_PLAN_DAY_URL } from "../Utils/UrlConstants";

export default async (planDayId) => {
    try {
        await axios.delete(`${DELETE_PLAN_DAY_URL}${planDayId}`)
    } catch (error) {
        return {
            isError: true
        }
    }
}