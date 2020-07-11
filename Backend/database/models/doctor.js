const Sequelize = require('sequelize');
const sequelize = require('../database');

const doctor = sequelize.define('doctor',{
    doctor_id:{
        type:Sequelize.BIGINT,
        primaryKey:true,
        allowNull:false,
        autoIncrement:true,
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

    //Verification
    doctor_name:{
        type:Sequelize.TEXT,
        allowNull:false,
    },
    registration_number:{
        type:Sequelize.TEXT,
        allowNull:false,
    },
    year_of_registration:{
        type:Sequelize.TEXT,
        allowNull:false,
    },
    state_medical_council:{
        type:Sequelize.TEXT,
        allowNull:false,
    },

    //Profile Photo
    photo_path:{
        type:Sequelize.TEXT,
        allowNull:true,
    },

    created_at: {
        type: Sequelize.DATE,
        allowNull: false,
        defaultValue: sequelize.literal('CURRENT_TIMESTAMP')
    },
    updated_at: Sequelize.DATE,
    deleted_at: Sequelize.DATE

})

module.exports = doctor;