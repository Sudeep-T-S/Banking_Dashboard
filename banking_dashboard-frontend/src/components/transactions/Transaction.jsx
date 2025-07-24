import React from 'react';
import TransactionForm from './TransactionForm';
import TransactionHistory from './TransactionHistory';
import './Transaction.css'; 

function Transaction() {
  return (
    <div className="transaction-page" style={{ maxWidth: 900, margin: 'auto', padding: '1rem' }}>
      <TransactionForm />
      <hr style={{ margin: '2rem 0' }} />
      <TransactionHistory />
    </div>
  );
}

export default Transaction;
