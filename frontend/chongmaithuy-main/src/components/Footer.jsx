import React from 'react';
import { Facebook, Twitter, Instagram, Linkedin } from 'lucide-react';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-slate-900/70 border-t border-slate-700 py-8 text-center text-gray-400">
      <div className="container mx-auto px-4">
        <div className="flex justify-center space-x-6 mb-4">
          <a href="https://www.google.com/" target="_blank" rel="noopener noreferrer" className="hover:text-primary transition-colors">
            <Facebook size={24} />
          </a>
          <a href="https://www.google.com/" target="_blank" rel="noopener noreferrer" className="hover:text-primary transition-colors">
            <Twitter size={24} />
          </a>
          <a href="https://www.google.com/" target="_blank" rel="noopener noreferrer" className="hover:text-primary transition-colors">
            <Instagram size={24} />
          </a>
          <a href="https://www.google.com/" target="_blank" rel="noopener noreferrer" className="hover:text-primary transition-colors">
            <Linkedin size={24} />
          </a>
        </div>
        <p className="text-sm">
          &copy; {currentYear} Tổ Chức Tình Nguyện Phòng Chống Ma Túy. Bảo lưu mọi quyền.
        </p>
      </div>
    </footer>
  );
};

export default Footer;
  