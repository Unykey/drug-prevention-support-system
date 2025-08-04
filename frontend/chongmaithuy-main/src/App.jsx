// Import thư viện React cốt lõi
import React from 'react';
// Import các component routing từ React Router DOM cho việc điều hướng SPA
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import {Routes, Route} from 'react-router-dom';

// Import Layout component - bao bọc toàn bộ ứng dụng với header, footer, navigation
import Layout from '@/components/Layout';

// Import các trang chính của ứng dụng - Public pages (không cần đăng nhập)
import HomePage from '@/pages/HomePage'; // Trang chủ hiển thị thông tin tổng quan
import CoursesPage from '@/pages/CoursesPage'; // Danh sách các khóa học phòng chống ma túy
import CourseDetailPage from '@/pages/CourseDetailPage'; // Chi tiết khóa học cụ thể
import SurveyPage from '@/pages/SurveyPage'; // Danh sách các khảo sát
import SurveyDetailPage from '@/pages/SurveyDetailPage'; // Chi tiết khảo sát và form làm bài
import AppointmentsPage from '@/pages/AppointmentsPage'; // Danh sách các buổi hẹn tư vấn
import FindDoctorPage from "@/pages/FindDoctorPage"; // Trang tìm kiếm bác sĩ

import ProgramsPage from '@/pages/ProgramsPage'; // Danh sách chương trình phòng chống ma túy
import ProgramDetailPage from '@/pages/ProgramDetailPage'; // Chi tiết chương trình
import BlogPage from '@/pages/BlogPage'; // Danh sách bài viết blog
import BlogDetailPage from '@/pages/BlogDetailPage'; // Chi tiết bài viết blog
import NotFoundPage from '@/pages/NotFoundPage'; // Trang 404 khi không tìm thấy route

// Import các trang yêu cầu authentication
import BookAppointmentPage from '@/pages/BookAppointmentPage'; // Đặt lịch hẹn tư vấn (cần đăng nhập)
import UserProfilePage from '@/pages/UserProfilePage'; // Trang hồ sơ cá nhân người dùng
import LoginPage from '@/pages/LoginPage'; // Trang đăng nhập
import RegisterPage from '@/pages/RegisterPage'; // Trang đăng ký tài khoản
import ForgotPasswordPage from '@/pages/ForgotPasswordPage'; // Trang quên mật khẩu
import AdminPage from '@/pages/AdminPage'; // Dashboard admin tổng quan
// import AdminDashboard from '@/pages/admin/AdminDashboard'

// Import UI components
import {Toaster} from '@/components/ui/toaster'; // Component hiển thị thông báo toast

// Import authentication context và protection
import {AuthProvider} from '@/contexts/AuthContext'; // Context quản lý trạng thái đăng nhập
import ProtectedRoute from '@/components/ProtectedRoute'; // Component bảo vệ route theo role
import {ROLES} from '@/config/roles'; // Định nghĩa các vai trò người dùng

// Import các trang quản trị chuyên biệt - chỉ dành cho Admin/Manager/Staff
import UserListPage from '@/pages/admin/users/UserListPage'; // Quản lý danh sách người dùng
import UserFormPage from '@/pages/admin/users/UserFormPage'; // Form tạo/chỉnh sửa người dùng
import CounselorListPage from '@/pages/admin/counselors/CounselorListPage'; // Quản lý danh sách tư vấn viên
import CounselorFormPage from '@/pages/admin/counselors/CounselorFormPage'; // Form tạo/chỉnh sửa tư vấn viên
import CourseListPage from '@/pages/admin/courses/CourseListPage'; // Quản lý danh sách khóa học
import CourseFormPage from '@/pages/admin/courses/CourseFormPage'; // Form tạo/chỉnh sửa khóa học
import BlogPostListPage from '@/pages/admin/blog/BlogPostListPage'; // Quản lý danh sách bài viết blog
import BlogPostFormPage from '@/pages/admin/blog/BlogPostFormPage'; // Form tạo/chỉnh sửa bài viết blog
import ProgramListPage from '@/pages/admin/programs/ProgramListPage'; // Quản lý danh sách chương trình
import ProgramFormPage from '@/pages/admin/programs/ProgramFormPage'; // Form tạo/chỉnh sửa chương trình
import SurveyListPage from '@/pages/admin/surveys/SurveyListPage'; // Quản lý danh sách khảo sát
import SurveyFormPage from '@/pages/admin/surveys/SurveyFormPage'; // Form tạo/chỉnh sửa khảo sát


