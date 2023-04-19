import axios from "axios";
import { REGISTER_URL } from "../Utils/UrlConstants";

export default async (username, name, gender, height, weight, email, password) => {

    try {
        const reponse = await axios.post(REGISTER_URL, {
            username: username,
            name: name,
            email: email,
            gender: gender,
            height: height,
            weight: weight,
            password: password,
            role: 1
        })
        return reponse.data
    } catch (error) {
        return {
            isError: true
        }
    }
}