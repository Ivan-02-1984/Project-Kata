## Настройка и запуск проекта

### Настройка профилей

Проект поддерживает 2 профиля:
1. `local` - для локальной разработки
2. `dev` - для dev-окружения (используется по умолчанию)

#### Запуск с разными профилями:

**Через IntelliJ IDEA**:
- Откройте Run → Edit Configurations
- В поле "VM options" добавьте:
  ```
  -Dspring.profiles.active=local или dev
  ```
- Нажмите "Apply" и запустите приложение.

### Как заменить переменные на свои значения

Проект использует переменные окружения для гибкой настройки. Вы можете подставить свои значения без изменения кода.

**Через IntelliJ IDEA**:
 - Откройте Run → Edit Configurations 
 - В поле Environment variables добавьте:
   ```
   JDBC_PROTOCOL=jdbc:postgresql
   DB_SERVER=localhost
   DB_PORT=5432
   DB_NAME=stackover_auth_service
   DB_SCHEMA=public
   HIBERNATE_DDL_CREATE=true
   DB_USERNAME=ваш_логин
   DB_PASSWORD=ваш_пароль
   ```  
 - Нажмите Apply → OK

==========================================
# 📘 Инструкция по запуску и тестированию

## 🚀 Подготовка к работе

Запустите сервисы в следующем порядке:

1. `eurika-server`
2. `auth-service`
3. `profile-service`
4. `resource-service`
5. `gateway-service`

### 🔧 Настройка `application.yml` для `eurika-server`

Убедитесь, что в `application.yml` указано следующее:

```yaml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: ${spring.application.name}
    preferIpAddress: false
    instance-id: ${spring.application.name}:${server.port}
```

❗ **Важно:** используйте `http` во всех URL, **не** `https`.

✅ Убедитесь, что **все сервисы зарегистрировались в Eureka**.

---

## 🧪 Тестирование через Postman

### 🔑 Заголовки

Во вкладке **Headers**:

- `Key`: `Content-Type`
- `Value`: `application/json`

Если этих полей нет — **добавьте их вручную.**

---

### 📘 Регистрация (Signup)

**Метод:** `POST`  
**URL:** `http://localhost:8180/api/auth/signup`  
**Body → raw → JSON:**
```json
{
  "email": "testuser@example.com",
  "password": "12345678",
  "roleName": "USER",
  "fullName": "Test User",
  "city": "CityName",
  "persistDateTime": "2025-04-28T12:00:00",
  "linkSite": "https://site.com",
  "linkGitHub": "https://github.com/testuser",
  "linkVk": "https://vk.com/testuser",
  "about": "About user",
  "imageLink": "https://image.com/photo.jpg",
  "nickname": "testnickname"
}
```

🔁 Вы можете указать свой `email` и `password`.

---

### 🔐 Логин (Login)

**Метод:** `POST`  
**URL:** `http://localhost:8081/api/auth/login`  
**Body → raw → JSON:**
```json
{
  "email": "testuser@example.com",
  "password": "12345678"
}
```

**Ответ:**
```json
{
  "accountId": 3,
  "email": "test@example.com",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9........",
  "expiresIn": 86400,
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9........"
}
```

---

### ♻️ Обновление access-токена (Refresh)

**Метод:** `POST`  
**URL:** `http://localhost:8081/api/auth/refresh`  
**Body → raw → Text:**
```
"6e1ceadf-c3de-4...  // вставьте сюда ваш refresh-токен"
```

---

## 🔑 Авторизация защищённых запросов

1. Перейдите во вкладку **Authorization** в Postman.
2. Выберите `Type: Bearer Token`.
3. Вставьте `accessToken`, полученный при логине.
4. Теперь можно обращаться к защищённым эндпоинтам (например, `/api/user/me`).
5. Добавьте в application.yml в остальные сервисы
```yaml
jwt:
  secret: ${JWT_SECRET:RFa4dus-HnNCQ71rIJPFo1HJHl7qWSQuO3cyRE1elssv0Yct9KWUUz7lNiXLZrcbXWiEVLtZtqWqjil71vIwfw}
```
---

## 📌 Примечания

- Все пароли хранятся в зашифрованном виде (BCrypt).
- JWT токены действуют **1 час**.
- Refresh токены действуют **30 дней**.