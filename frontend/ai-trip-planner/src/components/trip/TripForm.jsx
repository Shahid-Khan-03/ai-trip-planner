import { useState } from 'react';
import AiGenerateButton from '../ai/AiGenerateButton';

const initialForm = {
  destination: '',
  startDate: '',
  endDate: '',
  budget: '',
  interest: '',
  userId: '1',
};

const TripForm = ({ onSubmit, submitting = false }) => {
  const [step, setStep] = useState(1);
  const [form, setForm] = useState(initialForm);

  const updateField = (event) => {
    setForm((current) => ({ ...current, [event.target.name]: event.target.value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      ...form,
      budget: Number(form.budget),
      userId: Number(form.userId || 1),
    });
  };

  return (
    <form className="form-panel" onSubmit={handleSubmit}>
      <div className="steps">
        {[1, 2, 3].map((item) => (
          <button className={step === item ? 'step active' : 'step'} key={item} type="button" onClick={() => setStep(item)}>
            {item}
          </button>
        ))}
      </div>

      {step === 1 && (
        <section className="form-section">
          <label>Destination<input name="destination" value={form.destination} onChange={updateField} required /></label>
          <label>Interest<input name="interest" value={form.interest} onChange={updateField} placeholder="Food, beaches, history" /></label>
        </section>
      )}

      {step === 2 && (
        <section className="form-section two-column">
          <label>Start Date<input type="date" name="startDate" value={form.startDate} onChange={updateField} required /></label>
          <label>End Date<input type="date" name="endDate" value={form.endDate} onChange={updateField} required /></label>
        </section>
      )}

      {step === 3 && (
        <section className="form-section two-column">
          <label>Budget<input type="number" min="0" name="budget" value={form.budget} onChange={updateField} required /></label>
          <label>User ID<input type="number" min="1" name="userId" value={form.userId} onChange={updateField} required /></label>
        </section>
      )}

      <div className="form-actions">
        {step > 1 && <button className="button ghost" type="button" onClick={() => setStep(step - 1)}>Back</button>}
        {step < 3 && <button className="button primary" type="button" onClick={() => setStep(step + 1)}>Next</button>}
        {step === 3 && <button className="button primary" type="submit" disabled={submitting}>{submitting ? 'Saving...' : 'Save Trip'}</button>}
        {step === 3 && <AiGenerateButton disabled={!form.destination} />}
      </div>
    </form>
  );
};

export default TripForm;
