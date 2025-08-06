// Import các thư viện và component cần thiết
import React, {useEffect, useState} from 'react'; // React core và useState hook để quản lý state
import {Link, useNavigate} from 'react-router-dom'; // Router để điều hướng giữa các trang
import {Button} from '@/components/ui/button'; // Component button tùy chỉnh
import {Input} from '@/components/ui/input'; // Component input tùy chỉnh
import {Label} from '@/components/ui/label'; // Component label tùy chỉnh
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from '@/components/ui/card'; // Các component card để tạo layout
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import {UserPlus, Mail, KeyRound, User, Phone, Calendar, Shield} from 'lucide-react'; // Icons từ thư viện Lucide React
import {useToast} from '@/components/ui/use-toast'; // Hook để hiển thị thông báo toast
import * as Yup from 'yup';
import { Formik, Form, Field } from 'formik';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const calculateAge = (dateOfBirth) => {
    if (!dateOfBirth) return null;
    const today = new Date();
    const birthDate = new Date(dateOfBirth);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age;
};

// Validation schema cho form đăng ký
const validationSchema = Yup.object({
    name: Yup.string()
        .required('Họ và tên là bắt buộc')
        .min(2, 'Họ và tên phải có ít nhất 2 ký tự'),

    email: Yup.string()
        .email('Email không hợp lệ')
        .required('Email là bắt buộc'),

    phone: Yup.string()
        .matches(/^[0-9]{10,11}$/, 'Số điện thoại phải có 10-11 chữ số')
        .required('Số điện thoại là bắt buộc'),

    gender: Yup.string()
        .oneOf(['MALE', 'FEMALE', 'NON_BINARY', 'PREFER_NOT_TO_SAY'], 'Vui lòng chọn giới tính')
        .required('Giới tính là bắt buộc'),

    dateOfBirth: Yup.date()
        .typeError('Ngày sinh không hợp lệ. Vui lòng chọn từ lịch.')
        .min(new Date(1900, 0, 1), 'Ngày sinh không thể trước năm 1900')
        .max(new Date(), 'Ngày sinh không thể là tương lai')
        .required('Ngày sinh là bắt buộc'),

    password: Yup.string()
        .min(6, 'Mật khẩu phải có ít nhất 6 ký tự')
        .required('Mật khẩu là bắt buộc'),

    confirmPassword: Yup.string()
        .oneOf([Yup.ref('password'), null], 'Mật khẩu xác nhận không khớp')
        .required('Xác nhận mật khẩu là bắt buộc'),

    // Guardian fields - chỉ validate khi cần thiết
    guardianName: Yup.string().when('dateOfBirth', {
        is: (dateOfBirth) => {
            const age = calculateAge(dateOfBirth);
            return age !== null && age < 18;
        },
        then: (schema) => schema.required('Tên người giám hộ là bắt buộc'),
        otherwise: (schema) => schema.notRequired()
    }),

    guardianEmail: Yup.string().when('dateOfBirth', {
        is: (dateOfBirth) => {
            const age = calculateAge(dateOfBirth);
            return age !== null && age < 18;
        },
        then: (schema) => schema.email('Email người giám hộ không hợp lệ').required('Email người giám hộ là bắt buộc'),
        otherwise: (schema) => schema.notRequired()
    }),

    guardianPhone: Yup.string().when('dateOfBirth', {
        is: (dateOfBirth) => {
            const age = calculateAge(dateOfBirth);
            return age !== null && age < 18;
        },
        then: (schema) => schema.matches(/^[0-9]{10,11}$/, 'Số điện thoại người giám hộ phải có 10-11 chữ số').required('Số điện thoại người giám hộ là bắt buộc'),
        otherwise: (schema) => schema.notRequired()
    })
});

