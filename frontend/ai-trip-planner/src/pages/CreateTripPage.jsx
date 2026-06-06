import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ErrorMessage from '../components/common/ErrorMessage';
import TripForm from '../components/trip/TripForm';
import { useAuth } from '../hooks/useAuth';
import { tripService } from '../services/tripService';

const CreateTripPage = () => {
  const { currentUser } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const handleCreate = async (payload) => {
    try {
      setSubmitting(true);
      setError('');
      const trip = await tripService.createTrip({
        ...payload,
        userId: Number(currentUser?.id || payload.userId || 1),
      });
      navigate(`/trips/${trip.id}`);
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <main className="page narrow">
      <div className="page-header">
        <div>
          <p className="eyebrow">Create</p>
          <h1>Plan a new trip</h1>
        </div>
      </div>
      {error && <ErrorMessage message={error} />}
      <TripForm onSubmit={handleCreate} submitting={submitting} />
    </main>
  );
};

export default CreateTripPage;
