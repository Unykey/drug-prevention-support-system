// ProgramsPage.jsx: Trang danh sách các chương trình phòng chống ma túy
import React from 'react'; // React core cho functional component
import { Button } from '@/components/ui/button'; // Component button tùy chỉnh với styling nhất quán
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'; // Card components để tạo layout có cấu trúc cho từng chương trình
import { Users2, Presentation, CheckSquare } from 'lucide-react'; // Icons trực quan: người dùng, thuyết trình, checkbox
import { Link } from 'react-router-dom'; // Link component cho SPA navigation đến detail pages

// Mock data cho danh sách chương trình - trong thực tế sẽ được thay thế bằng API calls
// Mỗi chương trình bao gồm thông tin cơ bản để hiển thị trong card view
const programs = [
  { 
    id: 1, // ID duy nhất để routing và data handling
    title: "Chiến dịch 'Nói không với Ma túy'", // Tên chương trình
    type: "Truyền thông cộng đồng", // Phân loại chương trình để filtering và styling
    date: "15/06/2025 - 30/06/2025", // Thời gian diễn ra
      image: "https://images.unsplash.com/photo-1552664730-d307ca884978?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"
  },
  { 
    id: 2, 
    title: "Workshop Kỹ năng Sống cho Thanh Thiếu Niên", 
    type: "Giáo dục", // Chương trình giáo dục
    date: "Hàng tháng", // Chương trình định kỳ
    image: "https://images.unsplash.com/photo-1552664730-d307ca884978?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
  },
  { 
    id: 3, 
    title: "Tập huấn cho Tình nguyện viên", 
    type: "Đào tạo", // Chương trình đào tạo
    date: "01/07/2025", // Ngày cụ thể
    image: "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
  },
];

// Component chính hiển thị trang danh sách chương trình
const ProgramsPage = () => {
  return (
    // Container chính với spacing giữa các sections
    <div className="space-y-8">
      
      {/* Hero Section - Banner giới thiệu trang */}
      {/* Gradient background nhẹ nhàng với multiple colors để tạo visual appeal */}
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        {/* Tiêu đề chính với gradient text effect */}
        <h1 className="text-4xl font-bold mb-4 gradient-text">Chương Trình Truyền Thông & Giáo Dục</h1>
        {/* Mô tả ngắn gọn về mục đích của trang */}
        {/* max-w-2xl mx-auto: giới hạn độ rộng và căn giữa để dễ đọc */}
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Tham gia các chương trình ý nghĩa để cùng chúng tôi lan tỏa thông điệp phòng chống ma túy và xây dựng cộng đồng lành mạnh.
        </p>
      </section>

      {/* Programs Grid Section - Danh sách các chương trình */}
      {/* Responsive grid: 1 cột mobile, 2 cột tablet, 3 cột desktop */}
      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* Map qua từng chương trình để render card */}
        {programs.map(program => (
          // Card container với hover effect và flex layout cho equal height
          <Card key={program.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300 flex flex-col">
            
            {/* Card Header - Hình ảnh và thông tin cơ bản */}
            <CardHeader>
              {/* Hình ảnh thumbnail với aspect ratio cố định */}
              {/* h-40: chiều cao cố định, object-cover: giữ tỷ lệ hình ảnh */}
              <img  
                className="w-full h-40 object-cover rounded-t-lg mb-4" 
                alt={program.title}
               src={program.image} />
              {/* Tiêu đề chương trình */}
              <CardTitle className="text-xl light-theme-card-header">{program.title}</CardTitle>
              {/* Thông tin meta: loại hình và thời gian */}
              <CardDescription className="light-theme-card-description">
                Loại hình: {program.type} | Thời gian: {program.date}
              </CardDescription>
            </CardHeader>
            
            {/* Card Content - Mô tả ngắn */}
            {/* flex-grow: cho phép content mở rộng để đẩy footer xuống dưới */}
            <CardContent className="flex-grow">
              {/* Placeholder text với line-clamp để giới hạn số dòng hiển thị */}
              <p className="light-theme-card-content text-sm line-clamp-3">
                Mô tả chi tiết về chương trình, mục tiêu, đối tượng tham gia và các hoạt động chính...
              </p>
            </CardContent>
            
            {/* Card Footer - Action buttons */}
            {/* space-x-2: khoảng cách giữa các button */}
            <CardFooter className="space-x-2">
              {/* Button Chi Tiết - Link đến trang detail */}
              {/* flex-1: chiếm 50% width, w-full: full width trong container */}
              <Link to={`/programs/${program.id}`} className="flex-1">
                <Button variant="outline" className="w-full light-theme-button-outline font-semibold">
                  <Presentation className="h-5 w-5 mr-2" /> Chi Tiết
                </Button>
              </Link>
              
              {/* Button Đăng Ký - CTA chính */}
              <Button className="flex-1 bg-accent text-accent-foreground hover:bg-accent/90">
                <CheckSquare className="h-5 w-5 mr-2" /> Đăng Ký Tham Gia
              </Button>
            </CardFooter>
          </Card>
        ))}
      </section>

      {/* Survey Section - Call-to-action cho khảo sát */}
      {/* Card riêng để highlight tính năng khảo sát */}
      <section className="mt-12 p-6 light-theme-card rounded-lg">
        {/* Header với icon để làm rõ nội dung */}
        <h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
            <Users2 className="h-6 w-6 mr-3 light-theme-text-accent"/> Khảo sát chương trình
        </h2>
        
        {/* Mô tả về tầm quan trọng của khảo sát */}
        <p className="light-theme-card-content mb-4">
          Chúng tôi khuyến khích bạn thực hiện các bài khảo sát trước và sau khi tham gia chương trình. 
          Phản hồi của bạn là vô cùng quý giá, giúp chúng tôi cải tiến và nâng cao chất lượng các hoạt động trong tương lai.
        </p>
        
        {/* CTA button đến trang khảo sát */}
        {/* Màu sky-600 để phân biệt với accent color của registration buttons */}
        <Link to="/surveys">
            <Button className="bg-sky-600 text-white hover:bg-sky-700">
                Thực Hiện Khảo Sát Ngay
            </Button>
        </Link>
      </section>
    </div>
  );
};

// Export component ProgramsPage làm default export
// Component này sẽ được sử dụng trong routing để hiển thị danh sách tất cả chương trình
// Kết hợp với ProgramDetailPage để tạo thành complete programs feature
export default ProgramsPage;