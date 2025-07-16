// Import React hooks cần thiết cho việc quản lý state và context
import React, {createContext, useContext, useState, useEffect} from 'react';
// Import axios để gọi API login
import axios from 'axios';
import { useNavigate } from "react-router-dom";

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
    const navigate = useNavigate();
    const INACTIVITY_TIMEOUT = 1000 * 60 * 15; // 15 phút
    const TOKEN_REFRESH_INTERVAL = 12 * 60 * 1000; // 12 phút

    useEffect(() => {
        let timeoutId;

        const resetInactivityTimeout = () => {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => {
                logout();
                navigate('/login', { replace: true });
            }, INACTIVITY_TIMEOUT);
        };

        const handleActivity = () => {
            resetInactivityTimeout();
        };

        window.addEventListener('mousemove', handleActivity);
        window.addEventListener('keydown', handleActivity);
        window.addEventListener('click', handleActivity);

        resetInactivityTimeout();

        return () => {
            clearTimeout(timeoutId);
            window.removeEventListener('mousemove', handleActivity);
            window.removeEventListener('keydown', handleActivity);
            window.removeEventListener('click', handleActivity);
        };
    }, [navigate]); // Add navigate to dependency array

    // Token refresh
    useEffect(() => {
        const refreshToken = async () => {
            try {
                const token = localStorage.getItem('token');
                if (token && token !== 'undefined') {
                    const response = await axios.post('/api/auth/refresh', {}, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    const { success, data, message } = response.data;
                    if (success) {
                        localStorage.setItem('token', data);
                        console.log('Token refreshed:', message);
                    } else {
                        console.error('Token refresh failed:', message);
                        logout();
                        navigate('/login', { replace: true });
                    }
                }
            } catch (error) {
                console.error('Token refresh error:', {
                    message: error.message,
                    response: error.response?.data,
                    status: error.response?.status,
                });
                logout();
                navigate('/login', { replace: true });
            }
        };

        const refreshInterval = setInterval(refreshToken, TOKEN_REFRESH_INTERVAL);
        return () => clearInterval(refreshInterval);
    }, [navigate]);

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
        if (storedUser && storedUser !== "undefined") {
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
            console.log('Login response:', response.data);
            const {success, data, message} = response.data;
            if (!success) {
                console.error('Login failed with message:', message);
                throw new Error(message || 'Login failed');
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
            return {success: true, user, message};
        } catch (error) {
            const errorMessage = error.response?.data?.error || error.message || 'Login failed';
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