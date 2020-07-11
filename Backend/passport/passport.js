var passport = require('passport');
var JWTStrategy = require('passport-jwt').Strategy


var config = require('../config')

passport.use(new JWTStrategy({
    jwtFromRequest: req => req.headers.token,
    secretOrKey: config.jwt.jwtKey
},(jwtPayload, done) => {
    return done(null, jwtPayload)
}))
