import React, { useEffect, useState } from 'react';
import api from '../../axiosConfig';
import AccountSummary from './AccountSummary';
import TransactionList from './TransactionList';
import TransactionForm from '../transactions/TransactionForm';
import './Dashboard.css';

function Dashboard() {
  const [dashboardData, setDashboardData] = useState(null);
  const [error, setError] = useState('');

  const fetchDashboard = async () => {
    const token = localStorage.getItem('jwt');
    if (!token) {
      setError('User not logged in.');
      return;
    }

    try {
      const response = await api.get('/api/users/dashboard', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setDashboardData(response.data);
    } catch (err) {
      setError('Failed to load dashboard. ' + (err.response?.data?.message || ''));
      console.error(err);
    }
  };

  useEffect(() => {
    fetchDashboard();
  }, []);

  if (error) return <p className="error-text">{error}</p>;
  if (!dashboardData) return <p>Loading...</p>;

  return (
    <div className="dashboard">
      <AccountSummary
        name={dashboardData.name}
        email={dashboardData.email}
        accountNumber={dashboardData.accountNumber}
        balance={dashboardData.balance}
      />

      <section className="transactions-section">
        <h3>Recent Transactions:</h3>
        <TransactionList transactions={dashboardData.recentTransactions} />
      </section>

      <hr />

      <TransactionForm fetchDashboard={fetchDashboard} />
    </div>
  );
}

export default Dashboard;
