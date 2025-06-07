import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, UserCog } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleCounselors = [
  { id: 'coun1', name: 'Chuyên viên Nguyễn Văn A', expertise: 'Tư vấn cai nghiện, hỗ trợ tâm lý', email: 'a.nguyen@tuvan.org', phone: '0901234567' },
  { id: 'coun2', name: 'Chuyên viên Trần Thị B', expertise: 'Tư vấn gia đình, phòng ngừa tái nghiện', email: 'b.tran@tuvan.org', phone: '0912345678' },
  { id: 'coun3', name: 'Chuyên viên Lê Văn C', expertise: 'Tư vấn cho thanh thiếu niên', email: 'c.le@tuvan.org', phone: '0923456789' },
];

const CounselorListPage = () => {
  const { toast } = useToast();

  const handleDelete = (counselorId, counselorName) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa chuyên viên "${counselorName}" không?`)) {
      console.log(`Deleting counselor ${counselorId}`);
      toast({
        title: "Chuyên viên đã được xóa (mô phỏng)",
        description: `Chuyên viên "${counselorName}" đã được xóa.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <UserCog className="w-8 h-8 mr-2 text-primary" /> Quản Lý Chuyên Viên Tư Vấn
        </h1>
        <Link to="/admin/counselors/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Thêm Chuyên Viên Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Chuyên Viên</CardTitle>
          <CardDescription className="light-theme-card-description">
            Quản lý thông tin các chuyên viên tư vấn.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Tên Chuyên Viên</TableHead>
                <TableHead className="font-semibold text-slate-700">Chuyên Môn</TableHead>
                <TableHead className="font-semibold text-slate-700">Email</TableHead>
                <TableHead className="font-semibold text-slate-700">Điện Thoại</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {sampleCounselors.map((counselor) => (
                <TableRow key={counselor.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{counselor.name}</TableCell>
                  <TableCell className="text-slate-600">{counselor.expertise}</TableCell>
                  <TableCell className="text-slate-600">{counselor.email}</TableCell>
                  <TableCell className="text-slate-600">{counselor.phone}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/counselors/edit/${counselor.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(counselor.id, counselor.name)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
           {!sampleCounselors.length && (
            <p className="text-center py-4 text-slate-500">Chưa có chuyên viên nào.</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default CounselorListPage;