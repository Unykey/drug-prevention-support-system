// Import React và các hooks routing cần thiết
import React from 'react';
import { Navigate, useLocation } from 'react-router-dom'; // Navigate để redirect, useLocation để lưu location hiện tại
import { useAuth } from '@/contexts/AuthContext'; // Hook lấy thông tin user từ AuthContext
import { ROLES } from '@/config/roles'; // Import định nghĩa các vai trò trong hệ thống

/**
 * Component ProtectedRoute - Bảo vệ các route yêu cầu authentication và authorization
 * 
 * Chức năng:
 * - Kiểm tra user đã đăng nhập chưa
 * - Kiểm tra quyền truy cập theo vai trò (role-based access control)
 * - Redirect đến trang đăng nhập nếu chưa login
 * - Redirect đến trang chủ nếu không đủ quyền
 * - Hiển thị loading state trong khi kiểm tra auth
 * 
 * Props:
 * - children: Component con sẽ được render nếu pass qua các kiểm tra
 * - allowedRoles: Array các vai trò được phép truy cập route này
 *
 * @param, {ReactNode} children - Component con sẽ được render nếu pass qua các kiểm tra
 * @param, {Array} allowedRoles - Array các vai trò được phép truy cập route này
 */
const ProtectedRoute = ({ children, allowedRoles }) => {
  // Lấy thông tin user và trạng thái loading từ AuthContext
  const { user, loading } = useAuth();
  // Lấy location hiện tại để có thể redirect về sau khi đăng nhập
  const location = useLocation();

  // Hiển thị loading state khi đang kiểm tra authentication
  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p className="text-xl text-gray-300">Đang tải dữ liệu người dùng...</p>
      </div>
    );
  }

  // Kiểm tra user đã đăng nhập chưa
  if (!user) {
    // Chưa đăng nhập -> redirect đến trang login
    // Truyền location hiện tại qua state để có thể redirect về sau khi login thành công
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Kiểm tra quyền truy cập theo vai trò
  // Lấy role của user, nếu không có thì mặc định là GUEST
  const userRole = user && typeof user.role === 'string' ? user.role : ROLES.GUEST;
  
  // Nếu có định nghĩa allowedRoles và user không có quyền
  if (allowedRoles && !allowedRoles.includes(userRole)) {
    // Không đủ quyền -> redirect về trang chủ
    return <Navigate to="/not-found" replace />;
  }

  // User đã đăng nhập và có đủ quyền -> render component con
  return children;
};

// Export component ProtectedRoute làm default export
export default ProtectedRoute;