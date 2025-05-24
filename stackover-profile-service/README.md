# StackOver - Profile-Service
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
  DB_NAME=stackover_profile_service
  DB_SCHEMA=public
  HIBERNATE_DDL_CREATE=true
  DB_USERNAME=ваш_логин
  DB_PASSWORD=ваш_пароль
  ```  
- Нажмите Apply → OK

==========================================

