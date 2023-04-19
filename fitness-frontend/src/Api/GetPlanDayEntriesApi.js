import axios from "axios";
import { GET_PLAN_DAY_ENTRIES_URL } from "../Utils/UrlConstants";

export default async (planDayId) => {
    try {
        const response = await axios.get(`${GET_PLAN_DAY_ENTRIES_URL}${planDayId}`);
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    } 
}