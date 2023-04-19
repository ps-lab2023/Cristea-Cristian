import React, { useState } from 'react'
import LeftSidePhoto from '../Components/LeftSidePhoto'
import Input from '../Components/Input'
import '../Styles/RegisterPage.css'
import Button from '../Components/Button'
import SignUpApi from '../Api/SignUpApi'
import LogInApi from '../Api/LogInApi'
import { useNavigate } from 'react-router-dom'

const RegisterPage = () => {
    const [formData, setFromData] = useState({
        username: '',
        fullName: '',
        gender: "male",
        height: 0,
        weight: 0,
        email: '',
        password: '',
        confirmPassword: ''
    })

    const [emptyUsernameError, setEmptyUsernameError] = useState(false);
    const [emptyEmailError, setEmptyEmailError] = useState(false);
    const [emptyPasswordError, setEmptyPasswordError] = useState(false);
    const [emptyConfirmError, setEmptyConfirmError] = useState(false);
    const [emptyFullNameError, setEmptyFullNameError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate()

    const handleFormData = (event) => {
        const { name, value } = event.target
        setFromData(prevFormData => {
            return {
                ...prevFormData,
                [name]: value
            }
        })
    }

    const validateFormData = () => {
        setEmptyUsernameError(false);
        setEmptyEmailError(false);
        setEmptyPasswordError(false);
        setEmptyConfirmError(false);
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

    const SignUpFunction = async () => {
        if (validateFormData()) {
            if (formData.password !== formData.confirmPassword) {
                setErrorMessage("The password confirmation does not match!");
                setEmptyConfirmError(true);
                setEmptyPasswordError(true);
            } else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/.test(formData.password)) {
                setEmptyPasswordError(true);
                setErrorMessage("The password does not meet complexity requirements!")
            } else if (!formData.email.includes('@') || !formData.email.includes('.')) {
                setEmptyEmailError(true);
                setErrorMessage("The email is not valid!");
            }
            else {
                const response = await SignUpApi(
                    formData.username,
                    formData.fullName,
                    formData.gender[0] === "female" ? 0 : 1 ,
                    formData.height,
                    formData.weight,
                    formData.email,
                    formData.password
                )
                if (response.isError) {
                    setEmptyUsernameError(true);
                    setErrorMessage("This username is not available!")
                } else {
                    await LogInApi(
                        formData.username,
                        formData.password
                    )
                    localStorage.setItem("user", JSON.stringify(
                        {
                            id: response.id,
                            username: response.username
                        }))
                    navigate("/home")
                }
            }
        }
    }

    return (
        <div className='login-main-container'>
            <LeftSidePhoto />
            <div className='right-side-register'>
                <div className='register-inputs-container'>
                    <Input
                        labelText='Username*'
                        type='text'
                        placeholder='Enter your username here...'
                        name='username'
                        id='username'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyUsernameError}
                    />
                     <Input
                        labelText='Full Name*'
                        type='text'
                        placeholder='Enter your full name here...'
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
                    <div className='height-weight-container'>
                        <div className='input-with-label'>
                            <Input
                                labelText='Height'
                                type='number'
                                name='height'
                                id='height'
                                width='100px'
                                min='0'
                                max='300'
                                placeholder='0'
                                onChangeHandler={handleFormData}
                            />
                            <label className='input-label'>cm</label>
                        </div>
                        <div className='input-with-label'>
                            <Input
                                labelText='Weight'
                                type='number'
                                name='weight'
                                id='weight'
                                width='100px'
                                min='0'
                                max='200'
                                placeholder='0'
                                onChangeHandler={handleFormData}
                            />
                            <label className='input-label'>kg</label>
                        </div>
                    </div>
                    <Input
                        labelText='Email*'
                        type='email'
                        placeholder='Enter your email here...'
                        name='email'
                        id='email'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyEmailError}
                    />
                    <Input
                        labelText='Password*'
                        type='password'
                        placeholder='Enter your password here...'
                        name='password'
                        id='password'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyPasswordError}
                    />
                    <Input
                        labelText='Confirm Password*'
                        type='password'
                        placeholder='Enter your password again...'
                        name='confirmPassword'
                        id='confirmPassword'
                        onChangeHandler={handleFormData}
                        emptyFieldError={emptyConfirmError}
                    />
                    {errorMessage ? <p className='error-message'>{errorMessage}</p> : <div className='no-error-message'> </div>}
                </div>
                <div className='sign-up-button'>
                    <Button
                        text='Sign Up'
                        onClickFunction={SignUpFunction}
                    />
                </div>
            </div>
        </div>
    )
}

export default RegisterPage