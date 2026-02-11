-- 0) (선택) 기존꺼 있으면 정리하고 새로 시작
-- DROP DATABASE IF EXISTS malldb;
-- DROP USER IF EXISTS 'malldbuser'@'localhost';

-- 1) DB 생성 (utf8mb4 권장)
CREATE DATABASE IF NOT EXISTS malldb
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

-- 2) 사용자 생성 (로컬 접속용)
CREATE USER IF NOT EXISTS 'malldbuser'@'localhost'
  IDENTIFIED BY 'malldbuser';

-- 3) 권한 부여
GRANT ALL PRIVILEGES ON malldb.* TO 'malldbuser'@'localhost';

-- 4) 반영
FLUSH PRIVILEGES;
