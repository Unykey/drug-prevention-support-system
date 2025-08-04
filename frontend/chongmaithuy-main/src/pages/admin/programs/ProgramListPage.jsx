import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, Presentation } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const samplePrograms = [
  { id: 'prog1', title: "Chiến dịch 'Nói không với Ma túy'", type: 'Truyền thông', date: '2024-06-15', location: 'Cộng đồng' },
  { id: 'prog2', title: 'Workshop Kỹ năng Sống', type: 'Giáo dục', date: '2024-07-01', location: 'Trường học' },
];

const ProgramListPage = () => {
  const { toast } = useToast();

  const handleDelete = (programId, programTitle) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa chương trình "${programTitle}" không?`)) {
      console.log(`Deleting program ${programId}`);
      toast({
        title: "Chương trình đã được xóa (mô phỏng)",
        description: `Chương trình "${programTitle}" đã được xóa.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <Presentation className="w-8 h-8 mr-2 text-primary" /> Quản Lý Chương Trình
        </h1>
        <Link to="/admin/programs/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Thêm Chương Trình Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Chương Trình</CardTitle>
          <CardDescription className="light-theme-card-description">
            Quản lý các chương trình truyền thông và giáo dục cộng đồng.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Tên Chương Trình</TableHead>
                <TableHead className="font-semibold text-slate-700">Loại Hình</TableHead>
                <TableHead className="font-semibold text-slate-700">Ngày Diễn Ra</TableHead>
                <TableHead className="font-semibold text-slate-700">Địa Điểm/Đối Tượng</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {samplePrograms.map((program) => (
                <TableRow key={program.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{program.title}</TableCell>
                  <TableCell className="text-slate-600">{program.type}</TableCell>
                  <TableCell className="text-slate-600">{program.date}</TableCell>
                  <TableCell className="text-slate-600">{program.location}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/programs/edit/${program.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(program.id, program.title)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {!samplePrograms.length && (
            <p className="text-center py-4 text-slate-500">Chưa có chương trình nào.</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default ProgramListPage;