'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
  name: {
    type: String,
    required: 'Kindly enter the name of the user'
  },
  age: {
    type: String,
    required: 'Kindly enter the age of the user'
  },
  weight: {
    type: String,
    required: 'Kindly enter the weight of the user'
  },
  address: {
    type: String,
    required: 'Kindly enter the address of the user'
  },
  username: {
    type: String,
    required: 'Kindly enter the username of the user'
  },
  password: {
    type: String,
    required: 'Kindly enter the password of the user'
  },
});


module.exports = mongoose.model('User', UserSchema);
