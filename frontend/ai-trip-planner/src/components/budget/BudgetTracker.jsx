import { formatCurrency } from '../../utils/currencyUtils';

const BudgetTracker = ({ totalBudget = 0, totalSpent = 0 }) => {
  const percent = totalBudget ? Math.min((totalSpent / totalBudget) * 100, 100) : 0;

  return (
    <section className="card">
      <div className="split">
        <div>
          <p className="eyebrow">Trip Budget</p>
          <h3>{formatCurrency(totalBudget)}</h3>
        </div>
        <div className="text-right">
          <p className="eyebrow">Spent</p>
          <h3>{formatCurrency(totalSpent)}</h3>
        </div>
      </div>
      <div className="progress">
        <span style={{ width: `${percent}%` }} />
      </div>
    </section>
  );
};

export default BudgetTracker;
