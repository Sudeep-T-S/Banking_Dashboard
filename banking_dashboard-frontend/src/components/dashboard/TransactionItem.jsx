import React from 'react';
import { format } from 'date-fns';
import './TransactionItem.css';  // Create this CSS file for styling

function TransactionItem({ transaction }) {
  const { timestamp, type, amount, description } = transaction;

  // Convert type to lowercase for styling class
  const typeClass = type.toLowerCase();

  return (
    <li className={`transaction-item ${typeClass}`}>
      <span className="timestamp">{format(new Date(timestamp), 'dd MMM yyyy, hh:mm a')}</span>
      <strong className="transaction-type">{type}</strong> of ₹{amount.toLocaleString('en-IN')} - {description}
    </li>
  );
}

export default TransactionItem;
