import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { MailQuestion, Send, Mail } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const ForgotPasswordPage = () => {
  const [email, setEmail] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      toast({
        title: "Yêu cầu đã được gửi!",
        description: "Nếu email của bạn tồn tại trong hệ thống, bạn sẽ nhận được một liên kết để đặt lại mật khẩu.",
        variant: "default",
        className: "bg-green-500 text-white",
      });
    } catch (error) {
      toast({
        title: "Gửi yêu cầu thất bại",
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
          <div className="inline-block p-3 bg-primary/10 rounded-full mx-auto mb-4">
            <MailQuestion className="h-10 w-10 text-primary" />
          </div>
          <CardTitle className="text-3xl font-bold gradient-text">Quên Mật Khẩu</CardTitle>
          <CardDescription className="light-theme-card-description">
            Đừng lo lắng! Nhập email của bạn để nhận liên kết đặt lại mật khẩu.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="email" className="light-theme-text-default flex items-center">
                <Mail className="h-4 w-4 mr-2 text-primary" /> Email Đăng Ký
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
            <Button type="submit" className="w-full light-theme-button-primary text-lg py-3" disabled={isLoading}>
              {isLoading ? 'Đang gửi...' : (<><Send className="h-5 w-5 mr-2" /> Gửi Yêu Cầu</>)}
            </Button>
          </form>
        </CardContent>
        <CardFooter className="flex flex-col items-center space-y-3">
          <Link to="/login"
            className="text-sm text-accent hover:underline">
            Quay lại Đăng nhập
          </Link>
        </CardFooter>
      </Card>
    </div>
  );
};

export default ForgotPasswordPage;