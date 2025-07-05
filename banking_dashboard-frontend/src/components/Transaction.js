import React from 'react';
import TransactionForm from './TransactionForm';
import TransactionHistory from './TransactionHistory'; // future component

function Transaction() {
  return (
    <div style={{ maxWidth: 900, margin: 'auto' }}>
      <TransactionForm />
      <hr />
      <TransactionHistory />
    </div>
  );
}

export default Transaction;
