import React from 'react';
import { useParams, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { ArrowLeft, User, Calendar, Tag, MessageCircle, Edit } from 'lucide-react';
import { motion } from 'framer-motion';
import { useAuth } from '@/contexts/AuthContext';
import { ROLES } from '@/config/roles';
import { Textarea } from '@/components/ui/textarea';

const placeholderPosts = {
  1: { 
    title: "Tác hại của ma túy đá và cách phòng tránh hiệu quả", 
    date: "20/05/2025", 
    author: "BS. Nguyễn An", 
    category: "Kiến thức y khoa",
    tags: ["ma túy đá", "tác hại", "phòng tránh"],
    image: "https://images.unsplash.com/photo-1590099030599-3a7a344eb983?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80",
    content: `
      <p class="text-slate-700 leading-relaxed">Ma túy đá, hay còn gọi là methamphetamine, là một chất kích thích cực mạnh gây nghiện và tàn phá sức khỏe nghiêm trọng. Nó tác động trực tiếp lên hệ thần kinh trung ương, gây ra cảm giác hưng phấn, tỉnh táo và tăng năng lượng giả tạo. Tuy nhiên, những tác động này chỉ là tạm thời và kéo theo đó là những hậu quả khôn lường.</p>
      <h2 class="text-2xl font-semibold text-slate-800 my-4">Tác hại của ma túy đá</h2>
      <ul class="list-disc list-inside space-y-2 text-slate-600 mb-4">
        <li><strong>Về thể chất:</strong> Gây suy kiệt cơ thể, sụt cân nhanh chóng, tổn thương tim mạch, đột quỵ, hoang tưởng, ảo giác, suy giảm hệ miễn dịch, các vấn đề về răng miệng ("miệng meth").</li>
        <li><strong>Về tinh thần:</strong> Gây rối loạn tâm thần, lo âu, trầm cảm, paranoia, hành vi bạo lực, suy giảm trí nhớ và khả năng tập trung.</li>
        <li><strong>Về xã hội:</strong> Ảnh hưởng đến mối quan hệ gia đình, bạn bè, mất việc làm, vi phạm pháp luật.</li>
      </ul>
      <h2 class="text-2xl font-semibold text-slate-800 my-4">Cách phòng tránh</h2>
      <p class="text-slate-700 leading-relaxed">Việc phòng tránh ma túy đá cần sự chung tay của cả gia đình, nhà trường và xã hội:</p>
      <ul class="list-disc list-inside space-y-2 text-slate-600">
        <li>Nâng cao nhận thức về tác hại của ma túy.</li>
        <li>Xây dựng môi trường sống lành mạnh, không có ma túy.</li>
        <li>Trang bị kỹ năng từ chối và giải quyết vấn đề.</li>
        <li>Tìm kiếm sự giúp đỡ từ chuyên gia khi cần thiết.</li>
      </ul>
      <p class="mt-4 text-slate-700 leading-relaxed">Hãy nói không với ma túy để bảo vệ bản thân và những người xung quanh.</p>
    ` 
  },
};

const BlogDetailPage = () => {
  const { id } = useParams();
  const post = placeholderPosts[id]; 
  const { user } = useAuth();
  const canEditPost = user && [ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF].includes(user.role);


  if (!post) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy bài viết</h1>
        <Link to="/blog">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại trang Blog</Button>
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
      className="max-w-4xl mx-auto space-y-8"
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      <div className="relative shadow-xl rounded-lg overflow-hidden">
        <img  src={post.image} alt={post.title} className="w-full h-64 md:h-[450px] object-cover" />
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>
        <div className="absolute bottom-0 left-0 p-6 md:p-10">
          <motion.h1 variants={fadeIn} className="text-3xl md:text-5xl font-bold text-white mb-3 leading-tight">{post.title}</motion.h1>
          <motion.div variants={fadeIn} className="flex flex-wrap items-center text-sm text-gray-200 gap-x-4 gap-y-2">
            <span className="flex items-center"><User className="h-4 w-4 mr-1.5 text-sky-300" /> {post.author}</span>
            <span className="flex items-center"><Calendar className="h-4 w-4 mr-1.5 text-sky-300" /> {post.date}</span>
            <span className="flex items-center"><Tag className="h-4 w-4 mr-1.5 text-sky-300" /> {post.category}</span>
          </motion.div>
        </div>
      </div>

      <div className="flex justify-between items-center">
        <Link to="/blog" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
          <ArrowLeft className="h-5 w-5 mr-2" />
          Trở về trang Blog
        </Link>
        {canEditPost && (
          <Link to={`/admin/blog/edit/${id}`}>
            <Button variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white font-semibold">
              <Edit className="h-4 w-4 mr-2" /> Chỉnh Sửa Bài Viết
            </Button>
          </Link>
        )}
      </div>


      <motion.article 
        variants={fadeIn} 
        className="prose prose-lg max-w-none light-theme-card p-6 md:p-8"
        dangerouslySetInnerHTML={{ __html: post.content }} 
      />

      <motion.div variants={fadeIn} className="space-y-4 p-6 light-theme-card rounded-lg">
        <h3 className="text-xl font-semibold light-theme-card-header">Thẻ:</h3>
        <div className="flex flex-wrap gap-2">
          {post.tags.map(tag => (
            <span key={tag} className="px-3 py-1 bg-accent/10 text-accent text-sm rounded-full font-medium">{tag}</span>
          ))}
        </div>
      </motion.div>

      <motion.section variants={fadeIn} className="space-y-6 py-8 border-t border-gray-200">
        <h2 className="text-2xl font-semibold light-theme-text-default flex items-center">
          <MessageCircle className="h-6 w-6 mr-3 light-theme-text-primary"/> Bình luận (0)
        </h2>
        <form className="space-y-4">
          <Textarea 
            placeholder="Viết bình luận của bạn..." 
            rows="4"
            className="w-full p-3 light-theme-input"
          />
          <Button type="submit" className="light-theme-button-primary">Gửi Bình Luận</Button>
        </form>
        <p className="text-slate-500">Chưa có bình luận nào. Hãy là người đầu tiên!</p>
      </motion.section>

    </motion.div>
  );
};

export default BlogDetailPage;