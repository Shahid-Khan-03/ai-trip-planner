import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import ProtectedRoute from './components/common/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import { TripProvider } from './context/TripContext';
import BudgetPage from './pages/BudgetPage';
import CreateTripPage from './pages/CreateTripPage';
import DashboardPage from './pages/DashboardPage';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';

import RegisterPage from './pages/RegisterPage';
import TripDetailPage from './pages/TripDetailPage';

const App = () => (
  <BrowserRouter>
    <AuthProvider>
      <TripProvider>
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} />
          <Route path="/trips/create" element={<ProtectedRoute><CreateTripPage /></ProtectedRoute>} />
          <Route path="/trips/:id" element={<ProtectedRoute><TripDetailPage /></ProtectedRoute>} />
          <Route path="/trips/:id/budget" element={<ProtectedRoute><BudgetPage /></ProtectedRoute>} />
        </Routes>
      </TripProvider>
    </AuthProvider>
  </BrowserRouter>
);

export default App;
