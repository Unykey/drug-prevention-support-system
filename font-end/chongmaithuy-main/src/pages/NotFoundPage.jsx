
import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { AlertTriangle } from 'lucide-react';

const NotFoundPage = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-[60vh] text-center px-4">
      <AlertTriangle className="w-24 h-24 text-destructive mb-8 animate-bounce" />
      <h1 className="text-6xl font-bold text-gray-100 mb-4">404</h1>
      <h2 className="text-3xl font-semibold text-gray-300 mb-6">Trang Không Tìm Thấy</h2>
      <p className="text-lg text-gray-400 mb-8 max-w-md">
        Xin lỗi, trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.
      </p>
      <Link to="/">
        <Button size="lg" className="bg-primary hover:bg-primary/90 text-primary-foreground">
          Quay Về Trang Chủ
        </Button>
      </Link>
    </div>
  );
};

export default NotFoundPage;
  