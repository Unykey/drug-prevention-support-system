// ProgramDetailPage.jsx: Trang chi tiết chương trình phòng chống ma túy
import React from 'react'; // React core cho functional component
import { useParams, Link } from 'react-router-dom'; // useParams: lấy ID từ URL params, Link: điều hướng SPA
import { Button } from '@/components/ui/button'; // Component button tùy chỉnh với styling nhất quán
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'; // Card components để tạo layout có cấu trúc
import { ArrowLeft, CalendarDays, MapPin, Users, Info, CheckSquare } from 'lucide-react'; // Icons trực quan cho UI
import { motion } from 'framer-motion'; // Thư viện animation cho smooth transitions và effects

// Mock data cho các chương trình - trong thực tế sẽ được thay thế bằng API calls
// Cấu trúc data bao gồm tất cả thông tin cần thiết cho việc hiển thị chi tiết
const placeholderPrograms = {
  // Chương trình 1: Chiến dịch truyền thông cộng đồng
  1: { 
    title: "Chiến dịch 'Nói không với Ma túy'", 
    type: "Truyền thông cộng đồng", // Loại chương trình để phân loại và styling
    date: "15/06/2025 - 30/06/2025", // Thời gian diễn ra chương trình
    location: "Trung tâm Văn hóa Quận 1, TP.HCM", // Địa điểm tổ chức
    image: "https://images.unsplash.com/photo-1573167507997-41830a3f7575?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1169&q=80", // Hình ảnh banner
    description: "Một chiến dịch sâu rộng nhằm nâng cao nhận thức của cộng đồng về tác hại của ma túy và kêu gọi mọi người cùng chung tay phòng chống. Chương trình bao gồm các buổi nói chuyện, triển lãm, và các hoạt động tương tác.", // Mô tả chi tiết
    organizer: "Tổ chức Tình nguyện Vì Cộng Đồng Xanh", // Đơn vị tổ chức
    targetAudience: "Mọi lứa tuổi, đặc biệt là thanh thiếu niên và phụ huynh.", // Đối tượng tham gia
    activities: ["Triển lãm tranh ảnh về tác hại ma túy", "Buổi nói chuyện với chuyên gia", "Giao lưu với người cai nghiện thành công", "Phát tờ rơi và tài liệu tuyên truyền"] // Danh sách hoạt động cụ thể
  },
  // Chương trình 2: Workshop giáo dục kỹ năng sống
  2: { 
    title: "Workshop Kỹ năng Sống cho Thanh Thiếu Niên", 
    type: "Giáo dục", // Loại chương trình giáo dục
    date: "Thứ 7 hàng tuần, 9:00 - 11:00", // Lịch trình định kỳ
    location: "Trường THPT Nguyễn Du", // Địa điểm tại trường học
    image: "https://images.unsplash.com/photo-1552664730-d307ca884978?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
    description: "Workshop hàng tuần trang bị cho các em học sinh những kỹ năng sống cần thiết để tự tin đối mặt với các thử thách trong cuộc sống, bao gồm kỹ năng từ chối, giải quyết vấn đề, và xây dựng mối quan hệ lành mạnh.",
    organizer: "Hội Phụ huynh Học sinh trường Nguyễn Du",
    targetAudience: "Học sinh cấp 3", // Đối tượng cụ thể
    activities: ["Thảo luận nhóm", "Trò chơi tương tác", "Bài tập tình huống", "Chia sẻ kinh nghiệm"] // Các hoạt động học tập
  },
};

