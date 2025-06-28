import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { LogIn, Mail, KeyRound } from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { useToast } from '@/components/ui/use-toast';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();
  const { toast } = useToast();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      await new Promise(resolve => setTimeout(resolve, 1000));
      const mockUser = { email, name: 'Người dùng Test', role: email.includes('admin') ? 'Admin' : 'Member' };
      login(mockUser);

      toast({
        title: "Đăng nhập thành công!",
        description: `Chào mừng trở lại, ${mockUser.name || mockUser.email}!`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
      navigate('/');
    } catch (error) {
      toast({
        title: "Đăng nhập thất bại",
        description: error.message || "Email hoặc mật khẩu không đúng. Vui lòng thử lại.",
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
          <div className="inline-block p-3 bg-primary/10 rounded-full mx-auto mb-4">
            <LogIn className="h-10 w-10 text-primary" />
          </div>
          <CardTitle className="text-3xl font-bold gradient-text">Đăng Nhập</CardTitle>
          <CardDescription className="light-theme-card-description">
            Chào mừng trở lại! Vui lòng nhập thông tin của bạn.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="email" className="light-theme-text-default flex items-center">
                <Mail className="h-4 w-4 mr-2 text-primary" /> Email
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
            <div className="space-y-2">
              <Label htmlFor="password" className="light-theme-text-default flex items-center">
                <KeyRound className="h-4 w-4 mr-2 text-primary" /> Mật Khẩu
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
            <Button type="submit" className="w-full light-theme-button-primary text-lg py-3" disabled={isLoading}>
              {isLoading ? 'Đang xử lý...' : 'Đăng Nhập'}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="flex flex-col items-center space-y-3">
          <Link to="/forgot-password"
            className="text-sm text-primary hover:underline">
            Quên mật khẩu?
          </Link>
          <p className="text-sm text-slate-500">
            Chưa có tài khoản?{' '}
            <Link to="/register" className="font-semibold text-accent hover:underline">
              Đăng ký ngay
            </Link>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
};

export default LoginPage;