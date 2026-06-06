import api from './api';

export const tripService = {
  async getTrips(userId) {
    const response = await api.get('/trips', {
      params: userId ? { userId } : undefined,
    });
    return response.data || [];
  },

  async createTrip(payload) {
    const response = await api.post('/trips', payload);
    return response.data;
  },

  async deleteTrip(id) {
    const response = await api.delete(`/trips/${id}`);
    return response.data;
  },

  async getTripById(id) {
    const trips = await this.getTrips();
    return trips.find((trip) => String(trip.id) === String(id));
  },
};
