import axios from "axios";
import { GET_WORKOUT_PLANS_URL } from "../Utils/UrlConstants";

export default async (trainerId) => {
    try {
        const response = await axios.get(`${GET_WORKOUT_PLANS_URL}${trainerId}`);
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}