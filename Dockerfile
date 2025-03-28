# Sử dụng JDK 17
FROM eclipse-temurin:17-jdk

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ code vào container
COPY . .

# Cấp quyền thực thi cho mvnw và build project
RUN chmod +x mvnw && ./mvnw clean package

# Mở port 8080 (nếu app của bạn dùng port khác, sửa lại)
EXPOSE 8080

# Chạy ứng dụng khi container khởi động
CMD ["java", "-jar", "target/*.jar"]
