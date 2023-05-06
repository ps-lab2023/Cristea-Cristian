import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import LeftSidePhoto from "../Components/LeftSidePhoto";
import Input from "../Components/Input";
import Button from "../Components/Button";
import '../Styles/SendEmailPage.css';
import SendEmailApi from "../Api/SendEmailApi";
import VerifyCodeApi from "../Api/VerifyCodeApi";

const SendEmailPage = () => {
    const [isError, setError] = useState(false)
    const [formData, setFormData] = useState({
        email: '',
        verificationCode: ''
    })
    const [errorMessage, setErrorMessage] = useState("");
    const [verifyCode, setVerifyCode] = useState(false);
    const navigate = useNavigate();

    const handleFormData = (event) => {
        const { name, value } = event.target
        setFormData(prevFormData => {
            return {
                ...prevFormData,
                [name]: value
            }
        })
    }

    const validateEmail = () => {
        setError(false);
        if(!formData.email) {
            setError(true);
            setErrorMessage('Email is mandatory!')
            return false;
        }
        return true;
    }

    const sendEmail = async () => {
        if(validateEmail()) {
            setError(false);
            setErrorMessage('');
            setVerifyCode(true);
            const response = await SendEmailApi(formData.email)
            if(response?.isError) {
                setError(true);
                setErrorMessage('Please provide a valid email!');
                setVerifyCode(false);
            }
        }
    }

    const validateCode = () => {
        setError(false);
        if(!formData.verificationCode) {
            setError(true);
            setErrorMessage('Enter the verification code!')
            return false;
        }
        return true;
    }

    const verifyCodeFunction = async () => {
        if(validateCode()) {
            setError(false);
            setErrorMessage('');
            const response = await VerifyCodeApi(formData.email, formData.verificationCode);
            if(response?.isError) {
                setError(true);
                setErrorMessage('The code is not valid!');
            } else {
                sessionStorage.setItem("user", JSON.stringify(
                {
                    id: response.id,
                    username: response.username,
                    role: response.role
                }))
                navigate('/change-password')
            }
        }
    }

    const goBack = () => {
        setVerifyCode(false);
        setErrorMessage('');
        setError(false)
    }

    return(
        <div className='login-main-container'>
        <LeftSidePhoto />
        <div className='right-side-register'>
            <div className='password-trainer-inputs-container'>
                {verifyCode === false ?
                    <div>
                        <Input
                            labelText='Enter your email*'
                            type='email'
                            placeholder='Enter your email here...'
                            name='email'
                            id='email'
                            value={formData.email}
                            onChangeHandler={handleFormData}
                            emptyFieldError={isError}
                        />
                        {errorMessage ? <p className='error-message'>{errorMessage}</p> : <div className='no-error-message'> </div>}
                    </div>
                    :
                    <div>
                        <Input
                            labelText='You received a code on email'
                            type='text'
                            placeholder='Enter the verification code here...'
                            name='verificationCode'
                            id='verificationCode'
                            value={formData.verificationCode}
                            onChangeHandler={handleFormData}
                            emptyFieldError={isError}
                        />
                         {errorMessage ? <p className='error-message'>{errorMessage}</p> : <div className='no-error-message'> </div>}
                    </div>
                }
            </div>
            {verifyCode === false ?
                <div id='send-email-btns-container'>
                        <Button
                            text='Cancel'
                            onClickFunction={() => navigate('/')}
                        />
                        <Button
                            text='Send email'
                            onClickFunction={sendEmail}
                        />
                </div>
                :
                <div id='send-email-btns-container'>
                        <Button
                            text='Back'
                            onClickFunction={goBack}
                        />
                        <Button
                            text='Verify code'
                            onClickFunction={verifyCodeFunction}
                        />
                </div>
            }
        </div>
    </div>
    )
}
export default SendEmailPage