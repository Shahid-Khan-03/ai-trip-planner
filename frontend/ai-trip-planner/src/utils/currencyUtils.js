export const formatCurrency = (amount, currency = 'INR') => (
  new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency,
    maximumFractionDigits: 0,
  }).format(Number(amount || 0))
);

export const convertCurrency = (amount, rate = 1) => Number(amount || 0) * Number(rate || 1);
