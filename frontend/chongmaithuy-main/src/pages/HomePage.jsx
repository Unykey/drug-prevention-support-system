// HomePage.jsx: Trang chủ của website - landing page chính với giới thiệu dịch vụ
import React from 'react'; // Thư viện React cơ bản
import { Button } from '@/components/ui/button'; // Component nút bấm tùy chỉnh
import { Card, CardContent, CardHeader, CardTitle, CardDescription, CardFooter } from '@/components/ui/card'; // Các component card để hiển thị thông tin
import { ArrowRight, BookOpenText, Users, MessageSquare as MessageSquareHeart, Newspaper } from 'lucide-react'; // Các biểu tượng: mũi tên phải, sách, người dùng, tin nhắn, báo
import { Link } from 'react-router-dom'; // Component điều hướng
import { motion } from 'framer-motion'; // Thư viện animation cho hiệu ứng chuyển động

const HomePage = () => {
  // Cấu hình hiệu ứng xuất hiện mượt mà cho các phần tử
  const fadeIn = {
    hidden: { opacity: 0, y: 20 }, // Trạng thái ban đầu: mờ và dịch xuống 20px
    visible: { opacity: 1, y: 0, transition: { duration: 0.6 } } // Trạng thái kết thúc: rõ và về vị trí ban đầu trong 0.6 giây
  };

  return (
    <div className="space-y-12"> {/* Container chính với khoảng cách 12 giữa các section */}
      {/* HERO SECTION - Phần giới thiệu chính với tiêu đề lớn và call-to-action */}
      <motion.section 
        className="text-center py-16 md:py-24 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-xl shadow-xl overflow-hidden"
        // Căn giữa, padding dọc responsive, gradient background nhẹ, góc bo tròn, bóng đổ lớn
        initial="hidden" // Bắt đầu với trạng thái ẩn
        animate="visible" // Chuyển sang trạng thái hiện
        variants={fadeIn} // Áp dụng hiệu ứng fadeIn
      >
        <div className="container mx-auto px-4"> {/* Container responsive với padding ngang */}
          <motion.h1 
            className="text-5xl md:text-7xl font-bold mb-6 light-theme-text-default"
            // Tiêu đề: cỡ chữ 5xl trên mobile, 7xl trên desktop, đậm, margin bottom 6
            variants={fadeIn} // Hiệu ứng riêng cho tiêu đề
          >
            <span className="gradient-text">Chung Tay Vì Cộng Đồng</span><br/> Không Ma Túy
            {/* Dòng đầu có gradient effect, xuống dòng với br */}
          </motion.h1>
          <motion.p 
            className="text-lg md:text-xl text-slate-600 max-w-3xl mx-auto mb-8"
            // Mô tả: cỡ chữ lg/xl responsive, màu xám, giới hạn width, căn giữa
            variants={fadeIn}
          >
            Tổ chức tình nguyện của chúng tôi cung cấp thông tin, hỗ trợ và nguồn lực để phòng ngừa sử dụng ma túy, xây dựng một tương lai khỏe mạnh hơn cho mọi người.
          </motion.p>
          <motion.div variants={fadeIn} className="space-x-4"> {/* Container các nút CTA với khoảng cách ngang */}
            <Link to="/courses"> {/* Điều hướng đến trang khóa học */}
              <Button size="lg" className="light-theme-button-primary text-lg px-8 py-6 rounded-full shadow-lg transform hover:scale-105 transition-transform duration-300">
                {/* Nút lớn: theme chính, cỡ chữ lg, padding lớn, hình oval, bóng đổ, hiệu ứng scale khi hover */}
                Tìm Hiểu Khóa Học <ArrowRight className="ml-2 h-5 w-5" />
                {/* Text với icon mũi tên bên phải */}
              </Button>
            </Link>
            <Link to="/blog"> {/* Điều hướng đến trang blog */}
              <Button size="lg" variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white text-lg px-8 py-6 rounded-full shadow-lg transform hover:scale-105 transition-transform duration-300">
                {/* Nút outline: viền accent, hover đổi nền thành accent */}
                Đọc Blog <Newspaper className="ml-2 h-5 w-5" />
                {/* Text với icon báo */}
              </Button>
            </Link>
          </motion.div>
          <div className="mt-12 relative"> {/* Container ảnh hero với position relative */}
            <div className="absolute inset-0 bg-gradient-to-t from-sky-100 via-blue-50 to-transparent z-0"></div>
            {/* Lớp overlay gradient từ xanh da trời nhạt lên trong suốt, z-index 0 */}
            <img  
              className="w-full max-w-4xl mx-auto rounded-lg shadow-xl relative z-10" 
              // Ảnh: toàn width, max-width 4xl, căn giữa, góc bo tròn, bóng đổ, z-index 10
              alt="Happy diverse group of people participating in a community event"
              style={{ aspectRatio: '16/9', objectFit: 'cover' }} // Tỷ lệ 16:9, crop ảnh đẹp
             src="https://images.unsplash.com/photo-1531545514256-b1400bc00f31?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" />
          </div>
        </div>
      </motion.section>

      {/* SERVICES SECTION - Phần giới thiệu các dịch vụ chính */}
      <motion.section className="py-12" initial="hidden" whileInView="visible" viewport={{ once: true }} variants={fadeIn}>
        {/* Section với animation khi scroll vào view, chỉ chạy một lần */}
        <h2 className="text-4xl font-bold text-center mb-12 gradient-text">Dịch Vụ Của Chúng Tôi</h2>
        {/* Tiêu đề section: cỡ chữ lớn, đậm, căn giữa, gradient effect */}
        <div className="grid md:grid-cols-3 gap-8"> {/* Grid 3 cột trên desktop, khoảng cách 8 */}
          
          {/* Card 1: Khóa Học Nhận Thức */}
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-primary/20 transition-shadow duration-300 h-full flex flex-col">
              {/* Card với theme, shadow khi hover, chiều cao đầy đủ, flexbox cột */}
              <CardHeader>
                <div className="p-3 bg-primary/10 rounded-full w-fit mb-4">
                  {/* Container icon: padding, nền màu chính nhạt, hình tròn, vừa đủ */}
                  <BookOpenText className="h-8 w-8 text-primary" />
                  {/* Icon sách mở với màu chính */}
                </div>
                <CardTitle className="text-2xl light-theme-card-header">Khóa Học Nhận Thức</CardTitle>
                {/* Tiêu đề card: cỡ chữ 2xl, theme header */}
                <CardDescription className="light-theme-card-description">Các khóa học online đa dạng về phòng chống ma túy cho mọi lứa tuổi.</CardDescription>
                {/* Mô tả với theme description */}
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              {/* Content rỗng với flex-grow để đẩy footer xuống bottom */}
              <CardFooter>
                <Link to="/courses" className="w-full"> {/* Link toàn chiều rộng */}
                  <Button variant="link" className="text-primary p-0 w-full justify-start font-semibold">Xem Chi Tiết <ArrowRight className="ml-2 h-4 w-4" /></Button>
                  {/* Nút dạng link: màu chính, không padding, toàn width, căn trái, đậm */}
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
          
          {/* Card 2: Hỗ Trợ Cộng Đồng */}
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-accent/20 transition-shadow duration-300 h-full flex flex-col">
              {/* Tương tự card 1 nhưng shadow màu accent */}
              <CardHeader>
                <div className="p-3 bg-accent/10 rounded-full w-fit mb-4">
                  <Users className="h-8 w-8 text-accent" />
                  {/* Icon người dùng với màu accent */}
                </div>
                <CardTitle className="text-2xl light-theme-card-header">Hỗ Trợ Cộng Đồng</CardTitle>
                <CardDescription className="light-theme-card-description">Chương trình giáo dục và truyền thông nâng cao nhận thức cộng đồng.</CardDescription>
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              <CardFooter>
                <Link to="/programs" className="w-full">
                  <Button variant="link" className="text-accent p-0 w-full justify-start font-semibold">Khám Phá Chương Trình <ArrowRight className="ml-2 h-4 w-4" /></Button>
                  {/* Nút với màu accent */}
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
          
          {/* Card 3: Tư Vấn Chuyên Sâu */}
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-sky-500/20 transition-shadow duration-300 h-full flex flex-col">
              {/* Shadow màu sky-500 */}
              <CardHeader>
                <div className="p-3 bg-sky-500/10 rounded-full w-fit mb-4">
                  <MessageSquareHeart className="h-8 w-8 text-sky-500" />
                  {/* Icon tin nhắn với màu sky-500 */}
                </div>
                <CardTitle className="text-2xl light-theme-card-header">Tư Vấn Chuyên Sâu</CardTitle>
                <CardDescription className="light-theme-card-description">Đặt lịch hẹn trực tuyến với các chuyên gia tư vấn giàu kinh nghiệm.</CardDescription>
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              <CardFooter>
                <Link to="/appointments" className="w-full">
                  <Button variant="link" className="text-sky-500 p-0 w-full justify-start font-semibold">Đặt Lịch Ngay <ArrowRight className="ml-2 h-4 w-4" /></Button>
                  {/* Nút với màu sky-500 */}
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
        </div>
      </motion.section>

      {/* BLOG SECTION - Phần hiển thị các bài viết blog nổi bật */}
      <motion.section className="py-12" initial="hidden" whileInView="visible" viewport={{ once: true }} variants={fadeIn}>
        <h2 className="text-4xl font-bold text-center mb-12 gradient-text">Blog & Chia Sẻ Kinh Nghiệm</h2>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Grid responsive: 1 cột mobile, 2 cột medium, 3 cột large */}
          {[
            // Mảng dữ liệu mẫu cho các bài viết blog
            {id: 1, title: "Tác hại của ma túy đá và cách phòng tránh", image: "https://images.unsplash.com/photo-1590099030599-3a7a344eb983?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80"},
            {id: 2, title: "Vai trò của gia đình trong việc giáo dục con em", image: "https://images.unsplash.com/photo-1550358864-1017243f076a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"},
            {id: 3, title: "Kỹ năng từ chối lời mời sử dụng ma túy", image: "https://images.unsplash.com/photo-1604881989480-68d07586a9ab?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"}
          ].map((item) => ( // Lặp qua mảng để tạo card cho từng bài viết
            <motion.div variants={fadeIn} key={item.id}> {/* Container với animation và key duy nhất */}
              <Card className="light-theme-card overflow-hidden hover:shadow-primary/10 transition-shadow duration-300 h-full flex flex-col">
                {/* Card với overflow hidden để ảnh không tràn, shadow khi hover */}
                <img  
                  className="w-full h-48 object-cover" 
                  // Ảnh: toàn width, chiều cao cố định 48, crop ảnh đẹp
                  alt={item.title}
                 src={item.image} />
                <CardHeader>
                  <CardTitle className="text-xl light-theme-card-header">{item.title}</CardTitle>
                  {/* Tiêu đề bài viết: cỡ chữ xl */}
                  <CardDescription className="light-theme-card-description">Ngày đăng: 23 tháng 5, 2025</CardDescription>
                  {/* Ngày đăng với theme description */}
                </CardHeader>
                <CardContent className="flex-grow"> {/* Content với flex-grow để đẩy footer xuống */}
                  <p className="light-theme-card-content line-clamp-3">Đây là một đoạn trích ngắn từ bài viết blog. Nội dung chi tiết sẽ được hiển thị khi người dùng nhấp vào để đọc thêm...</p>
                  {/* Đoạn trích với line-clamp-3 để giới hạn 3 dòng */}
                </CardContent>
                <CardFooter>
                  <Link to={`/blog/${item.id}`} className="w-full"> {/* Link động đến trang chi tiết */}
                    <Button variant="outline" className="light-theme-button-outline w-full font-semibold">Đọc Thêm</Button>
                    {/* Nút outline toàn width */}
                  </Link>
                </CardFooter>
              </Card>
            </motion.div>
          ))}
        </div>
        <div className="text-center mt-12"> {/* Container nút "Xem tất cả" căn giữa */}
          <Link to="/blog">
            <Button size="lg" className="bg-accent text-accent-foreground hover:bg-accent/90">Xem Tất Cả Bài Viết <ArrowRight className="ml-2 h-5 w-5" /></Button>
            {/* Nút lớn với màu accent, hover nhạt hơn */}
          </Link>
        </div>
      </motion.section>
    </div>
  );
};

// Xuất component để sử dụng trong hệ thống routing
export default HomePage;