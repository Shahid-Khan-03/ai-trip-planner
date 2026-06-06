import DayCard from './DayCard';

const ItineraryView = ({ days = [], activities = [] }) => {
  const activitiesByDay = activities.reduce((grouped, activity) => {
    const dayId = activity.day?.id || activity.dayId;
    return {
      ...grouped,
      [dayId]: [...(grouped[dayId] || []), activity],
    };
  }, {});

  if (!days.length) {
    return <p className="muted">No itinerary days found for this trip.</p>;
  }

  return (
    <div className="stack">
      {days.map((day) => (
        <DayCard day={day} activities={activitiesByDay[day.id] || []} key={day.id} />
      ))}
    </div>
  );
};

export default ItineraryView;
