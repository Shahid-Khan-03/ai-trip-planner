import ErrorMessage from '../components/common/ErrorMessage';
import LoadingSpinner from '../components/common/LoadingSpinner';
import TripCard from '../components/trip/TripCard';
import { useTrips } from '../hooks/useTrips';
import { tripService } from '../services/tripService';

const DashboardPage = () => {
  const { trips, loading, error, refetch } = useTrips();

  const handleDelete = async (id) => {
  try {
    await tripService.deleteTrip(id);
    refetch();
  } catch (err) {
    alert('Could not delete trip: ' + err.message);
  }
};
  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;

  return (
    <main className="page">
      <div className="page-header">
        <div>
          <p className="eyebrow">Dashboard</p>
          <h1>My Trips</h1>
        </div>
      </div>
      <div className="grid">
        {trips.map((trip) => (
          <TripCard key={trip.id} trip={trip} onDelete={handleDelete} />
        ))}
      </div>
      {!trips.length && <p className="muted">No trips yet. Create your first travel plan.</p>}
    </main>
  );
};

export default DashboardPage;
