import React from 'react';
import { useParams, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { ArrowLeft, CalendarDays, MapPin, Users, Info, CheckSquare } from 'lucide-react';
import { motion } from 'framer-motion';

const placeholderPrograms = {
  1: { 
    title: "Chiến dịch 'Nói không với Ma túy'", 
    type: "Truyền thông cộng đồng", 
    date: "15/06/2025 - 30/06/2025", 
    location: "Trung tâm Văn hóa Quận 1, TP.HCM",
    image: "https://images.unsplash.com/photo-1573167507997-41830a3f7575?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1169&q=80",
    description: "Một chiến dịch sâu rộng nhằm nâng cao nhận thức của cộng đồng về tác hại của ma túy và kêu gọi mọi người cùng chung tay phòng chống. Chương trình bao gồm các buổi nói chuyện, triển lãm, và các hoạt động tương tác.",
    organizer: "Tổ chức Tình nguyện Vì Cộng Đồng Xanh",
    targetAudience: "Mọi lứa tuổi, đặc biệt là thanh thiếu niên và phụ huynh.",
    activities: ["Triển lãm tranh ảnh về tác hại ma túy", "Buổi nói chuyện với chuyên gia", "Giao lưu với người cai nghiện thành công", "Phát tờ rơi và tài liệu tuyên truyền"]
  },
  2: { 
    title: "Workshop Kỹ năng Sống cho Thanh Thiếu Niên", 
    type: "Giáo dục", 
    date: "Thứ 7 hàng tuần, 9:00 - 11:00", 
    location: "Trường THPT Nguyễn Du",
    image: "https://images.unsplash.com/photo-1552664730-d307ca884978?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
    description: "Workshop hàng tuần trang bị cho các em học sinh những kỹ năng sống cần thiết để tự tin đối mặt với các thử thách trong cuộc sống, bao gồm kỹ năng từ chối, giải quyết vấn đề, và xây dựng mối quan hệ lành mạnh.",
    organizer: "Hội Phụ huynh Học sinh trường Nguyễn Du",
    targetAudience: "Học sinh cấp 3",
    activities: ["Thảo luận nhóm", "Trò chơi tương tác", "Bài tập tình huống", "Chia sẻ kinh nghiệm"]
  },
};

const ProgramDetailPage = () => {
  const { id } = useParams();
  const program = placeholderPrograms[id]; 

  if (!program) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy chương trình</h1>
        <Link to="/programs">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách chương trình</Button>
        </Link>
      </div>
    );
  }
  
  const fadeIn = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } }
  };

  return (
    <motion.div 
      className="space-y-8"
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      <div className="relative shadow-xl rounded-lg overflow-hidden">
        <img  src={program.image} alt={program.title} className="w-full h-64 md:h-96 object-cover" />
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>
        <div className="absolute bottom-0 left-0 p-6 md:p-10">
          <motion.p variants={fadeIn} className="text-md text-accent-foreground opacity-90 uppercase tracking-wider mb-1 bg-accent/70 px-2 py-1 rounded w-fit">{program.type}</motion.p>
          <motion.h1 variants={fadeIn} className="text-3xl md:text-5xl font-bold text-white mb-2">{program.title}</motion.h1>
        </div>
      </div>

      <Link to="/programs" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Trở về danh sách chương trình
      </Link>

      <div className="grid md:grid-cols-3 gap-8">
        <motion.div variants={fadeIn} className="md:col-span-2 space-y-6">
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-2xl light-theme-card-header flex items-center"><Info className="h-6 w-6 mr-3 light-theme-text-primary" /> Mô tả chương trình</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="light-theme-card-content leading-relaxed">{program.description}</p>
            </CardContent>
          </Card>

          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-2xl light-theme-card-header">Hoạt động chính</CardTitle>
            </CardHeader>
            <CardContent>
              <ul className="space-y-3">
                {program.activities.map((activity, index) => (
                  <li key={index} className="flex items-start light-theme-card-content">
                    <CheckSquare className="h-5 w-5 mr-3 light-theme-text-accent flex-shrink-0 mt-1" />
                    <span>{activity}</span>
                  </li>
                ))}
              </ul>
            </CardContent>
          </Card>
        </motion.div>

        <motion.div variants={fadeIn} className="space-y-6">
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-xl light-theme-card-header">Thông tin chi tiết</CardTitle>
            </CardHeader>
            <CardContent className="space-y-3">
              <div className="flex items-center light-theme-card-content">
                <CalendarDays className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Thời gian: {program.date}</span>
              </div>
              <div className="flex items-center light-theme-card-content">
                <MapPin className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Địa điểm: {program.location}</span>
              </div>
              <div className="flex items-center light-theme-card-content">
                <Users className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Đối tượng: {program.targetAudience}</span>
              </div>
               <div className="flex items-center light-theme-card-content">
                <Users className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Đơn vị tổ chức: {program.organizer}</span>
              </div>
            </CardContent>
            <CardFooter>
              <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90 text-lg py-3">
                Đăng Ký Tham Gia
              </Button>
            </CardFooter>
          </Card>
        </motion.div>
      </div>
    </motion.div>
  );
};

export default ProgramDetailPage;