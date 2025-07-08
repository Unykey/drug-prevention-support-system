// LoginPage.jsx: Trang đăng nhập - xử lý authentication và điều hướng người dùng
import React, { useState } from 'react'; // React với useState hook để quản lý form state
import { Link, useNavigate } from 'react-router-dom'; // Link: điều hướng, useNavigate: điều hướng sau khi đăng nhập
import { Button } from '@/components/ui/button'; // Component nút bấm tùy chỉnh
import { Input } from '@/components/ui/input'; // Component ô nhập liệu
import { Label } from '@/components/ui/label'; // Component nhãn cho form fields
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'; // Các component card để tạo layout đẹp
import { LogIn, Mail, KeyRound } from 'lucide-react'; // Biểu tượng: đăng nhập, email, khóa
import { useAuth } from '@/contexts/AuthContext'; // Hook authentication để quản lý trạng thái đăng nhập
import { useToast } from '@/components/ui/use-toast'; // Hook hiển thị thông báo toast

// Component chính của trang đăng nhập
const LoginPage = () => {
  // State để quản lý dữ liệu form đăng nhập
  const [email, setEmail] = useState(''); // Lưu email người dùng nhập
  const [password, setPassword] = useState(''); // Lưu mật khẩu người dùng nhập
  const [isLoading, setIsLoading] = useState(false); // Trạng thái loading khi submit form

  // Hooks để điều hướng và quản lý auth
  const navigate = useNavigate(); // Hook để chuyển hướng sau khi đăng nhập thành công
  const { login } = useAuth(); // Lấy function login từ AuthContext để cập nhật trạng thái auth
  const { toast } = useToast(); // Hook để hiển thị thông báo toast success/error

  // Hàm xử lý submit form đăng nhập
  const handleSubmit = async (e) => {
    e.preventDefault(); // Ngăn chặn reload trang mặc định khi submit form
    setIsLoading(true); // Bật trạng thái loading để disable button và hiển thị spinner
    
    try {
      // Mô phỏng delay API call (1 giây) để test UX loading
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Tạo mock user dựa trên email nhập vào
      // Nếu email chứa 'admin' thì role = 'Admin', ngược lại là 'Member'
      const mockUser = { 
        email, 
        name: 'Người dùng Test', 
        role: email.includes('admin') ? 'Admin' : 'Member' 
      };
      
      // Gọi function login từ AuthContext để lưu thông tin user
      login(mockUser);

      // Hiển thị toast thông báo đăng nhập thành công
      toast({
        title: "Đăng nhập thành công!",
        description: `Chào mừng trở lại, ${mockUser.name || mockUser.email}!`,
        variant: "default", // Style mặc định
        className: "bg-green-500 text-white", // Custom style màu xanh lá
      });
      
      // Chuyển hướng về trang chủ sau khi đăng nhập thành công
      navigate('/');
    } catch (error) {
      // Hiển thị toast thông báo lỗi khi đăng nhập thất bại
      toast({
        title: "Đăng nhập thất bại",
        description: error.message || "Email hoặc mật khẩu không đúng. Vui lòng thử lại.",
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
            <LogIn className="h-10 w-10 text-primary" />
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
                <Mail className="h-4 w-4 mr-2 text-primary" /> Email
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
                <KeyRound className="h-4 w-4 mr-2 text-primary" /> Mật Khẩu
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