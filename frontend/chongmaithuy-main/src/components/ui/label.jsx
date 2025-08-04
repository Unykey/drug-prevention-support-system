
// Import React library để sử dụng JSX và React components
import React from 'react';
// Import Label primitive từ Radix UI - thư viện UI components headless
import * as LabelPrimitive from "@radix-ui/react-label"
// Import cva (class variance authority) - utility để tạo variant classes
import { cva } from "class-variance-authority";

// Import utility function cn để merge classes từ lib/utils
import { cn } from "@/lib/utils"

// Định nghĩa các variant styles cho label component sử dụng cva
// Bao gồm các styles mặc định cho typography và accessibility
const labelVariants = cva(
  "text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
)

// Tạo Label component sử dụng React.forwardRef để có thể truyền ref xuống DOM element
// Destructure className và spread các props khác
const Label = React.forwardRef(({ className, ...props }, ref) => (
  <LabelPrimitive.Root
    ref={ref}
    // Merge default styles từ labelVariants với className được truyền vào
    className={cn(labelVariants(), className)}
    {...props} />
))
// Đặt displayName cho component để dễ debug trong React DevTools
Label.displayName = LabelPrimitive.Root.displayName

// Export Label component để có thể import và sử dụng ở các file khác
export { Label }
  