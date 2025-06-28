import React from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle, CardDescription, CardFooter } from '@/components/ui/card';
import { ArrowRight, BookOpenText, Users, MessageSquare as MessageSquareHeart, Newspaper } from 'lucide-react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';

const HomePage = () => {
  const fadeIn = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.6 } }
  };

  return (
    <div className="space-y-12">
      <motion.section 
        className="text-center py-16 md:py-24 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-xl shadow-xl overflow-hidden"
        initial="hidden"
        animate="visible"
        variants={fadeIn}
      >
        <div className="container mx-auto px-4">
          <motion.h1 
            className="text-5xl md:text-7xl font-bold mb-6 light-theme-text-default"
            variants={fadeIn}
          >
            <span className="gradient-text">Chung Tay Vì Cộng Đồng</span><br/> Không Ma Túy
          </motion.h1>
          <motion.p 
            className="text-lg md:text-xl text-slate-600 max-w-3xl mx-auto mb-8"
            variants={fadeIn}
          >
            Tổ chức tình nguyện của chúng tôi cung cấp thông tin, hỗ trợ và nguồn lực để phòng ngừa sử dụng ma túy, xây dựng một tương lai khỏe mạnh hơn cho mọi người.
          </motion.p>
          <motion.div variants={fadeIn} className="space-x-4">
            <Link to="/courses">
              <Button size="lg" className="light-theme-button-primary text-lg px-8 py-6 rounded-full shadow-lg transform hover:scale-105 transition-transform duration-300">
                Tìm Hiểu Khóa Học <ArrowRight className="ml-2 h-5 w-5" />
              </Button>
            </Link>
            <Link to="/blog">
              <Button size="lg" variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white text-lg px-8 py-6 rounded-full shadow-lg transform hover:scale-105 transition-transform duration-300">
                Đọc Blog <Newspaper className="ml-2 h-5 w-5" />
              </Button>
            </Link>
          </motion.div>
          <div className="mt-12 relative">
            <div className="absolute inset-0 bg-gradient-to-t from-sky-100 via-blue-50 to-transparent z-0"></div>
            <img  
              className="w-full max-w-4xl mx-auto rounded-lg shadow-xl relative z-10" 
              alt="Happy diverse group of people participating in a community event"
              style={{ aspectRatio: '16/9', objectFit: 'cover' }}
             src="https://images.unsplash.com/photo-1531545514256-b1400bc00f31?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" />
          </div>
        </div>
      </motion.section>

      <motion.section className="py-12" initial="hidden" whileInView="visible" viewport={{ once: true }} variants={fadeIn}>
        <h2 className="text-4xl font-bold text-center mb-12 gradient-text">Dịch Vụ Của Chúng Tôi</h2>
        <div className="grid md:grid-cols-3 gap-8">
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-primary/20 transition-shadow duration-300 h-full flex flex-col">
              <CardHeader>
                <div className="p-3 bg-primary/10 rounded-full w-fit mb-4"><BookOpenText className="h-8 w-8 text-primary" /></div>
                <CardTitle className="text-2xl light-theme-card-header">Khóa Học Nhận Thức</CardTitle>
                <CardDescription className="light-theme-card-description">Các khóa học online đa dạng về phòng chống ma túy cho mọi lứa tuổi.</CardDescription>
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              <CardFooter>
                <Link to="/courses" className="w-full">
                  <Button variant="link" className="text-primary p-0 w-full justify-start font-semibold">Xem Chi Tiết <ArrowRight className="ml-2 h-4 w-4" /></Button>
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-accent/20 transition-shadow duration-300 h-full flex flex-col">
              <CardHeader>
                <div className="p-3 bg-accent/10 rounded-full w-fit mb-4"><Users className="h-8 w-8 text-accent" /></div>
                <CardTitle className="text-2xl light-theme-card-header">Hỗ Trợ Cộng Đồng</CardTitle>
                <CardDescription className="light-theme-card-description">Chương trình giáo dục và truyền thông nâng cao nhận thức cộng đồng.</CardDescription>
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              <CardFooter>
                <Link to="/programs" className="w-full">
                  <Button variant="link" className="text-accent p-0 w-full justify-start font-semibold">Khám Phá Chương Trình <ArrowRight className="ml-2 h-4 w-4" /></Button>
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
          <motion.div variants={fadeIn}>
            <Card className="light-theme-card hover:shadow-sky-500/20 transition-shadow duration-300 h-full flex flex-col">
              <CardHeader>
                <div className="p-3 bg-sky-500/10 rounded-full w-fit mb-4"><MessageSquareHeart className="h-8 w-8 text-sky-500" /></div>
                <CardTitle className="text-2xl light-theme-card-header">Tư Vấn Chuyên Sâu</CardTitle>
                <CardDescription className="light-theme-card-description">Đặt lịch hẹn trực tuyến với các chuyên gia tư vấn giàu kinh nghiệm.</CardDescription>
              </CardHeader>
              <CardContent className="flex-grow"></CardContent>
              <CardFooter>
                <Link to="/appointments" className="w-full">
                  <Button variant="link" className="text-sky-500 p-0 w-full justify-start font-semibold">Đặt Lịch Ngay <ArrowRight className="ml-2 h-4 w-4" /></Button>
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
        </div>
      </motion.section>

      <motion.section className="py-12" initial="hidden" whileInView="visible" viewport={{ once: true }} variants={fadeIn}>
        <h2 className="text-4xl font-bold text-center mb-12 gradient-text">Blog & Chia Sẻ Kinh Nghiệm</h2>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {[
            {id: 1, title: "Tác hại của ma túy đá và cách phòng tránh", image: "https://images.unsplash.com/photo-1590099030599-3a7a344eb983?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80"},
            {id: 2, title: "Vai trò của gia đình trong việc giáo dục con em", image: "https://images.unsplash.com/photo-1550358864-1017243f076a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"},
            {id: 3, title: "Kỹ năng từ chối lời mời sử dụng ma túy", image: "https://images.unsplash.com/photo-1604881989480-68d07586a9ab?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"}
          ].map((item) => (
            <motion.div variants={fadeIn} key={item.id}>
              <Card className="light-theme-card overflow-hidden hover:shadow-primary/10 transition-shadow duration-300 h-full flex flex-col">
                <img  
                  className="w-full h-48 object-cover" 
                  alt={item.title}
                 src={item.image} />
                <CardHeader>
                  <CardTitle className="text-xl light-theme-card-header">{item.title}</CardTitle>
                  <CardDescription className="light-theme-card-description">Ngày đăng: 23 tháng 5, 2025</CardDescription>
                </CardHeader>
                <CardContent className="flex-grow">
                  <p className="light-theme-card-content line-clamp-3">Đây là một đoạn trích ngắn từ bài viết blog. Nội dung chi tiết sẽ được hiển thị khi người dùng nhấp vào để đọc thêm...</p>
                </CardContent>
                <CardFooter>
                  <Link to={`/blog/${item.id}`} className="w-full">
                    <Button variant="outline" className="light-theme-button-outline w-full font-semibold">Đọc Thêm</Button>
                  </Link>
                </CardFooter>
              </Card>
            </motion.div>
          ))}
        </div>
        <div className="text-center mt-12">
          <Link to="/blog">
            <Button size="lg" className="bg-accent text-accent-foreground hover:bg-accent/90">Xem Tất Cả Bài Viết <ArrowRight className="ml-2 h-5 w-5" /></Button>
          </Link>
        </div>
      </motion.section>
    </div>
  );
};

export default HomePage;