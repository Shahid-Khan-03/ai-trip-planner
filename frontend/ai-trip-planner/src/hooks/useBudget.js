import { useCallback, useEffect, useMemo, useState } from 'react';
import { budgetService } from '../services/budgetService';

export const useBudget = (tripId) => {
  const [summary, setSummary] = useState({ totalSpent: 0, expenses: {} });
  const [loading, setLoading] = useState(Boolean(tripId));
  const [error, setError] = useState('');

  const fetchBudget = useCallback(async () => {
  if (!tripId) return;
  try {
    setLoading(true);
    setError('');
    const data = await budgetService.getSummary(tripId);

    //  Handle raw expense list from backend
    if (Array.isArray(data)) {
      const totalSpent = data.reduce((sum, e) => sum + (e.amount || 0), 0);
      const expenses = data.reduce((acc, e) => {
        acc[e.category] = (acc[e.category] || 0) + e.amount;
        return acc;
      }, {});
      setSummary({ totalSpent, expenses });
    } else {
      setSummary(data);
    }
  } catch (err) {
    setError(err.message);
  } finally {
    setLoading(false);
  }
}, [tripId]);

  useEffect(() => {
  if (!tripId) return undefined;
  let ignore = false;

  const loadBudget = async () => {
    try {
      const data = await budgetService.getSummary(tripId);
      if (!ignore) {
        //  Same normalization
        if (Array.isArray(data)) {
          const totalSpent = data.reduce((sum, e) => sum + (e.amount || 0), 0);
          const expenses = data.reduce((acc, e) => {
            acc[e.category] = (acc[e.category] || 0) + e.amount;
            return acc;
          }, {});
          setSummary({ totalSpent, expenses });
        } else {
          setSummary(data);
        }
        setError('');
      }
    } catch (err) {
      if (!ignore) setError(err.message);
    } finally {
      if (!ignore) setLoading(false);
    }
  };

  loadBudget();
  return () => { ignore = true; };
}, [tripId]);

   

  const chartData = useMemo(() => {
    const expenses = summary.expenses || {};
    return Object.entries(expenses).map(([category, amount]) => ({ category, amount }));
  }, [summary.expenses]);

  return { summary, chartData, loading, error, refetch: fetchBudget };
};
