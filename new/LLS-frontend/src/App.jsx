import './App.css';
import { Routes, Route, Navigate } from 'react-router-dom';
import bookImage from './assets/book-illustration.jpg';

import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import DashboardPage from "./components/DashboardPage";
function App() {
    return (
        <Routes>
            {/* 规则一：当路径是 /login 时，渲染包含两栏布局的 LoginPage */}
            <Route
                path="/login"
                element={
                    <div className="auth-layout">
                        <div className="form-container">
                            <LoginPage />
                        </div>
                        <div className="decorative-panel">
                            <img src={bookImage} alt="Book Illustration" />
                        </div>
                    </div>
                }
            />

            {/* 规则二：当路径是 /register 时，渲染包含两栏布局的 RegisterPage */}
            <Route
                path="/register"
                element={
                    <div className="auth-layout">
                        <div className="form-container">
                            <RegisterPage />
                        </div>
                        <div className="decorative-panel">
                            <img src={bookImage} alt="Book Illustration" />
                        </div>
                    </div>
                }
            />

            {/* 规则三：当路径是 /dashboard 时，只渲染 DashboardPage，不使用两栏布局 */}
            <Route
                path="/dashboard"
                element={<DashboardPage />}
            />

            {/* 规则四：默认跳转，当用户访问根路径时，自动跳转到登录页 */}
            <Route
                path="/"
                element={<Navigate to="/login" />}
            />
        </Routes>
    );
}

export default App;