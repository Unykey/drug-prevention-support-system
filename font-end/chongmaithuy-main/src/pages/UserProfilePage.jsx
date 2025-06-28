import React from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { User, CalendarCheck, BookOpenCheck, Edit3 } from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext'; // Import useAuth

const UserProfilePage = () => {
  const { user: authUser } = useAuth(); // Get authenticated user

  // Use authenticated user data if available, otherwise fallback to placeholder
  const user = authUser ? {
    name: authUser.name || "Người dùng",
    email: authUser.email,
    joinDate: authUser.joinDate || "Không rõ", // Assuming joinDate might be part of user object
    avatarUrl: authUser.avatarUrl || "https://images.unsplash.com/photo-1652841190565-b96e0acbae17" // Placeholder avatar
  } : {
    name: "Người Dùng Mẫu",
    email: "user@example.com",
    joinDate: "01/01/2024",
    avatarUrl: "https://images.unsplash.com/photo-1652841190565-b96e0acbae17"
  };


  const appointmentHistory = [
    { id: 1, counselor: "Chuyên viên Nguyễn Văn A", date: "15/04/2025", status: "Đã hoàn thành" },
    { id: 2, counselor: "Chuyên viên Trần Thị B", date: "10/05/2025", status: "Đã xác nhận" },
  ];

  const programHistory = [
    { id: 1, program: "Chiến dịch 'Nói không với Ma túy'", participationDate: "20/06/2025" },
  ];

  return (
    <div className="space-y-8">
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <div className="flex justify-center items-center mb-4">
          <img  
            className="w-24 h-24 rounded-full object-cover border-4 border-primary" 
            alt="User avatar"
           src={user.avatarUrl} />
        </div>
        <h1 className="text-4xl font-bold gradient-text">{user.name}</h1>
        <p className="text-lg text-slate-500">{user.email}</p>
        <p className="text-sm text-slate-400 mt-1">Tham gia từ: {user.joinDate}</p>
        <Button variant="outline" className="mt-4 border-accent text-accent hover:bg-accent hover:text-white font-semibold">
          <Edit3 className="h-4 w-4 mr-2" /> Chỉnh Sửa Thông Tin
        </Button>
      </section>

      <div className="grid md:grid-cols-2 gap-8">
        <Card className="light-theme-card">
          <CardHeader>
            <CardTitle className="text-2xl light-theme-card-header flex items-center">
              <CalendarCheck className="h-6 w-6 mr-3 text-primary" /> Lịch Sử Đặt Lịch Hẹn
            </CardTitle>
          </CardHeader>
          <CardContent>
            {appointmentHistory.length > 0 ? (
              <ul className="space-y-3">
                {appointmentHistory.map(appt => (
                  <li key={appt.id} className="p-3 bg-slate-50 border border-slate-200 rounded-md">
                    <p className="font-semibold text-slate-700">{appt.counselor}</p>
                    <p className="text-sm text-slate-500">Ngày: {appt.date} - Trạng thái: {appt.status}</p>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-slate-500">Chưa có lịch sử đặt lịch hẹn.</p>
            )}
          </CardContent>
        </Card>

        <Card className="light-theme-card">
          <CardHeader>
            <CardTitle className="text-2xl light-theme-card-header flex items-center">
              <BookOpenCheck className="h-6 w-6 mr-3 text-accent" /> Lịch Sử Tham Gia Chương Trình
            </CardTitle>
          </CardHeader>
          <CardContent>
            {programHistory.length > 0 ? (
              <ul className="space-y-3">
                {programHistory.map(prog => (
                  <li key={prog.id} className="p-3 bg-slate-50 border border-slate-200 rounded-md">
                    <p className="font-semibold text-slate-700">{prog.program}</p>
                    <p className="text-sm text-slate-500">Ngày tham gia: {prog.participationDate}</p>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-slate-500">Chưa có lịch sử tham gia chương trình.</p>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default UserProfilePage;