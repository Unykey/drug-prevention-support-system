import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { UserPlus, Mail, KeyRound, User } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const RegisterPage = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      toast({
        title: "Lỗi đăng ký",
        description: "Mật khẩu và xác nhận mật khẩu không khớp.",
        variant: "destructive",
      });
      return;
    }
    setIsLoading(true);
    try {
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      toast({
        title: "Đăng ký thành công!",
        description: "Tài khoản của bạn đã được tạo. Vui lòng đăng nhập.",
        variant: "default",
        className: "bg-green-500 text-white",
      });
      navigate('/login');
    } catch (error) {
      toast({
        title: "Đăng ký thất bại",
        description: error.message || "Đã có lỗi xảy ra. Vui lòng thử lại.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-200px)] py-12">
      <Card className="w-full max-w-md light-theme-card shadow-2xl">
        <CardHeader className="text-center">
          <div className="inline-block p-3 bg-accent/10 rounded-full mx-auto mb-4">
            <UserPlus className="h-10 w-10 text-accent" />
          </div>
          <CardTitle className="text-3xl font-bold gradient-text">Tạo Tài Khoản</CardTitle>
          <CardDescription className="light-theme-card-description">
            Tham gia cộng đồng của chúng tôi ngay hôm nay!
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-1">
              <Label htmlFor="name" className="light-theme-text-default flex items-center">
                <User className="h-4 w-4 mr-2 text-accent" /> Họ và Tên
              </Label>
              <Input
                id="name"
                type="text"
                placeholder="Nguyễn Văn A"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
                className="light-theme-input"
              />
            </div>
            <div className="space-y-1">
              <Label htmlFor="email" className="light-theme-text-default flex items-center">
                <Mail className="h-4 w-4 mr-2 text-accent" /> Email
              </Label>
              <Input
                id="email"
                type="email"
                placeholder="nhapemail@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="light-theme-input"
              />
            </div>
            <div className="space-y-1">
              <Label htmlFor="password" className="light-theme-text-default flex items-center">
                <KeyRound className="h-4 w-4 mr-2 text-accent" /> Mật Khẩu
              </Label>
              <Input
                id="password"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="light-theme-input"
              />
            </div>
            <div className="space-y-1">
              <Label htmlFor="confirmPassword" className="light-theme-text-default flex items-center">
                <KeyRound className="h-4 w-4 mr-2 text-accent" /> Xác Nhận Mật Khẩu
              </Label>
              <Input
                id="confirmPassword"
                type="password"
                placeholder="••••••••"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="light-theme-input"
              />
            </div>
            <Button type="submit" className="w-full bg-accent text-accent-foreground hover:bg-accent/90 text-lg py-3" disabled={isLoading}>
              {isLoading ? 'Đang xử lý...' : 'Đăng Ký'}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="flex flex-col items-center space-y-3">
          <p className="text-sm text-slate-500">
            Đã có tài khoản?{' '}
            <Link to="/login" className="font-semibold text-primary hover:underline">
              Đăng nhập
            </Link>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
};

export default RegisterPage;