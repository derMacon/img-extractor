# src: https://dev.to/luizcalaca/dockerize-a-react-app-with-node-and-nginx-and-how-to-push-an-image-to-docker-hub-l9g
FROM node:20 as build-stage
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY ./ /app/
RUN npm run build

FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY --from=build-stage /app/build .
EXPOSE 80
# run nginx with global directives and daemon off
# in order to keep container running
ENTRYPOINT ["nginx", "-g", "daemon off;"]