
# Tour Booking App - TravelVN

Ứng dụng Android đặt tour online của hệ thống TravelVN. Người dùng có thể tìm kiếm, đặt tour, nhận ưu đãi một cách nhanh chóng.


## Công nghệ sử dụng

| Thành phần              | Công nghệ                            |
|------------------------|--------------------------------------|
| Ngôn ngữ               | Kotlin                               |
| Kiến trúc              | MVVM + Clean Architecture            |
| UI                     | Jetpack Compose                      |
| Quản lý trạng thái     | ViewModel, State, Channel            |
| Networking             | Retrofit                             |
| Backend                | Spring Boot                   |
| Thông báo              | Firebase Cloud Messaging  |
| Thanh toán             | PayOS, ZaloPay                       |
| Dependency Injection   | Hilt                                 |
| Hình ảnh               | Coil                                 |
| Realtime Chat          | Firebase Firestore                   |


## Chức năng

- Tìm kiếm & duyệt tour theo địa điểm, thời gian, hoặc khuyến mãi
- Đặt tour trực tuyến với các lựa chọn thanh toán (PayOS và ZaloPay)
- Tham gia nhóm trò chuyện tour cùng hướng dẫn viên du lịch và hành khách khác
- Nhận thông báo tin nhắn mới
- Giao diện hỗ trợ nhiều thiết bị và chế độ ban đêm.


## Link demo
[Link demo](https://youtu.be/sLlLH-Hz0jg)

## Cài đặt

```bash
 Yêu cầu:
- Android Studio
- JDK 17
- Firebase config (google-services.json)

 Cách build:
1. Clone repo 
2. Mở bằng Android Studio
3. Sync Gradle
4. Chạy trên máy ảo hoặc thiết bị thật
