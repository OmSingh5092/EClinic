const { OAuth2Client } = require('google-auth-library');
const jwt = require('jsonwebtoken');

const patientTable = require('../database/models/patient')
const doctorTable = require('../database/models/doctor')

const config = require('../config')

const patientSignIn = async (req,res)=>{
    const idToken = req.body.idToken;
    const client = new OAuth2Client(config.gcp.clientId);

    if(idToken == null){
        return res.json({
            success:false,
            message:"Token not found"
        })
    }
    
    const ticket = await client.verifyIdToken({
        idToken:idToken,
        audience:config.gcp.clientId,
    });

    const payload = ticket.getPayload();
    const userEmail = payload['email'];
    const userName = payload['name'];

    const user = await patientTable.findOne({
        where:{
            email:userEmail
        },
        attributes: ['patient_id', 'patient_name', 'email']
    })

    

    if(!user){
        var create_object = {
            email: userEmail,
            patient_name: userName
        };
        
        patientTable.create(create_object)
        .then(login_data => {
            // The payload of the auth-token
            console.log("LOGIN DATA: ", login_data)
            var auth_data = {
                email: login_data.email,
                id: login_data.id,
                created_at: login_data.created_at
            };
            // Create and assign an auth-token
            const TOKEN_SECRET = config.jwt.jwtKey;
            // var token = jwt.sign(auth_data, TOKEN_SECRET, { expiresIn: (amount of time for storing jwt token)})
            var token = jwt.sign(auth_data, TOKEN_SECRET)
            // console.log(login_data)
            // console.log('new user')
            // console.log(login_data.new_user);
            return res.status(200).json({
                success: true,
                authToken: token,
                newUser: true
            });
        })
        .catch (err => {
            console.log(err);
            return res.status(500).json({
                success: false,
                msg: 'Internal server error'
            });
            
        })
    }else{
        var auth_data = {
            email: user.email,
            id: user.id,
            created_at: user.created_at
        }
        // Create and assign an auth-token
        const TOKEN_SECRET = config.app.jwtKey
        var token = jwt.sign(auth_data, TOKEN_SECRET);
        // console.log(user)
        // console.log('Already exists.')
        // console.log(user.new_user);
        return res.status(200).json({
            success: true,
            authToken: token,
            newUser: false,
        });

    }
}

const doctorSignIn = async (req,res)=>{

    const idToken = req.body.idToken;
    const client = new OAuth2Client(config.gcp.clientId);

    if(idToken == null){
        return res.json({
            success:false,
            message:"Token not found"
        })
    }
    
    const ticket = await client.verifyIdToken({
        idToken:idToken,
        audience:config.gcp.clientId,
    });

    const payload = ticket.getPayload();
    const userEmail = payload['email'];
    const userName = payload['name'];

    const user = await doctorTable.findOne({
        where:{
            email:userEmail
        },
        attributes: ['patient_id', 'patient_name', 'email']
    })

    

    if(!user){
        var create_object = {
            email: userEmail,
            doctor_name: userName
        };
        
        patientTable.create(create_object)
        .then(login_data => {
            // The payload of the auth-token
            console.log("LOGIN DATA: ", login_data)
            var auth_data = {
                email: login_data.email,
                id: login_data.id,
                created_at: login_data.created_at
            };
            // Create and assign an auth-token
            const TOKEN_SECRET = config.jwt.jwtKey;
            // var token = jwt.sign(auth_data, TOKEN_SECRET, { expiresIn: (amount of time for storing jwt token)})
            var token = jwt.sign(auth_data, TOKEN_SECRET)
            // console.log(login_data)
            // console.log('new user')
            // console.log(login_data.new_user);
            return res.status(200).json({
                success: true,
                authToken: token,
                newUser: true
            });
        })
        .catch (err => {
            console.log(err);
            return res.status(500).json({
                success: false,
                msg: 'Internal server error'
            });
            
        })
    }else{
        var auth_data = {
            email: user.email,
            id: user.id,
            created_at: user.created_at
        }
        // Create and assign an auth-token
        const TOKEN_SECRET = config.app.jwtKey
        var token = jwt.sign(auth_data, TOKEN_SECRET);
        // console.log(user)
        // console.log('Already exists.')
        // console.log(user.new_user);
        return res.status(200).json({
            success: true,
            authToken: token,
            newUser: false,
        });

    }

}


module.exports.patientSignIn = patientSignIn;
module.exports.doctorSignIn  = doctorSignIn;