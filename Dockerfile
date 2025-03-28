# Sử dụng JDK 17
FROM eclipse-temurin:17-jdk

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Cấp quyền thực thi cho mvnw
RUN chmod +x mvnw

# Build project và tạo file .jar
RUN ./mvnw clean package -DskipTests

# Hiển thị danh sách file trong target để debug
RUN ls -la target/

# Tìm file JAR chính xác trong target/
RUN cp target/*.jar app.jar

# Mở port 8080 (hoặc port app của bạn dùng)
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
