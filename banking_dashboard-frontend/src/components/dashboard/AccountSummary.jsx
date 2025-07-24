import React from 'react';
import './AccountSummary.css';  

function AccountSummary({ name, email, accountNumber, balance }) {
  return (
    <div className="account-summary-card">
      <h2>Welcome, {name}</h2>
      <p><strong>Email:</strong> {email}</p>
      <p><strong>Account Number:</strong> {accountNumber}</p>
      <p className="balance"><strong>Balance:</strong> ₹{balance.toLocaleString('en-IN')}</p>
    </div>
  );
}

export default AccountSummary;
