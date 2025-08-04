
/**
 * Input UI Component - Component input field tái sử dụng cho ứng dụng phòng chống ma túy
 * 
 * Đặc điểm:
 * - Styled input với design system nhất quán
 * - Hỗ trợ tất cả HTML input types (text, email, password, number, etc.)
 * - Accessible với focus states và screen reader support
 * - Responsive design với các breakpoints
 * - File input styling đặc biệt
 * - Forward ref để form libraries có thể control
 * 
 * Sử dụng:
 * <Input type="email" placeholder="Nhập email của bạn" />
 * <Input type="password" placeholder="Mật khẩu" />
 * <Input type="text" value={name} onChange={handleChange} />
 * <Input type="file" accept="image/*" />
 * 
 * Tích hợp với:
 * - React Hook Form
 * - Formik
 * - Form validation libraries
 * - File upload components
 */

// Import React core
import React from 'react';
// Import utility function để merge CSS classes
import { cn } from "@/lib/utils"

/**
 * Input - Component input field với styling nhất quán
 * 
 * Đặc điểm styling:
 * - Flexbox layout (flex)
 * - Chiều cao cố định 40px (h-10)
 * - Chiều rộng full container (w-full)
 * - Border radius medium (rounded-md)
 * - Border với màu input theme (border-input)
 * - Background theo theme (bg-background)
 * - Padding ngang 12px, dọc 8px (px-3 py-2)
 * - Font size small 14px (text-sm)
 * - Ring offset cho focus states
 * 
 * File input đặc biệt:
 * - Không có border (file:border-0)
 * - Background trong suốt (file:bg-transparent)
 * - Font styling riêng (file:text-sm file:font-medium)
 * 
 * States:
 * - Placeholder: màu muted (placeholder:text-muted-foreground)
 * - Focus: outline none + ring 2px (focus-visible:ring-2)
 * - Disabled: cursor not-allowed + opacity 50%
 * 
 * Props:
 * @param {string} className - CSS classes bổ sung
 * @param {string} type - HTML input type (text, email, password, file, etc.)
 * @param {React.Ref} ref - Reference đến input element
 * @param {Object} props - Các props HTML input khác (value, onChange, placeholder, etc.)
 */
const Input = React.forwardRef(({ className, type, ...props }, ref) => {
  return (
    (<input
      type={type}
      className={cn(
        // Base styles - Áp dụng cho tất cả input types
        "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background " +
        // File input specific styles - Styling đặc biệt cho input type="file"
        "file:border-0 file:bg-transparent file:text-sm file:font-medium " +
        // Placeholder styling - Màu chữ placeholder nhạt hơn
        "placeholder:text-muted-foreground " +
        // Focus states - Ring blue khi focus, accessible outline
        "focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 " +
        // Disabled states - Cursor và opacity khi disabled
        "disabled:cursor-not-allowed disabled:opacity-50",
        className
      )}
      ref={ref}
      {...props} />)
  );
})
// Đặt displayName để dễ debug trong React DevTools
Input.displayName = "Input"

// Export Input component để sử dụng trong forms, dialogs, và các UI components khác
export { Input }
  