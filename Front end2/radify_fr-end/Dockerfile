#download a base image
FROM node:lts-alpine3.14

#make the 'app' folder the current working directory
WORKDIR /app

#copy both json files (if available)
COPY package*.json ./

#install rpoject dependencies
RUN npm install

#copy project files and folders to the current directory
COPY . .

#open the port on which the server will start
EXPOSE 3000

#run the front end server
CMD ["npm", "start"]
