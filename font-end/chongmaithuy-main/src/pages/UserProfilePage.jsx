// Import React library để tạo functional component
import React from 'react';

// Import các UI components từ thư viện shadcn/ui
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';

// Import các icon từ thư viện Lucide React
import { User, CalendarCheck, BookOpenCheck, Edit3 } from 'lucide-react';

// Import hook useAuth để lấy thông tin người dùng đã đăng nhập
import { useAuth } from '@/contexts/AuthContext';

/**
 * Component UserProfilePage - Trang thông tin cá nhân của người dùng
 * Hiển thị thông tin người dùng, lịch sử đặt lịch hẹn và lịch sử tham gia chương trình
 */
const UserProfilePage = () => {
  // Destructure để lấy thông tin user từ AuthContext
  // Đổi tên thành authUser để tránh conflict với biến user bên dưới
  const { user: authUser } = useAuth();

  /**
   * Xử lý dữ liệu người dùng:
   * - Nếu có authUser (đã đăng nhập): sử dụng dữ liệu thật với fallback values
   * - Nếu không có authUser: sử dụng dữ liệu mẫu để test giao diện
   */
  const user = authUser ? {
    // Tên người dùng, nếu không có thì hiển thị "Người dùng"
    name: authUser.name || "Người dùng",
    // Email người dùng
    email: authUser.email,
    // Ngày tham gia, nếu không có thì hiển thị "Không rõ"
    joinDate: authUser.joinDate || "Không rõ",
    // Avatar URL, nếu không có thì dùng ảnh placeholder từ Unsplash
    avatarUrl: authUser.avatarUrl || "https://images.unsplash.com/photo-1652841190565-b96e0acbae17"
  } : {
    // Dữ liệu mẫu khi chưa đăng nhập (để test giao diện)
    name: "Người Dùng Mẫu",
    email: "user@example.com",
    joinDate: "01/01/2024",
    avatarUrl: "https://images.unsplash.com/photo-1652841190565-b96e0acbae17"
  };


  /**
   * Dữ liệu mẫu cho lịch sử đặt lịch hẹn tư vấn
   * Trong thực tế, dữ liệu này sẽ được fetch từ API backend
   * Mỗi appointment có: id, counselor (tên chuyên viên), date (ngày hẹn), status (trạng thái)
   */
  const appointmentHistory = [
    { 
      id: 1, 
      counselor: "Chuyên viên Nguyễn Văn A", 
      date: "15/04/2025", 
      status: "Đã hoàn thành" 
    },
    { 
      id: 2, 
      counselor: "Chuyên viên Trần Thị B", 
      date: "10/05/2025", 
      status: "Đã xác nhận" 
    },
  ];

  /**
   * Dữ liệu mẫu cho lịch sử tham gia các chương trình phòng chống tệ nạn xã hội
   * Trong thực tế, dữ liệu này sẽ được fetch từ API backend
   * Mỗi program có: id, program (tên chương trình), participationDate (ngày tham gia)
   */
  const programHistory = [
    { 
      id: 1, 
      program: "Chiến dịch 'Nói không với Ma túy'", 
      participationDate: "20/06/2025" 
    },
  ];

  // Render giao diện người dùng
  return (
    <div className="space-y-8">
      {/* SECTION 1: Header với thông tin cơ bản của người dùng */}
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        {/* Container cho avatar */}
        <div className="flex justify-center items-center mb-4">
          <img  
            className="w-24 h-24 rounded-full object-cover border-4 border-primary" 
            alt="User avatar"
            src={user.avatarUrl} 
          />
        </div>
        
        {/* Tên người dùng với gradient text effect */}
        <h1 className="text-4xl font-bold gradient-text">{user.name}</h1>
        
        {/* Email người dùng */}
        <p className="text-lg text-slate-500">{user.email}</p>
        
        {/* Ngày tham gia hệ thống */}
        <p className="text-sm text-slate-400 mt-1">Tham gia từ: {user.joinDate}</p>
        
        {/* Button chỉnh sửa thông tin (chưa có chức năng) */}
        <Button variant="outline" className="mt-4 border-accent text-accent hover:bg-accent hover:text-white font-semibold">
          <Edit3 className="h-4 w-4 mr-2" /> Chỉnh Sửa Thông Tin
        </Button>
      </section>

      {/* SECTION 2: Grid layout cho 2 card hiển thị lịch sử */}
      <div className="grid md:grid-cols-2 gap-8">
        
        {/* CARD 1: Lịch sử đặt lịch hẹn tư vấn */}
        <Card className="light-theme-card">
          <CardHeader>
            <CardTitle className="text-2xl light-theme-card-header flex items-center">
              <CalendarCheck className="h-6 w-6 mr-3 text-primary" /> 
              Lịch Sử Đặt Lịch Hẹn
            </CardTitle>
          </CardHeader>
          <CardContent>
            {/* Kiểm tra có dữ liệu appointment hay không */}
            {appointmentHistory.length > 0 ? (
              <ul className="space-y-3">
                {/* Map qua từng appointment để hiển thị */}
                {appointmentHistory.map(appt => (
                  <li key={appt.id} className="p-3 bg-slate-50 border border-slate-200 rounded-md">
                    {/* Tên chuyên viên tư vấn */}
                    <p className="font-semibold text-slate-700">{appt.counselor}</p>
                    {/* Ngày hẹn và trạng thái */}
                    <p className="text-sm text-slate-500">
                      Ngày: {appt.date} - Trạng thái: {appt.status}
                    </p>
                  </li>
                ))}
              </ul>
            ) : (
              /* Hiển thị khi chưa có lịch sử đặt hẹn */
              <p className="text-slate-500">Chưa có lịch sử đặt lịch hẹn.</p>
            )}
          </CardContent>
        </Card>

        {/* CARD 2: Lịch sử tham gia chương trình */}
        <Card className="light-theme-card">
          <CardHeader>
            <CardTitle className="text-2xl light-theme-card-header flex items-center">
              <BookOpenCheck className="h-6 w-6 mr-3 text-accent" /> 
              Lịch Sử Tham Gia Chương Trình
            </CardTitle>
          </CardHeader>
          <CardContent>
            {/* Kiểm tra có dữ liệu program hay không */}
            {programHistory.length > 0 ? (
              <ul className="space-y-3">
                {/* Map qua từng program để hiển thị */}
                {programHistory.map(prog => (
                  <li key={prog.id} className="p-3 bg-slate-50 border border-slate-200 rounded-md">
                    {/* Tên chương trình */}
                    <p className="font-semibold text-slate-700">{prog.program}</p>
                    {/* Ngày tham gia */}
                    <p className="text-sm text-slate-500">
                      Ngày tham gia: {prog.participationDate}
                    </p>
                  </li>
                ))}
              </ul>
            ) : (
              /* Hiển thị khi chưa có lịch sử tham gia */
              <p className="text-slate-500">Chưa có lịch sử tham gia chương trình.</p>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

// Export component để có thể import ở các file khác
export default UserProfilePage;