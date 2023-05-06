import axios from "axios";
import { DELETE_USER_PLAN } from "../Utils/UrlConstants";

export default async (userId, planId) => {
    try {
        await axios.delete(`${DELETE_USER_PLAN}?userId=${userId}&planId=${planId}`)
    } catch (error) {
        return {
            isError: true
        }
    }
}