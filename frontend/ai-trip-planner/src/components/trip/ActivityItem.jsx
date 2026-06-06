const ActivityItem = ({ activity }) => (
  <li className="activity-item">
    <span className="activity-time">{activity.time || 'Flexible'}</span>
    <div>
      <strong>{activity.name}</strong>
      <p>{activity.location}</p>
      {activity.notes && <small>{activity.notes}</small>}
    </div>
  </li>
);

export default ActivityItem;
