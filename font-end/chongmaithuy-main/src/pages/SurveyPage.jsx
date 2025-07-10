// SurveyPage.jsx: Trang danh sách các bài khảo sát đánh giá nguy cơ sử dụng chất gây nghiện
// Trang này cung cấp overview của các clinical screening tools có sẵn
import React from 'react'; // React core cho functional component
import { Button } from '@/components/ui/button'; // Component button với consistent styling
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'; // Card components để tạo structured layout cho từng survey
// Icons cho giao diện trực quan và meaningful
import { ClipboardList, PlayCircle } from 'lucide-react'; // ClipboardList: danh sách, PlayCircle: bắt đầu
import { Link } from 'react-router-dom'; // React Router Link cho SPA navigation đến survey details

// Danh sách các bài khảo sát có sẵn với thông tin chi tiết
// Dựa trên các công cụ sàng lọc thực tế được sử dụng trong clinical practice
const surveys = [
	{
		id: 'assist', // Unique identifier cho routing và data management
		name: 'Khảo sát ASSIST', // ASSIST = Alcohol, Smoking and Substance Involvement Screening Test
		description: 'Đánh giá mức độ sử dụng rượu, thuốc lá và các chất gây nghiện khác.', // WHO-approved screening tool
		image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80', // Visual representation
	},
	{
		id: 'crafft', // CRAFFT screening tool
		name: 'Khảo sát CRAFFT', // Car, Relax, Alone, Forget, Friends, Trouble
		description: 'Sàng lọc nguy cơ sử dụng chất gây nghiện cho thanh thiếu niên.', // Adolescent-specific screening
		image: 'https://images.unsplash.com/photo-1551884831-bbf3cdc6469e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80',
	},
	{
		id: 'dast10', // DAST-10 assessment
		name: 'Khảo sát DAST-10', // Drug Abuse Screening Test - 10 questions version
		description: 'Sàng lọc nhanh các vấn đề liên quan đến ma túy.', // Quick drug abuse screening
		image: 'https://images.unsplash.com/photo-1622542796588-a23539468173?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80',
	},
];

// Component chính hiển thị trang danh sách surveys
const SurveyPage = () => {
	return (
		// Main container với consistent spacing giữa sections
		<div className="space-y-8">
			{/* Phần header giới thiệu về trang khảo sát */}
			{/* Hero section với gradient background để tạo visual appeal */}
			<section className="text-center py-12 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg">
				{/* Main title với gradient text effect */}
				<h1 className="text-4xl font-bold mb-4 gradient-text">Bài Khảo Sát Đánh Giá Nguy Cơ</h1>
				{/* Value proposition và instruction cho users */}
				{/* max-w-2xl mx-auto: optimal reading width và centered */}
				<p className="text-lg text-slate-600 max-w-2xl mx-auto">
					Thực hiện các bài khảo sát trắc nghiệm để hiểu rõ hơn về mức độ nguy cơ và nhận được đề xuất hành động phù hợp.
				</p>
			</section>

			{/* Grid hiển thị danh sách các bài khảo sát dưới dạng card */}
			{/* Responsive grid layout: 1 → 2 → 3 columns theo screen size */}
			<section className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
				{/* Map through surveys để render individual cards */}
				{surveys.map((survey) => (
					// Individual survey card với hover effects
					<Card key={survey.id} className="light-theme-card hover:shadow-primary/15 transition-shadow duration-300">
						<CardHeader>
							{/* Hiển thị icon/ảnh và tên khảo sát */}
							{/* Horizontal layout với image và title cùng hàng */}
							<div className="flex items-center mb-3">
								{/* Survey thumbnail image với consistent size */}
								<img
									className="w-16 h-16 mr-4 rounded object-cover" // Fixed aspect ratio và rounded corners
									alt={`${survey.name} icon`} // Accessible alt text
									src={survey.image}
								/>
								{/* Survey title với prominent typography */}
								<CardTitle className="text-2xl light-theme-card-header">{survey.name}</CardTitle>
							</div>
							{/* Mô tả ngắn gọn về khảo sát */}
							{/* h-20 overflow-hidden: consistent card heights bằng cách fix description height */}
							<CardDescription className="light-theme-card-description h-20 overflow-hidden">
								{survey.description}
							</CardDescription>
						</CardHeader>
						<CardContent>
							{/* Nút bắt đầu khảo sát dẫn đến trang chi tiết */}
							{/* Link wrapper để entire button clickable for better UX */}
							<Link to={`/surveys/${survey.id}`}>
								{/* Full-width CTA button với meaningful icon */}
								<Button className="w-full light-theme-button-primary">
									<PlayCircle className="h-5 w-5 mr-2" /> Bắt Đầu Khảo Sát
								</Button>
							</Link>
						</CardContent>
					</Card>
				))}
			</section>

			{/* Phần lưu ý quan trọng về kết quả khảo sát */}
			{/* Disclaimer section để manage user expectations và legal compliance */}
			<section className="mt-12 p-6 light-theme-card rounded-lg">
				{/* Section header với icon để visual hierarchy */}
				<h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
					<ClipboardList className="h-6 w-6 mr-3 light-theme-text-accent" /> Lưu ý quan trọng
				</h2>
				{/* Important disclaimer về limitations của screening tools */}
				{/* Medical/legal disclaimer để protect users và organization */}
				<p className="light-theme-card-content">
					Kết quả từ các bài khảo sát này chỉ mang tính chất tham khảo và sàng lọc ban đầu. Chúng không thay thế cho việc
					chẩn đoán y khoa chuyên nghiệp. Nếu bạn có bất kỳ lo ngại nào về sức khỏe của mình hoặc người thân, vui lòng tìm
					kiếm sự tư vấn từ các chuyên gia y tế hoặc chuyên viên tư vấn của chúng tôi.
				</p>
			</section>
		</div>
	);
};

// Export component SurveyPage làm default export
// Component này cung cấp landing page cho clinical screening tools
// Kết hợp với SurveyDetailPage để tạo complete survey experience
// Design patterns: card-based listing, clear CTAs, professional disclaimers
export default SurveyPage;