import React, { useEffect, useState } from 'react';
import api from '../../axiosConfig'; 
import './TransactionHistory.css';

function TransactionHistory({ refreshKey }) {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchHistory = async () => {
    setLoading(true);
    setError('');
    try {
      const token = localStorage.getItem('jwt');
      const email = localStorage.getItem('email');
      if (!token || !email) throw new Error('User not authenticated');

      const response = await api.get(`/api/transactions/history?email=${email}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setHistory(response.data); 
    } catch (err) {
      setError('Failed to load transaction history.');
      console.error(err);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchHistory();
  }, [refreshKey]); 

  return (
    <div className="transaction-history">
      <h3>Transaction History</h3>
      {loading && <p>Loading...</p>}
      {error && <p className="error-text">{error}</p>}
      {!loading && !error && (
        <>
          {history.length === 0 ? (
            <p>No transactions found.</p>
          ) : (
            <ul>
              {history.map((txn, index) => (
                <li key={txn.id || index}>
                  <span>{new Date(txn.timestamp).toLocaleString()}</span> —&nbsp;
                  <strong>{txn.type}</strong> ₹{txn.amount} —&nbsp;
                  {txn.description}
                </li>
              ))}
            </ul>
          )}
        </>
      )}
    </div>
  );
}

export default TransactionHistory;
