import { useState } from 'react';

const ExpenseForm = ({ onSubmit, submitting = false }) => {
  const [form, setForm] = useState({ category: '', amount: '' });

  const updateField = (event) => {
    setForm((current) => ({ ...current, [event.target.name]: event.target.value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({ category: form.category, amount: Number(form.amount) });
    setForm({ category: '', amount: '' });
  };

  return (
    <form className="card form-section" onSubmit={handleSubmit}>
      <h3>Add Expense</h3>
      <label>Category<input name="category" value={form.category} onChange={updateField} placeholder="Hotel, food, taxi" required /></label>
      <label>Amount<input type="number" min="0" name="amount" value={form.amount} onChange={updateField} required /></label>
      <button className="button primary" type="submit" disabled={submitting}>
        {submitting ? 'Adding...' : 'Add Expense'}
      </button>
    </form>
  );
};

export default ExpenseForm;
