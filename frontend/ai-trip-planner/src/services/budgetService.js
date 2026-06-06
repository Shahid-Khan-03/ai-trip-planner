import api from './api';

export const budgetService = {
  async addExpense(payload) {
    const response = await api.post('/budget', payload);
    return response.data;
  },

  async getSummary(tripId) {
  const response = await api.get('/budget', { params: { tripId } });
  // api interceptor returns response.data directly, so response = APIResponse body
  // handle both list and object shapes
  const raw = response?.data ?? response;
  return raw || [];
},
};
