# Build the package
FROM node:14.4.0 as build

COPY . /app

WORKDIR /app

RUN ls

RUN npm install -g @angular/cli@9.1.7

RUN npm intall

EXPOSE 4200

CMD ["ng", "serve"]