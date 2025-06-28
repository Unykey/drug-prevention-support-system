import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, Newspaper } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const samplePosts = [
  { id: 'post1', title: 'Tác hại của ma túy đá', author: 'BS. Nguyễn An', category: 'Kiến thức', publishedDate: '2024-05-20' },
  { id: 'post2', title: 'Vai trò gia đình trong phòng chống ma túy', author: 'Chuyên gia Trần Mai', category: 'Gia đình', publishedDate: '2024-05-15' },
];

const BlogPostListPage = () => {
  const { toast } = useToast();

  const handleDelete = (postId, postTitle) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa bài viết "${postTitle}" không?`)) {
      console.log(`Deleting post ${postId}`);
      toast({
        title: "Bài viết đã được xóa (mô phỏng)",
        description: `Bài viết "${postTitle}" đã được xóa.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <Newspaper className="w-8 h-8 mr-2 text-primary" /> Quản Lý Bài Viết Blog
        </h1>
        <Link to="/admin/blog/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Thêm Bài Viết Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Bài Viết</CardTitle>
          <CardDescription className="light-theme-card-description">
            Quản lý các bài viết trên blog.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Tiêu Đề</TableHead>
                <TableHead className="font-semibold text-slate-700">Tác Giả</TableHead>
                <TableHead className="font-semibold text-slate-700">Chuyên Mục</TableHead>
                <TableHead className="font-semibold text-slate-700">Ngày Đăng</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {samplePosts.map((post) => (
                <TableRow key={post.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{post.title}</TableCell>
                  <TableCell className="text-slate-600">{post.author}</TableCell>
                  <TableCell className="text-slate-600">{post.category}</TableCell>
                  <TableCell className="text-slate-600">{post.publishedDate}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/blog/edit/${post.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(post.id, post.title)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {!samplePosts.length && (
            <p className="text-center py-4 text-slate-500">Chưa có bài viết nào.</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default BlogPostListPage;