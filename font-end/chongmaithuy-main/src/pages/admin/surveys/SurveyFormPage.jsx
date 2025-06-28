import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { ClipboardList, Save, ArrowLeft, PlusCircle, Trash2 } from 'lucide-react';
import { useToast } from '@/components/ui/use-toast';

const sampleSurveys = {
  survey1: { 
    title: 'Khảo sát ASSIST', 
    description: 'Đánh giá mức độ sử dụng chất.', 
    questions: [
      { id: 'q1_s1', text: 'Câu hỏi 1?', type: 'multiple-choice', options: 'A,B,C,D' },
      { id: 'q2_s1', text: 'Câu hỏi 2?', type: 'yes-no', options: 'Có,Không' },
    ] 
  },
};

const SurveyFormPage = () => {
  const { surveyId } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();
  const isEditing = Boolean(surveyId);

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    questions: [{ id: `q${Date.now()}`, text: '', type: 'multiple-choice', options: '' }],
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isEditing && surveyId && sampleSurveys[surveyId]) {
      setFormData(sampleSurveys[surveyId]);
    }
  }, [isEditing, surveyId]);

  const handleChange = (e, questionIndex) => {
    const { name, value } = e.target;
    if (questionIndex !== undefined) {
      const updatedQuestions = formData.questions.map((q, idx) => 
        idx === questionIndex ? { ...q, [name]: value } : q
      );
      setFormData(prev => ({ ...prev, questions: updatedQuestions }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const addQuestion = () => {
    setFormData(prev => ({
      ...prev,
      questions: [...prev.questions, { id: `q${Date.now()}`, text: '', type: 'multiple-choice', options: '' }]
    }));
  };

  const removeQuestion = (questionIndex) => {
    if (formData.questions.length <= 1) {
        toast({ title: "Không thể xóa", description: "Khảo sát phải có ít nhất một câu hỏi.", variant: "destructive"});
        return;
    }
    setFormData(prev => ({
      ...prev,
      questions: prev.questions.filter((_, idx) => idx !== questionIndex)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    await new Promise(resolve => setTimeout(resolve, 1000));
    setIsLoading(false);

    toast({
      title: `Khảo sát đã được ${isEditing ? 'cập nhật' : 'tạo'} (mô phỏng)`,
      description: `Khảo sát "${formData.title}" đã được lưu.`,
      variant: "default",
      className: "bg-green-500 text-white",
    });
    navigate('/admin/surveys');
  };

  return (
    <div className="space-y-6 max-w-4xl mx-auto">
      <Link to="/admin/surveys" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
        <ArrowLeft className="h-5 w-5 mr-2" />
        Quay lại danh sách khảo sát
      </Link>

      <Card className="light-theme-card">
        <CardHeader>
          <div className="flex items-center space-x-3 mb-2">
            <ClipboardList className="h-8 w-8 text-primary" />
            <CardTitle className="text-3xl gradient-text">
              {isEditing ? 'Chỉnh Sửa Khảo Sát' : 'Tạo Khảo Sát Mới'}
            </CardTitle>
          </div>
          <CardDescription className="light-theme-card-description">
            {isEditing ? `Cập nhật thông tin cho khảo sát "${formData.title}".` : 'Thiết kế bài khảo sát trắc nghiệm mới.'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="title" className="light-theme-text-default">Tên Khảo Sát</Label>
              <Input id="title" name="title" value={formData.title} onChange={handleChange} required className="light-theme-input mt-1" />
            </div>
            <div>
              <Label htmlFor="description" className="light-theme-text-default">Mô Tả Ngắn</Label>
              <Textarea id="description" name="description" value={formData.description} onChange={handleChange} className="light-theme-input mt-1 min-h-[80px]" />
            </div>

            <h3 className="text-xl font-semibold light-theme-card-header pt-4 border-t border-gray-200">Câu Hỏi Khảo Sát</h3>
            {formData.questions.map((q, index) => (
              <Card key={q.id || index} className="light-theme-card p-4 space-y-3 bg-slate-50">
                <div className="flex justify-between items-center">
                    <Label htmlFor={`q_text_${index}`} className="light-theme-text-default font-medium">Câu hỏi {index + 1}</Label>
                    <Button type="button" variant="ghost" size="sm" onClick={() => removeQuestion(index)} className="text-red-500 hover:text-red-700 hover:bg-red-50">
                        <Trash2 className="h-4 w-4 mr-1"/> Xóa câu hỏi này
                    </Button>
                </div>
                <Textarea id={`q_text_${index}`} name="text" value={q.text} onChange={(e) => handleChange(e, index)} required className="light-theme-input" placeholder="Nội dung câu hỏi"/>
                <div className="grid sm:grid-cols-2 gap-4">
                    <div>
                        <Label htmlFor={`q_type_${index}`} className="light-theme-text-default text-sm">Loại câu hỏi</Label>
                        <select id={`q_type_${index}`} name="type" value={q.type} onChange={(e) => handleChange(e, index)} className="w-full h-10 rounded-md border border-input bg-white px-3 py-2 text-sm text-slate-900 light-theme-input mt-1">
                            <option value="multiple-choice">Trắc nghiệm (Nhiều lựa chọn)</option>
                            <option value="single-choice">Trắc nghiệm (Một lựa chọn)</option>
                            <option value="yes-no">Có/Không</option>
                            <option value="likert-scale">Thang đo Likert (1-5)</option>
                        </select>
                    </div>
                    <div>
                        <Label htmlFor={`q_options_${index}`} className="light-theme-text-default text-sm">Các lựa chọn (cách nhau bằng dấu phẩy)</Label>
                        <Input id={`q_options_${index}`} name="options" value={q.options} onChange={(e) => handleChange(e, index)} className="light-theme-input mt-1" placeholder="VD: Lựa chọn 1,Lựa chọn 2"/>
                    </div>
                </div>
              </Card>
            ))}
            <Button type="button" variant="outline" onClick={addQuestion} className="light-theme-button-outline w-full sm:w-auto">
              <PlusCircle className="mr-2 h-5 w-5" /> Thêm Câu Hỏi
            </Button>

            <CardFooter className="px-0 pt-8 mt-4 border-t border-gray-200">
              <Button type="submit" className="light-theme-button-primary w-full" disabled={isLoading}>
                <Save className="mr-2 h-5 w-5" /> {isLoading ? 'Đang lưu...' : (isEditing ? 'Cập Nhật Khảo Sát' : 'Tạo Khảo Sát')}
              </Button>
            </CardFooter>
          </form>
        </CardContent>
      </Card>
      <div className="text-sm text-slate-500 p-4 bg-yellow-50 border border-yellow-200 rounded-md">
        <strong>Lưu ý:</strong> Chức năng này hiện tại chỉ là mô phỏng. Dữ liệu thực tế sẽ không được lưu trữ cho đến khi tích hợp Supabase.
      </div>
    </div>
  );
};

export default SurveyFormPage;