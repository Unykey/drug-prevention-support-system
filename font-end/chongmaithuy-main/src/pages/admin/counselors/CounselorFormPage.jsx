import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { UserPlus, Save, ArrowLeft, Briefcase, FileText as FileTextIcon } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleCounselors = {
  coun1: { name: 'Chuyên viên Nguyễn Văn A', expertise: 'Tư vấn cai nghiện, hỗ trợ tâm lý', email: 'a.nguyen@tuvan.org', phone: '0901234567', bio: 'Nhiều năm kinh nghiệm...', qualifications: 'Thạc sĩ Tâm lý học' },
};

const CounselorFormPage = () => {
  const { counselorId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(counselorId);

  const [formData, setFormData] = useState({
    name: '',
    expertise: '',
    email: '',
    phone: '',
    bio: '',
    qualifications: '',
    avatarUrl: '',
    workSchedule: '',
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && counselorId && sampleCounselors[counselorId]) {
      setFormData(sampleCounselors[counselorId]);
    }
  }, [isEditing, counselorId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await new Promise(resolve => setTimeout(resolve, 1000));
    setIsLoading(false);

    toast({
      title: `Chuyên viên đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Thông tin chuyên viên "${formData.name}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/counselors');
  };

  return (
    <div className="space-y-6 max-w-3xl mx-auto">
      <Link to="/admin/counselors" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách chuyên viên
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            <UserPlus className="h-8 w-8 text-primary" />
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Thông Tin Chuyên Viên' : 'Thêm Chuyên Viên Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật hồ sơ cho chuyên viên ${formData.name}.` : 'Điền thông tin chi tiết cho chuyên viên mới.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="name" className="light-theme-text-default">Họ và Tên</Label>
                <Input id="name" name="name" value={formData.name} onChange={handleChange} required className="light-theme-input mt-1" />
              </div>
              <div>
                <Label htmlFor="expertise" className="light-theme-text-default flex items-center"><Briefcase className="h-4 w-4 mr-2 text-primary" /> Chuyên Môn</Label>
                <Input id="expertise" name="expertise" value={formData.expertise} onChange={handleChange} required className="light-theme-input mt-1" placeholder="VD: Tư vấn tâm lý, Cai nghiện" />
              </div>
            </div>
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="email" className="light-theme-text-default">Email</Label>
                <Input id="email" name="email" type="email" value={formData.email} onChange={handleChange} required className="light-theme-input mt-1" />
              </div>
              <div>
                <Label htmlFor="phone" className="light-theme-text-default">Số Điện Thoại</Label>
                <Input id="phone" name="phone" type="tel" value={formData.phone} onChange={handleChange} className="light-theme-input mt-1" />
              </div>
            </div>
             <div>
                <Label htmlFor="avatarUrl" className="light-theme-text-default">URL Ảnh Đại Diện</Label>
                <Input id="avatarUrl" name="avatarUrl" value={formData.avatarUrl} onChange={handleChange} className="light-theme-input mt-1" placeholder="https://example.com/avatar.jpg"/>
            </div>
            <div>
              <Label htmlFor="qualifications" className="light-theme-text-default flex items-center"><FileTextIcon className="h-4 w-4 mr-2 text-primary" /> Bằng Cấp/Chứng Chỉ</Label>
              <Textarea id="qualifications" name="qualifications" value={formData.qualifications} onChange={handleChange} className="light-theme-input mt-1 min-h-[80px]" placeholder="Liệt kê các bằng cấp, chứng chỉ liên quan"/>
            </div>
            <div>
              <Label htmlFor="bio" className="light-theme-text-default">Tiểu Sử/Giới Thiệu Ngắn</Label>
              <Textarea id="bio" name="bio" value={formData.bio} onChange={handleChange} className="light-theme-input mt-1 min-h-[120px]" placeholder="Mô tả kinh nghiệm, phương pháp làm việc..."/>
            </div>
            <div>
                <Label htmlFor="workSchedule" className="light-theme-text-default">Lịch Làm Việc (Mô tả)</Label>
                <Input id="workSchedule" name="workSchedule" value={formData.workSchedule} onChange={handleChange} className="light-theme-input mt-1" placeholder="VD: Thứ 2 - Thứ 6, 9AM - 5PM"/>
            </div>
            <CardFooter className="px-0 pt-6">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Chuyên Viên' : 'Thêm Chuyên Viên')}
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

export default CounselorFormPage;