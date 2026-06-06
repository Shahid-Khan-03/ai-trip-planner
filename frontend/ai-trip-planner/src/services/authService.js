import api from './api';

const AUTH_KEY = 'currentUser';
const TOKEN_KEY = 'token';

export const authService = {
  async login(credentials) {
    const response = await api.post('/auth/login', credentials);
    const user = response.data;

    localStorage.setItem(TOKEN_KEY, user.token);
    localStorage.setItem(AUTH_KEY, JSON.stringify(user));

    return user;
  },

  async register(payload) {
    const response = await api.post('/auth/register', payload);
    const user = response.data;

    localStorage.setItem(TOKEN_KEY, user.token);
    localStorage.setItem(AUTH_KEY, JSON.stringify(user));

    return user;
  },

  logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(AUTH_KEY);
  },

  getCurrentUser() {
    const saved = localStorage.getItem(AUTH_KEY);
    return saved ? JSON.parse(saved) : null;
  },

  getToken() {
    return localStorage.getItem(TOKEN_KEY);
  },
};
