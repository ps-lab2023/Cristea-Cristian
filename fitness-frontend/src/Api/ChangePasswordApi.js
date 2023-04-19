import axios from "axios";
import { CHANGE_PASSWORD_URL } from "../Utils/UrlConstants";

export default async (userId, password) => {
    try {
        await axios.put(CHANGE_PASSWORD_URL, {
            userId: userId, 
            password: password
        })
    } catch (error) {
        return {
            isError: true
        }
    }
}