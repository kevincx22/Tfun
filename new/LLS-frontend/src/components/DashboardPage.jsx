import React, { useState } from "react";
import './DashboardPage.css';
import { Link } from "react-router-dom";
import Logo from '../assets/logo.png';
import user from '../assets/user.png';
import { IoMdSearch } from "react-icons/io";
import { AiOutlineDashboard } from "react-icons/ai";
import { LuBookOpenText } from "react-icons/lu";
import { MdOutlineCalendarMonth } from "react-icons/md";
import { HiOutlineInboxArrowDown } from "react-icons/hi2";
import { IoMdHelpCircleOutline } from "react-icons/io";
import { IoMdNotificationsOutline } from "react-icons/io";
import { RiProfileLine } from "react-icons/ri";
import { TbSettings } from "react-icons/tb";


// 定义假数据
const mockCourses = [
    { id: 1, title: 'ELEC5620(ND)', instructor: 'semester 2 2025', bgColor: '#EBF5FF' },
    { id: 2, title: 'Advanced JavaScript', instructor: 'Jane Smith', bgColor: '#E8F8F5' },
    { id: 3, title: 'UI/UX Design Principles', instructor: 'Emily Davis', bgColor: '#F4ECF7' },
    { id: 4, title: 'Node.js & Express', instructor: 'Michael Scott', bgColor: '#FEF9E7' },
    { id: 5, title: 'MongoDB Basics', instructor: 'Angela Yu', bgColor: '#FDEDEC' },
    { id: 6, title: 'Fullstack Development', instructor: 'Robert Langdon', bgColor: '#E9F7EF' },
];

function DashboardPage() {
    const [isAccountPanelOpen, setAccountPanelOpen] = useState(false);

    const today = new Date();
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const formattedDate = today.toLocaleDateString('en-US', options);

    return (
        // Area 1: Left sidebar
        <div className="dashboard-layout">
            <aside className="sidebar">
                <div className="Logo" style={{display: 'flex', alignItems: 'center', gap: '15px', marginBottom: '40px', width: '100%',}}>
                     <img src={Logo} alt="Side Logo" className="sidelogo" />
                     <span className="logo-text">ILS</span>
                </div>
                <div className="account-section">
                    <button className="account-button" onClick={() => setAccountPanelOpen(!isAccountPanelOpen)}>
                        <img src={user} alt="User Avatar" className="sidebar-avatar" />
                        <span>Account</span>
                    </button>
                </div>
                <nav className="nav-menu">
                    <Link to="/dashboard" className="nav-item active">
                        <AiOutlineDashboard className="nav-icon"/>Dashboard</Link>
                    <Link to="/courses" className="nav-item">
                        <LuBookOpenText className="nav-icon"/>Courses</Link>
                    <Link to="/calendar" className="nav-item">
                        <MdOutlineCalendarMonth className="nav-icon"/>Calendar</Link>
                    <Link to="/inbox" className="nav-item">
                        <HiOutlineInboxArrowDown className="nav-icon"/>Inbox</Link>
                    <Link to="/help" className="nav-item">
                        <IoMdHelpCircleOutline className="nav-icon"/>Help</Link>
                </nav>
                {isAccountPanelOpen && (
                    <div className="account-panel">
                        <button
                            className="close-panel-button"
                            onClick={() => setAccountPanelOpen(false)}
                        >
                            &times; {/* 这是一个 "x" 符号 */}
                        </button>
                        <div className="panel-profile">
                            <img src={user} alt="User Avatar" className="panel-avatar" />
                            <h3>Your Name</h3>
                            <button className="logout-button">Logout</button>
                        </div>
                        <nav className="panel-nav">
                            <Link to="/notifications" className="panel-link">
                                <IoMdNotificationsOutline className="nav-icon"/>Notifications</Link>
                            <Link to="/profile" className="panel-link">
                                <RiProfileLine className="nav-icon"/>Profile</Link>
                            <Link to="/settings" className="panel-link">
                                <TbSettings className="nav-icon"/>Settings</Link>
                        </nav>

                    </div>
                )}
            </aside>

            {/* 区域二：中间主内容区 (只包含 Header 和课程) */}
            <main className="main-content">
                <header className="main-header">
                    <div className="search-bar">
                        <IoMdSearch className='search-icon'/>
                        <input type="text" placeholder="Search..." />
                    </div>
                </header>
                <section className="dashboard-section">
                    <div className="welcome-header">
                        <h2>Dashboard</h2>
                        <span className="current-date">{formattedDate}</span>
                    </div>
                    <div className="courses-grid">
                        {mockCourses.map((course) => (
                            <div key={course.id} className="course-card card" >
                                <div className="card-content">
                                    <h3>{course.title}</h3>
                                    <p>{course.instructor}</p>
                                </div>
                                <div className="card-footer">
                                    <Link to={`/course/${course.id}`} className="go-to-course">Go to course</Link>
                                </div>
                            </div>
                            ))}
                        </div>
                </section>
            </main>

            {/* 区域三：右侧 To-Do List 栏 */}
            <aside className="right-sidebar">
                <div className="todo-list-card card">
                    <h3>To-Do List</h3>
                    <ul>
                        <li><label><input type="checkbox" /> Complete Stage One Report <span>Due: Oct 12</span></label></li>
                        <li><label><input type="checkbox" /> Revise JavaScript concepts <span>Due: July 30</span></label></li>
                        <li><label><input type="checkbox" /> Submit Project Proposal <span>Due: Sept 15</span></label></li>
                        <li><label><input type="checkbox" /> Prepare for Exam <span>Due: Oct 5</span></label></li>
                    </ul>
                </div>
            </aside>
        </div>
    );
}

export default DashboardPage;