import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { Presentation, Save, ArrowLeft, Users, CalendarDays, MapPin, Image as ImageIcon } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const samplePrograms = {
  prog1: { title: "Chiến dịch 'Nói không với Ma túy'", type: 'Truyền thông', date: '2024-06-15', location: 'Cộng đồng', description: 'Mô tả chi tiết...', targetAudience: 'Mọi lứa tuổi', imageUrl: '' },
};

const programTypes = ["Truyền thông cộng đồng", "Giáo dục", "Đào tạo", "Sự kiện", "Workshop"];

const ProgramFormPage = () => {
  const { programId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(programId);

  const [formData, setFormData] = useState({
    title: '',
    type: '',
    date: '',
    location: '',
    description: '',
    targetAudience: '',
    imageUrl: '',
    organizer: '',
    activities: '', // Comma-separated
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && programId && samplePrograms[programId]) {
      setFormData(samplePrograms[programId]);
    }
  }, [isEditing, programId]);

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
      title: `Chương trình đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Chương trình "${formData.title}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/programs');
  };

  return (
    <div className="space-y-6 max-w-3xl mx-auto">
      <Link to="/admin/programs" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách chương trình
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            <Presentation className="h-8 w-8 text-primary" />
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Chương Trình' : 'Tạo Chương Trình Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật thông tin cho chương trình "${formData.title}".` : 'Điền thông tin chi tiết cho chương trình mới.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="title" className="light-theme-text-default">Tên Chương Trình</Label>
              <Input id="title" name="title" value={formData.title} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="type" className="light-theme-text-default">Loại Hình Chương Trình</Label>
                 <select id="type" name="type" value={formData.type} onChange={handleChange} required className="w-full h-10 rounded-md border border-input bg-white px-3 py-2 text-sm text-slate-900 ring-offset-background focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 light-theme-input mt-1">
                    <option value="" disabled>Chọn loại hình</option>
                    {programTypes.map(opt => <option key={opt} value={opt}>{opt}</option>)}
                </select>
              </div>
              <div>
                <Label htmlFor="date" className="light-theme-text-default flex items-center"><CalendarDays className="h-4 w-4 mr-2 text-primary" /> Ngày Diễn Ra (hoặc khoảng thời gian)</Label>
                <Input id="date" name="date" type="text" value={formData.date} onChange={handleChange} required className="light-theme-input mt-1" placeholder="VD: 15/06/2025 or 15/06 - 30/06/2025" />
              </div>
            </div>

            <div className="grid md:grid-cols-2 gap-6">
                <div>
                    <Label htmlFor="location" className="light-theme-text-default flex items-center"><MapPin className="h-4 w-4 mr-2 text-primary" /> Địa Điểm</Label>
                    <Input id="location" name="location" value={formData.location} onChange={handleChange} className="light-theme-input mt-1" />
                </div>
                <div>
                    <Label htmlFor="targetAudience" className="light-theme-text-default flex items-center"><Users className="h-4 w-4 mr-2 text-primary" /> Đối Tượng Mục Tiêu</Label>
                    <Input id="targetAudience" name="targetAudience" value={formData.targetAudience} onChange={handleChange} className="light-theme-input mt-1" />
                </div>
            </div>
            
            <div>
                <Label htmlFor="organizer" className="light-theme-text-default">Đơn Vị Tổ Chức</Label>
                <Input id="organizer" name="organizer" value={formData.organizer} onChange={handleChange} className="light-theme-input mt-1" />
            </div>

            <div>
              <Label htmlFor="description" className="light-theme-text-default">Mô Tả Chi Tiết</Label>
              <Textarea id="description" name="description" value={formData.description} onChange={handleChange} required className="light-theme-input mt-1 min-h-[120px]" />
            </div>
            
            <div>
              <Label htmlFor="imageUrl" className="light-theme-text-default flex items-center"><ImageIcon className="h-4 w-4 mr-2 text-primary" /> URL Hình Ảnh Minh Họa</Label>
              <Input id="imageUrl" name="imageUrl" value={formData.imageUrl} onChange={handleChange} className="light-theme-input mt-1" placeholder="https://example.com/program-image.jpg" />
            </div>

            <div>
              <Label htmlFor="activities" className="light-theme-text-default">Các Hoạt Động Chính (cách nhau bằng dấu phẩy)</Label>
              <Textarea id="activities" name="activities" value={formData.activities} onChange={handleChange} className="light-theme-input mt-1 min-h-[80px]" placeholder="VD: Triển lãm, Hội thảo, Phát tờ rơi" />
            </div>

            <CardFooter className="px-0 pt-6">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Chương Trình' : 'Tạo Chương Trình')}
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

export default ProgramFormPage;