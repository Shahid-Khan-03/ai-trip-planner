const Section = ({ title, children }) => (
  <div style={{ marginBottom: '20px' }}>
    <p className="eyebrow" style={{ marginBottom: '8px' }}>{title}</p>
    {children}
  </div>
);

const RoadmapDay = ({ day }) => (
  <div
    style={{
      background: '#f8fafc',
      borderRadius: '8px',
      padding: '14px',
      marginBottom: '10px',
      borderLeft: '3px solid #2563eb',
    }}
  >
    <strong>Day {day.day} — {day.title}</strong>
    <div style={{
      marginTop: '8px',
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
      gap: '6px',
      fontSize: '14px',
      color: '#475569',
    }}>
      {day.morning && <p style={{ margin: 0 }}>🌅 <strong>Morning:</strong> {day.morning}</p>}
      {day.afternoon && <p style={{ margin: 0 }}>☀️ <strong>Afternoon:</strong> {day.afternoon}</p>}
      {day.evening && <p style={{ margin: 0 }}>🌙 <strong>Evening:</strong> {day.evening}</p>}
      {day.food && <p style={{ margin: 0 }}>🍽️ <strong>Food:</strong> {day.food}</p>}
      {day.transport && <p style={{ margin: 0 }}>🚗 <strong>Transport:</strong> {day.transport}</p>}
      {day.estimatedCost != null && (
        <p style={{ margin: 0 }}>💰 <strong>Est. Cost:</strong> ₹{day.estimatedCost.toLocaleString()}</p>
      )}
      {day.tips && <p style={{ margin: 0 }}>💡 <strong>Tip:</strong> {day.tips}</p>}
    </div>
  </div>
);

const AiItineraryPreview = ({ result }) => {
  if (!result) return null;

  const {
    overview,
    destination,
    startDate,
    endDate,
    roadmap = [],
    packingList = [],
    localTips = [],
    warnings = [],
    budgetBreakdown = {},
  } = result;

  return (
    <div className="card" style={{ width: '100%', boxSizing: 'border-box', overflow: 'hidden' }}>
      <p className="eyebrow">AI Generated Itinerary</p>

      {/* Overview + destination in one row */}
      <div style={{ marginBottom: '20px' }}>
        {destination && (
          <p style={{ margin: '0 0 6px', fontWeight: 700, fontSize: '16px' }}>
            📍 {destination}
            {startDate && endDate && (
              <span style={{ fontWeight: 400, color: '#64748b', fontSize: '14px' }}>
                {' '}· {startDate} → {endDate}
              </span>
            )}
          </p>
        )}
        {overview && (
          <p style={{ margin: 0, color: '#475569', fontSize: '14px', lineHeight: 1.6 }}>
            {overview}
          </p>
        )}
      </div>

      {/* Roadmap */}
      {roadmap.length > 0 && (
        <Section title="Day-by-Day Plan">
          {roadmap.map((day) => (
            <RoadmapDay key={day.day} day={day} />
          ))}
        </Section>
      )}

      {/* Budget + Packing + Tips in 3 columns */}
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
        gap: '20px',
        marginTop: '8px',
      }}>
        {Object.keys(budgetBreakdown).length > 0 && (
          <Section title="Budget Breakdown">
            <div style={{ display: 'grid', gap: '4px' }}>
              {Object.entries(budgetBreakdown).map(([key, value]) => (
                <div
                  key={key}
                  style={{ display: 'flex', justifyContent: 'space-between', fontSize: '14px' }}
                >
                  <span style={{ textTransform: 'capitalize', color: '#475569' }}>{key}</span>
                  <strong>₹{typeof value === 'number' ? value.toLocaleString() : value}</strong>
                </div>
              ))}
            </div>
          </Section>
        )}

        {packingList.length > 0 && (
          <Section title="Packing List">
            <ul style={{ margin: 0, paddingLeft: '18px', fontSize: '14px', color: '#475569', lineHeight: 1.8 }}>
              {packingList.map((item, i) => <li key={i}>{item}</li>)}
            </ul>
          </Section>
        )}

        {localTips.length > 0 && (
          <Section title="Local Tips">
            <ul style={{ margin: 0, paddingLeft: '18px', fontSize: '14px', color: '#475569', lineHeight: 1.8 }}>
              {localTips.map((tip, i) => <li key={i}>{tip}</li>)}
            </ul>
          </Section>
        )}
      </div>

      {warnings.length > 0 && (
        <div style={{
          marginTop: '16px',
          background: '#fef2f2',
          borderRadius: '8px',
          padding: '12px',
        }}>
          <p className="eyebrow" style={{ color: '#dc2626', marginBottom: '6px' }}>Warnings</p>
          <ul style={{ margin: 0, paddingLeft: '18px', fontSize: '14px', color: '#dc2626', lineHeight: 1.8 }}>
            {warnings.map((w, i) => <li key={i}>{w}</li>)}
          </ul>
        </div>
      )}
    </div>
  );
};

export default AiItineraryPreview;