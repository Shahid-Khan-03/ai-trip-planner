import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import AiItineraryPreview from '../components/ai/AiItineraryPreview';
import ErrorMessage from '../components/common/ErrorMessage';
import LoadingSpinner from '../components/common/LoadingSpinner';
import ItineraryView from '../components/trip/ItineraryView';
import WeatherWidget from '../components/trip/WeatherWidget';
import { useWeather } from '../hooks/useWeather';
import api from '../services/api';
import { aiService } from '../services/aiService';
import { tripService } from '../services/tripService';
import { formatCurrency } from '../utils/currencyUtils';
import { formatDate } from '../utils/dateUtils';

const TripDetailPage = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState(null);
  const [days, setDays] = useState([]);
  const [activities, setActivities] = useState([]);
  const [aiResult, setAiResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [aiLoading, setAiLoading] = useState(false);
  const [error, setError] = useState('');
  const { weather } = useWeather(trip);

  useEffect(() => {
    const fetchDetails = async () => {
      try {
        setLoading(true);
        setError('');
        const [tripData, daysResponse, activitiesResponse] = await Promise.all([
          tripService.getTripById(id),
          api.get('/days', { params: { tripId: id } }),
          api.get('/activities', { params: { tripId: id } }),
        ]);
        setTrip(tripData);
        setDays(daysResponse.data || []);
        setActivities(activitiesResponse.data || []);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchDetails();
  }, [id]);

  const generateAiPlan = async () => {
    try {
      setAiLoading(true);
      setError('');
      const result = await aiService.generateItinerary({
        tripId: Number(id),
        preferences: trip?.interest || '',
        specialRequests: 'Create a practical day wise itinerary',
      });
      setAiResult(result);
    } catch (err) {
      setError(err.message);
    } finally {
      setAiLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;
  if (!trip) return <ErrorMessage message="Trip not found" />;

  return (
    <main className="page">
      <div className="page-header">
        <div>
          <p className="eyebrow">{formatDate(trip.startDate)} - {formatDate(trip.endDate)}</p>
          <h1>{trip.destination}</h1>
          <p className="muted">{trip.interest} | {formatCurrency(trip.budget)}</p>
        </div>
        <div className="hero-actions">
          <button
            className="button primary"
            type="button"
            onClick={generateAiPlan}
            disabled={aiLoading}
          >
            {aiLoading ? 'Generating...' : 'Generate AI Itinerary'}
          </button>
          <Link className="button ghost" to={`/trips/${id}/budget`}>Budget</Link>
        </div>
      </div>

      {/*  Right sidebar — Weather only */}
      <div className="layout-two">
        <section>
          <h2>Itinerary</h2>
          <ItineraryView days={days} activities={activities} />
        </section>
        <aside>
          <WeatherWidget weather={weather} />
        </aside>
      </div>

      {/*  AI Preview — full width below, only shown when result exists */}
      {aiResult && (
        <div style={{ marginTop: '32px' }}>
          <AiItineraryPreview result={aiResult} />
        </div>
      )}
    </main>
  );
};

export default TripDetailPage;