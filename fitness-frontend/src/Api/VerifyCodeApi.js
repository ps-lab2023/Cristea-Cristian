import axios from "axios";
import { VERIFY_CODE } from "../Utils/UrlConstants";

export default async (email, verificationCode) => {
    try{
        const reponse = await axios.get(`${VERIFY_CODE}?email=${email}&code=${verificationCode}`)
        return reponse.data
    } catch(error) {
        return {
            isError: true
        }
    }
}