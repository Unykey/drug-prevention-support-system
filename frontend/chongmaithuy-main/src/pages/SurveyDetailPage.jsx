// SurveyDetailPage.jsx: Trang khảo sát tương tác với step-by-step flow
import React, { useState } from 'react'; // React với useState để quản lý state phức tạp của survey
import { useParams, Link } from 'react-router-dom'; // useParams: lấy survey ID từ URL, Link: navigation
import { Button } from '@/components/ui/button'; // Component button với consistent styling
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card'; // Card structure cho survey layout
import { ArrowLeft, Check, HelpCircle, Send, RotateCcw } from 'lucide-react'; // Icons cho navigation, completion, help, submit, restart
import { motion } from 'framer-motion'; // Animation library cho smooth transitions giữa questions
import { Progress } from "@/components/ui/progress"; // Progress bar component để hiển thị tiến độ
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"; // Radio button components cho single choice questions
import { Label } from "@/components/ui/label"; // Label component với proper accessibility

// Mock data cho các bài khảo sát chuyên dụng - dựa trên các công cụ đánh giá thực tế
// Trong thực tế sẽ được thay thế bằng API calls để lấy survey data từ backend
const placeholderSurveys = {
  // ASSIST (Alcohol, Smoking and Substance Involvement Screening Test)
  // Công cụ sàng lọc của WHO để đánh giá mức độ rủi ro sử dụng chất gây nghiện
  assist: { 
    name: 'Khảo sát ASSIST', 
    description: 'Đánh giá mức độ sử dụng rượu, thuốc lá và các chất gây nghiện khác.',
    questions: [
      { 
        id: 'q1', 
        text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng thuốc lá không?', 
        options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] // Frequency scale từ thấp đến cao
      },
      { 
        id: 'q2', 
        text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng đồ uống có cồn không?', 
        options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] 
      },
      { 
        id: 'q3', 
        text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng cần sa không?', 
        options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] 
      },
    ]
  },
  
  // CRAFFT (Car, Relax, Alone, Forget, Friends, Trouble)
  // Công cụ sàng lọc chuyên dụng cho thanh thiếu niên về sử dụng chất gây nghiện
  crafft: { 
    name: 'Khảo sát CRAFFT', 
    description: 'Sàng lọc nguy cơ sử dụng chất gây nghiện cho thanh thiếu niên.',
    questions: [
      { 
        id: 'c1', 
        text: 'Bạn đã bao giờ đi XE (Car) do người đã sử dụng rượu bia hoặc chất gây nghiện (kể cả bạn) lái chưa?', 
        options: ['Có', 'Không'] // Binary questions cho screening nhanh
      },
      { 
        id: 'r1', 
        text: 'Bạn có bao giờ sử dụng rượu bia hoặc chất gây nghiện để THƯ GIÃN (Relax), cảm thấy tốt hơn về bản thân, hoặc để hòa nhập hơn không?', 
        options: ['Có', 'Không'] 
      },
      { 
        id: 'a1', 
        text: 'Bạn có bao giờ sử dụng rượu bia hoặc chất gây nghiện khi ở MỘT MÌNH (Alone) không?', 
        options: ['Có', 'Không'] 
      },
    ]
  },
  
  // DAST-10 (Drug Abuse Screening Test)
  // Công cụ sàng lọc nhanh các vấn đề liên quan đến ma túy
   dast10: { 
    name: 'Khảo sát DAST-10', 
    description: 'Sàng lọc nhanh các vấn đề liên quan đến ma túy.',
    questions: [
      { 
        id: 'd1', 
        text: 'Bạn có sử dụng các loại ma túy không phải vì mục đích y tế không?', 
        options: ['Có', 'Không'] // Yes/No format cho clinical screening
      },
      { 
        id: 'd2', 
        text: 'Bạn có lạm dụng nhiều hơn một loại ma túy cùng lúc không?', 
        options: ['Có', 'Không'] 
      },
    ]
  },
};


