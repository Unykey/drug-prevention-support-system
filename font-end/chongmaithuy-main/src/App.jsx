import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from '@/components/Layout';
import HomePage from '@/pages/HomePage';
import CoursesPage from '@/pages/CoursesPage';
import CourseDetailPage from '@/pages/CourseDetailPage';
import SurveyPage from '@/pages/SurveyPage';
import SurveyDetailPage from '@/pages/SurveyDetailPage';
import AppointmentsPage from '@/pages/AppointmentsPage';
import BookAppointmentPage from '@/pages/BookAppointmentPage';
import ProgramsPage from '@/pages/ProgramsPage';
import ProgramDetailPage from '@/pages/ProgramDetailPage';
import AdminPage from '@/pages/AdminPage';
import UserProfilePage from '@/pages/UserProfilePage';
import LoginPage from '@/pages/LoginPage';
import RegisterPage from '@/pages/RegisterPage';
import ForgotPasswordPage from '@/pages/ForgotPasswordPage';
import BlogPage from '@/pages/BlogPage';
import BlogDetailPage from '@/pages/BlogDetailPage';
import NotFoundPage from '@/pages/NotFoundPage';
import { Toaster } from '@/components/ui/toaster';
import { AuthProvider } from '@/contexts/AuthContext';
import ProtectedRoute from '@/components/ProtectedRoute';
import { ROLES } from '@/config/roles';

import UserListPage from '@/pages/admin/users/UserListPage';
import UserFormPage from '@/pages/admin/users/UserFormPage';
import CounselorListPage from '@/pages/admin/counselors/CounselorListPage';
import CounselorFormPage from '@/pages/admin/counselors/CounselorFormPage';
import CourseListPage from '@/pages/admin/courses/CourseListPage';
import CourseFormPage from '@/pages/admin/courses/CourseFormPage';
import BlogPostListPage from '@/pages/admin/blog/BlogPostListPage';
import BlogPostFormPage from '@/pages/admin/blog/BlogPostFormPage';
import ProgramListPage from '@/pages/admin/programs/ProgramListPage';
import ProgramFormPage from '@/pages/admin/programs/ProgramFormPage';
import SurveyListPage from '@/pages/admin/surveys/SurveyListPage';
import SurveyFormPage from '@/pages/admin/surveys/SurveyFormPage';


function App() {
  return (
    <AuthProvider>
      <Router>
        <Layout>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/forgot-password" element={<ForgotPasswordPage />} />
            
            <Route path="/courses" element={<CoursesPage />} />
            <Route path="/courses/:id" element={<CourseDetailPage />} />
            
            <Route path="/surveys" element={<SurveyPage />} />
            <Route path="/surveys/:id" element={<SurveyDetailPage />} />
            
            <Route path="/appointments" element={<AppointmentsPage />} />
            <Route path="/book-appointment/:counselorId" element={
              <ProtectedRoute allowedRoles={[ROLES.MEMBER, ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                <BookAppointmentPage />
              </ProtectedRoute>
            } />
            
            <Route path="/programs" element={<ProgramsPage />} />
            <Route path="/programs/:id" element={<ProgramDetailPage />} />

            <Route path="/blog" element={<BlogPage />} />
            <Route path="/blog/:id" element={<BlogDetailPage />} />
            
            <Route path="/profile" element={
              <ProtectedRoute allowedRoles={[ROLES.MEMBER, ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                <UserProfilePage />
              </ProtectedRoute>
            } />
            
            <Route path="/admin" element={
              <ProtectedRoute allowedRoles={[ROLES.STAFF, ROLES.CONSULTANT, ROLES.MANAGER, ROLES.ADMIN]}>
                <AdminPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/users" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <UserListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/users/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <UserFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/users/edit/:userId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <UserFormPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/counselors" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <CounselorListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/counselors/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <CounselorFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/counselors/edit/:counselorId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER]}>
                <CounselorFormPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/courses" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <CourseListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/courses/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <CourseFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/courses/edit/:courseId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <CourseFormPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/blog" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <BlogPostListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/blog/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <BlogPostFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/blog/edit/:postId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <BlogPostFormPage />
              </ProtectedRoute>
            } />
            
            <Route path="/admin/programs" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <ProgramListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/programs/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <ProgramFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/programs/edit/:programId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <ProgramFormPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/surveys" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <SurveyListPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/surveys/new" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <SurveyFormPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/surveys/edit/:surveyId" element={
              <ProtectedRoute allowedRoles={[ROLES.ADMIN, ROLES.MANAGER, ROLES.STAFF]}>
                <SurveyFormPage />
              </ProtectedRoute>
            } />
            
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </Layout>
        <Toaster />
      </Router>
    </AuthProvider>
  );
}

export default App;