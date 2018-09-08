'use strict';
module.exports = function(app) {
  var userHandlers = require('../controllers/UserController.js');


app.route('/register')
    .post(userHandlers.register);

app.route('/signin')
    .post(userHandlers.signin);

app.route('/display')
    .post(userHandlers.display);

app.route('/edit')
    .post(userHandlers.edit);

};
