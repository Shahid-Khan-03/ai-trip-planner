import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

const Navbar = () => {
  const { currentUser, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="navbar">
      <Link className="brand" to="/">AI Trip Planner</Link>
      <nav>
        <NavLink to="/">Home</NavLink>
        {isAuthenticated && <NavLink to="/dashboard">Dashboard</NavLink>}
        {isAuthenticated && <NavLink to="/trips/create">Create Trip</NavLink>}
      </nav>
      <div className="nav-actions">
        {isAuthenticated ? (
          <>
            <span>{currentUser?.name}</span>
            <button className="button ghost" type="button" onClick={handleLogout}>Logout</button>
          </>
        ) : (
          <>
            <Link className="button ghost" to="/login">Login</Link>
            <Link className="button primary" to="/register">Register</Link>
          </>
        )}
      </div>
    </header>
  );
};

export default Navbar;
