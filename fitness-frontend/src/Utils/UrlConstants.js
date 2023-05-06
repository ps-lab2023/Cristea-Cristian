export const BASE_URL = 'http://localhost:8080'
export const LOGIN_URL = `${BASE_URL}/user/login`
export const LOGOUT_URL = `${BASE_URL}/user/logOut/`
export const REGISTER_URL = `${BASE_URL}/user/register`
export const ADD_TRAINER_URL = `${BASE_URL}/user/addTrainer`
export const CHANGE_PASSWORD_URL = `${BASE_URL}/user/changePassword`
export const SEND_EMAIL = `${BASE_URL}/user/sendEmail/`
export const VERIFY_CODE = `${BASE_URL}/user/verifyCode`
export const GET_LOGGEDIN_USERS_URL = `${BASE_URL}/user/getLoggedInUsers`

export const GET_WORKOUT_RECORDS_URL = `${BASE_URL}/workoutRecord/getByUserId/`
export const FILTER_BY_ACTIVITY_TYPE_URL = `${BASE_URL}/workoutRecord/filterByActivityType/`
export const ADD_WORKOUT_RECORD_URL = `${BASE_URL}/workoutRecord/addWorkoutRecord`
export const DELETE_WORKOUT_RECORD_URL = `${BASE_URL}/workoutRecord/deleteWorkoutRecord/`
export const EDIT_WORKOUT_RECORD_URL = `${BASE_URL}/workoutRecord/editWorkoutRecord`
export const GET_CHAR_DATA_URL = `${BASE_URL}/workoutRecord/chartData`

export const ADD_WORKOUT_PLAN_URL = `${BASE_URL}/workoutPlan/addWorkoutPlan`
export const EDIT_WORKOUT_PLAN_URL = `${BASE_URL}/workoutPlan/updateWorkoutPlan`
export const GET_WORKOUT_PLANS_URL = `${BASE_URL}/workoutPlan/getByTrainerId/`
export const GET_WORKOUT_PLAN_DAYS_URL = `${BASE_URL}/workoutPlan/getWorkoutPlanDays/`
export const GET_WORKOUT_PLAN_URL = `${BASE_URL}/workoutPlan/getWorkoutPlan/`
export const DELETE_WORKOUT_PLAN_URL = `${BASE_URL}/workoutPlan/deleteWorkoutPlan/`
export const GET_NEW_WORKOUT_PLANS_URL = `${BASE_URL}/workoutPlan/getNewWorkoutPlans/`
export const GET_WORKOUT_PLAN_XML_URL = `${BASE_URL}/workoutPlan/getWorkoutPlanXML/`

export const ADD_PLAN_DAY_URL = `${BASE_URL}/planDay/addPlanDay`
export const GET_PLAN_DAY_ENTRIES_URL = `${BASE_URL}/planDay/getPlanDayEntries/`
export const DELETE_PLAN_DAY_URL = `${BASE_URL}/planDay/deletePlanDay/`

export const ADD_PLAN_ENTRY_URL = `${BASE_URL}/planEntry/addPlanEntry`
export const DELETE_PLAN_ENTRY_URL = `${BASE_URL}/planEntry/deletePlanEntry/`
export const EDIT_PLAN_ENTRY_URL = `${BASE_URL}/planEntry/updatePlanEntry`

export const ADD_USER_PLAN_URL = `${BASE_URL}/userPlan/addUserPlan`
export const GET_USER_WORKOUT_PLANS = `${BASE_URL}/userPlan/getUserWorkoutPlans/`
export const DELETE_USER_PLAN = `${BASE_URL}/userPlan/deleteUserPlan`
export const GET_USER_PLAN = `${BASE_URL}/userPlan/getUserPlan`
export const UPDATE_CURRENT_DAY = `${BASE_URL}/userPlan/updateCurrentDay`