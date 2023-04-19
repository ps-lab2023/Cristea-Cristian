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
import TrainerChangePasswordPage from './Pages/TrainerChangePasswordPage';
function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route exact path='/' element={< LoginPage />}></Route>
          <Route exact path='/register' element={< RegisterPage />}></Route>
          <Route path='/home' element={<ProtectedRoute children={<HomePage />} />} />
          <Route path='/plan/:planIdParam' element={<ProtectedRoute children={<AddPlanPage/> } /> } />
          <Route path='/plan' element={<ProtectedRoute children={<AddPlanPage/> } /> } />
          <Route path='/trainer-plans' element={<ProtectedRoute children={<TrainerPlansPage/>} /> } />
          <Route path='/admin-page' element={<ProtectedRoute children={<AdminPage/>} />} />
          <Route path='/change-password' element={<ProtectedRoute children={<TrainerChangePasswordPage/>} />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
