// Import React hooks cần thiết cho việc quản lý state và context
import React, {createContext, useContext, useState, useEffect} from 'react';
// Import axios để gọi API login
import axios from 'axios';

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
export const AuthProvider = ({children}) => {
    // State lưu thông tin user hiện tại
    const [user, setUser] = useState(null);
    // State loading khi đang kiểm tra authentication từ localStorage
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Thêm interceptor axios để gọi API login khi có token trong localStorage
        axios.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem('token');
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        // Kiểm tra localStorage xem có thông tin user đã lưu không
        const storedUser = localStorage.getItem('user');
        if (storedUser && storedUser != "undefined") {
            try {
                setUser(JSON.parse(storedUser));
            } catch (error) {
                console.log('Failed to parse user from localStorage:', error);
                localStorage.removeItem('user');
            }
        }
        setLoading(false);
    }, [])

    /**
     * Hàm đăng nhập - Lưu thông tin user vào state và localStorage
     * @param {string} email - User email
     * @param {string} password - User password
     * @returns {Promise<{success: boolean, error?: string}>}
     */
    const login = async (email, password) => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {email, password});
            const {success, data, message} = response.data;
            if (!success) {
                throw new Error(message || 'Đăng nhập thất bại. Vui lòng thử lại.');
            }
            const {jwt, user} = data;
            // Lưu vào localStorage để persist qua session
            if (!jwt || !user) {
                console.error('Missing jwt or user in response:', data);
                throw new Error('Invalid response data');
            }
            localStorage.setItem('token', jwt);
            localStorage.setItem('user', JSON.stringify(user));
            // Cập nhật state
            setUser(user);
            return {success: true, user};
        } catch (error) {
            const errorMessage = error.response?.data?.error || 'Đăng nhập thất bại. Vui lòng thử lại.';
            return {success: false, error: errorMessage};
        }
    };

    /**
     * Hàm đăng xuất - Xóa thông tin token và user khỏi state và localStorage
     */
    const logout = () => {
        // Xóa khỏi localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        // Reset state về null
        setUser(null);
    };

// Các hàm khác như register, forgotPassword, etc. có thể được thêm ở đây
// và gọi API tương ứng.

// Cung cấp context value cho toàn bộ ứng dụng
    return (
        <AuthContext.Provider value={{user, login, logout, loading}}>
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