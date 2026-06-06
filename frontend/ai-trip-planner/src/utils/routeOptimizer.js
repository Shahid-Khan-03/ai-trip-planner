const distance = (first, second) => {
  const dx = Number(first.lat || 0) - Number(second.lat || 0);
  const dy = Number(first.lng || 0) - Number(second.lng || 0);
  return Math.sqrt((dx * dx) + (dy * dy));
};

export const optimizeNearestNeighbour = (places = []) => {
  if (places.length <= 2) return places;

  const [start, ...remaining] = places;
  const route = [start];
  let current = start;

  while (remaining.length) {
    let nearestIndex = 0;

    remaining.forEach((place, index) => {
      if (distance(current, place) < distance(current, remaining[nearestIndex])) {
        nearestIndex = index;
      }
    });

    current = remaining.splice(nearestIndex, 1)[0];
    route.push(current);
  }

  return route;
};
