const WeatherWidget = ({ weather = [] }) => (
  <section className="card">
    <h3>Weather</h3>
    {weather.length ? (
      <div className="weather-grid">
        {weather.map((item) => (
          <div className="weather-day" key={item.date}>
            <strong>{item.date}</strong>
            <span>{item.temp} deg</span>
            <small>{item.condition}</small>
          </div>
        ))}
      </div>
    ) : (
      <p className="muted">Weather API is not connected in the backend yet.</p>
    )}
  </section>
);

export default WeatherWidget;
