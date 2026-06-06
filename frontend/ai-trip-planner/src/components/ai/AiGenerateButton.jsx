// import React from 'react';
// import PropTypes from 'prop-types.js';
// import './AiGenerateButton.css';
// import { useAuth } from '../../hooks/useAuth';
// import { weatherService } from '../../services/weaterService';
// import { useWeather } from '../../hooks/useWeather';
// import { useNavigate } from 'react-router-dom';
// import { tripService } from '../../services/tripService';
// import { aiService } from '../../services/aiService';
// import ErrorMessage from '../common/ErrorMessage';
// import LoadingSpinner from '../common/LoadingSpinner';
// import AiItineraryPreview from './AiItineraryPreview';
// import { formatDate } from '../../utils/dateUtils';
// import { formatCurrency } from '../../utils/currencyUtils';
// import ItineraryView from '../trip/ItineraryView';
// import WeatherWidget from '../trip/WeatherWidget';
// import { useEffect, useState } from 'react';
// import { useParams } from 'react-router-dom';
// import { useWeather } from '../../hooks/useWeather';
// import api from '../../services/api';
// import { tripService } from '../../services/tripService';
// import { aiService } from '../../services/aiService';
// import { formatCurrency } from '../../utils/currencyUtils';
// import { formatDate } from '../../utils/dateUtils';
// import ErrorMessage from '../common/ErrorMessage';
// import LoadingSpinner from '../common/LoadingSpinner';
// import AiItineraryPreview from './AiItineraryPreview';
// import ItineraryView from '../trip/ItineraryView';
// import WeatherWidget from '../trip/WeatherWidget';
// import { useAuth } from '../../hooks/useAuth';
// import { useNavigate } from 'react-router-dom';
// import { useState } from 'react';
// import { tripService } from '../../services/tripService';
// import { aiService } from '../../services/aiService';
// import ErrorMessage from '../common/ErrorMessage';
// import LoadingSpinner from '../common/LoadingSpinner';
// import AiItineraryPreview from './AiItineraryPreview';
// import ItineraryView from '../trip/ItineraryView';
// import WeatherWidget from '../trip/WeatherWidget';

const AiGenerateButton = ({ loading = false, disabled = false, onClick }) => (
  <button className="button ghost" type="button" disabled={disabled || loading} onClick={onClick}>
    {loading ? 'Generating...' : 'Generate AI Plan'}
  </button>
);

export default AiGenerateButton;
