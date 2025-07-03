// Import các thư viện và component cần thiết
import React from 'react'; // React core library
import { useParams, Link } from 'react-router-dom'; // Hook để lấy params từ URL và component Link cho navigation
import { Button } from '@/components/ui/button'; // Component button tùy chỉnh
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'; // Các component card để hiển thị thông tin
import { ArrowLeft, BookUser, CalendarClock, Users, CheckCircle } from 'lucide-react'; // Icons từ thư viện Lucide React
import { motion } from 'framer-motion'; // Thư viện animation cho React

// Dữ liệu mẫu cho chi tiết khóa học - TODO: Thay thế bằng API call thực tế
const placeholderCourses = {
  // Khóa học 1: Dành cho học sinh phổ thông
  1: { 
    title: "Nhận thức về Ma túy cho Học sinh", 
    audience: "Học sinh", 
    duration: "4 tuần", 
    instructor: "ThS. Nguyễn Thị Lan", // Thông tin giảng viên
    image: "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80", 
    description: "Khóa học cung cấp kiến thức toàn diện về các loại ma túy phổ biến, tác hại của chúng đối với sức khỏe và xã hội. Học viên sẽ được trang bị kỹ năng nhận biết các dấu hiệu của người sử dụng ma túy và cách phòng tránh hiệu quả.", 
    modules: ["Tổng quan về ma túy", "Tác hại của ma túy tổng hợp", "Kỹ năng từ chối", "Tìm kiếm sự giúp đỡ"] // Danh sách các module học
  },
  // Khóa học 2: Dành cho sinh viên đại học
  2: { 
    title: "Kỹ năng Phòng tránh Ma túy cho Sinh viên", 
    audience: "Sinh viên", 
    duration: "6 tuần", 
    instructor: "TS. Trần Văn Minh", 
    image: "https://images.unsplash.com/photo-1543269865-cbf427effbad?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", 
    description: "Khóa học tập trung vào việc xây dựng các kỹ năng mềm cần thiết cho sinh viên để đối phó với các tình huống liên quan đến ma túy trong môi trường đại học và xã hội. Các chủ đề bao gồm quản lý căng thẳng, ra quyết định, và xây dựng lối sống lành mạnh.", 
    modules: ["Ma túy và giới trẻ", "Áp lực đồng trang lứa", "Xây dựng mạng lưới hỗ trợ", "Nói không với ma túy"] 
  },
};

// Component chính cho trang chi tiết khóa học
const CourseDetailPage = () => {
  // Lấy ID khóa học từ URL parameters
  const { id } = useParams();
  // Tìm thông tin khóa học dựa trên ID
  const course = placeholderCourses[id]; 

  // Xử lý trường hợp không tìm thấy khóa học
  if (!course) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy khóa học</h1>
        {/* Link quay về trang danh sách khóa học */}
        <Link to="/courses">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách khóa học</Button>
        </Link>
      </div>
    );
  }

  // Định nghĩa animation variants cho Framer Motion
  const fadeIn = {
    hidden: { opacity: 0, y: 20 }, // Trạng thái ẩn: trong suốt và dịch chuyển xuống
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } } // Trạng thái hiển thị: mờ dần và di chuyển lên
  };
  
  return (
    // Container chính với animation fade-in và spacing giữa các section
    <motion.div 
      className="space-y-8"
      initial="hidden" // Trạng thái ban đầu
      animate="visible" // Trạng thái animation đích
      variants={fadeIn} // Sử dụng animation variants đã định nghĩa
    >
      {/* Hero section với hình ảnh background và overlay */}
      <div className="relative shadow-xl rounded-lg overflow-hidden">
        {/* Hình ảnh background của khóa học */}
        <img  
          src={course.image} 
          alt={course.title} 
          className="w-full h-64 md:h-96 object-cover" // Responsive height và object-cover để maintain aspect ratio
        />
        {/* Gradient overlay để tạo contrast cho text */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>
        {/* Content overlay với thông tin khóa học */}
        <div className="absolute bottom-0 left-0 p-6 md:p-10">
          {/* Tiêu đề khóa học với animation riêng */}
          <motion.h1 variants={fadeIn} className="text-3xl md:text-5xl font-bold text-white mb-2">
            {course.title}
          </motion.h1>
          {/* Thông tin đối tượng với animation */}
          <motion.p variants={fadeIn} className="text-lg text-primary-foreground opacity-90">
            {course.audience}
          </motion.p>
        </div>
      </div>

      {/* Navigation link quay về trang danh sách */}
      <Link to="/courses" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Trở về danh sách khóa học
      </Link>

      {/* Main content layout với grid responsive */}
      <div className="grid md:grid-cols-3 gap-8">
        {/* Left column: Mô tả và nội dung khóa học (2/3 width trên desktop) */}
        <motion.div variants={fadeIn} className="md:col-span-2 space-y-6">
          {/* Card mô tả khóa học */}
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-2xl light-theme-card-header">Mô tả khóa học</CardTitle>
            </CardHeader>
            <CardContent>
              {/* Mô tả chi tiết với leading-relaxed để dễ đọc */}
              <p className="light-theme-card-content leading-relaxed">{course.description}</p>
            </CardContent>
          </Card>

          {/* Card hiển thị nội dung/module của khóa học */}
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-2xl light-theme-card-header">Nội dung khóa học</CardTitle>
            </CardHeader>
            <CardContent>
              {/* Danh sách các module với icon checkmark */}
              <ul className="space-y-3">
                {course.modules.map((module, index) => (
                  <li key={index} className="flex items-start light-theme-card-content">
                    {/* Icon checkmark với styling accent */}
                    <CheckCircle className="h-5 w-5 mr-3 light-theme-text-accent flex-shrink-0 mt-1" />
                    <span>{module}</span>
                  </li>
                ))}
              </ul>
            </CardContent>
          </Card>
        </motion.div>

        {/* Right column: Thông tin chi tiết và đăng ký (1/3 width trên desktop) */}
        <motion.div variants={fadeIn} className="space-y-6">
          {/* Card thông tin chi tiết khóa học */}
          <Card className="light-theme-card">
            <CardHeader>
              <CardTitle className="text-xl light-theme-card-header">Thông tin chi tiết</CardTitle>
            </CardHeader>
            <CardContent className="space-y-3">
              {/* Thông tin giảng viên */}
              <div className="flex items-center light-theme-card-content">
                <BookUser className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Giảng viên: {course.instructor}</span>
              </div>
              {/* Thông tin thời lượng */}
              <div className="flex items-center light-theme-card-content">
                <CalendarClock className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Thời lượng: {course.duration}</span>
              </div>
              {/* Thông tin đối tượng học viên */}
              <div className="flex items-center light-theme-card-content">
                <Users className="h-5 w-5 mr-3 light-theme-text-primary" />
                <span>Đối tượng: {course.audience}</span>
              </div>
            </CardContent>
            <CardFooter>
              {/* Button call-to-action đăng ký khóa học */}
              <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90 text-lg py-3">
                Đăng Ký Khóa Học
              </Button>
            </CardFooter>
          </Card>
        </motion.div>
      </div>
    </motion.div>
  );
};

// Export component để sử dụng trong router
export default CourseDetailPage;