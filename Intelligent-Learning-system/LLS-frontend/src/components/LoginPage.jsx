import React, {useState} from 'react';
import './LoginPage.css';
import { Link, useNavigate } from 'react-router-dom';

function LoginPage() {
    // "state" variables for storing user input
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    // "handleSubmit" function for handling form submission
    const handleSubmit = (event) => {
        event.preventDefault();
        const isLoginSuccessful = username === 'test' && password === '123';
        if (isLoginSuccessful) {
            alert(`登录成功！欢迎 ${username}`);
            // 3. 登录成功后，使用 navigate 函数跳转到 Dashboard 页面
            navigate('/dashboard');
        } else {
            alert('用户名或密码错误！');
        }
    };


    return (
        <div className="login-container">
            <h2>LOGIN</h2>
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        placeholder="Please enter your username"
                        value={username}  // 由"state"变量控制
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Please enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit" className="login-button">Login</button>
                <div className="form-footer">
                    <Link to="/forgot-password">Forgot password?</Link>
                    <span>No account yet? <Link to="/register">Register now</Link></span>
                </div>
            </form>
        </div>
    );
}

export default LoginPage;