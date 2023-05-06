import axios from "axios";
import { LOGOUT_URL } from "../Utils/UrlConstants";

export default async (userId) => {
    try{
        await axios.post(`${LOGOUT_URL}${userId}`)
    } catch (error) {
        return {
            isError: true
        }
    }
}