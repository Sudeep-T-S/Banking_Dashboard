import React from 'react';
import TransactionItem from './TransactionItem';
import './TransactionList.css'; 

function TransactionList({ transactions }) {
  if (!transactions || transactions.length === 0) {
    return <p>No transactions available.</p>;
  }

  return (
    <ul className="transaction-list">
      {transactions.map((txn) => (
        <TransactionItem key={txn.id || txn.timestamp} transaction={txn} />
      ))}
    </ul>
  );
}

export default TransactionList;
