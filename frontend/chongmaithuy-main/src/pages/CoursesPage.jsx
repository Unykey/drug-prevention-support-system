import React, { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Search, BookMarked, ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";

const placeholderCourses = [
  {
    id: 1,
    title: "Nhận thức về Ma túy cho Học sinh",
    audience: "Học sinh",
    duration: "4 tuần",
    image: "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80"
  },
  {
    id: 2,
    title: "Kỹ năng Phòng tránh Ma túy cho Sinh viên",
    audience: "Sinh viên",
    duration: "6 tuần",
    image: "https://images.unsplash.com/photo-1543269865-cbf427effbad?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"
  },
  {
    id: 3,
    title: "Hướng dẫn Phụ huynh về Phòng chống Ma túy",
    audience: "Phụ huynh",
    duration: "3 tuần",
    image: "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80"
  },
  {
    id: 4,
    title: "Nâng cao Nghiệp vụ cho Giáo viên",
    audience: "Giáo viên",
    duration: "5 tuần",
    image: "https://images.unsplash.com/photo-1517048676732-d65bc937f952?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"
  },
];

const CoursesPage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedAudience, setSelectedAudience] = useState(''); // State for audience filter

  // Unique audience options for dropdown
  const audienceOptions = ['', ...new Set(placeholderCourses.map(course => course.audience))];

  // Filter courses based on search term and audience
  const filteredCourses = placeholderCourses.filter(course =>
      course.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (selectedAudience === '' || course.audience === selectedAudience)
  );

  return (
      <div className="space-y-8">
        <section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
          <h1 className="text-4xl font-bold mb-4 gradient-text">Khóa Học Phòng Chống Ma Túy</h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto">
            Trang bị kiến thức và kỹ năng cần thiết để bảo vệ bản thân và cộng đồng khỏi hiểm họa ma túy.
          </p>
        </section>

        <section>
          <div className="flex gap-2 mb-8 max-w-xl mx-auto">
            <Input
                type="text"
                placeholder="Tìm kiếm khóa học (ví dụ: nhận thức, kỹ năng từ chối...)"
                className="light-theme-input"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <select
                className="light-theme-input border rounded-md px-3 py-2"
                value={selectedAudience}
                onChange={(e) => setSelectedAudience(e.target.value)}
            >
              {audienceOptions.map(audience => (
                  <option key={audience || 'all'} value={audience}>
                    {audience || 'Tất cả đối tượng'}
                  </option>
              ))}
            </select>
            <Button className="light-theme-button-primary">
              <Search className="h-5 w-5 mr-2" /> Tìm Kiếm
            </Button>
          </div>
        </section>

        <section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredCourses.length > 0 ? (
              filteredCourses.map(course => (
                  <Card key={course.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300 flex flex-col">
                    <CardHeader>
                      <img
                          className="w-full h-40 object-cover rounded-t-lg mb-4"
                          alt={course.title}
                          src={course.image}
                      />
                      <CardTitle className="text-xl light-theme-card-header">{course.title}</CardTitle>
                      <CardDescription className="light-theme-card-description">
                        Đối tượng: {course.audience} | Thời lượng: {course.duration}
                      </CardDescription>
                    </CardHeader>
                    <CardContent className="flex-grow">
                      <p className="light-theme-card-content text-sm line-clamp-3">
                        Mô tả ngắn về khóa học, những kiến thức và kỹ năng chính mà học viên sẽ nhận được sau khi hoàn thành...
                      </p>
                    </CardContent>
                    <CardFooter className="flex-col items-start space-y-2">
                      <Link to={`/courses/${course.id}`} className="w-full">
                        <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90">
                          <BookMarked className="h-5 w-5 mr-2" /> Xem Chi Tiết
                        </Button>
                      </Link>
                      <Button variant="outline" className="w-full light-theme-button-outline font-semibold">
                        Đăng Ký Ngay <ArrowRight className="ml-2 h-4 w-4" />
                      </Button>
                    </CardFooter>
                  </Card>
              ))
          ) : (
              <p className="text-center col-span-full text-slate-600">
                Không tìm thấy khóa học phù hợp với từ khóa "{searchTerm}" và đối tượng "{selectedAudience || 'Tất cả'}"
              </p>
          )}
        </section>
      </div>
  );
};

export default CoursesPage;