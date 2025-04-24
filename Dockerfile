# ---------- 1. build된 jar파일이 있는 경우 ----------------------
## alpine에서 제공하는 베이스 이미지를 사용할거야
#FROM eclipse-temurin:17-jre-alpine
##app 폴더에서 작업을 수행할꺼야(linux의 폴더 구조)
#WORKDIR /app
## Dockerfile이 있는 프로젝트 내에서 jar파일이 있는 상태로 만들거라 해당 위치에 있는 jar파일들을 현재 위치 즉, app폴더(./)로 복사해 올거야
#COPY build/libs/*.jar ./
## 그리고 그 jar 파일 중에 plain이란 글자가 없는 것을 app.jar로 이름을 바꿀거야
#RUN mv $(ls *.jar | grep -v plain) app.jar
## java -jar .\app.jar 을 실행하기 위한 명령어 인자를 전달할거야
#ENTRYPOINT ["java", "-jar", "app.jar"]
# --------------------------------------------------------

# ---------- 2. build 후 jar 파일로 실행되게 수정 --------
# ---------- 2-1. gradle 이미지로 build(jar 생성) -------
FROM gradle:8.5-jdk17-alpine AS build
# 컨테이너 이미지 내부의 작업 디렉토리 설정(/app에서 작업을 하겠다)
WORKDIR /app
# 호스트(local)의 현재 디렉토리(Dockerfile이 존재하는 프로젝트의 위치)의 모든 파일을
# 현재 위치(컨테이너 이미지 내부의 현재 작업 디렉토리, 즉 위에서 설정한 WORKDIR /app)으로 복사
COPY . .
# FROM에서 설정한 gradle(8.5-jdk17-alpine)을 사용하여 build
# --no-daemon: Gradle을 데몬 모드 없이 실행
               #즉, 빌드 후 백그라운드에 Gradle 프로세스를 남기지 않고 바로 종료
               #→ Docker 빌드에서는 권장되는 옵션(1회성이기 때문에 리소스를 남기지 않고 명시적으로 데몬을 꺼준다.)
# daemon 스레드를 쓰지 않음으로써 쓸데 없이 리소스가 소모되는 것을 방지하는 코드로 작성
# 빌드 시 테스트 코드를 하지 않을 거라면(-x test) 옵션을 설정 - 안정성은 떨어짐
RUN gradle clean build --no-daemon -x test
# -----------------------------------------------
# ------- 2-2. 앞선 build라는 이름의 스테이지 결과로 실행 스테이지 시작 ------------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# 위 스테이지의 build 단계의 이미지(결과로부터) 파일을 복사
COPY --from=build /app/build/libs/*.jar ./
RUN mv $(ls *.jar | grep -v plain) app.jar
# 컨테이너 내부에서 app.jar가 실행 됨
ENTRYPOINT ["java", "-jar", "app.jar"]