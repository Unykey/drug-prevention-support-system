import React from 'react';
import { Button } from '@/components/ui/button';
import { CalendarDays, UserCheck } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Link } from 'react-router-dom';

const counselors = [
  { id: 1, name: "Chuyên viên Nguyễn Văn A", expertise: "Tư vấn cai nghiện, hỗ trợ tâm lý", available: "Thứ 2, Thứ 4, Thứ 6", image: "https://images.unsplash.com/photo-1557862921-37829c790f19?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80" },
  { id: 2, name: "Chuyên viên Trần Thị B", expertise: "Tư vấn gia đình, phòng ngừa tái nghiện", available: "Thứ 3, Thứ 5, Sáng Thứ 7", image: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=764&q=80" },
  { id: 3, name: "Chuyên viên Lê Văn C", expertise: "Tư vấn cho thanh thiếu niên, kỹ năng sống", available: "Chiều Thứ 2, Cả ngày Thứ 7", image: "https://images.unsplash.com/photo-1521119989659-a83eee488004?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=723&q=80" },
];

const AppointmentsPage = () => {
  return (
    <div className="space-y-8">
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <h1 className="text-4xl font-bold mb-4 gradient-text">Đặt Lịch Hẹn Tư Vấn Trực Tuyến</h1>
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Kết nối với các chuyên viên tư vấn giàu kinh nghiệm để nhận được sự hỗ trợ và định hướng kịp thời.
        </p>
      </section>

      <section>
        <h2 className="text-3xl font-semibold text-center mb-8 light-theme-text-default">Chọn Chuyên Viên Tư Vấn</h2>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {counselors.map(counselor => (
            <Card key={counselor.id} className="light-theme-card hover:shadow-accent/15 transition-shadow duration-300">
              <CardHeader className="flex flex-col items-center text-center sm:flex-row sm:text-left sm:items-start gap-4">
                <img  
                  className="w-24 h-24 rounded-full object-cover border-2 border-accent" 
                  alt={counselor.name}
                 src={counselor.image} />
                <div className="flex-1">
                  <CardTitle className="text-xl light-theme-card-header">{counselor.name}</CardTitle>
                  <CardDescription className="light-theme-text-accent mt-1">{counselor.expertise}</CardDescription>
                </div>
              </CardHeader>
              <CardContent>
                <p className="text-sm light-theme-card-content mb-1"><span className="font-semibold text-slate-700">Lịch làm việc:</span> {counselor.available}</p>
                <p className="text-sm light-theme-card-content mb-4 line-clamp-2">Mô tả ngắn về kinh nghiệm và phương pháp tư vấn của chuyên viên. Chuyên viên có nhiều năm kinh nghiệm trong lĩnh vực tư vấn tâm lý và cai nghiện...</p>
                <Link to={`/book-appointment/${counselor.id}`}>
                  <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90">
                    <CalendarDays className="h-5 w-5 mr-2" /> Xem Lịch & Đặt Hẹn
                  </Button>
                </Link>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      <section className="mt-12 p-6 light-theme-card rounded-lg">
        <h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
          <UserCheck className="h-6 w-6 mr-3 light-theme-text-primary"/> Quy trình đặt lịch
        </h2>
        <ol className="list-decimal list-inside light-theme-card-content space-y-2">
          <li>Chọn chuyên viên tư vấn phù hợp với nhu cầu của bạn.</li>
          <li>Xem lịch làm việc còn trống của chuyên viên.</li>
          <li>Chọn ngày giờ tư vấn mong muốn và xác nhận thông tin.</li>
          <li>Bạn sẽ nhận được email xác nhận lịch hẹn và hướng dẫn tham gia buổi tư vấn trực tuyến.</li>
        </ol>
      </section>
    </div>
  );
};

export default AppointmentsPage;