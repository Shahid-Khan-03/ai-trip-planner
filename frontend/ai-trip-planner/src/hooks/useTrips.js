import { useCallback, useEffect, useState } from 'react';
import { tripService } from '../services/tripService';

export const useTrips = (userId) => {
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchTrips = useCallback(async () => {
    try {
      setLoading(true);
      setError('');
      const data = await tripService.getTrips(userId);
      setTrips(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [userId]);

  useEffect(() => {
    let ignore = false;

    const loadTrips = async () => {
      try {
        const data = await tripService.getTrips(userId);
        if (!ignore) {
          setTrips(data);
          setError('');
        }
      } catch (err) {
        if (!ignore) setError(err.message);
      } finally {
        if (!ignore) setLoading(false);
      }
    };

    loadTrips();

    return () => {
      ignore = true;
    };
  }, [userId]);

  return { trips, setTrips, loading, error, refetch: fetchTrips };
};
