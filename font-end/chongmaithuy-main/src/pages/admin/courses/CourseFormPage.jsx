import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { BookOpen, Save, ArrowLeft, Users, CalendarClock, UserCircle as UserIcon } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleCourses = {
  course1: { title: 'Nhận thức về Ma túy cho Học sinh', audience: 'Học sinh', duration: '4 tuần', instructor: 'ThS. Nguyễn Thị Lan', description: 'Mô tả chi tiết...', imageUrl: 'https://example.com/image.jpg', modules: 'Module 1, Module 2' },
};

const audienceOptions = ["Học sinh", "Sinh viên", "Phụ huynh", "Giáo viên", "Cộng đồng"];

const CourseFormPage = () => {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(courseId);

  const [formData, setFormData] = useState({
    title: '',
    audience: '',
    duration: '',
    instructor: '',
    description: '',
    imageUrl: '',
    modules: '', // Could be a JSON string or comma-separated
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && courseId && sampleCourses[courseId]) {
      setFormData(sampleCourses[courseId]);
    }
  }, [isEditing, courseId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };
  
  const handleAudienceChange = (value) => {
    setFormData(prev => ({ ...prev, audience: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await new Promise(resolve => setTimeout(resolve, 1000));
    setIsLoading(false);

    toast({
      title: `Khóa học đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Khóa học "${formData.title}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/courses');
  };

  return (
    <div className="space-y-6 max-w-3xl mx-auto">
      <Link to="/admin/courses" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách khóa học
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            <BookOpen className="h-8 w-8 text-primary" />
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Khóa Học' : 'Tạo Khóa Học Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật thông tin cho khóa học "${formData.title}".` : 'Điền thông tin chi tiết cho khóa học mới.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="title" className="light-theme-text-default">Tên Khóa Học</Label>
              <Input id="title" name="title" value={formData.title} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="audience" className="light-theme-text-default flex items-center"><Users className="h-4 w-4 mr-2 text-primary" /> Đối Tượng</Label>
                 <select id="audience" name="audience" value={formData.audience} onChange={handleChange} required className="w-full h-10 rounded-md border border-input bg-white px-3 py-2 text-sm text-slate-900 ring-offset-background focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 light-theme-input mt-1">
                    <option value="" disabled>Chọn đối tượng</option>
                    {audienceOptions.map(opt => <option key={opt} value={opt}>{opt}</option>)}
                </select>
              </div>
              <div>
                <Label htmlFor="duration" className="light-theme-text-default flex items-center"><CalendarClock className="h-4 w-4 mr-2 text-primary" /> Thời Lượng</Label>
                <Input id="duration" name="duration" value={formData.duration} onChange={handleChange} required className="light-theme-input mt-1" placeholder="VD: 4 tuần, 10 buổi" />
              </div>
            </div>

            <div>
              <Label htmlFor="instructor" className="light-theme-text-default flex items-center"><UserIcon className="h-4 w-4 mr-2 text-primary" /> Giảng Viên</Label>
              <Input id="instructor" name="instructor" value={formData.instructor} onChange={handleChange} className="light-theme-input mt-1" />
            </div>

            <div>
              <Label htmlFor="description" className="light-theme-text-default">Mô Tả Khóa Học</Label>
              <Textarea id="description" name="description" value={formData.description} onChange={handleChange} required className="light-theme-input mt-1 min-h-[120px]" />
            </div>
            
            <div>
              <Label htmlFor="imageUrl" className="light-theme-text-default">URL Hình Ảnh Minh Họa</Label>
              <Input id="imageUrl" name="imageUrl" value={formData.imageUrl} onChange={handleChange} className="light-theme-input mt-1" placeholder="https://example.com/course-image.jpg" />
            </div>

            <div>
              <Label htmlFor="modules" className="light-theme-text-default">Nội Dung/Các Module Chính (cách nhau bằng dấu phẩy)</Label>
              <Textarea id="modules" name="modules" value={formData.modules} onChange={handleChange} className="light-theme-input mt-1 min-h-[80px]" placeholder="VD: Module 1: Giới thiệu, Module 2: Tác hại..." />
            </div>

            <CardFooter className="px-0 pt-6">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Khóa Học' : 'Tạo Khóa Học')}
              </Button>
            </CardFooter>
          </form>
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default CourseFormPage;