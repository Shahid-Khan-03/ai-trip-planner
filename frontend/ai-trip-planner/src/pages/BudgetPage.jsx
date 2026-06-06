import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import BudgetChart from '../components/budget/BudgetChart';
import BudgetTracker from '../components/budget/BudgetTracker';
import ExpenseForm from '../components/budget/ExpenseForm';
import ErrorMessage from '../components/common/ErrorMessage';
import LoadingSpinner from '../components/common/LoadingSpinner';
import { useBudget } from '../hooks/useBudget';
import { budgetService } from '../services/budgetService';
import { tripService } from '../services/tripService';

const BudgetPage = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [pageError, setPageError] = useState('');
  const { summary, chartData, loading, error, refetch } = useBudget(id);

  useEffect(() => {
    tripService.getTripById(id).then(setTrip).catch((err) => setPageError(err.message));
  }, [id]);

  const addExpense = async (expense) => {
    try {
      setSubmitting(true);
      await budgetService.addExpense({ ...expense, tripId: Number(id) });
      refetch();
    } catch (err) {
      setPageError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (error || pageError) return <ErrorMessage message={error || pageError} />;

  return (
    <main className="page">
      <div className="page-header">
        <div>
          <p className="eyebrow">Budget</p>
          <h1>{trip?.destination || 'Trip'} expenses</h1>
        </div>
      </div>

      <div className="layout-two">
        <section className="stack">
          <BudgetTracker totalBudget={trip?.budget || 0} totalSpent={summary.totalSpent || 0} />
          <BudgetChart data={chartData} />
        </section>
        <aside>
          <ExpenseForm onSubmit={addExpense} submitting={submitting} />
        </aside>
      </div>
    </main>
  );
};

export default BudgetPage;
