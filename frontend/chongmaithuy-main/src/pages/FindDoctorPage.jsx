// Import các dependencies cần thiết
import React, { useState } from 'react'; // Thư viện React core với useState
import { Button } from '@/components/ui/button'; // Component Button tùy chỉnh từ UI library
import { CalendarDays, UserCheck, Search } from 'lucide-react'; // Icons từ thư viện Lucide React
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'; // Components Card từ UI library
import { Link } from 'react-router-dom'; // Component Link để điều hướng trong React Router

/**
 * Dữ liệu tĩnh các bác sĩ
 * Trong thực tế, dữ liệu này nên được fetch từ API backend
 * Mỗi doctor object chứa:
 * - id: Định danh duy nhất
 * - name: Tên bác sĩ
 * - expertise: Chuyên môn/lĩnh vực
 * - hospital: Cơ sở y tế
 * - language: Ngôn ngữ
 * - rating: Điểm đánh giá (1-5)
 * - available: Lịch làm việc
 * - image: URL ảnh đại diện từ Unsplash
 */
const doctors = [
  {
    id: 1,
    name: "Bác sĩ Nguyễn Văn A",
    expertise: "Tim mạch",
    hospital: "Vinmec Central Park",
    language: "Tiếng Việt, Tiếng Anh",
    rating: 4.5,
    available: "Thứ 2, Thứ 4, Thứ 6",
    image: "https://images.unsplash.com/photo-1557862921-37829c790f19?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80"
  },
  {
    id: 2,
    name: "Bác sĩ Trần Thị B",
    expertise: "Sản phụ khoa",
    hospital: "Vinmec Times City",
    language: "Tiếng Việt, Tiếng Nhật",
    rating: 4.8,
    available: "Thứ 3, Thứ 5, Sáng Thứ 7",
    image: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=764&q=80"
  },
  {
    id: 3,
    name: "Bác sĩ Lê Văn C",
    expertise: "Thần kinh",
    hospital: "Vinmec Hải Phòng",
    language: "Tiếng Việt, Tiếng Hàn Quốc",
    rating: 4.2,
    available: "Chiều Thứ 2, Cả ngày Thứ 7",
    image: "https://images.unsplash.com/photo-1521119989659-a83eee488004?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=723&q=80"
  },
  {
    id: 4,
    name: "Bác sĩ Phạm Thị D",
    expertise: "Nhi",
    hospital: "Vinmec Phú Quốc",
    language: "Tiếng Việt",
    rating: 4.7,
    available: "Thứ 2, Thứ 5",
    image: "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80"
  },
];

/**
 * Component FindDoctorPage - Trang tìm kiếm bác sĩ
 *
 * Chức năng chính:
 * - Hiển thị danh sách các bác sĩ có sẵn
 * - Cho phép người dùng lọc bác sĩ theo chuyên môn, ngôn ngữ, học hàm, học vị, và đánh giá
 * - Cung cấp liên kết để đặt lịch hẹn với bác sĩ
 *
 * Layout:
 * - Header section: Tiêu đề và mô tả trang với các nút điều hướng
 * - Doctor filter section: Lọc bác sĩ theo các tiêu chí
 * - Doctors section: Grid hiển thị danh sách bác sĩ
 * - Process section: Quy trình đặt lịch hẹn
 *
 * @returns {JSX.Element} React component
 */
