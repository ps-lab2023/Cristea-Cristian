import axios from "axios";
import { GET_LOGGEDIN_USERS_URL } from "../Utils/UrlConstants";

export default async () => {
    try {
        const response = await axios.get(GET_LOGGEDIN_USERS_URL);
        return response.data
    } catch (error) {
        return {
            isError: true
        }
    }
}