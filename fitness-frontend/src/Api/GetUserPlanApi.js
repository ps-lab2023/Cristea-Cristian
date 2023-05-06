import axios from "axios";
import { GET_USER_PLAN} from "../Utils/UrlConstants";

export default async (userId, planId) => {
    try {
        const response = await axios.get(`${GET_USER_PLAN}?userId=${userId}&planId=${planId}`);
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}