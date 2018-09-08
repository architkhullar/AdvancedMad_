'user strict';

var mongoose = require('mongoose'),
  jwt = require('jsonwebtoken'),
  user = mongoose.model('User');

  exports.register = function(req, res) {
  var newUser = new User(req.body);
  newUser.save(function(err, user) {
    if (err) {
      return res.status(400).send({
        message: err, status:'400'
      });
    } else {
      // var token = jwt.sign({username: newUser.username}, 'secretkey');
      // return res.json(token
      return res.json({token: jwt.sign({ username: newUser.username}, 'secretkey'), status:'200'});
      }
  });
};



exports.signin = function(req, res) {
  User.findOne({
    username: req.body.username,
    password: req.body.password
  }, function(err, user) {
    if (err) throw err;
    if (!user) {
      res.status(401).json({ message: 'Authentication failed. User not found.', status: '401' });
    } else if (user) {
        return res.json({token: jwt.sign({ username: user.username}, 'secretkey'), status:'200'});
    }
  });
};


exports.display = function(req, res) {
  console.log(req.headers.authorization);
  var newUsername = jwt.decode(req.headers.authorization);
  console.log(newUsername);
  var string = JSON.stringify(newUsername);
  var objectValue = JSON.parse(string);
  var getuser = objectValue['username'];

  User.findOne({
    username: getuser
  }, function(err, user) {
    if (err) throw err;
    if (!user) {
      res.status(401).json({ message: 'Authentication failed. User not found.', status: '401' });
    } else if (user) {
        return res.json(user);
    }
  });
};



exports.edit = function(req, res) {
  console.log(req.headers.authorization);
  var newUsername = jwt.decode(req.headers.authorization);
  console.log(newUsername);
  var string = JSON.stringify(newUsername);
  var objectValue = JSON.parse(string);
  var getuser = objectValue['username'];
  User.findOneAndUpdate({
    username: getuser
  },
  req.body,
  {new:true},
  function(err, user) {
    if (err) throw err;
    if (!user) {
      res.status(401).json({ message: 'Authentication failed. User not found.', status: '401' });
    } else if (user) {
        return res.json({ message: 'User details updated successfully', status: '200' });
    }
  });
};
