FROM gradle:6.1.1-jdk11
RUN apt-get update
RUN apt-get install -qq default-mysql-client
RUN mkdir -p /app/reports && chown gradle:gradle /app/reports
COPY --chown=gradle:gradle . /app
RUN rm -r /app/gradle ||:
RUN rm -r /app/reports ||:
RUN rm -r /app/cache ||:
WORKDIR /app
