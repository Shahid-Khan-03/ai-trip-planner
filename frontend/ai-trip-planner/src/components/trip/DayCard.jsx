import { formatDate } from '../../utils/dateUtils';
import ActivityItem from './ActivityItem';

const DayCard = ({ day, activities = [] }) => (
  <article className="card day-card">
    <div>
      <p className="eyebrow">Day {day.dayNumber}</p>
      <h3>{formatDate(day.date)}</h3>
    </div>
    {activities.length ? (
      <ul className="activity-list">
        {activities.map((activity) => (
          <ActivityItem activity={activity} key={activity.id || activity.name} />
        ))}
      </ul>
    ) : (
      <p className="muted">No activities added yet.</p>
    )}
  </article>
);

export default DayCard;
