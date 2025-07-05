import React, { useState } from 'react';
import axios from 'axios';
import './TransactionForm.css'; 

function TransactionForm({ fetchDashboard }) {
  const [formData, setFormData] = useState({
    amount: '',
    description: '',
    type: 'TRANSFER',
    recipientEmail: '',
  });

  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
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

    const payload = {
      email: email,
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
      await axios.post(`http://localhost:8080${endpoint}`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setMessage('Transaction successful!');
      setFormData({
        amount: '',
        description: '',
        type: 'TRANSFER',
        recipientEmail: '',
      });

      if (fetchDashboard) {
        await fetchDashboard(); 
      }

    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || 'Transaction failed. Try again.');
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: 'auto', padding: 20 }}>
      <h3>Make a Transaction</h3>
      <form onSubmit={handleSubmit}>
        <select name="type" value={formData.type} onChange={handleChange} required>
          <option value="DEPOSIT">Deposit</option>
          <option value="WITHDRAW">Withdraw</option>
          <option value="TRANSFER">Transfer</option>
        </select>
        <br /><br />

        {formData.type === 'TRANSFER' && (
          <>
            <input
              type="email"
              name="recipientEmail"
              placeholder="Recipient Email"
              value={formData.recipientEmail}
              onChange={handleChange}
              required
            />
            <br /><br />
          </>
        )}

        <input
          type="number"
          name="amount"
          placeholder="Amount"
          value={formData.amount}
          onChange={handleChange}
          required
        />
        <br /><br />

        <input
          type="text"
          name="description"
          placeholder="Description"
          value={formData.description}
          onChange={handleChange}
          required
        />
        <br /><br />

        <button type="submit">Submit</button>
      </form>

      {message && <p style={{ color: 'green' }}>{message}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default TransactionForm;
