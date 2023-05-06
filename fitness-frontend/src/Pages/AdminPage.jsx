import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import LogOutApi from '../Api/LogOutApi';
import LeftSidePhoto from '../Components/LeftSidePhoto';
import Input from '../Components/Input';
import Button from '../Components/Button';
import '../Styles/AdminPage.css'
import AddTrainerApi from '../Api/AddTrainerApi';
import ConfirmationMessageModal from '../Components/ConfirmationMessageModal';
import UsersTable from '../Components/UsersTable';

const AdminPage = () => {   
    const [formData, setFormData] = useState({
        username: '',
        fullName: '',
        gender: 'male',
        email: ''
    });

    const [response, setResponse] = useState({
        username: '',
        password: ''
    })

    const [isModalOpen, setModalOpen] = useState(false);
    const [emptyUsernameError, setEmptyUsernameError] = useState(false);
    const [emptyEmailError, setEmptyEmailError] = useState(false);
    const [emptyFullNameError, setEmptyFullNameError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [isTableOpen, setTableOpen] = useState(false);

    const validateFormData = () => {
        setEmptyUsernameError(false);
        setEmptyEmailError(false);
        setEmptyFullNameError(false);
        setErrorMessage("");

        let error = false;
        if (!formData.username) {
            setEmptyUsernameError(true);
            error = true;
        }
        if (!formData.fullName) {
            setEmptyFullNameError(true);
            error = true;
        }
        if (!formData.email) {
            setEmptyEmailError(true);
            error = true;
        }
        if (error) {
            setErrorMessage("All fields marked with * are mandatory!");
        }
        return !error;
    }

    const addTrainerFunction = async () => {
        if (validateFormData()) {
            if (!formData.email.includes('@') || !formData.email.includes('.')) {
                setEmptyEmailError(true);
                setErrorMessage("The email is not valid!");
            } else {
                const response = await AddTrainerApi(
                    formData.username,
                    formData.fullName,
                    formData.email,
                    formData.gender[0] === "female" ? 0 : 1
                )
                if (response.isError) {
                    setEmptyUsernameError(true);
                    setErrorMessage("This username is not available!")
                } else {
                    setModalOpen(true);
                    setResponse(response)
                }
            }
        }
    }

    const handleFormData = (event) => {
        const { name, value } = event.target
        setFormData(prevFormData => {
            return {
                ...prevFormData,
                [name]: value
            }
        })
    }

    const refreshPage = () => {
        window.location.reload()
    }

    const navigate = useNavigate();

    const logOutFunction = async () => {
        await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
        sessionStorage.clear()
        navigate("/")
    }

    return (
        <div className='login-main-container'>
            <ConfirmationMessageModal
                text={`Username: ${response.username}\nPassword: ${response.password}`}
                isOpen={isModalOpen}
                setOpen={setModalOpen}
                buttonText='Ok'
                buttonFunction={() => refreshPage()}
            />
            <UsersTable
                isOpen={isTableOpen}
                setOpen={setTableOpen}
            />
            <LeftSidePhoto />
            <div className='right-side-register'>
                <div className='add-trainer-inputs-container'>
                    <Input
                        labelText='Username*'
                        type='text'
                        placeholder="Enter the trainer's username here..."
                        name='username'
                        id='username'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyUsernameError}
                    />
                    <Input
                        labelText='Full Name*'
                        type='text'
                        placeholder="Enter trainer's full name here..."
                        name='fullName'
                        id='fullName'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyFullNameError}
                    />
                     <label className='gender-label'>Gender*</label>
                    <fieldset className='radio-buttons-gender'>
                        <div>
                            <input
                                type='radio'
                                id='male-radio-button'
                                name='gender'
                                className='gender-radio-button'
                                checked={formData.gender === "male"}
                                value="male"
                                onChange={handleFormData}
                            />
                            <label htmlFor='male-radio-button' className='gender-labels'>Male</label>
                        </div>
                        <div>
                            <input
                                type='radio'
                                id='female-radio-button'
                                name='gender'
                                className='gender-radio-button'
                                checked={formData.gender === "female"}
                                value="female"
                                onChange={handleFormData}
                            />
                            <label htmlFor='female-radio-button' className='gender-labels'>Female</label>
                        </div>
                    </fieldset>
                    <Input
                        labelText='Email*'
                        type='email'
                        placeholder="Enter trainer's email here..."
                        name='email'
                        id='email'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyEmailError}
                    />
                    {errorMessage ? <p className='error-message'>{errorMessage}</p> : <div className='no-error-message'> </div>}
                </div>
                <div className='add-trainer-button-container'>
                    <Button
                        text='Add trainer'
                        onClickFunction={addTrainerFunction}
                    />
                </div>
                <div className='admin-page-buttons'>
                    <Button
                        text='Log out'
                        onClickFunction={() => logOutFunction()}
                    />
                    <Button
                        text='Log in activity'
                        onClickFunction={() => setTableOpen(true)}
                    />
                </div>
            </div>
        </div>
    )
}

export default AdminPage