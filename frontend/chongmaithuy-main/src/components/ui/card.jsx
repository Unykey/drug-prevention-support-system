
/**
 * Card UI Components - Bộ component thẻ (card) tái sử dụng cho ứng dụng phòng chống ma túy
 * 
 * Bộ component này bao gồm:
 * - Card: Container chính với border, shadow và background
 * - CardHeader: Phần đầu chứa title và description
 * - CardTitle: Tiêu đề chính của card
 * - CardDescription: Mô tả phụ dưới title
 * - CardContent: Nội dung chính của card
 * - CardFooter: Phần cuối chứa actions hoặc metadata
 * 
 * Cấu trúc thông dụng:
 * <Card>
 *   <CardHeader>
 *     <CardTitle>Khóa học phòng chống ma túy</CardTitle>
 *     <CardDescription>Tìm hiểu về tác hại và cách phòng tránh</CardDescription>
 *   </CardHeader>
 *   <CardContent>
 *     Nội dung chi tiết khóa học...
 *   </CardContent>
 *   <CardFooter>
 *     <Button>Đăng ký ngay</Button>
 *   </CardFooter>
 * </Card>
 * 
 * Sử dụng trong:
 * - Danh sách khóa học
 * - Bài viết blog
 * - Thông tin chương trình
 * - Profile cards
 * - Dashboard widgets
 */

// Import React core
import React from 'react';
// Import utility function để merge CSS classes
import { cn } from "@/lib/utils"

/**
 * Card - Component container chính của thẻ
 * 
 * Đặc điểm:
 * - Border radius tròn góc (rounded-lg)
 * - Viền mỏng (border)
 * - Background theo theme (bg-card)
 * - Màu text theo theme (text-card-foreground)
 * - Shadow nhẹ (shadow-sm) tạo độ nổi
 * - Forward ref để có thể truy cập DOM element
 * 
 * Props:
 * @param {string} className - CSS classes bổ sung
 * @param {React.Ref} ref - Reference đến div element
 * @param {Object} props - Các props khác (onClick, id, data-*, etc.)
 */
const Card = React.forwardRef(({ className, ...props }, ref) => (
  <div
    ref={ref}
    className={cn("rounded-lg border bg-card text-card-foreground shadow-sm", className)}
    {...props} />
))
Card.displayName = "Card"

/**
 * CardHeader - Component phần đầu của card
 * 
 * Đặc điểm:
 * - Flexbox layout dọc (flex flex-col)
 * - Khoảng cách giữa các element con (space-y-1.5)
 * - Padding đều 4 hướng (p-6 = 24px)
 * - Thường chứa CardTitle và CardDescription
 * 
 * Sử dụng:
 * <CardHeader>
 *   <CardTitle>Tiêu đề</CardTitle>
 *   <CardDescription>Mô tả ngắn</CardDescription>
 * </CardHeader>
 */
const CardHeader = React.forwardRef(({ className, ...props }, ref) => (
  <div
    ref={ref}
    className={cn("flex flex-col space-y-1.5 p-6", className)}
    {...props} />
))
CardHeader.displayName = "CardHeader"

/**
 * CardTitle - Component tiêu đề chính của card
 * 
 * Đặc điểm:
 * - Semantic HTML h3 element (SEO friendly)
 * - Font size lớn (text-2xl = 24px)
 * - Font weight semibold (font-semibold)
 * - Line height chặt chẽ (leading-none)
 * - Letter spacing chặt (tracking-tight)
 * - Accessible heading structure
 * 
 * Sử dụng:
 * <CardTitle>Khóa học cơ bản về tác hại ma túy</CardTitle>
 */
const CardTitle = React.forwardRef(({ className, ...props }, ref) => (
  <h3
    ref={ref}
    className={cn("text-2xl font-semibold leading-none tracking-tight", className)}
    {...props} />
))
CardTitle.displayName = "CardTitle"

/**
 * CardDescription - Component mô tả phụ của card
 * 
 * Đặc điểm:
 * - Semantic HTML p element
 * - Font size nhỏ (text-sm = 14px)
 * - Màu chữ muted (text-muted-foreground) - nhạt hơn title
 * - Thường đặt ngay dưới CardTitle
 * - Cung cấp thông tin tóm tắt hoặc context
 * 
 * Sử dụng:
 * <CardDescription>
 *   Tìm hiểu về tác hại của ma túy và cách phòng tránh hiệu quả
 * </CardDescription>
 */
const CardDescription = React.forwardRef(({ className, ...props }, ref) => (
  <p
    ref={ref}
    className={cn("text-sm text-muted-foreground", className)}
    {...props} />
))
CardDescription.displayName = "CardDescription"

/**
 * CardContent - Component nội dung chính của card
 * 
 * Đặc điểm:
 * - Padding ngang và dưới (p-6 = 24px)
 * - Không có padding top (pt-0) để liền kề với header
 * - Chứa nội dung chính như text, images, forms
 * - Flexible layout - có thể chứa bất kỳ content nào
 * 
 * Sử dụng:
 * <CardContent>
 *   <p>Nội dung chi tiết về khóa học...</p>
 *   <img src="course-image.jpg" alt="Khóa học" />
 * </CardContent>
 */
const CardContent = React.forwardRef(({ className, ...props }, ref) => (
  <div ref={ref} className={cn("p-6 pt-0", className)} {...props} />
))
CardContent.displayName = "CardContent"

/**
 * CardFooter - Component phần cuối của card
 * 
 * Đặc điểm:
 * - Flexbox layout ngang (flex)
 * - Căn giữa theo chiều dọc (items-center)
 * - Padding ngang và dưới (p-6)
 * - Không có padding top (pt-0) để liền kề với content
 * - Thường chứa buttons, links, metadata, timestamps
 * 
 * Sử dụng:
 * <CardFooter>
 *   <Button variant="default">Đăng ký khóa học</Button>
 *   <Button variant="outline">Xem chi tiết</Button>
 * </CardFooter>
 */
const CardFooter = React.forwardRef(({ className, ...props }, ref) => (
  <div
    ref={ref}
    className={cn("flex items-center p-6 pt-0", className)}
    {...props} />
))
CardFooter.displayName = "CardFooter"

// Export tất cả card components để sử dụng trong ứng dụng
// Thứ tự export phản ánh thứ tự sử dụng thông thường: Card -> Header -> Footer -> Title -> Description -> Content
export { Card, CardHeader, CardFooter, CardTitle, CardDescription, CardContent }
  