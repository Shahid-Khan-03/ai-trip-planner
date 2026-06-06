import { useMemo, useState } from 'react';
import { TripContext } from './tripContextValue';

export const TripProvider = ({ children }) => {
  const [activeTrip, setActiveTrip] = useState(null);

  const value = useMemo(
    () => ({
      activeTrip,
      setActiveTrip,
    }),
    [activeTrip],
  );

  return <TripContext.Provider value={value}>{children}</TripContext.Provider>;
};
