import axios from "axios";
import { DELETE_PLAN_ENTRY_URL } from "../Utils/UrlConstants";

export default async (planEntryId) => {
    try {
        await axios.delete(`${DELETE_PLAN_ENTRY_URL}${planEntryId}`);
    } catch (error) {
        return {
            isError: true
        }
    }
}