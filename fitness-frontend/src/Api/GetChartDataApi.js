import axios from "axios";
import { GET_CHAR_DATA_URL } from "../Utils/UrlConstants";

export default async (userId, month, year, reportType) => {
    try {
        const response = await axios.get(`${GET_CHAR_DATA_URL}?userId=${userId}&month=${month}&year=${year}&reportType=${reportType}`);
        return response.data;
    } catch (error) {
        return {
            isError: true
        }
    }
}