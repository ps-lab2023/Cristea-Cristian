import axios from "axios";
import { ADD_USER_PLAN_URL } from "../Utils/UrlConstants";

export default async (userId, planId) => {
    try {
        const response = await axios.post(ADD_USER_PLAN_URL, {
            userId: userId,
            planId: planId
        })
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}