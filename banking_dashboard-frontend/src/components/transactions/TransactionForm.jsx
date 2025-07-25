import React, { useState } from 'react';
import api from '../../axiosConfig';
import './TransactionForm.css';

function TransactionForm({ fetchDashboard, onSuccess }) {
  const [formData, setFormData] = useState({
    type: 'TRANSFER',
    recipientEmail: '',
    amount: '',
    description: '',
  });

  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  setMessage('');
  setError('');

  const token = localStorage.getItem('jwt');
  const email = localStorage.getItem('email');

  if (!token || !email) {
    setError('User not logged in.');
    return;
  }

  if (formData.type === 'TRANSFER' && !formData.recipientEmail) {
    setError('Recipient email is required for transfer transactions.');
    return;
  }

  if (!formData.amount || parseFloat(formData.amount) <= 0) {
    setError('Please enter a valid amount greater than zero.');
    return;
  }

  const payload = {
    email,
    type: formData.type,
    description: formData.description,
    recipientEmail: formData.type === 'TRANSFER' ? formData.recipientEmail : null,
    amount: parseFloat(formData.amount),
  };

  let endpoint = '/api/transactions';
  if (formData.type === 'DEPOSIT') {
    endpoint += '/deposit';
  } else if (formData.type === 'WITHDRAW') {
    endpoint += '/withdraw';
  } else if (formData.type === 'TRANSFER') {
    endpoint += '/transfer';
  }

  try {
    await api.post(endpoint, payload, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    setMessage('Transaction successful!');
    setFormData({
      type: 'TRANSFER',
      recipientEmail: '',
      amount: '',
      description: '',
    });

    if (fetchDashboard) {
      await fetchDashboard();
    }

    if (onSuccess) {
      onSuccess();
    }
  } catch (err) {
    setError(err.response?.data?.message || 'Transaction failed. Try again.');
  }
};

  return (
    <div className="transaction-form-container">
      <h3>Make a Transaction</h3>
      <form onSubmit={handleSubmit} className="transaction-form" noValidate>
        <label>
          Transaction Type:
          <select name="type" value={formData.type} onChange={handleChange}>
            <option value="DEPOSIT">Deposit</option>
            <option value="WITHDRAW">Withdraw</option>
            <option value="TRANSFER">Transfer</option>
          </select>
        </label>

        {formData.type === 'TRANSFER' && (
          <label>
            Recipient Email:
            <input
              type="email"
              name="recipientEmail"
              placeholder="Recipient Email"
              value={formData.recipientEmail}
              onChange={handleChange}
              required
            />
          </label>
        )}

        <label>
          Amount:
          <input
            type="number"
            name="amount"
            placeholder="Amount"
            value={formData.amount}
            onChange={handleChange}
            min="0.01"
            step="0.01"
            required
          />
        </label>

        <label>
          Description:
          <input
            type="text"
            name="description"
            placeholder="Description"
            value={formData.description}
            onChange={handleChange}
            required
          />
        </label>

        <button type="submit">Submit</button>
      </form>

      {message && <p className="success-text" role="alert">{message}</p>}
      {error && <p className="error-text" role="alert">{error}</p>}
    </div>
  );
}

export default TransactionForm;
