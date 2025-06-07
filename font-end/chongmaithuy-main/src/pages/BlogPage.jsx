import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Search, ArrowRight, Edit } from 'lucide-react';
import { motion } from 'framer-motion';
import { useAuth } from '@/contexts/AuthContext';
import { ROLES } from '@/config/roles';

const placeholderPosts = [
  { id: 1, title: "Tác hại của ma túy đá và cách phòng tránh hiệu quả", date: "20/05/2025", author: "BS. Nguyễn An", excerpt: "Ma túy đá, hay còn gọi là methamphetamine, là một chất kích thích cực mạnh gây nghiện và tàn phá sức khỏe nghiêm trọng...", image: "https://images.unsplash.com/photo-1590099030599-3a7a344eb983?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80" },
  { id: 2, title: "Vai trò của gia đình trong việc giáo dục phòng chống ma túy cho con em", date: "15/05/2025", author: "Chuyên gia tâm lý Trần Mai", excerpt: "Gia đình là nền tảng vững chắc nhất giúp trẻ hình thành nhân cách và tránh xa các tệ nạn xã hội, bao gồm cả ma túy...", image: "https://images.unsplash.com/photo-1550358864-1017243f076a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
  { id: 3, title: "Kỹ năng từ chối lời mời sử dụng ma túy dành cho thanh thiếu niên", date: "10/05/2025", author: "Cô giáo Hoàng Yến", excerpt: "Đối mặt với áp lực từ bạn bè và những lời mời gọi thử ma túy, thanh thiếu niên cần được trang bị những kỹ năng từ chối mạnh mẽ...", image: "https://images.unsplash.com/photo-1604881989480-68d07586a9ab?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
  { id: 4, title: "Câu chuyện cai nghiện thành công: Ánh sáng cuối đường hầm", date: "05/05/2025", author: "Anh Minh Tuấn (chia sẻ)", excerpt: "Từ bóng tối của nghiện ngập, tôi đã tìm thấy con đường trở về với cuộc sống nhờ sự giúp đỡ của gia đình và cộng đồng...", image: "https://images.unsplash.com/photo-1500099817043-86d46000d58f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
];

const BlogPage = () => {
  const { user } = useAuth();
  const canCreatePost = user && [ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF].includes(user.role);

  const fadeIn = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.6, staggerChildren: 0.1 } }
  };

  return (
    <motion.div 
      className="space-y-8"
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <motion.h1 variants={fadeIn} className="text-4xl md:text-5xl font-bold mb-4 gradient-text">Blog & Chia Sẻ Kinh Nghiệm</motion.h1>
        <motion.p variants={fadeIn} className="text-lg text-slate-600 max-w-2xl mx-auto">
          Nơi chia sẻ kiến thức, kinh nghiệm và câu chuyện về phòng chống ma túy.
        </motion.p>
      </section>

      <motion.section variants={fadeIn} className="flex flex-col sm:flex-row gap-2 mb-8 max-w-2xl mx-auto">
        <Input 
          type="text" 
          placeholder="Tìm kiếm bài viết..." 
          className="light-theme-input flex-grow" 
        />
        <Button className="light-theme-button-primary">
          <Search className="h-5 w-5 mr-2" /> Tìm Kiếm
        </Button>
        {canCreatePost && (
          <Link to="/admin/blog/create"> 
            <Button variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white w-full sm:w-auto font-semibold">
              <Edit className="h-5 w-5 mr-2" /> Viết Bài Mới
            </Button>
          </Link>
        )}
      </motion.section>

      <motion.section 
        variants={fadeIn} 
        className="grid md:grid-cols-2 lg:grid-cols-3 gap-8"
      >
        {placeholderPosts.map(post => (
          <motion.div variants={fadeIn} key={post.id}>
            <Card className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300 flex flex-col h-full">
              <img  
                className="w-full h-48 object-cover rounded-t-lg" 
                alt={post.title}
                src={post.image} 
              />
              <CardHeader>
                <CardTitle className="text-xl light-theme-card-header line-clamp-2">{post.title}</CardTitle>
                <CardDescription className="light-theme-card-description">
                  {post.date} - Tác giả: {post.author}
                </CardDescription>
              </CardHeader>
              <CardContent className="flex-grow">
                <p className="light-theme-card-content text-sm line-clamp-3">{post.excerpt}</p>
              </CardContent>
              <CardFooter>
                <Link to={`/blog/${post.id}`} className="w-full">
                  <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90">
                    Đọc Thêm <ArrowRight className="ml-2 h-4 w-4" />
                  </Button>
                </Link>
              </CardFooter>
            </Card>
          </motion.div>
        ))}
      </motion.section>
      
      <motion.div variants={fadeIn} className="text-center mt-12">
        <Button size="lg" variant="outline" className="light-theme-button-outline font-semibold">
          Xem Thêm Bài Viết Cũ
        </Button>
      </motion.div>
    </motion.div>
  );
};

export default BlogPage;