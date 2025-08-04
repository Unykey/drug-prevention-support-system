import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { Newspaper, Save, ArrowLeft, User, Tag as TagIcon, Image as ImageIcon } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const samplePosts = {
  post1: { title: 'Tác hại của ma túy đá', author: 'BS. Nguyễn An', category: 'Kiến thức', content: 'Nội dung chi tiết...', imageUrl: 'https://example.com/image.jpg', tags: 'ma túy, tác hại' },
};

const BlogPostFormPage = () => {
  const { postId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(postId);

  const [formData, setFormData] = useState({
    title: '',
    author: '',
    category: '',
    content: '',
    imageUrl: '',
    tags: '', // Comma-separated
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && postId && samplePosts[postId]) {
      setFormData(samplePosts[postId]);
    }
  }, [isEditing, postId]);

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
      title: `Bài viết đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Bài viết "${formData.title}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/blog');
  };

  return (
    <div className="space-y-6 max-w-3xl mx-auto">
      <Link to="/admin/blog" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách bài viết
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            <Newspaper className="h-8 w-8 text-primary" />
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Bài Viết' : 'Tạo Bài Viết Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật nội dung cho bài viết "${formData.title}".` : 'Soạn thảo bài viết mới cho blog.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="title" className="light-theme-text-default">Tiêu Đề Bài Viết</Label>
              <Input id="title" name="title" value={formData.title} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="author" className="light-theme-text-default flex items-center"><User className="h-4 w-4 mr-2 text-primary" /> Tác Giả</Label>
                <Input id="author" name="author" value={formData.author} onChange={handleChange} required className="light-theme-input mt-1" />
              </div>
              <div>
                <Label htmlFor="category" className="light-theme-text-default flex items-center"><TagIcon className="h-4 w-4 mr-2 text-primary" /> Chuyên Mục</Label>
                <Input id="category" name="category" value={formData.category} onChange={handleChange} required className="light-theme-input mt-1" placeholder="VD: Kiến thức, Chia sẻ" />
              </div>
            </div>

            <div>
              <Label htmlFor="content" className="light-theme-text-default">Nội Dung Bài Viết (Hỗ trợ HTML cơ bản)</Label>
              <Textarea id="content" name="content" value={formData.content} onChange={handleChange} required className="light-theme-input mt-1 min-h-[200px]" />
            </div>
            
            <div>
              <Label htmlFor="imageUrl" className="light-theme-text-default flex items-center"><ImageIcon className="h-4 w-4 mr-2 text-primary" /> URL Hình Ảnh Nổi Bật</Label>
              <Input id="imageUrl" name="imageUrl" value={formData.imageUrl} onChange={handleChange} className="light-theme-input mt-1" placeholder="https://example.com/blog-image.jpg" />
            </div>

            <div>
              <Label htmlFor="tags" className="light-theme-text-default">Thẻ (cách nhau bằng dấu phẩy)</Label>
              <Input id="tags" name="tags" value={formData.tags} onChange={handleChange} className="light-theme-input mt-1" placeholder="VD: ma túy, phòng chống, thanh thiếu niên" />
            </div>

            <CardFooter className="px-0 pt-6">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Bài Viết' : 'Đăng Bài Viết')}
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

export default BlogPostFormPage;