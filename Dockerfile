FROM maven:alpine
MAINTAINER  Alex Balenko <podorozhnick@gmail.com>
RUN apk add --update git
ADD ./ /var/www/public/
WORKDIR /var/www/public/
RUN mvn package -Dmaven.test.skip=true
RUN git clone https://github.com/BomberNastya/MoneyTracker web
EXPOSE 8915
CMD ["java","-jar","/var/www/public/target/moneytracker-0.1-SNAPSHOT.jar"]