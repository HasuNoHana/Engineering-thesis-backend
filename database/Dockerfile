FROM mysql:5.7.15

ENV MYSQL_ROOT_PASSWORD=dash42367vajdas3124SWW3

COPY setup.sh /mysql/setup.sh
COPY init.sql /mysql/init.sql
RUN chmod +x /mysql/setup.sh
RUN /mysql/setup.sh

EXPOSE 3306
