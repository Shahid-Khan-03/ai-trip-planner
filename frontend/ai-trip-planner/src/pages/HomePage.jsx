import { Link } from 'react-router-dom';
import heroImage from '../assets/hero.png';

const HomePage = () => (
  <main>
    <section className="hero">
      <div className="hero-copy">
        <p className="eyebrow">AI travel planning</p>
        <h1>Plan smarter trips with itinerary, budget, and daily activities.</h1>
        <p>
          Create trips, generate day-wise plans, track expenses, and keep your travel details in one clean dashboard.
        </p>
        <div className="hero-actions">
          <Link className="button primary" to="/trips/create">Plan a Trip</Link>
          <Link className="button ghost" to="/dashboard">View Dashboard</Link>
        </div>
      </div>
      <img src={heroImage} alt="Travel planning illustration" />
    </section>
  </main>
);

export default HomePage;
