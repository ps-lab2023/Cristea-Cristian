import axios from "axios";
import { GET_NEW_WORKOUT_PLANS_URL } from "../Utils/UrlConstants";

export default async (userId) => {
    try {
        const response = await axios.get(`${GET_NEW_WORKOUT_PLANS_URL}${userId}`);
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}