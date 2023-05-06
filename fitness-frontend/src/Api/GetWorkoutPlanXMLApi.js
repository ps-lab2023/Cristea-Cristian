import axios from "axios";
import { GET_WORKOUT_PLAN_XML_URL } from "../Utils/UrlConstants";

export default async (planId) => {
    try {
        const response = await axios.get(`${GET_WORKOUT_PLAN_XML_URL}${planId}`);
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}