import { useMemo, useState } from 'react';
import { authService } from '../services/authService';
import { AuthContext } from './AuthContextValue';

const getStoredUser = () => {
  const rawUser = localStorage.getItem('currentUser');

  if (!rawUser) {
    return null;
  }

  try {
    return JSON.parse(rawUser);
  } catch {
    return null;
  }
};

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(() => localStorage.getItem('token'));
  const [currentUser, setCurrentUser] = useState(getStoredUser);

  const login = async (credentials) => {
    const auth = await authService.login(credentials);
    const user = {
      id: auth.id,
      name: auth.name,
      email: auth.email,
    };

    localStorage.setItem('token', auth.token);
    localStorage.setItem('currentUser', JSON.stringify(user));
    setToken(auth.token);
    setCurrentUser(user);

    return user;
  };

  const register = async (payload) => {
    const auth = await authService.register(payload);
    const user = {
      id: auth.id,
      name: auth.name,
      email: auth.email,
    };

    localStorage.setItem('token', auth.token);
    localStorage.setItem('currentUser', JSON.stringify(user));
    setToken(auth.token);
    setCurrentUser(user);

    return user;
  };

  const logout = () => {
    authService.logout();
    setToken(null);
    setCurrentUser(null);
  };

  const value = useMemo(
    () => ({
      currentUser,
      isAuthenticated: Boolean(token),
      login,
      logout,
      register,
      token,
    }),
    [currentUser, token],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
