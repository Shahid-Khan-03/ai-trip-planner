import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

const BudgetChart = ({ data = [] }) => (
  <section className="card chart-card">
    <h3>Expense Breakdown</h3>
    {data.length ? (
      <ResponsiveContainer width="100%" height={260}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="category" />
          <YAxis />
                <Tooltip
        formatter={(value) => {
          const num = typeof value === 'object' ? value?.amount : value;
          return `₹${Number(num || 0).toLocaleString()}`;
        }}
      />
          <Bar dataKey="amount" fill="#2563eb" radius={[6, 6, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    ) : (
      <p className="muted">No expenses added yet.</p>
    )}
  </section>
);

export default BudgetChart;
