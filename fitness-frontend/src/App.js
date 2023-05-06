import './App.css';
import LoginPage from './Pages/LoginPage';
import RegisterPage from './Pages/RegisterPage';
import { Routes, Route } from "react-router-dom";
import { BrowserRouter as Router } from "react-router-dom";
import HomePage from './Pages/HomePage';
import ProtectedRoute from './Components/ProtectedRoute';
import AddPlanPage from './Pages/AddPlanPage';
import TrainerPlansPage from './Pages/TrainerPlansPage';
import AdminPage from './Pages/AdminPage';
import ChangePasswordPage from './Pages/ChangePasswordPage';
import FindPlansPage from './Pages/FindPlansPage';
import PlanPage from './Pages/PlanPage';
import MyPlansPage from './Pages/MyPlansPage';
import SendEmailPage from './Pages/SendEmailPage';
import ChatRoom from './Components/ChatRoom';
import { useEffect } from 'react';
import LogOutApi from './Api/LogOutApi';
function App() {

  useEffect(() => {
    let shouldLogout = true;
  
    const handleBeforeUnload = () => {
      if (shouldLogout) {
        handleLogout();
      }
    };
  
    window.addEventListener('beforeunload', handleBeforeUnload);
  
    return () => {
      shouldLogout = false;
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, []);

  const handleLogout = async () => {
    await LogOutApi((JSON.parse(sessionStorage.getItem("user"))).id);
  };

  return (
    <div>
      <Router>
        <Routes>
          <Route exact path='/' element={< LoginPage />}></Route>
          <Route exact path='/register' element={< RegisterPage />}></Route>
          <Route exact path='/chatroom' element={< ChatRoom />}></Route>
          <Route path='/home' element={<ProtectedRoute children={<HomePage />} />} />
          <Route path='/add-plan/:planIdParam' element={<ProtectedRoute children={<AddPlanPage/> } /> } />
          <Route path='/add-plan' element={<ProtectedRoute children={<AddPlanPage/> } /> } />
          <Route path='/trainer-plans' element={<ProtectedRoute children={<TrainerPlansPage/>} /> } />
          <Route path='/admin-page' element={<ProtectedRoute children={<AdminPage/>} />} />
          <Route path='/change-password' element={<ProtectedRoute children={<ChangePasswordPage/>} />} />
          <Route path='/find-plans' element={<ProtectedRoute children={<FindPlansPage/>} />} />
          <Route path='/plan/:planId/:followedPlan' element={<ProtectedRoute children={<PlanPage/>} />} />
          <Route path='/my-plans' element={<ProtectedRoute children={<MyPlansPage/>} />} />
          <Route path='/send-email' element={< SendEmailPage />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
