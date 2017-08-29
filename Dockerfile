FROM maven
MAINTAINER  Alex Balenko <podorozhnick@gmail.com>
ADD ./ /var/www/public/
WORKDIR /var/www/public/
RUN mvn package
RUN git clone https://github.com/BomberNastya/MoneyTracker web
EXPOSE 8915
CMD ["java","-jar","/var/www/public/target/moneytracker-0.1-SNAPSHOT.jar"]