// ForgotPasswordPage.jsx: Trang quên mật khẩu - cho phép người dùng khôi phục mật khẩu qua email
import React, { useState } from 'react'; // React cơ bản với useState hook để quản lý trạng thái
import { Link } from 'react-router-dom'; // Component điều hướng giữa các trang
import { Button } from '@/components/ui/button'; // Component nút bấm tùy chỉnh
import { Input } from '@/components/ui/input'; // Component ô nhập liệu
import { Label } from '@/components/ui/label'; // Component nhãn cho form
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'; // Các component card để tạo giao diện đẹp
import { MailQuestion, Send, Mail } from 'lucide-react'; // Các biểu tượng: thư hỏi, gửi, thư
import { useToast } from '@/components/ui/use-toast'; // Hook hiển thị thông báo toast

const ForgotPasswordPage = () => {
  // Quản lý giá trị email người dùng nhập vào
  const [email, setEmail] = useState(''); // State lưu địa chỉ email
  // Quản lý trạng thái đang tải khi gửi yêu cầu
  const [isLoading, setIsLoading] = useState(false); // State hiển thị loading
  // Hook để hiển thị thông báo cho người dùng
  const { toast } = useToast(); // Lấy function toast để hiển thị thông báo

  // Xử lý khi người dùng submit form quên mật khẩu
  const handleSubmit = async (e) => {
    e.preventDefault(); // Ngăn form reload trang
    setIsLoading(true); // Bật trạng thái loading
    try {
      // Giả lập gọi API gửi email khôi phục (delay 1 giây)
      await new Promise(resolve => setTimeout(resolve, 1000)); // Mô phỏng thời gian xử lý
      
      // Hiển thị thông báo thành công
      toast({
        title: "Yêu cầu đã được gửi!", // Tiêu đề thông báo
        description: "Nếu email của bạn tồn tại trong hệ thống, bạn sẽ nhận được một liên kết để đặt lại mật khẩu.", // Nội dung chi tiết
        variant: "default", // Kiểu thông báo mặc định
        className: "bg-green-500 text-white", // Màu nền xanh cho thành công
      });
    } catch (error) {
      // Hiển thị thông báo lỗi nếu có sự cố
      toast({
        title: "Gửi yêu cầu thất bại", // Tiêu đề lỗi
        description: error.message || "Đã có lỗi xảy ra. Vui lòng thử lại.", // Thông báo lỗi chi tiết
        variant: "destructive", // Kiểu thông báo lỗi (màu đỏ)
      });
    } finally {
      setIsLoading(false); // Tắt trạng thái loading dù thành công hay thất bại
    }
  };

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-200px)] py-12">
      {/* Container chính căn giữa với chiều cao tối thiểu trừ đi header/footer */}
      <Card className="w-full max-w-md light-theme-card shadow-2xl">
        {/* Card chính: toàn chiều rộng nhưng tối đa md, có bóng đổ lớn */}
        
        <CardHeader className="text-center"> {/* Header card căn giữa */}
          <div className="inline-block p-3 bg-primary/10 rounded-full mx-auto mb-4">
            {/* Container icon: padding, nền màu chính nhạt 10%, hình tròn, căn giữa */}
            <MailQuestion className="h-10 w-10 text-primary" />
            {/* Icon thư hỏi: kích thước 10x10, màu chính */}
          </div>
          <CardTitle className="text-3xl font-bold gradient-text">Quên Mật Khẩu</CardTitle>
          {/* Tiêu đề: cỡ chữ lớn, đậm, hiệu ứng gradient */}
          <CardDescription className="light-theme-card-description">
            {/* Mô tả với theme styling */}
            Đừng lo lắng! Nhập email của bạn để nhận liên kết đặt lại mật khẩu.
          </CardDescription>
        </CardHeader>
        
        <CardContent> {/* Nội dung chính của card */}
          <form onSubmit={handleSubmit} className="space-y-6"> {/* Form với khoảng cách 6 giữa các phần tử */}
            <div className="space-y-2"> {/* Container field email với khoảng cách 2 */}
              <Label htmlFor="email" className="light-theme-text-default flex items-center">
                {/* Label với theme và flexbox để căn icon */}
                <Mail className="h-4 w-4 mr-2 text-primary" /> Email Đăng Ký
                {/* Icon thư nhỏ với khoảng cách bên phải */}
              </Label>
              <Input
                id="email" // ID liên kết với label
                type="email" // Kiểu input email (có validation tự động)
                placeholder="nhapemail@example.com" // Văn bản gợi ý
                value={email} // Giá trị được điều khiển bởi state
                onChange={(e) => setEmail(e.target.value)} // Cập nhật state khi thay đổi
                required // Bắt buộc nhập
                className="light-theme-input" // Theme styling cho input
              />
            </div>
            <Button type="submit" className="w-full light-theme-button-primary text-lg py-3" disabled={isLoading}>
              {/* Nút submit: toàn chiều rộng, theme chính, cỡ chữ lớn, padding dọc 3, vô hiệu hóa khi loading */}
              {isLoading ? 'Đang gửi...' : (<><Send className="h-5 w-5 mr-2" /> Gửi Yêu Cầu</>)}
              {/* Hiển thị text loading hoặc icon + text bình thường */}
            </Button>
          </form>
        </CardContent>
        
        <CardFooter className="flex flex-col items-center space-y-3">
          {/* Footer card: flexbox cột, căn giữa, khoảng cách 3 */}
          <Link to="/login"
            className="text-sm text-accent hover:underline">
            {/* Link quay lại đăng nhập: cỡ chữ nhỏ, màu accent, gạch chân khi hover */}
            Quay lại Đăng nhập
          </Link>
        </CardFooter>
      </Card>
    </div>
  );
};

// Xuất component để sử dụng trong hệ thống routing
export default ForgotPasswordPage;