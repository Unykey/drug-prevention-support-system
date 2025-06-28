import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { PlusCircle, Edit, Trash2, ClipboardList, BarChart } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleSurveys = [
  { id: 'survey1', title: 'Khảo sát ASSIST', description: 'Đánh giá mức độ sử dụng chất.', questionCount: 8, responseCount: 150 },
  { id: 'survey2', title: 'Khảo sát CRAFFT', description: 'Sàng lọc nguy cơ cho thanh thiếu niên.', questionCount: 6, responseCount: 230 },
  { id: 'survey3', title: 'Khảo sát DAST-10', description: 'Sàng lọc nhanh vấn đề ma túy.', questionCount: 10, responseCount: 95 },
];

const SurveyListPage = () => {
  const { toast } = useToast();

  const handleDelete = (surveyId, surveyTitle) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa khảo sát "${surveyTitle}" không? Tất cả dữ liệu phản hồi liên quan cũng sẽ bị xóa.`)) {
      console.log(`Deleting survey ${surveyId}`);
      toast({
        title: "Khảo sát đã được xóa (mô phỏng)",
        description: `Khảo sát "${surveyTitle}" đã được xóa.`,
        variant: "default",
        className: "bg-green-500 text-white",
      });
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold gradient-text flex items-center">
          <ClipboardList className="w-8 h-8 mr-2 text-primary" /> Quản Lý Khảo Sát
        </h1>
        <Link to="/admin/surveys/new">
          <Button className="light-theme-button-primary">
            <PlusCircle className="mr-2 h-5 w-5" /> Tạo Khảo Sát Mới
          </Button>
        </Link>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="light-theme-card-header">Danh Sách Khảo Sát</CardTitle>
          <CardDescription className="light-theme-card-description">
            Quản lý các bài khảo sát trắc nghiệm.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="font-semibold text-slate-700">Tên Khảo Sát</TableHead>
                <TableHead className="font-semibold text-slate-700">Mô Tả Ngắn</TableHead>
                <TableHead className="font-semibold text-slate-700 text-center">Số Câu Hỏi</TableHead>
                <TableHead className="font-semibold text-slate-700 text-center">Lượt Trả Lời</TableHead>
                <TableHead className="text-right font-semibold text-slate-700">Hành Động</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {sampleSurveys.map((survey) => (
                <TableRow key={survey.id} className="hover:bg-slate-50">
                  <TableCell className="font-medium text-slate-800">{survey.title}</TableCell>
                  <TableCell className="text-slate-600">{survey.description}</TableCell>
                  <TableCell className="text-slate-600 text-center">{survey.questionCount}</TableCell>
                  <TableCell className="text-slate-600 text-center">{survey.responseCount}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Link to={`/admin/surveys/results/${survey.id}`}>
                      <Button variant="outline" size="sm" className="border-green-500 text-green-500 hover:bg-green-50 hover:text-green-600">
                        <BarChart className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Link to={`/admin/surveys/edit/${survey.id}`}>
                      <Button variant="outline" size="sm" className="border-blue-500 text-blue-500 hover:bg-blue-50 hover:text-blue-600">
                        <Edit className="h-4 w-4" />
                      </Button>
                    </Link>
                    <Button
                      variant="outline"
                      size="sm"
                      className="border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600"
                      onClick={() => handleDelete(survey.id, survey.title)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {!sampleSurveys.length && (
            <p className="text-center py-4 text-slate-500">Chưa có khảo sát nào.</p>
          )}
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default SurveyListPage;