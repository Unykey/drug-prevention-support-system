// Import các thư viện và component cần thiết
import React, {useEffect, useState} from 'react'; // React core và useState hook để quản lý state
import {Link, useNavigate} from 'react-router-dom'; // Router để điều hướng giữa các trang
import {Button} from '@/components/ui/button'; // Component button tùy chỉnh
import {Input} from '@/components/ui/input'; // Component input tùy chỉnh
import {Label} from '@/components/ui/label'; // Component label tùy chỉnh
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from '@/components/ui/card'; // Các component card để tạo layout
import {UserPlus, Mail, KeyRound, User} from 'lucide-react'; // Icons từ thư viện Lucide React
import {useToast} from '@/components/ui/use-toast'; // Hook để hiển thị thông báo toast

// Component chính cho trang đăng ký
const RegisterPage = () => {
    // Quản lý state cho các trường input
    const [name, setName] = useState(''); // Tên người dùng
    const [birthday, setBirthday] = useState(''); // Ngày sinh
    const [age, setAge] = useState(null);
    const [birthdayError, setBirthdayError] = useState('');
    const [gender, setGender] = useState(''); // Giới tính
    const [email, setEmail] = useState(''); // Email đăng ký
    const [phone, setPhone] = useState(''); // Số điện thoại
    const [password, setPassword] = useState(''); // Mật khẩu
    const [confirmPassword, setConfirmPassword] = useState(''); // Xác nhận mật khẩu

    const [guardianName, setGuardianName] = useState(''); // Tên người giám hộ
    const [guardianEmail, setGuardianEmail] = useState(''); // Email người giám hộ
    const [guardianPhone, setGuardianPhone] = useState(''); // Số điện thoại người giám hộ

    const [isLoading, setIsLoading] = useState(false); // Trạng thái loading khi đang xử lý đăng ký

    // Hooks để điều hướng và hiển thị thông báo
    const navigate = useNavigate(); // Hook để chuyển hướng trang
    const {toast} = useToast(); // Hook để hiển thị thông báo toast

    // Hàm xử lý submit form đăng ký
    const handleSubmit = async (e) => {
        e.preventDefault(); // Ngăn chặn hành vi submit mặc định của form

        // Kiểm tra xem đã nhập email hoặc số điện thoại chưa
        if (!email.trim() && !phone.trim()) {
            toast({
                title: "Lỗi đăng ký",
                description: "Vui lòng nhập email hoặc số điện thoại.",
                variant: "destructive", // Hiển thị toast với style lỗi
            })
            return;
        }

        // Kiểm tra xem đã nhập email hoặc số điện thoại chưa
        if (age != null && age < 18) {
            if (!guardianEmail.trim() || !guardianPhone.trim()) {
                toast({
                    title: "Lỗi đăng ký",
                    description: "Vui lòng nhập email hoặc số điện thoại của người giám hộ.",
                    variant: "destructive", // Hiển thị toast với style lỗi
                })
                return;
            }
        }

        // Kiểm tra xác nhận mật khẩu có khớp không
        if (password !== confirmPassword) {
            toast({
                title: "Lỗi đăng ký",
                description: "Mật khẩu và xác nhận mật khẩu không khớp.",
                variant: "destructive", // Hiển thị toast với style lỗi
            });
            return; // Dừng xử lý nếu mật khẩu không khớp
        }

        setIsLoading(true); // Bật trạng thái loading
        try {
            // Mô phỏng API call đăng ký (hiện tại là delay 1 giây)
            // TODO: Thay thế bằng API call thực tế đến backend Spring Boot
            await new Promise(resolve => setTimeout(resolve, 1000));

            // Hiển thị thông báo đăng ký thành công
            toast({
                title: "Đăng ký thành công!",
                description: "Tài khoản của bạn đã được tạo. Vui lòng đăng nhập.",
                variant: "default",
                className: "bg-green-500 text-white", // Style màu xanh cho thành công
            });

            // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
            navigate('/login');
        } catch (error) {
            // Xử lý lỗi khi đăng ký thất bại
            toast({
                title: "Đăng ký thất bại",
                description: error.message || "Đã có lỗi xảy ra. Vui lòng thử lại.",
                variant: "destructive", // Hiển thị toast với style lỗi
            });
        } finally {
            setIsLoading(false); // Tắt trạng thái loading dù thành công hay thất bại
        }
    };

    useEffect(() => {
        if (birthday) {
            const brithDate = new Date(birthday);

            // Check if valid full date
            if (isNaN(brithDate.getTime())) {
                //  Don't show any error yet — wait for full date
                setBirthdayError('');
                return;
            }

            const currentDate = new Date();

            if (brithDate > currentDate) {
                setBirthdayError("Ngày sinh không được quá ngày hiện tại.");
                setAge(null);
                return;
            }

            if (brithDate.getFullYear() < 1900) {
                setBirthdayError("Năm sinh không hợp lệ");
                setAge(null);
                return;
            }


            const age = currentDate.getFullYear() - brithDate.getFullYear();
            const month = currentDate.getMonth() - brithDate.getMonth();
            const day = currentDate.getDate() - brithDate.getDate();
            const realAge = month < 0 || (month === 0 && day < 0) ? age - 1 : age;

            setAge(age);
            if (currentDate.getFullYear() - brithDate.getFullYear() < 18) {
                setBirthdayError("Hãy nhập thông tin của người giám hộ.");
            } else {
                setBirthdayError("");
            }
        } else {
            setAge(null);
            setBirthdayError("");
        }
    }, [birthday]);


    return (
        // Container chính với layout flex căn giữa, chiều cao tối thiểu và padding
        <div className="flex items-center justify-center min-h-[calc(100vh-200px)] py-12">
            {/* Card chính chứa form đăng ký với shadow và theme sáng */}
            <Card className="w-full max-w-md light-theme-card shadow-2xl">
                {/* Header của card với tiêu đề và mô tả */}
                <CardHeader className="text-center">
                    {/* Icon container với background accent và border radius */}
                    <div className="inline-block p-3 bg-accent/10 rounded-full mx-auto mb-4">
                        <UserPlus className="h-10 w-10 text-accent"/>
                    </div>
                    {/* Tiêu đề chính với gradient text */}
                    <CardTitle className="text-3xl font-bold gradient-text">Tạo Tài Khoản</CardTitle>
                    {/* Mô tả phụ với style cho light theme */}
                    <CardDescription className="light-theme-card-description">
                        Tham gia cộng đồng của chúng tôi ngay hôm nay!
                    </CardDescription>
                </CardHeader>

                {/* Content chính chứa form đăng ký */}
                <CardContent>
                    {/* Form đăng ký với spacing giữa các field */}
                    <form onSubmit={handleSubmit} className="space-y-4">
                        {/* Field nhập tên người dùng */}
                        <div className="space-y-1">
                            <Label htmlFor="name" className="light-theme-text-default flex items-center">
                                <User className="h-4 w-4 mr-2 text-accent"/> Họ và Tên
                            </Label>
                            <Input
                                id="name"
                                type="text"
                                placeholder="Nguyễn Văn A"
                                value={name}
                                onChange={(e) => setName(e.target.value)} // Cập nhật state khi user nhập
                                required // Trường bắt buộc
                                className="light-theme-input"
                            />
                        </div>

                        {/* Field nhập ngày tháng năm sinh */}
                        <div className="space-y-1">
                            <Label htmlFor="birthday" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Ngày Tháng Năm Sinh
                            </Label>
                            <Input
                                id="birthday"
                                type="date"
                                value={birthday}
                                onChange={(e) => setBirthday(e.target.value)} // Cập nhật state khi user nhập
                                required // Trường bắt buộc
                                className="light-theme-input"
                            />
                            {/* Hiển thị lỗi ngày sinh */
                                birthdayError && <p className="text-red-500 teset-sm">{birthdayError}</p>}
                        </div>

                        {/* Guardian Info - show only if age is less than 18*/}
                        {age != null && age < 18 && (
                            <div className="border rounded-x1 p-4 bg-gray-200">
                                <h2 className="text-lg font-semibold text-blue-700 mb-2">
                                    Thông tin người giám hộ
                                </h2>

                                <div className="space-y-4">
                                    {/* Field nhập tên người giám hộ */}
                                    <div className="space-y-1">
                                        <Label htmlFor="guardianName"
                                               className="light-theme-text-default flex items-center">
                                            <User className="h-4 w-4 mr-2 text-accent"/> Họ và Tên người giám hộ
                                        </Label>
                                        <Input
                                            id="guardianName"
                                            type="text"
                                            placeholder="Nguyễn Văn A"
                                            value={guardianName}
                                            onChange={(e) => setGuardianName(e.target.value)} // Cập nhật state khi user nhập
                                            required // Trường bắt buộc
                                            className="light-theme-input"
                                        />
                                    </div>
                                    {/* Field nhập email người giám hộ */}
                                    <div className="space-y-1">
                                        <Label htmlFor="guardianGmail"
                                               className="light-theme-text-default flex items-center">
                                            <KeyRound className="h-4 w-4 mr-2 text-accent"/> Email người giám hộ
                                        </Label>
                                        <Input
                                            id="guardianGmail"
                                            type="text"
                                            placeholder="email@example.com"
                                            value={guardianEmail}
                                            onChange={(e) => setGuardianEmail(e.target.value)} // Cập nhật state khi user nhập
                                            className="light-theme-input"
                                        />
                                    </div>
                                    {/* Field nhập phone người giám hộ */}
                                    <div className="space-y-1">
                                        <Label htmlFor="guardianPhone"
                                               className="light-theme-text-default flex items-center">
                                            <KeyRound className="h-4 w-4 mr-2 text-accent"/> Số điện thoại người giám hộ
                                        </Label>
                                        <Input
                                            id="guardianPhone"
                                            type="text"
                                            placeholder="0123456789"
                                            value={guardianPhone}
                                            onChange={(e) => setGuardianPhone(e.target.value)} // Cập nhật state khi user nhập
                                            className="light-theme-input"
                                        />
                                    </div>
                                </div>
                            </div>
                        )}

                        {/* Field nhập giới tính */}
                        <div className="space-y-1">
                            <Label htmlFor="gender" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Giới Tính
                            </Label>
                            <select
                                id="gender"
                                value={gender}
                                onChange={(e) => setGender(e.target.value)} // Cập nhật state khi user nhập
                                className="light-theme-input"
                            >
                                <option value="" disabled selected>Chọn giới tính</option>
                                <option value="MALE">Nam</option>
                                <option value="FEMALE">Nữ</option>
                                <option value="NON_BINARY">Khác</option>
                                <option value="PREFER_NOT_TO_SAY">Không muốn tiết lộ</option>
                            </select>
                        </div>

                        {/* Field nhập email */}
                        <div className="space-y-1">
                            <Label htmlFor="email" className="light-theme-text-default flex items-center">
                                <Mail className="h-4 w-4 mr-2 text-accent"/> Email
                            </Label>
                            <Input
                                id="email"
                                type="email" // Validation email tự động
                                placeholder="email@example.com"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)} // Cập nhật state khi user nhập
                                className="light-theme-input"
                            />
                        </div>

                        {/* Field nhập số điện thoại */}
                        <div className="space-y-1">
                            <Label htmlFor="phone" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Số Điện Thoại
                            </Label>
                            <Input
                                id="phone"
                                type="text" // Validation số điện thoại tự động
                                placeholder="0123456789"
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)} // Cập nhật state khi user nhập
                                className="light-theme-input"
                            />
                        </div>

                        {/* Field nhập mật khẩu */}
                        <div className="space-y-1">
                            <Label htmlFor="password" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Mật Khẩu
                            </Label>
                            <Input
                                id="password"
                                type="password" // Ẩn text hiển thị
                                placeholder="••••••••"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)} // Cập nhật state khi user nhập
                                required // Trường bắt buộc
                                className="light-theme-input"
                            />
                        </div>

                        {/* Field xác nhận mật khẩu */}
                        <div className="space-y-1">
                            <Label htmlFor="confirmPassword" className="light-theme-text-default flex items-center">
                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Xác Nhận Mật Khẩu
                            </Label>
                            <Input
                                id="confirmPassword"
                                type="password" // Ẩn text hiển thị
                                placeholder="••••••••"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)} // Cập nhật state khi user nhập
                                required // Trường bắt buộc
                                className="light-theme-input"
                            />
                        </div>

                        {/* Button submit với trạng thái loading */}
                        <Button
                            type="submit"
                            className="w-full bg-accent text-accent-foreground hover:bg-accent/90 text-lg py-3"
                            disabled={isLoading} // Disable button khi đang loading
                        >
                            {/* Hiển thị text khác nhau tùy theo trạng thái loading */}
                            {isLoading ? 'Đang xử lý...' : 'Đăng Ký'}
                        </Button>
                    </form>
                </CardContent>

                {/* Footer chứa link đến trang đăng nhập */}
                <CardFooter className="flex flex-col items-center space-y-3">
                    <p className="text-sm text-slate-500">
                        Đã có tài khoản?{' '}
                        {/* Link navigation đến trang login */}
                        <Link to="/login" className="font-semibold text-primary hover:underline">
                            Đăng nhập
                        </Link>
                    </p>
                </CardFooter>
            </Card>
        </div>
    );
};

// Export component để sử dụng trong router
export default RegisterPage;