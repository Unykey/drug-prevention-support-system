// Import React core to enable JSX and component functionality
import React from 'react';
// Import Link to navigate between routes and useNavigate to imperatively redirect
import { Link, useNavigate } from 'react-router-dom';
// Import icon components from lucide-react for navigation visuals
import { ShieldCheck, BookOpen, Users, CalendarDays, BarChart3, UserCircle, LogIn, LogOut, Newspaper } from 'lucide-react';
// Import styled Button component from shared UI library
import { Button } from '@/components/ui/button';
// Import NavigationMenu components and trigger styles for dropdown menu navigation UI
import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  navigationMenuTriggerStyle,
} from '@/components/ui/navigation-menu';
// Import authentication context hook to access user state and logout action
import { useAuth } from '@/contexts/AuthContext';

/**
 * Header component renders the application's main navigation bar
 * - Site title/logo linking to home
 * - Primary navigation links with icons
 * - User auth controls (login/logout/profile/admin)
 */
const Header = () => {
  // Destructure current user info and logout function from auth context
  const { user, logout } = useAuth();
  // Hook for programmatic navigation after actions like logout
  const navigate = useNavigate();

  // navLinks array defines each menu item: route path, label text, and icon element
  const navLinks = [
    // Home link
    { href: '/', label: 'Trang Chủ', icon: <ShieldCheck className="h-5 w-5 mr-2" /> },
    // Courses link
    { href: '/courses', label: 'Khóa Học', icon: <BookOpen className="h-5 w-5 mr-2" /> },
    // Surveys link
    { href: '/surveys', label: 'Khảo Sát', icon: <BarChart3 className="h-5 w-5 mr-2" /> },
    // Appointments link
    { href: '/appointments', label: 'Đặt Lịch Hẹn', icon: <CalendarDays className="h-5 w-5 mr-2" /> },
    // Programs link
    { href: '/programs', label: 'Chương Trình', icon: <Users className="h-5 w-5 mr-2" /> },
    // Blog link
    { href: '/blog', label: 'Blog', icon: <Newspaper className="h-5 w-5 mr-2" /> },
  ];

  // handleLogout calls logout action then redirects user to home page
  const handleLogout = () => {
    logout(); // clear user session
    navigate('/'); // go back to landing page
  };

  // JSX returned for rendering the header UI
  return (
    // Header container with styling for background, shadow, and sticky positioning
    <header className="bg-white/80 backdrop-blur-lg shadow-md sticky top-0 z-50 border-b border-gray-200">
      {/* Inner wrapper for layout spacing and flex alignment */}
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        {/* Site logo/title linking back to home route */}
        <Link to="/" className="text-3xl font-bold gradient-text">
          Phòng Chống Ma Túy
        </Link>
        {/* Primary navigation menu built from navLinks array */}
        <NavigationMenu>
          <NavigationMenuList className="space-x-1">
            {navLinks.map((link) => (
              <NavigationMenuItem key={link.href}>
                {/* Each link uses NavigationMenuLink for consistent styling */}
                <Link to={link.href}>
                  <NavigationMenuLink className={`${navigationMenuTriggerStyle()} bg-transparent hover:bg-primary/10 hover:text-primary transition-colors duration-300 text-slate-700 font-medium`}>
                    {/* Icon and label for the link */}
                    {link.icon}
                    {link.label}
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
            ))}
          </NavigationMenuList>
        </NavigationMenu>
        {/* User authentication controls: profile/login/logout/admin */}
        <div className="space-x-2 flex items-center">
          {user ? (
            // If user is logged in, show profile and logout buttons
            <>
              {/* Profile link showing user icon and name */}
              <Link to="/profile">
                <Button variant="ghost" className="text-slate-700 hover:text-primary hover:bg-primary/10">
                  <UserCircle className="h-5 w-5 mr-2" /> {user.name || 'Hồ Sơ'}
                </Button>
              </Link>
              {/* Logout button triggers handleLogout */}
              <Button onClick={handleLogout} variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white">
                <LogOut className="h-5 w-5 mr-2" /> Đăng Xuất
              </Button>
            </>
          ) : (
            // If no user, show login button
            <Link to="/login">
               <Button className="light-theme-button-primary">
                <LogIn className="h-5 w-5 mr-2" /> Đăng Nhập
              </Button>
            </Link>
          )}
          {/* Show admin dashboard link if user has Admin or Manager role */}
          {user && (user.role === 'ADMIN' || user.role === 'MANAGER') && (
             <Link to="/admin">
               <Button className="bg-slate-700 text-white hover:bg-slate-800">
                Quản Trị
              </Button>
            </Link>
          )}
        </div>
      </div>
    </header>
  );
};

// Export Header as default component of this module
export default Header;