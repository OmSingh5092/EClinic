const Sequelize = require('sequelize');
const sequelize = require('../database');

const patient = sequelize.define('patient',{
    patient_id:{
        type:Sequelize.BIGINT,
        primaryKey:true,
        allowNull:false,
        autoIncrement:true,
    },
    //Name
    patient_name:{
        type:Sequelize.TEXT,
        allowNull:false,
    },

    //Contact Details
    phone_number:{
        type:Sequelize.TEXT,
        allowNull:false,
    },
    email:{
        type:Sequelize.TEXT,
        allowNull:false
    },

    //Medical Details
    blood:{
        type:Sequelize.TEXT,
        allowNull:true
    },
    gender:{
        type:Sequelize.TEXT,
        allowNull:true,
    },
    weight:{
        type:Sequelize.INTEGER,
        allowNull:true,
    },

    //Profile Photo
    photo_path:{
        type:Sequelize.TEXT,
        allowNull:true,
    }

})

module.exports = patient;