/**
 * Component App chính - Điểm khởi đầu của ứng dụng phòng chống ma túy
 *
 * Chức năng chính:
 * - Thiết lập routing cho toàn bộ ứng dụng
 * - Quản lý authentication và authorization
 * - Phân quyền truy cập theo vai trò người dùng
 * - Bao bọc ứng dụng với Layout chung và AuthProvider
 *
 * Cấu trúc route:
 * 1. Public routes: Trang chủ, đăng nhập, đăng ký, khóa học, blog...
 * 2. Protected routes: Profile, đặt lịch hẹn (cần đăng nhập)
 * 3. Admin routes: Quản lý người dùng, nội dung (phân quyền theo role)
 */
function App() {
    return (
        <Layout>
            <Routes>
                {/* =========================== ROUTE CÔNG KHAI =========================== */}
                {/* Các route này không yêu cầu đăng nhập, ai cũng có thể truy cập */}

                {/* Trang chủ - Điểm khởi đầu của ứng dụng */}
                <Route path="/" element={<HomePage/>}/>

                {/* Nhóm route xác thực - Đăng nhập, đăng ký, quên mật khẩu */}
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/forgot-password" element={<ForgotPasswordPage/>}/>

                {/* Nhóm route khóa học - Xem danh sách và chi tiết khóa học phòng chống ma túy */}
                <Route path="/courses" element={<CoursesPage/>}/>
                <Route path="/courses/:id" element={<CourseDetailPage/>}/>

                {/* Nhóm route khảo sát - Xem danh sách và làm khảo sát */}
                <Route path="/surveys" element={<SurveyPage/>}/>
                <Route path="/surveys/:id" element={<SurveyDetailPage/>}/>

                {/* Nhóm route cuộc hẹn - Xem danh sách cuộc hẹn tư vấn */}
                <Route path="/appointments" element={<AppointmentsPage/>}/>

                {/* Nhóm route tìm bác sĩ - Tìm bác sĩ theo tên, số điện thoại, địa chỉ*/}
                <Route path="/find-doctor" element={<FindDoctorPage/>}/>

                {/* Nhóm route chương trình - Xem các chương trình phòng chống ma túy */}
                <Route path="/programs" element={<ProgramsPage/>}/>
                <Route path="/programs/:id" element={<ProgramDetailPage/>}/>

                {/* Nhóm route blog - Đọc bài viết, tin tức về phòng chống ma túy */}
                <Route path="/blog" element={<BlogPage/>}/>
                <Route path="/blog/:id" element={<BlogDetailPage/>}/>
                {/* =========================== ROUTE BẢO VỆ =========================== */}
                {/* Các route này yêu cầu đăng nhập và có phân quyền theo vai trò */}

                {/* Đặt lịch hẹn với tư vấn viên - Yêu cầu đăng nhập */}
                <Route path="/book-appointment/:counselorId" element={
                    <ProtectedRoute
                        allowedRoles={[ROLES.MEMBER, ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                        <BookAppointmentPage/>
                    </ProtectedRoute>
                }/>

                {/* Trang hồ sơ cá nhân - Tất cả người dùng đã đăng nhập */}
                <Route path="/profile" element={
                    <ProtectedRoute
                        allowedRoles={[ROLES.MEMBER, ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                        <UserProfilePage/>
                    </ProtectedRoute>
                }/>

                {/* =========================== ROUTE QUẢN TRỊ =========================== */}
                {/* Các route này chỉ dành cho staff trở lên */}

                {/* Dashboard tổng quan admin */}
                <Route path="/admin" element={
                    <ProtectedRoute allowedRoles={[ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                        <AdminPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ NGƯỜI DÙNG ============= */}
                {/* Chỉ Admin và Manager mới có quyền quản lý người dùng */}

                {/* Danh sách tất cả người dùng trong hệ thống */}
                <Route path="/admin/users" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <UserListPage/>
                    </ProtectedRoute>
                }/>
                {/* Tạo mới người dùng */}
                <Route path="/admin/users/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <UserFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa thông tin người dùng */}
                <Route path="/admin/users/edit/:userId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <UserFormPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ TỦ VẤN VIÊN ============= */}
                {/* Quản lý đội ngũ tư vấn viên chuyên nghiệp */}

                {/* Danh sách tư vấn viên */}
                <Route path="/admin/counselors" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <CounselorListPage/>
                    </ProtectedRoute>
                }/>
                {/* Thêm tư vấn viên mới */}
                <Route path="/admin/counselors/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <CounselorFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa thông tin tư vấn viên */}
                <Route path="/admin/counselors/edit/:counselorId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                        <CounselorFormPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ KHÓA HỌC ============= */}
                {/* Staff trở lên có thể quản lý nội dung khóa học */}

                {/* Danh sách khóa học phòng chống ma túy */}
                <Route path="/admin/courses" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <CourseListPage/>
                    </ProtectedRoute>
                }/>
                {/* Tạo khóa học mới */}
                <Route path="/admin/courses/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <CourseFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa khóa học */}
                <Route path="/admin/courses/edit/:courseId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <CourseFormPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ BLOG ============= */}
                {/* Quản lý bài viết, tin tức về phòng chống ma túy */}

                {/* Danh sách bài viết blog */}
                <Route path="/admin/blog" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <BlogPostListPage/>
                    </ProtectedRoute>
                }/>
                {/* Tạo bài viết mới */}
                <Route path="/admin/blog/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <BlogPostFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa bài viết */}
                <Route path="/admin/blog/edit/:postId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <BlogPostFormPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ CHƯƠNG TRÌNH ============= */}
                {/* Quản lý các chương trình phòng chống ma túy */}

                {/* Danh sách chương trình */}
                <Route path="/admin/programs" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <ProgramListPage/>
                    </ProtectedRoute>
                }/>
                {/* Tạo chương trình mới */}
                <Route path="/admin/programs/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <ProgramFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa chương trình */}
                <Route path="/admin/programs/edit/:programId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <ProgramFormPage/>
                    </ProtectedRoute>
                }/>

                {/* ============= QUẢN LÝ KHẢO SÁT ============= */}
                {/* Quản lý các bài khảo sát đánh giá tình trạng */}

                {/* Danh sách khảo sát */}
                <Route path="/admin/surveys" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <SurveyListPage/>
                    </ProtectedRoute>
                }/>
                {/* Tạo khảo sát mới */}
                <Route path="/admin/surveys/new" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <SurveyFormPage/>
                    </ProtectedRoute>
                }/>
                {/* Chỉnh sửa khảo sát */}
                <Route path="/admin/surveys/edit/:surveyId" element={
                    <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                        <SurveyFormPage/>
                    </ProtectedRoute>
                }/>

                {/* Route dự phòng - Hiển thị trang 404 khi không tìm thấy route */}
                <Route path="*" element={<NotFoundPage/>}/>
            </Routes>
            {/* Toaster - Component hiển thị thông báo toast trên toàn ứng dụng */}
            <Toaster/>
        </Layout>
    );
}

// Export component App làm default export cho ứng dụng React
export default App;