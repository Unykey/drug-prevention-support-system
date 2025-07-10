// Import React hooks cần thiết cho việc quản lý state và context
import React, { createContext, useContext, useState, useEffect } from 'react';

// Tạo AuthContext để chia sẻ trạng thái authentication trên toàn ứng dụng
const AuthContext = createContext(null);

/**
 * AuthProvider - Component cung cấp context authentication
 * 
 * Chức năng:
 * - Quản lý trạng thái đăng nhập của user
 * - Lưu trữ thông tin user trong localStorage để persist qua session
 * - Cung cấp các hàm login, logout cho toàn ứng dụng
 * - Quản lý loading state khi khởi tạo app
 * 
 * State:
 * - user: Thông tin user hiện tại (null nếu chưa đăng nhập)
 * - loading: Trạng thái loading khi check auth từ localStorage
 */
export const AuthProvider = ({ children }) => {
  // State lưu thông tin user hiện tại
  const [user, setUser] = useState(null);
  // State loading khi đang kiểm tra authentication từ localStorage
  const [loading, setLoading] = useState(true);

  // Effect chạy khi component mount để restore user từ localStorage
  useEffect(() => {
    // Kiểm tra localStorage xem có thông tin user đã lưu không
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      // Parse JSON và set vào state
      setUser(JSON.parse(storedUser));
    }
    // Kết thúc quá trình loading
    setLoading(false);
  }, []);

  /**
   * Hàm đăng nhập - Lưu thông tin user vào state và localStorage
   * @param {Object} userData - Thông tin user từ API login
   */
  const login = (userData) => {
    // Lưu vào localStorage để persist qua session
    localStorage.setItem('user', JSON.stringify(userData));
    // Cập nhật state
    setUser(userData);
  };

  /**
   * Hàm đăng xuất - Xóa thông tin user khỏi state và localStorage
   */
  const logout = () => {
    // Xóa khỏi localStorage
    localStorage.removeItem('user');
    // Reset state về null
    setUser(null);
  };

  // Các hàm khác như register, forgotPassword, etc. có thể được thêm ở đây
  // và gọi API tương ứng.

  // Cung cấp context value cho toàn bộ ứng dụng
  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

/**
 * Hook useAuth - Sử dụng AuthContext một cách an toàn
 * 
 * Chức năng:
 * - Cung cấp access đến AuthContext
 * - Kiểm tra context được sử dụng trong AuthProvider
 * - Throw error nếu sử dụng ngoài AuthProvider
 * 
 * @returns {Object} Context value với user, login, logout, loading
 * @throws {Error} Nếu sử dụng ngoài AuthProvider
 */
export const useAuth = () => {
  // Lấy context value
  const context = useContext(AuthContext);
  
  // Kiểm tra context có tồn tại không (được sử dụng trong AuthProvider)
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  
  // Trả về context value
  return context;
};