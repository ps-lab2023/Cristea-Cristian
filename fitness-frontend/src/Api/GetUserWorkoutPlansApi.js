import axios from "axios";
import { GET_USER_WORKOUT_PLANS } from "../Utils/UrlConstants";

export default async (userId) => {
    try {
        const response =await axios.get(`${GET_USER_WORKOUT_PLANS}${userId}`);
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}