// Component chính hiển thị chi tiết chương trình
const ProgramDetailPage = () => {
  // Lấy ID chương trình từ URL parameters (ví dụ: /programs/1 -> id = "1")
  const { id } = useParams();
  
  // Tìm chương trình tương ứng với ID từ mock data
  // Trong thực tế sẽ thay bằng API call để fetch data từ backend
  const program = placeholderPrograms[id]; 

  // Error handling: Hiển thị thông báo nếu không tìm thấy chương trình với ID đã cho
  if (!program) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy chương trình</h1>
        {/* Link quay về danh sách chương trình khi không tìm thấy */}
        <Link to="/programs">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách chương trình</Button>
        </Link>
      </div>
    );
  }
  
  // Cấu hình animation fade-in cho các elements với framer-motion
  // Tạo hiệu ứng mượt mà khi trang load và các phần tử xuất hiện
  const fadeIn = {
    hidden: { opacity: 0, y: 20 }, // Trạng thái ban đầu: trong suốt và dịch xuống 20px
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } } // Trạng thái cuối: hiện rõ và về vị trí gốc trong 0.5s
  };

  return (
    // Container chính với animation fade-in và spacing giữa các sections
    <motion.div 
      className="space-y-8"
      initial="hidden" // Bắt đầu với trạng thái hidden
      animate="visible" // Animate đến trạng thái visible
      variants={fadeIn} // Sử dụng cấu hình animation đã định nghĩa
    >
      {/* Hero section - Banner image với overlay text */}
      <div className="relative shadow-xl rounded-lg overflow-hidden">
        {/* Hình ảnh banner responsive với object-cover để giữ tỷ lệ */}
        <img  src={program.image} alt={program.title} className="w-full h-64 md:h-96 object-cover" />
        
        {/* Overlay gradient để text dễ đọc trên background image */}
        {/* Gradient từ đen đậm (bottom) đến trong suốt (top) */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>
        
        {/* Content overlay - thông tin chính hiển thị trên banner */}
        <div className="absolute bottom-0 left-0 p-6 md:p-10">
          {/* Badge hiển thị loại chương trình với animation riêng */}
          <motion.p variants={fadeIn} className="text-md text-accent-foreground opacity-90 uppercase tracking-wider mb-1 bg-accent/70 px-2 py-1 rounded w-fit">{program.type}</motion.p>
          
          {/* Tiêu đề chính với typography lớn và responsive */}
          <motion.h1 variants={fadeIn} className="text-3xl md:text-5xl font-bold text-white mb-2">{program.title}</motion.h1>
        </div>
      </div>

      {/* Navigation link trở về danh sách chương trình */}
      {/* inline-flex để icon và text cùng hàng, hover effect để UX tốt hơn */}
      <Link to="/programs" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Trở về danh sách chương trình
      </Link>

      {/* Main content area - Grid layout responsive */}
      {/* md:grid-cols-3: 3 cột trên màn hình medium+, 1 cột trên mobile */}
      <div className="grid md:grid-cols-3 gap-8">
        
        {/* Left column - nội dung chính (2/3 width trên desktop) */}
        <motion.div variants={fadeIn} className="md:col-span-2 space-y-6">
          
          {/* Card mô tả chương trình */}
          <Card className="light-theme-card">
            <CardHeader>
              {/* Header với icon Info để làm rõ nội dung */}
              <CardTitle className="text-2xl light-theme-card-header flex items-center">
                <Info className="h-6 w-6 mr-3 light-theme-text-primary" /> Mô tả chương trình
              </CardTitle>
            </CardHeader>
            <CardContent>
              {/* Nội dung mô tả với line-height thoải mái để dễ đọc */}
              <p className="light-theme-card-content leading-relaxed">{program.description}</p>
            </CardContent>
          </Card>

          {/* Card danh sách hoạt động */}
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-2xl light-theme-card-header">Hoạt động chính</CardTitle>
            </CardHeader>
            <CardContent>
              {/* List các hoạt động với icons checkbox */}
              <ul className="space-y-3">
                {program.activities.map((activity, index) => (
                  <li key={index} className="flex items-start light-theme-card-content">
                    {/* Icon checkbox với flex-shrink-0 để không bị co lại */}
                    <CheckSquare className="h-5 w-5 mr-3 light-theme-text-accent flex-shrink-0 mt-1" />
                    <span>{activity}</span>
                  </li>
                ))}
              </ul>
            </CardContent>
          </Card>
        </motion.div>

        {/* Right sidebar - thông tin chi tiết và CTA (1/3 width trên desktop) */}
        <motion.div variants={fadeIn} className="space-y-6">
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-xl light-theme-card-header">Thông tin chi tiết</CardTitle>
            </CardHeader>
            
            {/* Card content với các thông tin chi tiết có icon */}
            <CardContent className="space-y-3">
              {/* Thời gian với icon lịch */}
              <div className="flex items-center light-theme-card-content">
                <CalendarDays className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Thời gian: {program.date}</span>
              </div>
              
              {/* Địa điểm với icon bản đồ */}
              <div className="flex items-center light-theme-card-content">
                <MapPin className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Địa điểm: {program.location}</span>
              </div>
              
              {/* Đối tượng tham gia với icon người dùng */}
              <div className="flex items-center light-theme-card-content">
                <Users className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Đối tượng: {program.targetAudience}</span>
              </div>
              
              {/* Đơn vị tổ chức với icon người dùng */}
               <div className="flex items-center light-theme-card-content">
                <Users className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Đơn vị tổ chức: {program.organizer}</span>
              </div>
            </CardContent>
            
            {/* Footer với CTA button đăng ký */}
            <CardFooter>
              {/* Button full-width với styling nổi bật và hover effect */}
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

// Export component ProgramDetailPage làm default export
// Component này sẽ được sử dụng trong routing để hiển thị chi tiết chương trình khi user click vào một chương trình cụ thể
export default ProgramDetailPage;