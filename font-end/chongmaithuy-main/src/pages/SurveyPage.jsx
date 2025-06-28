import React from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { ClipboardList, PlayCircle } from 'lucide-react';
import { Link } from 'react-router-dom';

const surveys = [
  { id: 'assist', name: 'Khảo sát ASSIST', description: 'Đánh giá mức độ sử dụng rượu, thuốc lá và các chất gây nghiện khác.', image: "https://images.unsplash.com/photo-1576091160550-2173dba999ef?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
  { id: 'crafft', name: 'Khảo sát CRAFFT', description: 'Sàng lọc nguy cơ sử dụng chất gây nghiện cho thanh thiếu niên.', image: "https://images.unsplash.com/photo-1551884831-bbf3cdc6469e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
  { id: 'dast10', name: 'Khảo sát DAST-10', description: 'Sàng lọc nhanh các vấn đề liên quan đến ma túy.', image: "https://images.unsplash.com/photo-1622542796588-a23539468173?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
];

const SurveyPage = () => {
  return (
    <div className="space-y-8">
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <h1 className="text-4xl font-bold mb-4 gradient-text">Bài Khảo Sát Đánh Giá Nguy Cơ</h1>
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Thực hiện các bài khảo sát trắc nghiệm để hiểu rõ hơn về mức độ nguy cơ và nhận được đề xuất hành động phù hợp.
        </p>
      </section>

      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {surveys.map(survey => (
          <Card key={survey.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300">
            <CardHeader>
              <div className="flex items-center mb-3">
                <img  className="w-16 h-16 mr-4 rounded object-cover" alt={`${survey.name} icon`} src={survey.image} />
                <CardTitle className="text-2xl light-theme-card-header">{survey.name}</CardTitle>
              </div>
              <CardDescription className="light-theme-card-description h-20 overflow-hidden">{survey.description}</CardDescription>
            </CardHeader>
            <CardContent>
              <Link to={`/surveys/${survey.id}`}>
                <Button className="w-full light-theme-button-primary">
                  <PlayCircle className="h-5 w-5 mr-2" /> Bắt Đầu Khảo Sát
                </Button>
              </Link>
            </CardContent>
          </Card>
        ))}
      </section>

      <section className="mt-12 p-6 light-theme-card rounded-lg">
        <h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
          <ClipboardList className="h-6 w-6 mr-3 light-theme-text-accent"/> Lưu ý quan trọng
        </h2>
        <p className="light-theme-card-content">
          Kết quả từ các bài khảo sát này chỉ mang tính chất tham khảo và sàng lọc ban đầu. Chúng không thay thế cho việc chẩn đoán y khoa chuyên nghiệp. Nếu bạn có bất kỳ lo ngại nào về sức khỏe của mình hoặc người thân, vui lòng tìm kiếm sự tư vấn từ các chuyên gia y tế hoặc chuyên viên tư vấn của chúng tôi.
        </p>
      </section>
    </div>
  );
};

export default SurveyPage;