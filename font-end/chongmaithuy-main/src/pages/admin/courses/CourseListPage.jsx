import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, BookOpen } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleCourses = [
  { id: 'course1', title: 'Nhận thức về Ma túy cho Học sinh', audience: 'Học sinh', duration: '4 tuần', instructor: 'ThS. Nguyễn Thị Lan' },
  { id: 'course2', title: 'Kỹ năng Phòng tránh Ma túy cho Sinh viên', audience: 'Sinh viên', duration: '6 tuần', instructor: 'TS. Trần Văn Minh' },
];

const CourseListPage = () => {
  const { toast } = useToast();

  const handleDelete = (courseId, courseTitle) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa khóa học "${courseTitle}" không?`)) {
      console.log(`Deleting course ${courseId}`);
      toast({
        title: "Khóa học đã được xóa (mô phỏng)",
        description: `Khóa học "${courseTitle}" đã được xóa.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <BookOpen className="w-8 h-8 mr-2 text-primary" /> Quản Lý Khóa Học
        </h1>
        <Link to="/admin/courses/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Thêm Khóa Học Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Khóa Học</CardTitle>
          <CardDescription className="light-theme-card-description">
            Quản lý các khóa học đào tạo online.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Tên Khóa Học</TableHead>
                <TableHead className="font-semibold text-slate-700">Đối Tượng</TableHead>
                <TableHead className="font-semibold text-slate-700">Thời Lượng</TableHead>
                <TableHead className="font-semibold text-slate-700">Giảng Viên</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {sampleCourses.map((course) => (
                <TableRow key={course.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{course.title}</TableCell>
                  <TableCell className="text-slate-600">{course.audience}</TableCell>
                  <TableCell className="text-slate-600">{course.duration}</TableCell>
                  <TableCell className="text-slate-600">{course.instructor}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/courses/edit/${course.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(course.id, course.title)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {!sampleCourses.length && (
            <p className="text-center py-4 text-slate-500">Chưa có khóa học nào.</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default CourseListPage;