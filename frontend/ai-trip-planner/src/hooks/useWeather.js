import { useEffect, useState } from 'react';
import { weatherService } from '../services/weatherService';

export const useWeather = (trip) => {
  const [weather, setWeather] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!trip) return;

    const fetchWeather = async () => {
      try {
        setLoading(true);
        setError('');
        const data = await weatherService.getWeather(trip.destination, {
          startDate: trip.startDate,
          endDate: trip.endDate,
        });
        setWeather(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchWeather();
  }, [trip]);

  return { weather, loading, error };
};
