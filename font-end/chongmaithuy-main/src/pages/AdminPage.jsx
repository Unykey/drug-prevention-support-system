import React from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Users, UserCog, BarChartBig, FileText, Settings, Newspaper, BookOpen, CalendarClock, ShieldAlert } from 'lucide-react';
import { Link } from 'react-router-dom';

const adminSections = [
  { title: "Quản Lý Người Dùng", description: "Hồ sơ, phân quyền, lịch sử.", icon: <Users className="h-8 w-8 text-primary" />, link: "/admin/users" },
  { title: "Quản Lý Chuyên Viên", description: "Thông tin, bằng cấp, lịch.", icon: <UserCog className="h-8 w-8 text-accent" />, link: "/admin/counselors" },
  { title: "Quản Lý Khóa Học", description: "Tạo, sửa, xóa khóa học.", icon: <BookOpen className="h-8 w-8 text-sky-500" />, link: "/admin/courses" },
  { title: "Quản Lý Bài Viết Blog", description: "Tạo, sửa, xóa bài viết.", icon: <Newspaper className="h-8 w-8 text-green-500" />, link: "/admin/blog" },
  { title: "Quản Lý Chương Trình", description: "Truyền thông, giáo dục.", icon: <FileText className="h-8 w-8 text-orange-500" />, link: "/admin/programs" },
  { title: "Quản Lý Khảo Sát", description: "Tạo, sửa, xem kết quả.", icon: <ShieldAlert className="h-8 w-8 text-red-500" />, link: "/admin/surveys" },
  { title: "Quản Lý Lịch Hẹn", description: "Xem, xác nhận, hủy lịch hẹn.", icon: <CalendarClock className="h-8 w-8 text-purple-500" />, link: "/admin/appointments" },
  { title: "Dashboard & Báo Cáo", description: "Thống kê, phân tích dữ liệu.", icon: <BarChartBig className="h-8 w-8 text-yellow-500" />, link: "/admin/dashboard" },
  { title: "Cài Đặt Hệ Thống", description: "Cấu hình chung.", icon: <Settings className="h-8 w-8 text-gray-500" />, link: "/admin/settings" },
];

const AdminPage = () => {
  return (
    <div className="space-y-8">
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <h1 className="text-4xl font-bold mb-4 gradient-text">Trang Quản Trị Hệ Thống</h1>
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Quản lý toàn bộ hoạt động của nền tảng phòng chống ma túy.
        </p>
      </section>

      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {adminSections.map(section => (
          <Card key={section.title} className="light-theme-card hover:shadow-primary/10 transition-shadow duration-300 flex flex-col">
            <CardHeader>
              <div className="p-3 bg-slate-100 rounded-full w-fit mb-4">{section.icon}</div>
              <CardTitle className="text-xl light-theme-card-header">{section.title}</CardTitle>
              <CardDescription className="light-theme-card-description h-12 overflow-hidden">{section.description}</CardDescription>
            </CardHeader>
            <CardContent className="mt-auto">
              <Link to={section.link}>
                <Button className="w-full light-theme-button-primary">Truy Cập</Button>
              </Link>
            </CardContent>
          </Card>
        ))}
      </section>
    </div>
  );
};

export default AdminPage;