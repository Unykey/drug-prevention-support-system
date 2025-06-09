import React, { useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from '@/components/ui/card';
import { ArrowLeft, Check, HelpCircle, Send, RotateCcw } from 'lucide-react';
import { motion } from 'framer-motion';
import { Progress } from "@/components/ui/progress";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";

const placeholderSurveys = {
  assist: { 
    name: 'Khảo sát ASSIST', 
    description: 'Đánh giá mức độ sử dụng rượu, thuốc lá và các chất gây nghiện khác.',
    questions: [
      { id: 'q1', text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng thuốc lá không?', options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] },
      { id: 'q2', text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng đồ uống có cồn không?', options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] },
      { id: 'q3', text: 'Trong 3 tháng qua, bạn có thường xuyên sử dụng cần sa không?', options: ['Không bao giờ', '1-2 lần', 'Hàng tháng', 'Hàng tuần', 'Hàng ngày hoặc gần như hàng ngày'] },
    ]
  },
  crafft: { 
    name: 'Khảo sát CRAFFT', 
    description: 'Sàng lọc nguy cơ sử dụng chất gây nghiện cho thanh thiếu niên.',
    questions: [
      { id: 'c1', text: 'Bạn đã bao giờ đi XE (Car) do người đã sử dụng rượu bia hoặc chất gây nghiện (kể cả bạn) lái chưa?', options: ['Có', 'Không'] },
      { id: 'r1', text: 'Bạn có bao giờ sử dụng rượu bia hoặc chất gây nghiện để THƯ GIÃN (Relax), cảm thấy tốt hơn về bản thân, hoặc để hòa nhập hơn không?', options: ['Có', 'Không'] },
      { id: 'a1', text: 'Bạn có bao giờ sử dụng rượu bia hoặc chất gây nghiện khi ở MỘT MÌNH (Alone) không?', options: ['Có', 'Không'] },
    ]
  },
   dast10: { 
    name: 'Khảo sát DAST-10', 
    description: 'Sàng lọc nhanh các vấn đề liên quan đến ma túy.',
    questions: [
      { id: 'd1', text: 'Bạn có sử dụng các loại ma túy không phải vì mục đích y tế không?', options: ['Có', 'Không'] },
      { id: 'd2', text: 'Bạn có lạm dụng nhiều hơn một loại ma túy cùng lúc không?', options: ['Có', 'Không'] },
    ]
  },
};


const SurveyDetailPage = () => {
  const { id } = useParams();
  const survey = placeholderSurveys[id];
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [showResults, setShowResults] = useState(false);

  if (!survey) {
    return (
      <div className="text-center py-10">
        <h1 className="text-2xl font-semibold light-theme-text-default">Không tìm thấy khảo sát</h1>
        <Link to="/surveys">
          <Button variant="link" className="light-theme-text-primary mt-4">Quay lại danh sách khảo sát</Button>
        </Link>
      </div>
    );
  }
  
  const currentQuestion = survey.questions[currentQuestionIndex];
  const progress = ((currentQuestionIndex + 1) / survey.questions.length) * 100;

  const handleAnswer = (questionId, answer) => {
    setAnswers(prev => ({ ...prev, [questionId]: answer }));
  };

  const handleNext = () => {
    if (currentQuestionIndex < survey.questions.length - 1) {
      setCurrentQuestionIndex(prev => prev + 1);
    } else {
      setShowResults(true);
    }
  };
  
  const handleRestart = () => {
    setCurrentQuestionIndex(0);
    setAnswers({});
    setShowResults(false);
  };

  const fadeIn = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.5 } }
  };

  if (showResults) {
    return (
      <motion.div 
        className="max-w-2xl mx-auto space-y-6"
        initial="hidden"
        animate="visible"
        variants={fadeIn}
      >
        <Card className="light-theme-card">
          <CardHeader className="text-center">
            <Check className="h-16 w-16 text-green-500 mx-auto mb-4" />
            <CardTitle className="text-3xl light-theme-card-header">Hoàn Thành Khảo Sát!</CardTitle>
            <CardDescription className="light-theme-card-description">Cảm ơn bạn đã hoàn thành khảo sát "{survey.name}".</CardDescription>
          </CardHeader>
          <CardContent className="text-center">
            <p className="light-theme-card-content mb-4">
              Dựa trên câu trả lời của bạn, chúng tôi sẽ sớm đưa ra những đề xuất phù hợp. 
              Trong thực tế, phần này sẽ hiển thị kết quả chi tiết và các khuyến nghị.
            </p>
            <Button onClick={handleRestart} className="bg-accent text-accent-foreground hover:bg-accent/90">
              <RotateCcw className="h-4 w-4 mr-2"/> Làm lại khảo sát
            </Button>
          </CardContent>
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


  return (
    <motion.div 
      className="max-w-2xl mx-auto space-y-6"
      initial="hidden"
      animate="visible"
      variants={fadeIn}
    >
      <div className="flex items-center justify-between">
        <Link to="/surveys" className="inline-flex items-center light-theme-text-primary hover:underline font-medium">
          <ArrowLeft className="h-5 w-5 mr-2" />
          Trở về danh sách khảo sát
        </Link>
        <span className="text-sm text-slate-500">Câu hỏi {currentQuestionIndex + 1} / {survey.questions.length}</span>
      </div>
      
      <Card className="light-theme-card">
        <CardHeader>
          <CardTitle className="text-2xl gradient-text">{survey.name}</CardTitle>
          <CardDescription className="light-theme-card-description">{survey.description}</CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          <Progress value={progress} className="w-full [&>div]:bg-primary" />
          <div>
            <h2 className="text-xl font-semibold light-theme-card-header mb-4">{currentQuestion.text}</h2>
            <RadioGroup 
              value={answers[currentQuestion.id] || ""} 
              onValueChange={(value) => handleAnswer(currentQuestion.id, value)}
              className="space-y-3"
            >
              {currentQuestion.options.map((option, index) => (
                <div key={index} className="flex items-center space-x-3 p-3 bg-slate-50 rounded-md hover:bg-slate-100 transition-colors border border-slate-200">
                  <RadioGroupItem value={option} id={`${currentQuestion.id}-${index}`} className="border-primary text-primary focus:ring-primary checked:bg-primary" />
                  <Label htmlFor={`${currentQuestion.id}-${index}`} className="light-theme-text-default text-base cursor-pointer flex-1">{option}</Label>
                </div>
              ))}
            </RadioGroup>
          </div>
        </CardContent>
        <CardFooter className="flex justify-end">
          <Button 
            onClick={handleNext} 
            disabled={!answers[currentQuestion.id]}
            className="light-theme-button-primary"
          >
            {currentQuestionIndex < survey.questions.length - 1 ? 'Câu Tiếp Theo' : 'Hoàn Thành'}
            <Send className="h-4 w-4 ml-2"/>
          </Button>
        </CardFooter>
      </Card>
      
      <div className="text-center text-sm text-slate-500 flex items-center justify-center">
        <HelpCircle className="h-4 w-4 mr-2"/>
        Mọi thông tin bạn cung cấp sẽ được bảo mật.
      </div>
    </motion.div>
  );
};

export default SurveyDetailPage;