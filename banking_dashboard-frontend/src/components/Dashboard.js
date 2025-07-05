import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TransactionForm from './TransactionForm';
import { format } from 'date-fns';
import './Dashboard.css'; 

function Dashboard() {
  const [dashboardData, setDashboardData] = useState(null);
  const [error, setError] = useState('');

  const fetchDashboard = async () => {
    const token = localStorage.getItem('jwt');
    if (!token) {
      setError("User not logged in.");
      return;
    }

    try {
      const response = await axios.get('http://localhost:8080/api/users/dashboard', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setDashboardData(response.data);
    } catch (err) {
      setError("Failed to load dashboard. " + (err.response?.data?.message || ''));
      console.error(err);
    }
  };

  useEffect(() => { fetchDashboard(); }, []);

  if (error) return <p className="error-text">{error}</p>;
  if (!dashboardData) return <p>Loading...</p>;

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h2>Welcome, {dashboardData.name}</h2>
        <p>Email: {dashboardData.email}</p>
        <p>Account Number: {dashboardData.accountNumber}</p>
        <p className="balance">Balance: ₹{dashboardData.balance}</p>
      </div>

      <div className="transactions-section">
        <h3>Recent Transactions:</h3>
        <ul className="transactions-list">
          {dashboardData.recentTransactions?.length > 0 ? (
            dashboardData.recentTransactions.map((txn, index) => (
              <li
                key={index}
                className={`transaction-item ${txn.type.toLowerCase()}`}
              >
                <span className="timestamp">
                  {format(new Date(txn.timestamp), 'dd MMM yyyy, hh:mm a')}
                </span>
                <strong>{txn.type}</strong> of ₹{txn.amount} - {txn.description}
              </li>
            ))
          ) : (
            <li>No recent transactions available.</li>
          )}
        </ul>
      </div>

      <hr />
      <TransactionForm fetchDashboard={fetchDashboard} />
    </div>
  );
}

export default Dashboard;