// Component chính quản lý flow khảo sát với state management phức tạp
const SurveyDetailPage = () => {
  // Lấy survey ID từ URL parameters (ví dụ: /surveys/assist -> id = "assist")
  const { id } = useParams();
  
  // Tìm survey data tương ứng với ID từ mock data
  const survey = placeholderSurveys[id];
  
  // State management cho survey flow
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0); // Index của câu hỏi hiện tại (0-based)
  const [answers, setAnswers] = useState({}); // Object lưu trữ tất cả answers: {questionId: selectedAnswer}
  const [showResults, setShowResults] = useState(false); // Flag để toggle giữa survey view và results view

  // Error handling: Hiển thị fallback nếu survey không tồn tại
  if (!survey) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy khảo sát</h1>
        {/* Navigation link quay về danh sách surveys */}
        <Link to="/surveys">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách khảo sát</Button>
        </Link>
      </div>
    );
  }
  
  // Computed values dựa trên current state
  const currentQuestion = survey.questions[currentQuestionIndex]; // Question object hiện tại
  const progress = ((currentQuestionIndex + 1) / survey.questions.length) * 100; // Progress percentage (1-100)

  // Handler để lưu answer cho một question cụ thể
  // Sử dụng functional update để đảm bảo state immutability
  const handleAnswer = (questionId, answer) => {
    setAnswers(prev => ({ ...prev, [questionId]: answer }));
  };

  // Handler cho navigation: next question hoặc finish survey
  const handleNext = () => {
    if (currentQuestionIndex < survey.questions.length - 1) {
      // Còn questions tiếp theo -> chuyển sang question tiếp theo
      setCurrentQuestionIndex(prev => prev + 1);
    } else {
      // Đã hết questions -> hiển thị results
      setShowResults(true);
    }
  };
  
  // Handler để reset survey về trạng thái ban đầu
  const handleRestart = () => {
    setCurrentQuestionIndex(0); // Reset về question đầu tiên
    setAnswers({}); // Clear tất cả answers
    setShowResults(false); // Quay về survey view
  };

  // Animation configuration cho smooth transitions
  const fadeIn = {
    hidden: { opacity: 0, y: 20 }, // Start: invisible và offset xuống
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } } // End: visible và về vị trí gốc
  };

  // Conditional rendering: Results view khi survey hoàn thành
  if (showResults) {
    return (
      // Results container với animation
      <motion.div 
        className="max-w-2xl mx-auto space-y-6"
        initial="hidden"
        animate="visible"
        variants={fadeIn}
      >
        {/* Success card với completion message */}
        <Card className="light-theme-card">
          <CardHeader className="text-center">
            {/* Large success icon để visual confirmation */}
            <Check className="h-16 w-16 text-green-500 mx-auto mb-4" />
            {/* Success title */}
            <CardTitle className="text-3xl light-theme-card-header">Hoàn Thành Khảo Sát!</CardTitle>
            {/* Thank you message với survey name */}
            <CardDescription className="light-theme-card-description">
              Cảm ơn bạn đã hoàn thành khảo sát "{survey.name}".
            </CardDescription>
          </CardHeader>
          
          <CardContent className="text-center">
            {/* Placeholder message cho results - trong thực tế sẽ có analysis logic */}
            <p className="light-theme-card-content mb-4">
              Dựa trên câu trả lời của bạn, chúng tôi sẽ sớm đưa ra những đề xuất phù hợp. 
              Trong thực tế, phần này sẽ hiển thị kết quả chi tiết và các khuyến nghị.
            </p>
            {/* Restart button cho user muốn làm lại */}
            <Button onClick={handleRestart} className="bg-accent text-accent-foreground hover:bg-accent/90">
              <RotateCcw className="h-4 w-4 mr-2"/> Làm lại khảo sát
            </Button>
          </CardContent>
          
          {/* Footer với navigation back to surveys list */}
           <CardFooter className="flex justify-center">
            <Link to="/surveys">
              <Button variant="outline" className="light-theme-button-outline">
                <ArrowLeft className="h-4 w-4 mr-2"/> Quay lại danh sách khảo sát
              </Button>
            </Link>
          </CardFooter>
        </Card>
      </motion.div>
    );
  }


  // Main survey view: Active question với interactive elements
  return (
    // Survey container với animation và responsive width
    <motion.div 
      className="max-w-2xl mx-auto space-y-6" // Centered container với optimal reading width
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      {/* Top navigation bar với progress indicator */}
      <div className="flex items-center justify-between">
        {/* Back to surveys list link */}
        <Link to="/surveys" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
          <ArrowLeft className="h-5 w-5 mr-2" />
          Trở về danh sách khảo sát
        </Link>
        {/* Question counter: current/total */}
        <span className="text-sm text-slate-500">
          Câu hỏi {currentQuestionIndex + 1} / {survey.questions.length}
        </span>
      </div>
      
      {/* Main survey card */}
      <Card className="light-theme-card">
        {/* Survey header với title và description */}
        <CardHeader>
          <CardTitle className="text-2xl gradient-text">{survey.name}</CardTitle>
          <CardDescription className="light-theme-card-description">{survey.description}</CardDescription>
        </CardHeader>
        
        <CardContent className="space-y-6">
          {/* Progress bar để hiển thị completion percentage */}
          {/* Custom styling với [&>div]:bg-primary để override default colors */}
          <Progress value={progress} className="w-full [&>div]:bg-primary" />
          
          {/* Current question section */}
          <div>
            {/* Question text */}
            <h2 className="text-xl font-semibold light-theme-card-header mb-4">{currentQuestion.text}</h2>
            
            {/* Answer options using RadioGroup for single selection */}
            <RadioGroup 
              value={answers[currentQuestion.id] || ""} // Controlled value từ answers state
              onValueChange={(value) => handleAnswer(currentQuestion.id, value)} // Update answer khi user select
              className="space-y-3"
            >
              {/* Map qua tất cả options để render radio buttons */}
              {currentQuestion.options.map((option, index) => (
                // Option container với hover effects và proper spacing
                <div key={index} className="flex items-center space-x-3 p-3 bg-slate-50 rounded-md hover:bg-slate-100 transition-colors border border-slate-200">
                  {/* Radio button với custom styling */}
                  <RadioGroupItem 
                    value={option} 
                    id={`${currentQuestion.id}-${index}`} 
                    className="border-primary text-primary focus:ring-primary checked:bg-primary" 
                  />
                  {/* Label text với proper accessibility linking */}
                  <Label 
                    htmlFor={`${currentQuestion.id}-${index}`} 
                    className="light-theme-text-default text-base cursor-pointer flex-1"
                  >
                    {option}
                  </Label>
                </div>
              ))}
            </RadioGroup>
          </div>
        </CardContent>
        
        {/* Card footer với next/complete button */}
        <CardFooter className="flex justify-end">
          <Button 
            onClick={handleNext} 
            disabled={!answers[currentQuestion.id]} // Disable nếu chưa chọn answer
            className="light-theme-button-primary"
          >
            {/* Dynamic button text dựa trên position trong survey */}
            {currentQuestionIndex < survey.questions.length - 1 ? 'Câu Tiếp Theo' : 'Hoàn Thành'}
            <Send className="h-4 w-4 ml-2"/>
          </Button>
        </CardFooter>
      </Card>
      
      {/* Privacy notice để tăng user confidence */}
      <div className="text-center text-sm text-slate-500 flex items-center justify-center">
        <HelpCircle className="h-4 w-4 mr-2"/>
        Mọi thông tin bạn cung cấp sẽ được bảo mật.
      </div>
    </motion.div>
  );
};

// Export component SurveyDetailPage làm default export
// Component này implement complete survey experience với:
// - Step-by-step question flow với progress tracking
// - Interactive radio button selections với validation
// - Smooth animations giữa questions và completion
// - Results view với restart functionality
// Có thể được extend để support multiple question types, scoring logic, và detailed results
export default SurveyDetailPage;