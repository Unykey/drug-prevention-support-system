import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ShieldCheck, BookOpen, Users, CalendarDays, BarChart3, UserCircle, LogIn, LogOut, Newspaper } from 'lucide-react';
import { Button } from '@/components/ui/button';
import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  navigationMenuTriggerStyle,
} from '@/components/ui/navigation-menu';
import { useAuth } from '@/contexts/AuthContext';

const Header = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const navLinks = [
    { href: '/', label: 'Trang Chủ', icon: <ShieldCheck className="h-5 w-5 mr-2" /> },
    { href: '/courses', label: 'Khóa Học', icon: <BookOpen className="h-5 w-5 mr-2" /> },
    { href: '/surveys', label: 'Khảo Sát', icon: <BarChart3 className="h-5 w-5 mr-2" /> },
    { href: '/appointments', label: 'Đặt Lịch Hẹn', icon: <CalendarDays className="h-5 w-5 mr-2" /> },
    { href: '/programs', label: 'Chương Trình', icon: <Users className="h-5 w-5 mr-2" /> },
    { href: '/blog', label: 'Blog', icon: <Newspaper className="h-5 w-5 mr-2" /> },
  ];

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="bg-white/80 backdrop-blur-lg shadow-md sticky top-0 z-50 border-b border-gray-200">
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        <Link to="/" className="text-3xl font-bold gradient-text">
          Phòng Chống Ma Túy
        </Link>
        <NavigationMenu>
          <NavigationMenuList className="space-x-1">
            {navLinks.map((link) => (
              <NavigationMenuItem key={link.href}>
                <Link to={link.href}>
                  <NavigationMenuLink className={`${navigationMenuTriggerStyle()} bg-transparent hover:bg-primary/10 hover:text-primary transition-colors duration-300 text-slate-700 font-medium`}>
                    {link.icon}
                    {link.label}
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
            ))}
          </NavigationMenuList>
        </NavigationMenu>
        <div className="space-x-2 flex items-center">
          {user ? (
            <>
              <Link to="/profile">
                <Button variant="ghost" className="text-slate-700 hover:text-primary hover:bg-primary/10">
                  <UserCircle className="h-5 w-5 mr-2" /> {user.name || 'Hồ Sơ'}
                </Button>
              </Link>
              <Button onClick={handleLogout} variant="outline" className="border-accent text-accent hover:bg-accent hover:text-white">
                <LogOut className="h-5 w-5 mr-2" /> Đăng Xuất
              </Button>
            </>
          ) : (
            <Link to="/login">
               <Button className="light-theme-button-primary">
                <LogIn className="h-5 w-5 mr-2" /> Đăng Nhập
              </Button>
            </Link>
          )}
          {user && (user.role === 'Admin' || user.role === 'Manager') && (
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

export default Header;