import React from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Users2, Presentation, CheckSquare } from 'lucide-react';
import { Link } from 'react-router-dom';

const programs = [
  { id: 1, title: "Chiến dịch 'Nói không với Ma túy'", type: "Truyền thông cộng đồng", date: "15/06/2025 - 30/06/2025", image: "https://images.unsplash.com/photo-1573167507997-41830a3f7575?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1169&q=80" },
  { id: 2, title: "Workshop Kỹ năng Sống cho Thanh Thiếu Niên", type: "Giáo dục", date: "Hàng tháng", image: "https://images.unsplash.com/photo-1552664730-d307ca884978?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
  { id: 3, title: "Tập huấn cho Tình nguyện viên", type: "Đào tạo", date: "01/07/2025", image: "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" },
];

const ProgramsPage = () => {
  return (
    <div className="space-y-8">
      <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
        <h1 className="text-4xl font-bold mb-4 gradient-text">Chương Trình Truyền Thông & Giáo Dục</h1>
        <p className="text-lg text-slate-600 max-w-2xl mx-auto">
          Tham gia các chương trình ý nghĩa để cùng chúng tôi lan tỏa thông điệp phòng chống ma túy và xây dựng cộng đồng lành mạnh.
        </p>
      </section>

      <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {programs.map(program => (
          <Card key={program.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300 flex flex-col">
            <CardHeader>
              <img  
                className="w-full h-40 object-cover rounded-t-lg mb-4" 
                alt={program.title}
               src={program.image} />
              <CardTitle className="text-xl light-theme-card-header">{program.title}</CardTitle>
              <CardDescription className="light-theme-card-description">Loại hình: {program.type} | Thời gian: {program.date}</CardDescription>
            </CardHeader>
            <CardContent className="flex-grow">
              <p className="light-theme-card-content text-sm line-clamp-3">Mô tả chi tiết về chương trình, mục tiêu, đối tượng tham gia và các hoạt động chính...</p>
            </CardContent>
            <CardFooter className="space-x-2">
              <Link to={`/programs/${program.id}`} className="flex-1">
                <Button variant="outline" className="w-full light-theme-button-outline font-semibold">
                  <Presentation className="h-5 w-5 mr-2" /> Chi Tiết
                </Button>
              </Link>
              <Button className="flex-1 bg-accent text-accent-foreground hover:bg-accent/90">
                <CheckSquare className="h-5 w-5 mr-2" /> Đăng Ký Tham Gia
              </Button>
            </CardFooter>
          </Card>
        ))}
      </section>

      <section className="mt-12 p-6 light-theme-card rounded-lg">
        <h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
            <Users2 className="h-6 w-6 mr-3 light-theme-text-accent"/> Khảo sát chương trình
        </h2>
        <p className="light-theme-card-content mb-4">
          Chúng tôi khuyến khích bạn thực hiện các bài khảo sát trước và sau khi tham gia chương trình. Phản hồi của bạn là vô cùng quý giá, giúp chúng tôi cải tiến và nâng cao chất lượng các hoạt động trong tương lai.
        </p>
        <Link to="/surveys">
            <Button className="bg-sky-600 text-white hover:bg-sky-700">
                Thực Hiện Khảo Sát Ngay
            </Button>
        </Link>
      </section>
    </div>
  );
};

export default ProgramsPage;