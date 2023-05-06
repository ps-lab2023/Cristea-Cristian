import axios from "axios";
import { UPDATE_CURRENT_DAY } from "../Utils/UrlConstants";

export default async(userId, planId) => {
    try {
        await axios.put(`${UPDATE_CURRENT_DAY}?userId=${userId}&planId=${planId}`);
    } catch (error) {
        return {
            isError: true
        }
    }
}