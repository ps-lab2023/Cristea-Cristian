import axios from "axios";
import { ADD_TRAINER_URL } from "../Utils/UrlConstants";

export default async (username, name, email, gender) => {
    try {
        const response = await axios.post(ADD_TRAINER_URL, {
            username: username,
            name: name,
            email: email,
            gender: gender
        })
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}