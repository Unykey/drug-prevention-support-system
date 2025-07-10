// Import React và các component con
import React from 'react';
import Header from '@/components/Header'; // Component header với navigation
import Footer from '@/components/Footer'; // Component footer

/**
 * Component Layout - Cấu trúc chung cho toàn bộ ứng dụng
 * 
 * Chức năng:
 * - Bao bọc toàn bộ nội dung trang với cấu trúc Header - Main - Footer
 * - Thiết lập background gradient màu sắc chủ đạo
 * - Đảm bảo main content chiếm hết không gian còn lại (flex-grow)
 * - Responsive layout với container và padding phù hợp
 * 
 * Props:
 * - children: Nội dung chính của từng trang sẽ được render trong main
 */
const Layout = ({ children }) => {
  return (
    // Container chính với flexbox layout dọc, chiều cao tối thiểu 100vh
    <div className="flex flex-col min-h-screen bg-gradient-to-br from-sky-100 via-blue-50 to-gray-100 text-slate-800">
      {/* Header với navigation menu */}
      <Header />
      
      {/* Main content area - flex-grow để chiếm hết space còn lại */}
      <main className="flex-grow container mx-auto px-4 py-8">
        {children}
      </main>
      
      {/* Footer */}
      <Footer />
    </div>
  );
};

// Export component Layout làm default export
export default Layout;