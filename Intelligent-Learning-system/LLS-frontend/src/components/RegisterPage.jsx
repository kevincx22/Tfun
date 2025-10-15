import React, {useState} from 'react';
import './RegisterPage.css';
import {Link} from "react-router-dom"; // 我们稍后会创建这个 CSS 文件

function RegisterPage() {
    // "state" variables for storing user input
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    // "handleSubmit" function for handling form submission
    const handleRegisterSubmit = (event) => {
        event.preventDefault();
        alert(`Trying Login, username: ${username}, password: ${password}, confirmPassword: ${confirmPassword}`);
        console.log('Submitted email:', email);
        console.log('Submitted username:', username);
        console.log('Submitted password:', password);
        console.log('Submitted confirmPassword:', confirmPassword);
    };


    return (
        <div className="login-container">
            <h2>REGISTER</h2>
            <form className="login-form" onSubmit={handleRegisterSubmit}>
                <div className="input-group">
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Please enter your email"
                        value={email}  // 由"state"变量控制
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
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
                <div className="input-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        placeholder="Please confirm your password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </div>
                <button type="submit" className="register-button">Register</button>
                <div className="form-footer register-footer">
                    <span>Already have an account? <Link to="/login">Login now</Link></span>
                </div>
            </form>
        </div>
    );
}

export default RegisterPage;