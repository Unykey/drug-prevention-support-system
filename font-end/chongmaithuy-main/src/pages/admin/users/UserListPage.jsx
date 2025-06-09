import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, Users } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleUsers = [
  { id: 'user1', name: 'Nguyễn Văn An', email: 'an.nguyen@example.com', role: 'Member', joinDate: '2024-01-15' },
  { id: 'user2', name: 'Trần Thị Bích', email: 'bich.tran@example.com', role: 'Staff', joinDate: '2024-02-20' },
  { id: 'user3', name: 'Lê Minh Cường', email: 'cuong.le@example.com', role: 'Consultant', joinDate: '2024-03-10' },
  { id: 'user4', name: 'Phạm Thu Hà', email: 'ha.pham@example.com', role: 'Admin', joinDate: '2023-12-01' },
];

const UserListPage = () => {
  const { toast } = useToast();

  const handleDelete = (userId, userName) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa người dùng "${userName}" không? Hành động này không thể hoàn tác.`)) {
      // Placeholder for actual delete logic
      console.log(`Deleting user ${userId}`);
      toast({
        title: "Người dùng đã được xóa (mô phỏng)",
        description: `Người dùng "${userName}" đã được xóa khỏi hệ thống.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <Users className="w-8 h-8 mr-2 text-primary" /> Quản Lý Người Dùng
        </h1>
        <Link to="/admin/users/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Thêm Người Dùng Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Người Dùng</CardTitle>
          <CardDescription className="light-theme-card-description">
            Xem và quản lý tất cả người dùng trong hệ thống.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Họ và Tên</TableHead>
                <TableHead className="font-semibold text-slate-700">Email</TableHead>
                <TableHead className="font-semibold text-slate-700">Vai Trò</TableHead>
                <TableHead className="font-semibold text-slate-700">Ngày Tham Gia</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {sampleUsers.map((user) => (
                <TableRow key={user.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{user.name}</TableCell>
                  <TableCell className="text-slate-600">{user.email}</TableCell>
                  <TableCell className="text-slate-600">{user.role}</TableCell>
                  <TableCell className="text-slate-600">{user.joinDate}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/users/edit/${user.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(user.id, user.name)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {!sampleUsers.length && (
            <p className="text-center py-4 text-slate-500">Chưa có người dùng nào. Hãy thêm người dùng mới!</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng xóa hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không bị ảnh hưởng cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default UserListPage;