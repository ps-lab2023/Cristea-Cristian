import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import '../Styles/TrainerChangePasswordPage.css';
import LeftSidePhoto from '../Components/LeftSidePhoto';
import Input from '../Components/Input';
import Button from '../Components/Button';
import ChangePasswordApi from '../Api/ChangePasswordApi';
import LogInApi from '../Api/LogInApi';

const ChangePasswordPage = () => {
    const [formData, setFormData] = useState({
        password: '',
        confirmPassword: ''
    });

    const [emptyPasswordError, setEmptyPasswordError] = useState(false);
    const [emptyConfirmError, setEmptyConfirmError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate()

    const handleFormData = (event) => {
        const { name, value } = event.target
        setFormData(prevFormData => {
            return {
                ...prevFormData,
                [name]: value
            }
        })
    }

    const validateFormData = () => {
        setEmptyPasswordError(false);
        setEmptyConfirmError(false);

        let error = false;

        if (!formData.password) {
            setEmptyPasswordError(true);
            error = true;
        }
        if (!formData.confirmPassword) {
            setEmptyConfirmError(true);
            error = true;
        }
        if (error) {
            setErrorMessage("All fields marked with * are mandatory!");
        }
        return !error;
    }

    const changePasswordFunction = async () => {
        if(validateFormData()) {
            if (formData.password !== formData.confirmPassword) {
                setErrorMessage("The password confirmation does not match!");
                setEmptyConfirmError(true);
                setEmptyPasswordError(true);
            } else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/.test(formData.password)) {
                setEmptyPasswordError(true);
                setErrorMessage("The password does not meet complexity requirements!")
            } else {
                await ChangePasswordApi(
                    (JSON.parse(sessionStorage.getItem("user"))).id,
                    formData.password
                )
                if((JSON.parse(sessionStorage.getItem("user"))).role == 'TRAINER')
                    navigate('/trainer-plans')
                else
                    navigate('/home')
            }
        }
    }
    return (
        <div className='login-main-container'>
            <LeftSidePhoto />
            <div className='right-side-register'>
                <div className='password-trainer-inputs-container'>
                <Input
                        labelText='Change password*'
                        type='password'
                        placeholder='Enter your new password here...'
                        name='password'
                        id='password'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyPasswordError}
                    />
                    <Input
                        labelText='Confirm Password*'
                        type='password'
                        placeholder='Enter your new password again...'
                        name='confirmPassword'
                        id='confirmPassword'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyConfirmError}
                    />
                    {errorMessage ? <p className='error-message'>{errorMessage}</p> : <div className='no-error-message'> </div>}
                </div>
                <div className='change-password-button'>
                    <Button
                        text='Submit'
                        onClickFunction={changePasswordFunction}
                    />
                </div>
            </div>
        </div>
    )
}

export default ChangePasswordPage