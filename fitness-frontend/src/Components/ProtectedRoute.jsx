import React from 'react'
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
    if (!localStorage.getItem("user")) {
        return <Navigate to="/" />;
    }
    return children;
}

export default ProtectedRoute