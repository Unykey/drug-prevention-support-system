/**
 * Button UI Component - Component nút bấm tái sử dụng cho ứng dụng phòng chống ma túy
 * 
 * File này chứa:
 * - buttonVariants: Cấu hình các biến thể styling sử dụng CVA
 * - Button: Component React với ref forwarding và polymorphic rendering
 * 
 * Sử dụng:
 * <Button variant="default" size="lg">Đăng nhập</Button>
 * <Button variant="destructive" size="sm">Xóa</Button>
 * <Button variant="outline">Hủy</Button>
 * <Button asChild><Link to="/home">Trang chủ</Link></Button>
 * 
 * Dependencies:
 * - @radix-ui/react-slot: Polymorphic rendering
 * - class-variance-authority: Type-safe variant styling
 * - clsx/tailwind-merge: Conditional CSS classes
 */

// Import utility function để merge class names
import { cn } from '@/lib/utils';
// Import Slot từ Radix UI để hỗ trợ polymorphic rendering
import { Slot } from '@radix-ui/react-slot';
// Import Class Variance Authority để tạo variant-based styling
import { cva } from 'class-variance-authority';
// Import React core
import React from 'react';

/**
 * Định nghĩa các biến thể của Button component sử dụng CVA (Class Variance Authority)
 * 
 * Cấu trúc:
 * - Base styles: Các class CSS cơ bản áp dụng cho tất cả button
 * - Variants: Các biến thể khác nhau (màu sắc, kích thước)
 * - Default variants: Giá trị mặc định khi không truyền props
 */
const buttonVariants = cva(
	// Base styles - Áp dụng cho tất cả button variants
	'inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50',
	{
		variants: {
			// Các biến thể màu sắc và style
			variant: {
				// Nút chính - Màu primary (thường là xanh dương chủ đạo)
				default: 'bg-primary text-primary-foreground hover:bg-primary/90',
				// Nút nguy hiểm - Màu đỏ cho các hành động xóa, hủy
				destructive:
          'bg-destructive text-destructive-foreground hover:bg-destructive/90',
				// Nút có viền - Nền trắng với border
				outline:
          'border border-input bg-background hover:bg-accent hover:text-accent-foreground',
				// Nút phụ - Màu nhạt hơn, ít prominent hơn nút chính
				secondary:
          'bg-secondary text-secondary-foreground hover:bg-secondary/80',
				// Nút trong suốt - Chỉ hiện background khi hover
				ghost: 'hover:bg-accent hover:text-accent-foreground',
				// Nút dạng link - Có gạch chân như hyperlink
				link: 'text-primary underline-offset-4 hover:underline',
			},
			// Các biến thể kích thước
			size: {
				// Kích thước mặc định - Chiều cao 40px (h-10)
				default: 'h-10 px-4 py-2',
				// Kích thước nhỏ - Chiều cao 36px (h-9)
				sm: 'h-9 rounded-md px-3',
				// Kích thước lớn - Chiều cao 44px (h-11)
				lg: 'h-11 rounded-md px-8',
				// Kích thước icon - Vuông 40x40px, dành cho nút chỉ có icon
				icon: 'h-10 w-10',
			},
		},
		// Giá trị mặc định khi không truyền props
		defaultVariants: {
			variant: 'default',
			size: 'default',
		},
	},
);

/**
 * Button Component - Component nút bấm có thể tùy chỉnh với nhiều biến thể
 * 
 * Đặc điểm:
 * - Sử dụng React.forwardRef để forward ref đến element thực tế
 * - Hỗ trợ polymorphic rendering (có thể render thành các element khác nhau)
 * - Tích hợp với Radix UI Slot để composite pattern
 * - Responsive và accessible theo chuẩn WCAG
 * 
 * Props:
 * @param {string} className - CSS classes bổ sung
 * @param {'default'|'destructive'|'outline'|'secondary'|'ghost'|'link'} variant - Biến thể màu sắc
 * @param {'default'|'sm'|'lg'|'icon'} size - Kích thước button
 * @param {boolean} asChild - Nếu true, sẽ render children thay vì button element
 * @param {React.Ref} ref - Reference được forward đến element
 * @param {Object} props - Các props khác sẽ được spread vào element
 */
const Button = React.forwardRef(({ className, variant, size, asChild = false, ...props }, ref) => {
	// Xác định component sẽ render: Slot (nếu asChild=true) hoặc button element
	const Comp = asChild ? Slot : 'button';
	
	return (
		<Comp
			// Merge các class từ buttonVariants với className được truyền vào
			className={cn(buttonVariants({ variant, size, className }))}
			// Forward ref đến element thực tế
			ref={ref}
			// Spread tất cả props khác (onClick, disabled, type, etc.)
			{...props}
		/>
	);
});

// Đặt displayName để dễ debug trong React DevTools
Button.displayName = 'Button';

// Export Button component và buttonVariants function
// Button: Component chính để sử dụng trong UI
// buttonVariants: Function để tạo class names, có thể dùng riêng nếu cần custom
export { Button, buttonVariants };