const FindDoctorPage = () => {
  const [isOpen, setIsOpen] = useState({
    hospitals: false,
    specialties: false,
    languages: false,
    titles: false,
    degrees: false,
    ratings: false,
  });

  const toggleDropdown = (key) => {
    setIsOpen((prev) => ({ ...prev, [key]: !prev[key] }));
  };

  return (
      <div className="space-y-8">
        {/* HEADER SECTION - Phần tiêu đề và giới thiệu với các nút */}
        <section className="text-center py-5 bg-gradient-to-r from-primary/5 via-accent/5 to-sky-500/5 rounded-lg flex flex-col justify-between h-64">
          <h1 className="text-4xl font-bold mb-4 gradient-text">Tìm Kiếm Bác Sĩ</h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto">
            Tìm kiếm bác sĩ phù hợp với chuyên môn và nhu cầu của bạn để nhận được sự chăm sóc tốt nhất.
          </p>
          <div className="flex justify-center space-x-4 mt-auto">
            <Link to="/book-appointment">
              <Button className="mb-4 bg-accent text-accent-foreground hover:bg-accent/90">
                <CalendarDays className="h-5 w-5 mr-2" /> Đặt lịch hẹn
              </Button>
            </Link>
            <Link to="/find-doctor">
              <Button className="ml-4 bg-accent text-accent-foreground hover:bg-accent/90">
                <Search className="h-5 w-5 mr-2" /> Tìm bác sĩ
              </Button>
            </Link>
          </div>
        </section>

        {/* DOCTOR FILTER SECTION - Phần lọc bác sĩ */}
        <section className="fill_doctor">
          <div className="list_opttion_fill flex justify-between">
            <div className="col_fill_doctor">
              <div className="drop_doctor">
                <div className="drop_name" onClick={() => toggleDropdown('hospitals')}>
                  <img src="/assets/images/doctor/icon-address.svg" alt="hospital" /> Lựa chọn cơ sở
                </div>
                {isOpen.hospitals && (
                    <ul className="dropdown-content" id="dropdownItems">
                      <li><label><input type="checkbox" className="hospitals-items" value="1957" />Vinmec Central Park</label></li>
                      <li><label><input type="checkbox" className="hospitals-items" value="1967" />Vinmec Times City</label></li>
                      <li><label><input type="checkbox" className="hospitals-items" value="1987" />Vinmec Hải Phòng</label></li>
                      <li><label><input type="checkbox" className="hospitals-items" value="2011" />Vinmec Phú Quốc</label></li>
                    </ul>
                )}
              </div>
            </div>
            <div className="col_fill_doctor">
              <div className="drop_doctor">
                <div className="drop_name" onClick={() => toggleDropdown('specialties')}>
                  <img src="/assets/images/doctor/icon_chuyenmon.svg" alt="specialty" /> Yêu cầu chuyên môn
                </div>
                {isOpen.specialties && (
                    <ul className="dropdown-content">
                      <li><label><input type="checkbox" className="specialties-items" value="1" />Nhi</label></li>
                      <li><label><input type="checkbox" className="specialties-items" value="4" />Tim mạch</label></li>
                      <li><label><input type="checkbox" className="specialties-items" value="6" />Sản phụ khoa</label></li>
                      <li><label><input type="checkbox" className="specialties-items" value="10" />Thần kinh</label></li>
                    </ul>
                )}
              </div>
            </div>
            <div className="col_fill_doctor">
              <div className="drop_doctor">
                <div className="drop_name" onClick={() => toggleDropdown('languages')}>
                  <img src="/assets/images/doctor/lang_doc.svg" alt="language" /> Ngôn ngữ
                </div>
                {isOpen.languages && (
                    <ul className="dropdown-content">
                      <li><label><input type="checkbox" className="languages-items" value="1" />Tiếng Việt</label></li>
                      <li><label><input type="checkbox" className="languages-items" value="3" />Tiếng Anh</label></li>
                      <li><label><input type="checkbox" className="languages-items" value="15" />Tiếng Hàn Quốc</label></li>
                      <li><label><input type="checkbox" className="languages-items" value="17" />Tiếng Nhật</label></li>
                    </ul>
                )}
              </div>
            </div>
            <div className="col_fill_doctor">
              <div className="drop_doctor">
                <div className="drop_name" onClick={() => toggleDropdown('titles')}>
                  <img src="/assets/images/doctor/icon_hocvi.svg" alt="title" /> Học hàm
                </div>
                {isOpen.titles && (
                    <ul className="dropdown-content">
                      <li><label><input type="checkbox" className="education_tiles-items" value="1" />Giáo sư</label></li>
                      <li><label><input type="checkbox" className="education_tiles-items" value="3" />Phó giáo sư</label></li>
                    </ul>
                )}
              </div>
            </div>
            <div className="col_fill_doctor">
              <div className="drop_doctor">
                <div className="drop_name" onClick={() => toggleDropdown('degrees')}>
                  <img src="/assets/images/doctor/icon_hocvi.svg" alt="degree" /> Học vị
                </div>
                {isOpen.degrees && (
                    <ul className="dropdown-content">
                      <li><label><input type="checkbox" className="education_levels-items" value="1" />Bác sĩ y khoa</label></li>
                      <li><label><input type="checkbox" className="education_levels-items" value="5" />Bác sĩ chuyên khoa I</label></li>
                      <li><label><input type="checkbox" className="education_levels-items" value="7" />Bác sĩ chuyên khoa II</label></li>
                    </ul>
                )}
              </div>
            </div>
          </div>
          <div className="flex mt-4">
            <div className="col-6 name-rating">
              <div className="col_fill_doctor">
                <div className="drop_doctor">
                  <div className="drop_name" onClick={() => toggleDropdown('ratings')}>
                    <div className="rate">
                      <label className="active"></label> Số điểm đánh giá
                    </div>
                  </div>
                  {isOpen.ratings && (
                      <ul className="dropdown-content" style={{ flexDirection: 'column' }}>
                        <li><label className="rating-pick-list"><input type="checkbox" className="rating-items" value="5" /><div className="rate"><label className="active"></label><label className="active"></label><label className="active"></label><label className="active"></label><label className="active"></label></div></label></li>
                        <li><label className="rating-pick-list"><input type="checkbox" className="rating-items" value="4" /><div className="rate"><label className="active"></label><label className="active"></label><label className="active"></label><label className="active"></label></div></label></li>
                        <li><label className="rating-pick-list"><input type="checkbox" className="rating-items" value="3" /><div className="rate"><label className="active"></label><label className="active"></label><label className="active"></label></div></label></li>
                      </ul>
                  )}
                </div>
              </div>
              <input className="txt_name_doctor" type="text" placeholder="Nhập tên bác sĩ/chuyên gia..." id="txt_name_doctor" />
            </div>
            <div className="col-6">
              <button className="btn_fill_doctor">Tìm kiếm</button>
            </div>
          </div>
        </section>

        {/* DOCTORS SECTION - Phần hiển thị danh sách bác sĩ */}
        <section>
          <h2 className="text-3xl font-semibold text-center mb-8 light-theme-text-default">Danh Sách Bác Sĩ</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {doctors.map(doctor => (
                <Card key={doctor.id} className="light-theme-card hover:shadow-accent/15 transition-shadow duration-300">
                  <CardHeader className="flex flex-col items-center text-center sm:flex-row sm:text-left sm:items-start gap-4">
                    <img
                        className="w-24 h-24 rounded-full object-cover border-2 border-accent"
                        alt={doctor.name}
                        src={doctor.image}
                    />
                    <div className="flex-1">
                      <CardTitle className="text-xl light-theme-card-header">{doctor.name}</CardTitle>
                      <CardDescription className="light-theme-text-accent mt-1">{doctor.expertise}</CardDescription>
                      <CardDescription className="light-theme-text-accent mt-1">Cơ sở: {doctor.hospital}</CardDescription>
                    </div>
                  </CardHeader>
                  <CardContent>
                    <p className="text-sm light-theme-card-content mb-1">
                      <span className="font-semibold text-slate-700">Lịch làm việc:</span> {doctor.available}
                    </p>
                    <p className="text-sm light-theme-card-content mb-1">
                      <span className="font-semibold text-slate-700">Ngôn ngữ:</span> {doctor.language}
                    </p>
                    <p className="text-sm light-theme-card-content mb-1">
                      <span className="font-semibold text-slate-700">Đánh giá:</span> {doctor.rating} / 5
                    </p>
                    <Link to={`/book-appointment/${doctor.id}`}>
                      <Button className="w-full bg-accent text-accent-foreground hover:bg-accent/90">
                        <CalendarDays className="h-5 w-5 mr-2" /> Xem Lịch & Đặt Hẹn
                      </Button>
                    </Link>
                  </CardContent>
                </Card>
            ))}
          </div>
        </section>

        {/* PROCESS SECTION - Phần hướng dẫn quy trình đặt lịch */}
        <section className="mt-12 p-6 light-theme-card rounded-lg">
          <h2 className="text-2xl font-semibold light-theme-card-header mb-3 flex items-center">
            <UserCheck className="h-6 w-6 mr-3 light-theme-text-primary"/> Quy trình đặt lịch
          </h2>
          <ol className="list-decimal list-inside light-theme-card-content space-y-2">
            <li>Chọn bác sĩ phù hợp với nhu cầu của bạn.</li>
            <li>Xem lịch làm việc còn trống của bác sĩ.</li>
            <li>Chọn ngày giờ tư vấn mong muốn và xác nhận thông tin.</li>
            <li>Bạn sẽ nhận được email xác nhận lịch hẹn và hướng dẫn tham gia buổi tư vấn trực tuyến.</li>
          </ol>
        </section>
      </div>
  );
};

// Export component để sử dụng trong các file khác
export default FindDoctorPage;