// Import các thư viện và component cần thiết
import React from 'react'; // React core library
import { Input } from "@/components/ui/input"; // Component input tùy chỉnh cho tìm kiếm
import { Button } from "@/components/ui/button"; // Component button tùy chỉnh
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"; // Các component card để hiển thị khóa học
import { Search, BookMarked, ArrowRight } from "lucide-react"; // Icons từ thư viện Lucide React
import { Link } from "react-router-dom"; // Component Link để navigation giữa các trang

// Dữ liệu mẫu cho các khóa học phòng chống ma túy
// TODO: Thay thế bằng dữ liệu từ API backend Spring Boot
const placeholderCourses = [
  { 
    id: 1, 
    title: "Nhận thức về Ma túy cho Học sinh", 
    audience: "Học sinh", // Đối tượng học viên mục tiêu
    duration: "4 tuần", // Thời lượng khóa học
    image: "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80" // Hình ảnh minh họa từ Unsplash
  },
  { 
    id: 2, 
    title: "Kỹ năng Phòng tránh Ma túy cho Sinh viên", 
    audience: "Sinh viên", 
    duration: "6 tuần", 
    image: "https://images.unsplash.com/photo-1543269865-cbf427effbad?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
  },
  { 
    id: 3, 
    title: "Hướng dẫn Phụ huynh về Phòng chống Ma túy", 
    audience: "Phụ huynh", 
    duration: "3 tuần", 
    image: "https://images.unsplash.com/photo-1588075592446-289101424704?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
  },
  { 
    id: 4, 
    title: "Nâng cao Nghiệp vụ cho Giáo viên", 
    audience: "Giáo viên", 
    duration: "5 tuần", 
    image: "https://images.unsplash.com/photo-1517048676732-d65bc937f952?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
  },
];

// Component chính cho trang hiển thị danh sách khóa học
const CoursesPage = () => {
  return (
    // Container chính với khoảng cách giữa các section
    <div className="space-y-8">
      {/* Section header với tiêu đề và mô tả trang */}
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        {/* Tiêu đề chính với gradient styling */}
        <h1 className="text-4xl font-bold mb-4 gradient-text">Khóa Học Phòng Chống Ma Túy</h1>
        {/* Mô tả mục đích của trang */}
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Trang bị kiến thức và kỹ năng cần thiết để bảo vệ bản thân và cộng đồng khỏi hiểm họa ma túy.
        </p>
      </section>

      {/* Section tìm kiếm khóa học */}
      <section>
        {/* Container cho thanh tìm kiếm với layout flex và max-width */}
        <div className="flex gap-2 mb-8 max-w-xl mx-auto">
          {/* Input tìm kiếm với placeholder hướng dẫn user */}
          <Input 
            type="text" 
            placeholder="Tìm kiếm khóa học (ví dụ: nhận thức, kỹ năng từ chối...)" 
            className="light-theme-input" 
          />
          {/* Button thực hiện tìm kiếm */}
          <Button className="light-theme-button-primary">
            <Search className="h-5 w-5 mr-2" /> Tìm Kiếm
          </Button>
        </div>
      </section>

      {/* Section hiển thị danh sách khóa học dưới dạng grid layout */}
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* Render từng khóa học từ dữ liệu placeholder */}
        {placeholderCourses.map(course => (
          // Card component cho mỗi khóa học với hover effect và flex layout
          <Card key={course.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300 flex flex-col">
            {/* Header chứa hình ảnh và thông tin cơ bản */}
            <CardHeader>
              {/* Hình ảnh minh họa khóa học */}
              <img  
                className="w-full h-40 object-cover rounded-t-lg mb-4" 
                alt={course.title} // Alt text cho accessibility
                src={course.image} 
              />
              {/* Tiêu đề khóa học */}
              <CardTitle className="text-xl light-theme-card-header">{course.title}</CardTitle>
              {/* Thông tin đối tượng và thời lượng */}
              <CardDescription className="light-theme-card-description">
                Đối tượng: {course.audience} | Thời lượng: {course.duration}
              </CardDescription>
            </CardHeader>
            
            {/* Content chứa mô tả khóa học với flex-grow để chiếm không gian còn lại */}
            <CardContent className="flex-grow">
              {/* Mô tả ngắn với line-clamp để giới hạn số dòng hiển thị */}
              <p className="light-theme-card-content text-sm line-clamp-3">
                Mô tả ngắn về khóa học, những kiến thức và kỹ năng chính mà học viên sẽ nhận được sau khi hoàn thành...
              </p>
            </CardContent>
            
            {/* Footer chứa các button action */}
            <CardFooter className="flex-col items-start space-y-2">
              {/* Button xem chi tiết khóa học */}
              <Link to={`/courses/${course.id}`} className="w-full">
                <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90">
                  <BookMarked className="h-5 w-5 mr-2" /> Xem Chi Tiết
                </Button>
              </Link>
              {/* Button đăng ký khóa học */}
              <Button variant="outline" className="w-full light-theme-button-outline font-semibold">
                Đăng Ký Ngay <ArrowRight className="ml-2 h-4 w-4" />
              </Button>
            </CardFooter>
          </Card>
        ))}
      </section>
    </div>
  );
};

// Export component để sử dụng trong router
export default CoursesPage;