// Component chính cho trang đăng ký
const RegisterPage = () => {
        // Hooks để điều hướng và hiển thị thông báo
        const navigate = useNavigate(); // Hook để chuyển hướng trang
        const {toast} = useToast(); // Hook để hiển thị thông báo toast

        const initialValues = {
            name: '',
            email: '',
            phone: '',
            gender: '',
            dateOfBirth: '',
            password: '',
            confirmPassword: '',
            guardianName: '',
            guardianEmail: '',
            guardianPhone: ''
        };

        // Hàm xử lý submit form đăng ký
        const handleSubmit = async (e, values, {setSubmitting}) => {
            try {
                const age = calculateAge(values.dateOfBirth);

                // Chuẩn bị userData
                const userData = {
                    name: values.name.trim(),
                    email: values.email.trim().toLowerCase(),
                    password: values.password,
                    gender: values.gender,
                    dateOfBirth: values.dateOfBirth,
                    phone: values.phone.trim()
                };

                // Thêm guardian nếu dưới 18 tuổi
                if (age < 18) {
                    userData.guardian = {
                        guardianName: values.guardianName.trim(),
                        guardianEmail: values.guardianEmail.trim().toLowerCase(),
                        guardianPhone: values.guardianPhone.trim()
                    };
                }

                toast({
                    title: "Đăng ký thành công!",
                    description: "Tài khoản của bạn đã được tạo. Vui lòng đăng nhập.",
                    variant: "default",
                    className: "bg-green-500 text-white",
                });

                navigate('/login');
            } catch (error) {
                const errorMessage = error?.message || 'Đã có lỗi xảy ra. Vui lòng thử lại.';

                toast({
                    title: "Đăng ký thất bại",
                    description: errorMessage,
                    variant: "destructive",
                });

                console.error('Register error:', error);
            } finally {
                setSubmitting(false); // Tắt trạng thái submit khi xử lý xong
            }
        };

        return (
            <div className="flex items-center justify-center min-h-[calc(100vh-200px)] py-12">
                <Card className="w-full max-w-md light-theme-card shadow-2xl">
                    <CardHeader className="text-center">
                        <div className="inline-block p-3 bg-accent/10 rounded-full mx-auto mb-4">
                            <UserPlus className="h-10 w-10 text-accent"/>
                        </div>
                        <CardTitle className="text-3xl font-bold gradient-text">Tạo Tài Khoản</CardTitle>
                        <CardDescription className="light-theme-card-description">
                            Tham gia cộng đồng của chúng tôi ngay hôm nay!
                        </CardDescription>
                    </CardHeader>

                    <CardContent>
                        <Formik
                            initialValues={initialValues}
                            validationSchema={validationSchema}
                            onSubmit={handleSubmit}
                        >
                            {({values, errors, touched, setFieldValue, isSubmitting}) => {
                                const age = calculateAge(values.dateOfBirth);
                                const showGuardianFields = age !== null && age < 18;

                                return (
                                    <Form className="space-y-4">
                                        {/* Field nhập tên người dùng */}
                                        <div className="space-y-1">
                                            <Label htmlFor="name" className="light-theme-text-default flex items-center">
                                                <User className="h-4 w-4 mr-2 text-accent"/> Họ và Tên
                                            </Label>
                                            <Field name="name">
                                                {({field}) => (
                                                    <Input
                                                        {...field}
                                                        id="name"
                                                        type="text"
                                                        placeholder="Nguyễn Văn A"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.name && touched.name && (
                                                <p className="text-sm text-red-500">{errors.name}</p>
                                            )}
                                        </div>

                                        {/* Field nhập email */}
                                        <div className="space-y-1">
                                            <Label htmlFor="email" className="light-theme-text-default flex items-center">
                                                <Mail className="h-4 w-4 mr-2 text-accent"/> Email
                                            </Label>
                                            <Field name="email">
                                                {({field}) => (
                                                    <Input
                                                        {...field}
                                                        id="email"
                                                        type="email"
                                                        placeholder="email@example.com"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.email && touched.email && (
                                                <p className="text-sm text-red-500">{errors.email}</p>
                                            )}
                                        </div>


                                        {/* Field nhập số điện thoại*/}
                                        <div className="space-y-1">
                                            <Label htmlFor="phone" className="light-theme-text-default flex items-center">
                                                <Phone className="h-4 w-4 mr-2 text-accent"/> Số Điện Thoại
                                            </Label>
                                            <Field name="phone">
                                                {({field}) => (
                                                    <Input
                                                        {...field}
                                                        id="phone"
                                                        type="tel"
                                                        placeholder="0123456789"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.phone && touched.phone && (
                                                <p className="text-sm text-red-500">{errors.phone}</p>
                                            )}
                                        </div>

                                        {/* Field chon giới tính */}
                                        <div className="space-y-1">
                                            <Label className="light-theme-text-default flex items-center">
                                                <User className="h-4 w-4 mr-2 text-accent"/> Giới Tính
                                            </Label>
                                            <Select
                                                value={values.gender}
                                                onValueChange={(value) => setFieldValue('gender', value)}
                                            >
                                                <SelectTrigger className="light-theme-input">
                                                    <SelectValue placeholder="Chọn giới tính"/>
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="MALE">Nam</SelectItem>
                                                    <SelectItem value="FEMALE">Nữ</SelectItem>
                                                    <SelectItem value="NON_BINARY">Khác</SelectItem>
                                                    <SelectItem value="PREFER_NOT_TO_SAY">Không muốn tiết lộ</SelectItem>
                                                </SelectContent>
                                            </Select>
                                            {errors.gender && touched.gender && (
                                                <p className="text-sm text-red-500">{errors.gender}</p>
                                            )}
                                        </div>

                                        {/* Field nhập tháng/ngày/năm sinh*/}
                                        <div className="space-y-1">
                                            <Label htmlFor="dateOfBirth"
                                                   className="light-theme-text-default flex items-center">
                                                <Calendar className="h-4 w-4 mr-2 text-accent"/> Ngày Sinh
                                            </Label>
                                            <Field name="dateOfBirth">
                                                {({field, form}) => (
                                                    <DatePicker
                                                        {...field}
                                                        selected={field.value}
                                                        onChange={(val) => form.setFieldValue('dateOfBirth', val)}
                                                        dateFormat="dd/MM/yyyy"
                                                        maxDate={new Date()}
                                                        minDate={new Date('1900-01-01')}
                                                        placeholderText="Chọn ngày sinh"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.dateOfBirth && touched.dateOfBirth && (
                                                <p className="text-sm text-red-500">{errors.dateOfBirth}</p>
                                            )}
                                            {age !== null && (
                                                <p className="text-sm text-slate-600">
                                                    Tuổi: {age} {age < 18 && "(Cần thông tin người giám hộ)"}
                                                </p>
                                            )}
                                        </div>


                                        {/* Guardian Fields - Show only if under 18 */}
                                        {showGuardianFields && (
                                            <div className="space-y-4 p-4 bg-orange-50 rounded-lg border border-orange-200">
                                                <div className="flex items-center text-orange-700 font-medium">
                                                    <Shield className="h-4 w-4 mr-2"/>
                                                    Thông tin người giám hộ (dưới 18 tuổi)
                                                </div>

                                                {/* Guardian Name */}
                                                <div className="space-y-1">
                                                    <Label htmlFor="guardianName"
                                                           className="light-theme-text-default flex items-center">
                                                        <User className="h-4 w-4 mr-2 text-accent"/> Tên Người Giám Hộ
                                                    </Label>
                                                    <Field name="guardianName">
                                                        {({field}) => (
                                                            <Input
                                                                {...field}
                                                                id="guardianName"
                                                                type="text"
                                                                placeholder="Tên người giám hộ"
                                                                className="light-theme-input"
                                                            />
                                                        )}
                                                    </Field>
                                                    {errors.guardianName && touched.guardianName && (
                                                        <p className="text-sm text-red-500">{errors.guardianName}</p>
                                                    )}
                                                </div>

                                                {/* Guardian Email */}
                                                <div className="space-y-1">
                                                    <Label htmlFor="guardianEmail"
                                                           className="light-theme-text-default flex items-center">
                                                        <Mail className="h-4 w-4 mr-2 text-accent"/> Email Người Giám Hộ
                                                    </Label>
                                                    <Field name="guardianEmail">
                                                        {({field}) => (
                                                            <Input
                                                                {...field}
                                                                id="guardianEmail"
                                                                type="email"
                                                                placeholder="guardian@example.com"
                                                                className="light-theme-input"
                                                            />
                                                        )}
                                                    </Field>
                                                    {errors.guardianEmail && touched.guardianEmail && (
                                                        <p className="text-sm text-red-500">{errors.guardianEmail}</p>
                                                    )}
                                                </div>

                                                {/* Guardian Phone */}
                                                <div className="space-y-1">
                                                    <Label htmlFor="guardianPhone"
                                                           className="light-theme-text-default flex items-center">
                                                        <Phone className="h-4 w-4 mr-2 text-accent"/> Số ĐT Người Giám Hộ
                                                    </Label>
                                                    <Field name="guardianPhone">
                                                        {({field}) => (
                                                            <Input
                                                                {...field}
                                                                id="guardianPhone"
                                                                type="tel"
                                                                placeholder="0123456789"
                                                                className="light-theme-input"
                                                            />
                                                        )}
                                                    </Field>
                                                    {errors.guardianPhone && touched.guardianPhone && (
                                                        <p className="text-sm text-red-500">{errors.guardianPhone}</p>
                                                    )}
                                                </div>
                                            </div>
                                        )}


                                        {/* Password Field */}
                                        <div className="space-y-1">
                                            <Label htmlFor="password"
                                                   className="light-theme-text-default flex items-center">
                                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Mật Khẩu
                                            </Label>
                                            <Field name="password">
                                                {({field}) => (
                                                    <Input
                                                        {...field}
                                                        id="password"
                                                        type="password"
                                                        placeholder="••••••••"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.password && touched.password && (
                                                <p className="text-sm text-red-500">{errors.password}</p>
                                            )}
                                        </div>

                                        {/* Confirm Password Field */}
                                        <div className="space-y-1">
                                            <Label htmlFor="confirmPassword"
                                                   className="light-theme-text-default flex items-center">
                                                <KeyRound className="h-4 w-4 mr-2 text-accent"/> Xác Nhận Mật Khẩu
                                            </Label>
                                            <Field name="confirmPassword">
                                                {({field}) => (
                                                    <Input
                                                        {...field}
                                                        id="confirmPassword"
                                                        type="password"
                                                        placeholder="••••••••"
                                                        className="light-theme-input"
                                                    />
                                                )}
                                            </Field>
                                            {errors.confirmPassword && touched.confirmPassword && (
                                                <p className="text-sm text-red-500">{errors.confirmPassword}</p>
                                            )}
                                        </div>

                                        {/* Button submit với trạng thái loading */}
                                        <Button
                                            type="submit"
                                            className="w-full bg-accent text-accent-foreground hover:bg-accent/90 text-lg py-3"
                                            disabled={isSubmitting} // Disable button khi đang loading
                                        >
                                            {/* Hiển thị text khác nhau tùy theo trạng thái loading */}
                                            {isSubmitting ? 'Đang xử lý...' : 'Đăng Ký'}
                                        </Button>
                                    </Form>
                                );
                            }}
                        </Formik>
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
    }
;

// Export component để sử dụng trong router
export default RegisterPage;