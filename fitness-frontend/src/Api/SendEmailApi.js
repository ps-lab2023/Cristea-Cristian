import axios from "axios";
import { SEND_EMAIL } from "../Utils/UrlConstants";

export default async (email) => {
    try {
        await axios.post(`${SEND_EMAIL}${email}`)
    } catch (error) {
        return {
            isError: true
        }
    }
}