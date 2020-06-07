# Build the package
FROM node:14.4.0 as build

COPY . /app

WORKDIR /app

RUN npm install

RUN npm install -g @angular/cli@9.1.7

EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]