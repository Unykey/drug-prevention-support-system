
// NotFoundPage.jsx: Trang 404 - hiển thị khi người dùng truy cập URL không tồn tại
import React from 'react'; // Import React để tạo functional component
import { Link } from 'react-router-dom'; // Link component để điều hướng về trang chủ không reload trang
import { Button } from '@/components/ui/button'; // UI component button tùy chỉnh với styling nhất quán
import { AlertTriangle } from 'lucide-react'; // Icon cảnh báo tam giác để biểu thị lỗi 404

// Component trang 404 - đơn giản, thân thiện và hướng dẫn người dùng rõ ràng
const NotFoundPage = () => {
  return (
    // Container chính - căn giữa nội dung theo cả chiều dọc và ngang
    // min-h-[60vh]: Chiều cao tối thiểu 60% viewport để đẹp mắt trên mọi màn hình
    <div className="flex flex-col items-center justify-center min-h-[60vh] text-center px-4">
      
      {/* Icon cảnh báo với animation bounce để thu hút sự chú ý */}
      {/* text-destructive: màu đỏ cảnh báo, animate-bounce: hiệu ứng nhảy nhẹ */}
      <AlertTriangle className="w-24 h-24 text-destructive mb-8 animate-bounce" />
      
      {/* Mã lỗi 404 với typography nổi bật */}
      {/* text-6xl: font size rất lớn, font-bold: đậm, text-gray-100: màu xám sáng */}
      <h1 className="text-6xl font-bold text-gray-100 mb-4">404</h1>
      
      {/* Tiêu đề chính giải thích lỗi bằng tiếng Việt */}
      {/* text-3xl: font size lớn, font-semibold: hơi đậm, text-gray-300: màu xám vừa */}
      <h2 className="text-3xl font-semibold text-gray-300 mb-6">Trang Không Tìm Thấy</h2>
      
      {/* Thông điệp mô tả chi tiết và thân thiện với người dùng */}
      {/* max-w-md: giới hạn độ rộng để text dễ đọc, text-gray-400: màu xám nhạt */}
      <p className="text-lg text-gray-400 mb-8 max-w-md">
        Xin lỗi, trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.
      </p>
      
      {/* Link điều hướng về trang chủ - sử dụng React Router Link để SPA navigation */}
      <Link to="/">
        {/* Button với size lớn và hover effect để tạo trải nghiệm tốt */}
        {/* size="lg": kích thước lớn, hover:bg-primary/90: hiệu ứng hover làm tối màu */}
        <Button size="lg" className="bg-primary hover:bg-primary/90 text-primary-foreground">
          Quay Về Trang Chủ
        </Button>
      </Link>
    </div>
  );
};

// Export component NotFoundPage làm default export để sử dụng trong routing
// Trang này sẽ được hiển thị khi React Router không tìm thấy route phù hợp
export default NotFoundPage;
  