// LoginPage.jsx: Trang đăng nhập - xử lý authentication và điều hướng người dùng
import React, {useState} from 'react'; // React với useState hook để quản lý form state
import {Link, useNavigate} from 'react-router-dom'; // Link: điều hướng, useNavigate: điều hướng sau khi đăng nhập
import {Button} from '@/components/ui/button'; // Component nút bấm tùy chỉnh
import {Input} from '@/components/ui/input'; // Component ô nhập liệu
import {Label} from '@/components/ui/label'; // Component nhãn cho form fields
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from '@/components/ui/card'; // Các component card để tạo layout đẹp
import {LogIn, Mail, KeyRound} from 'lucide-react'; // Biểu tượng: đăng nhập, email, khóa
import {useAuth} from '@/contexts/AuthContext'; // Hook authentication để quản lý trạng thái đăng nhập
import {useToast} from '@/components/ui/use-toast';
import {ROLES} from "@/config/roles.js";
import {AxiosHeaders as state} from "axios"; // Hook hiển thị thông báo toast
import { useEffect } from 'react'; // Added for loading Google script

// Component chính của trang đăng nhập
const LoginPage = () => {
    // State để quản lý dữ liệu form đăng nhập
    const [email, setEmail] = useState(''); // Lưu email người dùng nhập
    const [password, setPassword] = useState(''); // Lưu mật khẩu người dùng nhập
    const [isLoading, setIsLoading] = useState(false); // Trạng thái loading khi submit form

    // Hooks để điều hướng và quản lý auth
    const navigate = useNavigate(); // Hook để chuyển hướng sau khi đăng nhập thành công
    const {login} = useAuth(); // Lấy function login từ AuthContext để cập nhật trạng thái auth
    const {toast} = useToast(); // Hook để hiển thị thông báo toast success/error

    const [googleLoaded, setGoogleLoaded] = useState(false); // Track if Google script is loaded

    useEffect(() => {
        // Dynamically load Google Identity Services script
        const script = document.createElement('script');
        script.src = 'https://accounts.google.com/gsi/client';
        script.async = true;
        script.defer = true;
        script.onload = () => setGoogleLoaded(true); // Set flag when script loads
        document.body.appendChild(script);

        // Cleanup script on component unmount
        return () => {
            document.body.removeChild(script);
        };
    }, []);

    const handleGoogleSignIn = async (response) => {
        setIsLoading(true);
        try {
            // Send Google ID token to your backend for validation
            const res = await fetch('https://yourdomain.com/auth/google', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id_token: response.credential }),
            });
            const result = await res.json();

            if (result.success) {
                // Call login from AuthContext with user data
                await login(result.user.email, null, result.user); // Pass Google user data
                toast({
                    title: result.message || 'Đăng nhập thành công với Google!',
                    description: `Chào mừng trở lại, ${result.user?.name || result.user.email}!`,
                    variant: 'default',
                    className: 'bg-green-500 text-white',
                });
                setTimeout(() => {
                    const redirectTo = state?.from?.pathname || (result.user?.role === ROLES.ADMIN ? '/admin' : '/');
                    navigate(redirectTo, { replace: true });
                }, 100);
            } else {
                throw new Error(result.error || 'Google Sign-In failed');
            }
        } catch (error) {
            console.error('Google Sign-In error:', error);
            toast({
                title: 'Đăng nhập thất bại',
                description: error.message || 'Đã có lỗi khi đăng nhập với Google.',
                variant: 'destructive',
            });
        } finally {
            setIsLoading(false);
        }
    };

    // Hàm xử lý submit form đăng nhập
    const handleSubmit = async (e) => {
        e.preventDefault(); // Ngăn chặn reload trang mặc định khi submit form
        setIsLoading(true); // Bật trạng thái loading để disable button và hiển thị spinner

        try {
            const result = await login(email, password);
            // Mô phỏng delay API call (1 giây) để test UX loading
            // await new Promise(resolve => setTimeout(resolve, 1000));

            // Tạo mock user dựa trên email nhập vào
            // Nếu email chứa 'admin' thì role = 'Admin', ngược lại là 'Member'
            // const mockUser = {
            //   email,
            //   name: 'Người dùng Test',
            //   role: email.includes('admin') ? 'Admin' : 'Member'
            // };

            // Gọi function login từ AuthContext để lưu thông tin user
            // login(mockUser);

            // Hiển thị toast thông báo đăng nhập thành công
            if (result.success) {
                toast({
                    title: result.message || 'Đăng nhập thành công!',
                    description: `Chào mừng trở lại, ${result.user?.name || email}!`,
                    variant: "default", // Style mặc định
                    className: "bg-green-500 text-white", // Custom style màu xanh lá
                });
                setTimeout(() => {
                    const redirectTo = state?.from?.pathname || (result.user?.role === ROLES.ADMIN ? '/admin' : '/');
                    navigate(redirectTo, { replace: true });
                }, 100);
            } else {
                console.error('Login failed with error:', result.error);
                toast({
                    title: 'Đăng nhập thất bại',
                    description: result.error, // Use backend message
                    variant: 'destructive',
                });
            }
        } catch (error) {
            console.error('HandleSubmit error:', {
                message: error.message,
                stack: error.stack,
            });
            // Hiển thị toast thông báo lỗi khi đăng nhập thất bại
            toast({
                title: "Đăng nhập thất bại",
                description: error.message || "Đã có lỗi xảy ra. Vui lòng thử lại.",
                variant: "destructive", // Style màu đỏ cho lỗi
            });
        } finally {
            // Luôn tắt loading dù thành công hay thất bại
            setIsLoading(false);
        }
    };

    return (
        // Container chính - căn giữa form đăng nhập trên màn hình
        <div className="flex items-center justify-center min-h-[calc(100vh-200px)] py-12">
            {/* Card chứa form đăng nhập - có shadow và theme tùy chỉnh */}
            <Card className="w-full max-w-md light-theme-card shadow-2xl">

                {/* Header của card - tiêu đề và mô tả */}
                <CardHeader className="text-center">
                    {/* Icon đăng nhập với background tròn */}
                    <div className="inline-block p-3 bg-primary/10 rounded-full mx-auto mb-4">
                        <LogIn className="h-10 w-10 text-primary"/>
                    </div>
                    {/* Tiêu đề trang với gradient text effect */}
                    <CardTitle className="text-3xl font-bold gradient-text">Đăng Nhập</CardTitle>
                    {/* Mô tả ngắn gọn về trang */}
                    <CardDescription className="light-theme-card-description">
                        Chào mừng trở lại! Vui lòng nhập thông tin của bạn.
                    </CardDescription>
                </CardHeader>

                {/* Nội dung chính của card - chứa form */}
                <CardContent>
                    {/* Form đăng nhập với onSubmit handler */}
                    <form onSubmit={handleSubmit} className="space-y-6">

                        {/* Trường Email */}
                        <div className="space-y-2">
                            {/* Label với icon email */}
                            <Label htmlFor="email" className="light-theme-text-default flex items-center">
                                <Mail className="h-4 w-4 mr-2 text-primary"/> Email
                            </Label>
                            {/* Input email với validation và controlled component */}
                            <Input
                                id="email"
                                type="email" // HTML5 email validation
                                placeholder="nhapemail@example.com"
                                value={email} // Controlled component - value từ state
                                onChange={(e) => setEmail(e.target.value)} // Cập nhật state khi user nhập
                                required // Bắt buộc nhập
                                className="light-theme-input"
                            />
                        </div>

                        {/* Trường Password */}
                        <div className="space-y-2">
                            {/* Label với icon khóa */}
                            <Label htmlFor="password" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-primary"/> Mật Khẩu
                            </Label>
                            {/* Input password với controlled component */}
                            <Input
                                id="password"
                                type="password" // Ẩn text khi nhập
                                placeholder="••••••••"
                                value={password} // Controlled component - value từ state
                                onChange={(e) => setPassword(e.target.value)} // Cập nhật state khi user nhập
                                required // Bắt buộc nhập
                                className="light-theme-input"
                            />
                        </div>

                        {/* Button submit - disabled khi đang loading */}
                        <Button
                            type="submit"
                            className="w-full light-theme-button-primary text-lg py-3"
                            disabled={isLoading} // Disable khi đang xử lý
                        >
                            {/* Text động dựa trên trạng thái loading */}
                            {isLoading ? 'Đang xử lý...' : 'Đăng Nhập'}
                        </Button>
                    </form>
                </CardContent>

                {/* Footer của card - chứa các link phụ */}
                <CardFooter className="flex flex-col items-center space-y-3">
                    <div className="text-center">
                        <div className="relative flex items-center">
                            <div className="flex-grow border-t border-gray-300"></div>
                            <span className="flex-shrink mx-4 text-gray-600 text-sm">Hoặc đăng nhập bằng</span>
                            <div className="flex-grow border-t border-gray-300"></div>
                        </div>
                    </div>

                    {googleLoaded && (
                        <div className="mt-4 w-full">
                            <div
                                id="g_id_onload"
                                data-client_id="YOUR_GOOGLE_CLIENT_ID"
                                data-callback="handleGoogleSignIn"
                                data-auto_prompt="false"
                            ></div>
                            <div
                                className="g_id_signin w-full"
                                data-type="standard"
                                data-size="large"
                                data-theme="outline"
                                data-text="signin_with"
                                data-shape="rectangular"
                            ></div>
                        </div>
                    )}

                    {/* Link quên mật khẩu */}
                    <Link to="/forgot-password"
                          className="text-sm text-primary hover:underline">
                        Quên mật khẩu?
                    </Link>
                    {/* Text và link đăng ký tài khoản mới */}
                    <p className="text-sm text-slate-500">
                        Chưa có tài khoản?{' '}
                        <Link to="/register" className="font-semibold text-accent hover:underline">
                            Đăng ký ngay
                        </Link>
                    </p>
                </CardFooter>
            </Card>
        </div>
    );
};

// Export component LoginPage làm default export để có thể import ở các file khác
export default LoginPage;