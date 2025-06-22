// BookAppointmentPage.jsx: Trang đặt lịch hẹn với chuyên viên tư vấn
import React, { useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea'; 
import { Calendar as CalendarIcon, ArrowLeft, Send, UserCircle, Phone, MessageSquare } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';
import { motion } from 'framer-motion';

// Dữ liệu giả lập danh sách chuyên viên tư vấn
const counselorsData = {
  1: { name: "Chuyên viên Nguyễn Văn A", expertise: "Tư vấn cai nghiện, hỗ trợ tâm lý", image: "https://images.unsplash.com/photo-1557862921-37829c790f19?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80" },
  2: { name: "Chuyên viên Trần Thị B", expertise: "Tư vấn gia đình, phòng ngừa tái nghiện", image: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=764&q=80" },
  3: { name: "Chuyên viên Lê Văn C", expertise: "Tư vấn cho thanh thiếu niên, kỹ năng sống", image: "https://images.unsplash.com/photo-1521119989659-a83eee488004?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=723&q=80" },
};

// Khung thời gian có sẵn để chọn lịch hẹn
const availableTimeSlots = [
  "09:00 - 10:00", "10:00 - 11:00", "14:00 - 15:00", "15:00 - 16:00"
];

const BookAppointmentPage = () => {
  // Lấy counselorId từ URL params và truy xuất chuyên viên tương ứng
  const { counselorId } = useParams();
  const counselor = counselorsData[counselorId];
  // Khởi tạo điều hướng và toast cho thông báo
  const navigate = useNavigate();
  const { toast } = useToast();

  // Các state quản lý dữ liệu form
  const [selectedDate, setSelectedDate] = useState(null); // ngày được chọn
  const [selectedTime, setSelectedTime] = useState('');    // khung giờ được chọn
  const [name, setName] = useState('');                    // họ tên người đặt
  const [phone, setPhone] = useState('');                  // số điện thoại
  const [notes, setNotes] = useState('');                  // ghi chú (tuỳ chọn)
  const [isLoading, setIsLoading] = useState(false);       // trạng thái loading khi submit

  // Nếu không tìm thấy chuyên viên, hiển thị thông báo và nút quay lại
  if (!counselor) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy chuyên viên tư vấn</h1>
        <Link to="/appointments">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách</Button>
        </Link>
      </div>
    );
  }
  
  // Hàm xử lý khi người dùng submit form đặt lịch
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Kiểm tra nhập liệu bắt buộc: ngày, giờ, tên, phone
    if (!selectedDate || !selectedTime || !name || !phone) {
        toast({
            title: "Thông tin chưa đầy đủ",
            description: "Vui lòng chọn ngày, giờ và điền đủ thông tin cá nhân.",
            variant: "destructive",
        });
        return;
    }
    // Hiển thị loading, giả lập gửi request
    setIsLoading(true);
    await new Promise(resolve => setTimeout(resolve, 1500));
    setIsLoading(false);
    // Thông báo thành công và điều hướng về profile
    toast({
        title: "Đặt lịch thành công!",
        description: `Bạn đã đặt lịch hẹn với ${counselor.name} vào ${selectedTime} ngày ${selectedDate}.`,
        variant: "default",
    });
    navigate('/profile'); 
  };

  // Cấu hình hiệu ứng fadeIn cho các phần tử khi hiển thị
  const fadeIn = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } }
  };

  return (
    <motion.div 
      className="max-w-3xl mx-auto space-y-8"
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      {/* Liên kết quay lại danh sách chuyên viên */}
      <Link to="/appointments" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Chọn chuyên viên khác
      </Link>

      {/* Thẻ Card hiển thị thông tin chuyên viên và form đặt lịch */}
      <Card className="light-theme-card shadow-xl">
        <CardHeader className="text-center">
          {/* Ảnh đại diện chuyên viên */}
          <img src={counselor.image} alt={counselor.name} className="w-24 h-24 rounded-full object-cover mx-auto mb-4 border-2 border-accent"/>
          <CardTitle className="text-3xl font-bold gradient-text">Đặt Lịch Hẹn</CardTitle>
          <CardDescription className="light-theme-card-description">
            Với chuyên viên: <span className="font-semibold light-theme-text-accent">{counselor.name}</span>
            <br/>Chuyên môn: {counselor.expertise}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Chọn ngày và giờ */}
            <div className="grid sm:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="date" className="light-theme-text-default flex items-center"><CalendarIcon className="h-4 w-4 mr-2 light-theme-text-primary"/> Chọn Ngày</Label>
                <Input 
                    type="date" 
                    id="date" 
                    value={selectedDate || ""}
                    onChange={(e) => setSelectedDate(e.target.value)}
                    className="light-theme-input"
                    min={new Date().toISOString().split("T")[0]}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="time" className="light-theme-text-default flex items-center"><CalendarIcon className="h-4 w-4 mr-2 light-theme-text-primary"/> Chọn Giờ</Label>
                <select 
                    id="time"
                    value={selectedTime}
                    onChange={(e) => setSelectedTime(e.target.value)}
                    required
                    className="w-full h-10 rounded-md border border-input bg-white px-3 py-2 text-sm text-slate-900 ring-offset-background focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 light-theme-input"
                >
                    <option value="" disabled className="text-slate-400">Chọn khung giờ</option>
                    {availableTimeSlots.map(slot => <option key={slot} value={slot} className="text-slate-900">{slot}</option>)}
                </select>
              </div>
            </div>

            {/* Nhập thông tin cá nhân */}
            <div className="space-y-2">
              <Label htmlFor="name" className="light-theme-text-default flex items-center"><UserCircle className="h-4 w-4 mr-2 light-theme-text-primary"/> Họ và Tên</Label>
              <Input id="name" type="text" placeholder="Nguyễn Văn A" value={name} onChange={e => setName(e.target.value)} required className="light-theme-input" />
            </div>
            <div className="space-y-2">
              <Label htmlFor="phone" className="light-theme-text-default flex items-center"><Phone className="h-4 w-4 mr-2 light-theme-text-primary"/> Số Điện Thoại</Label>
              <Input id="phone" type="tel" placeholder="09xxxxxxxx" value={phone} onChange={e => setPhone(e.target.value)} required className="light-theme-input" />
            </div>
            <div className="space-y-2">
              <Label htmlFor="notes" className="light-theme-text-default flex items-center"><MessageSquare className="h-4 w-4 mr-2 light-theme-text-primary"/> Ghi Chú (nếu có)</Label>
              <Textarea id="notes" placeholder="Nội dung vắn tắt bạn muốn trao đổi..." value={notes} onChange={e => setNotes(e.target.value)} className="light-theme-input min-h-[100px]" />
            </div>
            
            {/* Nút Xác nhận đặt lịch hiển thị loading khi đang gửi */}
            <Button type="submit" className="w-full light-theme-button-primary text-lg py-3" disabled={isLoading}>
              {isLoading ? 'Đang xử lý...' : (<><Send className="h-5 w-5 mr-2"/> Xác Nhận Đặt Lịch</>)}
            </Button>
          </form>
        </CardContent>
      </Card>
    </motion.div>
  );
};

export default BookAppointmentPage;