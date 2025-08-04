import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { UserPlus, Save, ArrowLeft, UserCog } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';
import { ROLES } from '@/config/roles';

const sampleUsers = {
  user4: { name: 'Phạm Thu Hà', email: 'ha.pham@example.com', role: ROLES.ADMIN },
};

const UserFormPage = () => {
  const { userId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(userId);

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: ROLES.MEMBER,
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && userId && sampleUsers[userId]) {
      const userData = sampleUsers[userId];
      setFormData({
        name: userData.name,
        email: userData.email,
        password: '', 
        confirmPassword: '',
        role: userData.role,
      });
    }
  }, [isEditing, userId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleRoleChange = (value) => {
    setFormData(prev => ({ ...prev, role: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!isEditing && formData.password !== formData.confirmPassword) {
      toast({ title: "Lỗi", description: "Mật khẩu và xác nhận mật khẩu không khớp.", variant: "destructive" });
      return;
    }
    if (!isEditing && !formData.password) {
      toast({ title: "Lỗi", description: "Mật khẩu là bắt buộc khi tạo người dùng mới.", variant: "destructive" });
      return;
    }

    setIsLoading(true);
    // Placeholder for actual API call
    await new Promise(resolve => setTimeout(resolve, 1000));
    setIsLoading(false);

    toast({
      title: `Người dùng đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Thông tin người dùng "${formData.name}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/users');
  };

  return (
    <div className="space-y-6 max-w-2xl mx-auto">
      <Link to="/admin/users" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách người dùng
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            {isEditing ? <UserCog className="h-8 w-8 text-primary" /> : <UserPlus className="h-8 w-8 text-primary" />}
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Người Dùng' : 'Tạo Người Dùng Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật thông tin cho người dùng ${formData.name}.` : 'Điền thông tin để tạo người dùng mới.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="name" className="light-theme-text-default">Họ và Tên</Label>
              <Input id="name" name="name" value={formData.name} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            <div>
              <Label htmlFor="email" className="light-theme-text-default">Email</Label>
              <Input id="email" name="email" type="email" value={formData.email} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            <div>
              <Label htmlFor="password">{isEditing ? 'Mật khẩu mới (để trống nếu không đổi)' : 'Mật khẩu'}</Label>
              <Input id="password" name="password" type="password" value={formData.password} onChange={handleChange} className="light-theme-input mt-1" />
            </div>
            {!isEditing && (
              <div>
                <Label htmlFor="confirmPassword">Xác nhận mật khẩu</Label>
                <Input id="confirmPassword" name="confirmPassword" type="password" value={formData.confirmPassword} onChange={handleChange} className="light-theme-input mt-1" />
              </div>
            )}
            <div>
              <Label htmlFor="role" className="light-theme-text-default">Vai trò</Label>
              <Select name="role" value={formData.role} onValueChange={handleRoleChange}>
                <SelectTrigger className="w-full light-theme-input mt-1">
                  <SelectValue placeholder="Chọn vai trò" />
                </SelectTrigger>
                <SelectContent>
                  {Object.values(ROLES).map(role => (
                    <SelectItem key={role} value={role}>{role}</SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
            <CardFooter className="px-0 pt-6">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Người Dùng' : 'Tạo Người Dùng')}
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

export default UserFormPage;