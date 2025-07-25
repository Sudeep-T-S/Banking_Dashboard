import React, { useState } from 'react';
import TransactionForm from './TransactionForm';
import TransactionHistory from './TransactionHistory';
import './Transaction.css';

function Transaction() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleTransactionSuccess = () => {
    setRefreshKey(prev => prev + 1);
  };

  return (
    <div className="transaction-page" style={{ maxWidth: 900, margin: 'auto', padding: '1rem' }}>
      {/* Pass onSuccess callback to form */}
      <TransactionForm onSuccess={handleTransactionSuccess} />
      <hr style={{ margin: '2rem 0' }} />
      {/* Pass refreshKey to history component to refetch data */}
      <TransactionHistory refreshKey={refreshKey} />
    </div>
  );
}

export default Transaction;
