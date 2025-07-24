import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../../styles/Navbar.css';
import logo from '../../assets/logo.png';

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('email');
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <img src={logo} alt="Bank Logo" className="logo" />
        <span>TrustBank</span>
      </div>
      <ul className="navbar-links">
        <li><Link to="/dashboard">Dashboard</Link></li>
        <li><Link to="/transaction">Transactions</Link></li>
        <li><button className="logout-button" onClick={handleLogout}>Logout</button></li>
      </ul>
    </nav>
  );
}

export default Navbar;
