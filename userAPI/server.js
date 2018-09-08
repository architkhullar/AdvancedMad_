var express = require('express'),
  app = express(),
  port = process.env.PORT || 3000;
  mongoose = require('mongoose'),
  User = require('./api/models/UserModel'),
  jsonwebtoken = require("jsonwebtoken");
  bodyParser = require('body-parser');

  mongoose.Promise = global.Promise;
  mongoose.connect('mongodb+srv://aMad:12019@cluster0-axmyu.mongodb.net/test?retryWrites=true');

  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(bodyParser.json());

  var routes = require('./api/routes/UserRoutes'); //importing route
  routes(app); //register the route

  app.listen(port);

console.log('todo list RESTful API server started on: ' + port);
