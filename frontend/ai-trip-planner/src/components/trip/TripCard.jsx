import { useState } from 'react';
import { Link } from 'react-router-dom';
import { formatCurrency } from '../../utils/currencyUtils';
import { formatDate, getDuration } from '../../utils/dateUtils';

const TripCard = ({ trip, onDelete }) => {
  const [deleting, setDeleting] = useState(false);

  const handleDelete = async () => {
    //  Confirmation so accidental clicks don't delete
    if (!window.confirm(`Delete trip to ${trip.destination}? This cannot be undone.`)) return;

    try {
      setDeleting(true);
      await onDelete(trip.id);
    } catch (err) {
      alert('Failed to delete trip: ' + err.message);
    } finally {
      setDeleting(false);
    }
  };

  return (
    <article className="card trip-card">
      <div>
        <p className="eyebrow">{getDuration(trip.startDate, trip.endDate)} days</p>
        <h3>{trip.destination}</h3>
        <p>{formatDate(trip.startDate)} — {formatDate(trip.endDate)}</p>
      </div>
      <p className="muted">Interest: {trip.interest || 'General travel'}</p>
      <p className="budget-value">{formatCurrency(trip.budget)}</p>
      <div className="card-actions">
        <Link className="button primary" to={`/trips/${trip.id}`}>View</Link>
        <Link className="button ghost" to={`/trips/${trip.id}/budget`}>Budget</Link>
        {onDelete && (
          <button
            className="button danger"
            type="button"
            onClick={handleDelete}
            disabled={deleting}
          >
            {deleting ? 'Deleting...' : 'Delete'}
          </button>
        )}
      </div>
    </article>
  );
};

export default TripCard;