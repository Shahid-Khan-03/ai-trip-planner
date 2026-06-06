import api from './api';

export const aiService = {
  async generateItinerary(payload) {
    const response = await api.post('ai/generate', payload);
    return response.data;
  },

  async optimizeRoute(payload) {
    const response = await api.post('ai/optimize', payload);
    return response.data;
  },
};
