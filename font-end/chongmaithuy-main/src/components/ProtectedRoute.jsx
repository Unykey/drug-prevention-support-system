import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';
import { ROLES } from '@/config/roles'; // Ensure ROLES are correctly imported

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user, loading } = useAuth();
  const location = useLocation();

  if (loading) {
    // Optionally, return a loading spinner or a blank page while checking auth state
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p className="text-xl text-gray-300">Đang tải dữ liệu người dùng...</p>
      </div>
    );
  }

  if (!user) {
    // User not logged in, redirect to login page
    // Pass the current location so we can redirect back after login
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Check if user's role is allowed
  // If allowedRoles is not provided, any logged-in user can access
  // Ensure user.role exists and is a string
  const userRole = user && typeof user.role === 'string' ? user.role : ROLES.GUEST;
  
  if (allowedRoles && !allowedRoles.includes(userRole)) {
    // User does not have the required role, redirect to an unauthorized page or home
    // For simplicity, redirecting to home page
    return <Navigate to="/" replace />; 
  }

  return children; // User is authenticated and has the required role
};

export default ProtectedRoute;