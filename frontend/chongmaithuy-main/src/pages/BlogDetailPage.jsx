// BlogDetailPage.jsx: Trang hiển thị chi tiết một bài viết trên blog
import React from 'react'; // Thư viện React cơ bản
import { useParams, Link } from 'react-router-dom'; // useParams: lấy tham số từ URL, Link: điều hướng
import { Button } from '@/components/ui/button'; // Component nút bấm tùy chỉnh
// Các biểu tượng sử dụng trong giao diện
import { ArrowLeft, User, Calendar, Tag, MessageCircle, Edit } from 'lucide-react'; // Biểu tượng từ thư viện Lucide React
// Thư viện tạo hiệu ứng chuyển động mượt mà
import { motion } from 'framer-motion'; // Thư viện animation cho hiệu ứng chuyển tiếp
// Xác thực người dùng và phân quyền
import { useAuth } from '@/contexts/AuthContext'; // Hook lấy thông tin người dùng đăng nhập
import { ROLES } from '@/config/roles'; // Cấu hình các vai trò trong hệ thống
import { Textarea } from '@/components/ui/textarea'; // Component ô nhập văn bản nhiều dòng

// Dữ liệu mẫu cho các bài viết (dữ liệu tạm thời)
// Trong thực tế, dữ liệu này sẽ được lấy từ máy chủ thông qua API
const placeholderPosts = {
  1: { 
    title: "Tác hại của ma túy đá và cách phòng tránh hiệu quả", // Tiêu đề của bài viết
    date: "20/05/2025", // Ngày đăng bài
    author: "BS. Nguyễn An", // Tên tác giả
    category: "Kiến thức y khoa", // Danh mục bài viết
    tags: ["ma túy đá", "tác hại", "phòng tránh"], // Các từ khóa liên quan
    image: "https://images.unsplash.com/photo-1590099030599-3a7a344eb983?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80", // Đường dẫn ảnh đại diện
    content: ` // Nội dung bài viết dạng HTML để hiển thị định dạng đẹp
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
  // Lấy mã số bài viết từ đường dẫn URL (ví dụ: /blog/1 thì id = "1")
  const { id } = useParams(); // Tách mã số id từ các tham số URL
  // Tìm kiếm bài viết trong dữ liệu mẫu dựa trên mã số
  const post = placeholderPosts[id]; // Lấy thông tin bài viết theo id
  // Lấy thông tin người dùng hiện tại đã đăng nhập
  const { user } = useAuth(); // Sử dụng hook để lấy dữ liệu người dùng
  // Kiểm tra quyền chỉnh sửa: chỉ quản trị viên, quản lý và nhân viên mới được phép
  const canEditPost = user && [ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF].includes(user.role);
  // Điều kiện: người dùng phải tồn tại VÀ có vai trò trong danh sách được phép


  // Nếu không tìm thấy bài viết, hiển thị trang thông báo lỗi
  if (!post) {
    return (
      <div className="text-center py-10"> {/* Container căn giữa với khoảng cách trên dưới */}
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy bài viết</h1>
        {/* Thông báo lỗi với kiểu chữ lớn và màu sắc phù hợp theme */}
        <Link to="/blog"> {/* Liên kết quay về trang danh sách blog */}
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại trang Blog</Button>
          {/* Nút dạng liên kết với màu chủ đạo và khoảng cách phía trên */}
        </Link>
      </div>
    );
  }

  // Cài đặt hiệu ứng xuất hiện cho các thành phần
  const fadeIn = {
    hidden: { opacity: 0, y: 20 }, // Trạng thái ban đầu: mờ và dịch xuống 20px
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } } // Trạng thái kết thúc: rõ và về vị trí ban đầu trong 0.5 giây
  };
  
  return (
    <motion.div 
      className="max-w-4xl mx-auto space-y-8" // Giới hạn độ rộng tối đa và căn giữa, khoảng cách giữa các phần
      initial="hidden" // Bắt đầu với trạng thái ẩn
      animate="visible" // Chuyển sang trạng thái hiện
      variants={fadeIn} // Áp dụng hiệu ứng fadeIn đã định nghĩa
    >
      {/* Phần banner chính - Ảnh lớn với thông tin bài viết */}
      <div className="relative shadow-xl rounded-lg overflow-hidden"> {/* Container có bóng đổ và góc bo tròn */}
        <img src={post.image} alt={post.title} className="w-full h-64 md:h-[450px] object-cover" />
        {/* Ảnh toàn chiều rộng: cao 64 trên điện thoại, 450px trên máy tính, cắt ảnh đẹp */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/40 to-transparent"></div>
        {/* Lớp phủ màu đen gradient từ đậm ở dưới đến trong suốt ở trên */}
        <div className="absolute bottom-0 left-0 p-6 md:p-10"> {/* Vùng văn bản đè lên ảnh ở góc dưới trái */}
          <motion.h1 variants={fadeIn} className="text-3xl md:text-5xl font-bold text-white mb-3 leading-tight">
            {/* Tiêu đề: cỡ chữ 3xl trên điện thoại, 5xl trên máy tính, màu trắng, đậm */}
            {post.title} {/* Hiển thị tiêu đề bài viết */}
          </motion.h1>
          <motion.div variants={fadeIn} className="flex flex-wrap items-center text-sm text-gray-200 gap-x-4 gap-y-2">
            {/* Container thông tin meta với flexbox, khoảng cách ngang 4, dọc 2 */}
            <span className="flex items-center"><User className="h-4 w-4 mr-1.5 text-sky-300" /> {post.author}</span>
            {/* Tác giả với biểu tượng người dùng màu xanh da trời */}
            <span className="flex items-center"><Calendar className="h-4 w-4 mr-1.5 text-sky-300" /> {post.date}</span>
            {/* Ngày đăng với biểu tượng lịch */}
            <span className="flex items-center"><Tag className="h-4 w-4 mr-1.5 text-sky-300" /> {post.category}</span>
            {/* Danh mục với biểu tượng thẻ */}
          </motion.div>
        </div>
      </div>

      {/* Thanh điều hướng và hành động */}
      <div className="flex justify-between items-center"> {/* Flexbox với khoảng cách đều giữa các phần tử */}
        <Link to="/blog" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
          {/* Liên kết quay lại với hiệu ứng gạch chân khi hover */}
          <ArrowLeft className="h-5 w-5 mr-2" /> {/* Mũi tên trái với khoảng cách bên phải */}
          Trở về trang Blog
        </Link>
        {canEditPost && ( // Chỉ hiển thị nút chỉnh sửa nếu người dùng có quyền
          <Link to={`/admin/blog/edit/${id}`}> {/* Đường dẫn động đến trang chỉnh sửa với id bài viết */}
            <Button variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white font-semibold">
              {/* Nút viền với màu accent, hover đổi thành nền accent */}
              <Edit className="h-4 w-4 mr-2" /> Chỉnh Sửa Bài Viết {/* Biểu tượng chỉnh sửa */}
            </Button>
          </Link>
        )}
      </div>


      {/* Phần nội dung chính của bài viết */}
      <motion.article 
        variants={fadeIn} // Áp dụng hiệu ứng xuất hiện
        className="prose prose-lg max-w-none light-theme-card p-6 md:p-8" // Kiểu typography chuyên nghiệp với card
        dangerouslySetInnerHTML={{ __html: post.content }} // Hiển thị HTML (cần bảo mật trong thực tế)
      />
      {/* prose: plugin typography của Tailwind, prose-lg: cỡ chữ lớn hơn */}

      {/* Phần hiển thị các thẻ từ khóa */}
      <motion.div variants={fadeIn} className="space-y-4 p-6 light-theme-card rounded-lg">
        {/* Card chứa tags với khoảng cách và góc bo tròn */}
        <h3 className="text-xl font-semibold light-theme-card-header">Thẻ:</h3> {/* Tiêu đề phần */}
        <div className="flex flex-wrap gap-2"> {/* Container flexbox với khoảng cách giữa các tag */}
          {post.tags.map(tag => ( // Lặp qua mảng tags để hiển thị từng thẻ
            <span key={tag} className="px-3 py-1 bg-accent/10 text-accent text-sm rounded-full font-medium">{tag}</span>
            // Thẻ tag: padding, nền màu accent nhạt 10%, hình oval, chữ nhỏ
          ))}
        </div>
      </motion.div>

      {/* Phần bình luận */}
      <motion.section variants={fadeIn} className="space-y-6 py-8 border-t border-gray-200">
        {/* Section với đường viền trên làm ngăn cách */}
        <h2 className="text-2xl font-semibold light-theme-text-default flex items-center">
          {/* Tiêu đề phần bình luận với biểu tượng */}
          <MessageCircle className="h-6 w-6 mr-3 light-theme-text-primary"/> Bình luận (0)
          {/* Biểu tượng tin nhắn với màu chủ đạo */}
        </h2>
        <form className="space-y-4"> {/* Form nhập bình luận với khoảng cách */}
          <Textarea 
            placeholder="Viết bình luận của bạn..." // Văn bản gợi ý
            rows="4" // Số dòng hiển thị
            className="w-full p-3 light-theme-input" // Toàn chiều rộng với padding và theme
          />
          <Button type="submit" className="light-theme-button-primary">Gửi Bình Luận</Button>
          {/* Nút gửi với kiểu nút chính */}
        </form>
        <p className="text-slate-500">Chưa có bình luận nào. Hãy là người đầu tiên!</p>
        {/* Thông báo trống với màu chữ nhạt */}
      </motion.section>

    </motion.div>
  );
};

// Xuất component để sử dụng trong hệ thống định tuyến
export default BlogDetailPage;