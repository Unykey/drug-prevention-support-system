// Import các dependencies cần thiết
import React from 'react'; // React core library
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'; // UI Card components
import { Button } from '@/components/ui/button'; // UI Button component
import { Users, UserCog, BarChartBig, FileText, Settings, Newspaper, BookOpen, CalendarClock, ShieldAlert } from 'lucide-react'; // Lucide React icons
import { Link } from 'react-router-dom'; // React Router navigation component

/**
 * Cấu hình các module/section quản trị
 * Mỗi section đại diện cho một chức năng quản lý riêng biệt
 * Structure cho mỗi section:
 * - title: Tên hiển thị của module
 * - description: Mô tả ngắn gọn chức năng
 * - icon: JSX Element icon với styling riêng (màu sắc phân biệt)
 * - link: Đường dẫn điều hướng đến trang quản lý chi tiết
 */
const adminSections = [
  { 
    title: "Quản Lý Người Dùng", // Tên hiển thị trên card
    description: "Hồ sơ, phân quyền, lịch sử.", // Mô tả ngắn gọn chức năng
    icon: <Users className="h-8 w-8 text-primary" />, // Icon với màu primary
    link: "/admin/users" // Route điều hướng đến trang users
  },
  { 
    title: "Quản Lý Chuyên Viên", 
    description: "Thông tin, bằng cấp, lịch.", 
    icon: <UserCog className="h-8 w-8 text-accent" />, // Icon với màu accent
    link: "/admin/counselors" 
  },
  { 
    title: "Quản Lý Khóa Học", 
    description: "Tạo, sửa, xóa khóa học.", 
    icon: <BookOpen className="h-8 w-8 text-sky-500" />, // Icon với màu sky-500
    link: "/admin/courses" 
  },
  { 
    title: "Quản Lý Bài Viết Blog", 
    description: "Tạo, sửa, xóa bài viết.", 
    icon: <Newspaper className="h-8 w-8 text-green-500" />, // Icon với màu green-500
    link: "/admin/blog" 
  },
  { 
    title: "Quản Lý Chương Trình", 
    description: "Truyền thông, giáo dục.", 
    icon: <FileText className="h-8 w-8 text-orange-500" />, // Icon với màu orange-500
    link: "/admin/programs" 
  },
  { 
    title: "Quản Lý Khảo Sát", 
    description: "Tạo, sửa, xem kết quả.", 
    icon: <ShieldAlert className="h-8 w-8 text-red-500" />, // Icon với màu red-500
    link: "/admin/surveys" 
  },
  { 
    title: "Quản Lý Lịch Hẹn", 
    description: "Xem, xác nhận, hủy lịch hẹn.", 
    icon: <CalendarClock className="h-8 w-8 text-purple-500" />, // Icon với màu purple-500
    link: "/admin/appointments" 
  },
  { 
    title: "Dashboard & Báo Cáo", 
    description: "Thống kê, phân tích dữ liệu.", 
    icon: <BarChartBig className="h-8 w-8 text-yellow-500" />, // Icon với màu yellow-500
    link: "/admin/dashboard" 
  },
  { 
    title: "Cài Đặt Hệ Thống", 
    description: "Cấu hình chung.", 
    icon: <Settings className="h-8 w-8 text-gray-500" />, // Icon với màu gray-500
    link: "/admin/settings" 
  },
];

/**
 * Component AdminPage - Trang chủ của hệ thống quản trị
 * 
 * Chức năng chính:
 * - Hiển thị dashboard tổng quan với các module quản lý
 * - Cung cấp điều hướng nhanh đến các chức năng quản trị
 * - Layout dạng grid card cho dễ dàng truy cập
 * 
 * Các module quản lý bao gồm:
 * 1. Quản lý người dùng - User management và phân quyền
 * 2. Quản lý chuyên viên - Counselor profiles và scheduling
 * 3. Quản lý khóa học - Course content management
 * 4. Quản lý blog - Content management system
 * 5. Quản lý chương trình - Program và campaign management
 * 6. Quản lý khảo sát - Survey creation và analytics
 * 7. Quản lý lịch hẹn - Appointment scheduling system
 * 8. Dashboard & Báo cáo - Analytics và reporting
 * 9. Cài đặt hệ thống - System configuration
 * 
 * Layout:
 * - Header section: Tiêu đề và giới thiệu trang quản trị
 * - Grid section: Hiển thị cards các module quản lý
 * 
 * @returns {JSX.Element} React component
 */
const AdminPage = () => {
  return (
    <div className="space-y-8"> {/* Container với khoảng cách 8 giữa các section */}
      
      {/* Header section với gradient background */}
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        {/* text-center: căn giữa text, py-12: padding top/bottom 12 */}
        <h1 className="text-4xl font-bold mb-4 gradient-text">Trang Quản Trị Hệ Thống</h1>
        {/* text-4xl: font size lớn, mb-4: margin bottom 4 */}
        
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          {/* text-lg: font size vừa, max-w-2xl: giới hạn width, mx-auto: center horizontally */}
          Quản lý toàn bộ hoạt động của nền tảng phòng chống ma túy.
        </p>
      </section>

      {/* Grid section hiển thị các admin modules */}
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* Responsive grid: 1 cột mobile, 2 cột medium, 3 cột large, gap-6: khoảng cách 6 */}
        
        {adminSections.map(section => ( // Loop qua từng section trong adminSections array
          <Card key={section.title} className="light-theme-card hover:shadow-primary/10 transition-shadow duration-300 flex flex-col">
            {/* key={section.title}: React key cho list rendering */}
            {/* flex flex-col: flexbox column để button luôn ở bottom */}
            {/* hover:shadow-primary/10: shadow effect khi hover */}
            {/* transition-shadow duration-300: smooth animation 300ms */}
            
            <CardHeader> {/* Phần header của card chứa icon và title */}
              <div className="p-3 bg-slate-100 rounded-full w-fit mb-4">
                {/* p-3: padding 3, bg-slate-100: background xám nhạt */}
                {/* rounded-full: bo tròn hoàn toàn, w-fit: width vừa đủ content */}
                {section.icon} {/* Render icon từ section data */}
              </div>
              
              <CardTitle className="text-xl light-theme-card-header">
                {/* text-xl: font size extra large */}
                {section.title} {/* Hiển thị tên module */}
              </CardTitle>
              
              <CardDescription className="light-theme-card-description h-12 overflow-hidden">
                {/* h-12: height cố định 12, overflow-hidden: ẩn text dài */}
                {section.description} {/* Hiển thị mô tả module */}
              </CardDescription>
            </CardHeader>
            
            <CardContent className="mt-auto"> {/* mt-auto: margin-top auto để đẩy xuống bottom */}
              <Link to={section.link}> {/* React Router Link điều hướng */}
                <Button className="w-full light-theme-button-primary">
                  {/* w-full: width 100% */}
                  Truy Cập
                </Button>
              </Link>
            </CardContent>
          </Card>
        ))}
      </section>
    </div>
  );
};

// Export component để sử dụng trong routing và các file khác
export default AdminPage;